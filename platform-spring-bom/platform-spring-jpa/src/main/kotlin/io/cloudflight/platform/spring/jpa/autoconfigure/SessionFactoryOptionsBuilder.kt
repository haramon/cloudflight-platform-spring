package io.cloudflight.platform.spring.jpa.autoconfigure

import org.hibernate.boot.internal.SessionFactoryOptionsBuilder
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.spi.BootstrapContext
import org.slf4j.LoggerFactory

internal class SessionFactoryOptionsBuilder(
    serviceRegistry: StandardServiceRegistry,
    context: BootstrapContext,
    private val properties: JpaConfigurationProperties,
) : SessionFactoryOptionsBuilder(serviceRegistry, context) {

    init {
        if (!isFailOnPaginationOverCollectionFetchEnabled) {
            LOG.warn(
                """
                failOnPaginationOverCollectionFetchEnabled is disabled. This can lead to significant performance issues
                whenever you use pagination and SQL joins to retrieve entities and their associations to prevent the
                N+1 select queries problem. Also see https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/internal/CoreMessageLogger.html#firstOrMaxResultsSpecifiedWithCollectionFetch--
                """.trimIndent()
            )
        }
    }

    override fun isFailOnPaginationOverCollectionFetchEnabled(): Boolean {
        return properties.failOnInMemoryPaginationOverCollectionFetch
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(SessionFactoryOptionsBuilder::class.java)
    }
}