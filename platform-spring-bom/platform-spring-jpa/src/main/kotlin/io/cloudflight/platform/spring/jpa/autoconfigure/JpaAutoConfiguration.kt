package io.cloudflight.platform.spring.jpa.autoconfigure

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.spi.BootstrapContext
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.EnableTransactionManagement

@AutoConfiguration
@EnableTransactionManagement(order = 2000)
@EnableConfigurationProperties(TransactionProperties::class, JpaConfigurationProperties::class)
class JpaAutoConfiguration {

    @Bean
    fun platformTransactionCustomizer(properties: TransactionProperties): TransactionCustomizer {
        return TransactionCustomizer(properties)
    }

    @Bean
    internal fun sessionFactoryOptionsBuilder(
        serviceRegistry: StandardServiceRegistry,
        context: BootstrapContext,
        properties: JpaConfigurationProperties,
    ): SessionFactoryOptionsBuilder {
        return SessionFactoryOptionsBuilder(
            serviceRegistry = serviceRegistry,
            context = context,
            properties = properties,
        )
    }
}
