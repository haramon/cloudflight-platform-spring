package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.types.dsl.PathBuilderFactory
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.Querydsl
import javax.persistence.EntityManager

internal class JPQLPageableQuery<T>(
    private val jpqlQuery: JPQLQuery<T>,
    private val entityManager: EntityManager,
) : JPQLQuery<T> by jpqlQuery {

    /**
     * Applies the given [pageable] to the [jpqlQuery].
     *
     * @param pageable
     * @return the Querydsl {@link JPQLQuery}.
     */
    fun applyPagination(pageable: Pageable, domainClass: Class<*>): JPQLQuery<T> {
        val pathBuilder = PathBuilderFactory().create(domainClass)
        val queryDsl = Querydsl(entityManager, pathBuilder)

        return queryDsl.applyPagination(pageable, this)
    }
}
