package com.netflix.spinnaker.clouddriver.cloudfoundry.security

import com.netflix.spinnaker.cats.module.CatsModule
import com.netflix.spinnaker.cats.provider.ProviderSynchronizerTypeWrapper
import com.netflix.spinnaker.clouddriver.cloudfoundry.config.CloudFoundryConfigurationProperties
import com.netflix.spinnaker.clouddriver.security.AccountCredentialsRepository
import com.netflix.spinnaker.clouddriver.security.CredentialsInitializerSynchronizable
import com.netflix.spinnaker.clouddriver.security.ProviderUtils
import com.sun.tools.javac.comp.Todo
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

//TODO write tests for implementation
@Configuration
class CloudFoundryCredentialsInitializer implements CredentialsInitializerSynchronizable {

  @Bean
  List<? extends CloudFoundryAccountCredentials> cloudFoundryAccountCredentials(CloudFoundryConfigurationProperties cloudFoundryConfigurationProperties,
                                                                                AccountCredentialsRepository accountCredentialsRepository,
                                                                                ApplicationContext applicationContext,
                                                                                List<ProviderSynchronizerTypeWrapper> providerSynchronizerTypeWrappers) {
    synchronizeCloudFoundryAccounts(cloudFoundryConfigurationProperties, accountCredentialsRepository, null, applicationContext, providerSynchronizerTypeWrappers)
  }

  @Override
  String getCredentialsSynchronizationBeanName() {
    return  "synchronizeCloudFoundryAccounts"
  }

  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  @Bean
  List<? extends CloudFoundryAccountCredentials> synchronizeCloudFoundryAccounts(CloudFoundryConfigurationProperties cloudFoundryConfigurationProperties,
                                                                                 AccountCredentialsRepository accountCredentialsRepository,
                                                                                 CatsModule catsModule,
                                                                                 ApplicationContext applicationContext,
                                                                                 List<ProviderSynchronizerTypeWrapper> providerSynchronizerTypeWrappers) {
    def (ArrayList<CloudFoundryAccountCredentials> accountsToAdd, List<String> namesOfDeletedAccounts) =
    ProviderUtils.calculateAccountDeltas(accountCredentialsRepository, CloudFoundryAccountCredentials,
      cloudFoundryConfigurationProperties.accounts)

    accountsToAdd.each { CloudFoundryConfigurationProperties.ManagedAccount managedAccount ->
      def cloudFoundryAccountCreditentials = new CloudFoundryAccountCredentials(
        managedAccount.user,
        managedAccount.password,
        managedAccount.name
      )
      accountCredentialsRepository.save(managedAccount.name, cloudFoundryAccountCreditentials)
    }

    ProviderUtils.unscheduleAndDeregisterAgents(namesOfDeletedAccounts, catsModule)

    if (accountsToAdd && catsModule) {
      ProviderUtils.synchronizeAgentProviders(applicationContext, providerSynchronizerTypeWrappers)
    }

    accountCredentialsRepository.all.findAll {
      it instanceof CloudFoundryAccountCredentials
    } as List<CloudFoundryAccountCredentials>
  }
}
