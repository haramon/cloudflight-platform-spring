package io.cloudflight.platform.spring.jpa.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("cloudflight.spring.jpa")
class JpaConfigurationProperties(

    /**
     * Raises an exception when in-memory pagination over collection fetch is about to be performed.
     * Disabled by default. Set to true to enable.
     *
     * @see [org.hibernate.cfg.AvailableSettings.FAIL_ON_PAGINATION_OVER_COLLECTION_FETCH]
     */
    val failOnInMemoryPaginationOverCollectionFetch: Boolean = false
)