package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.types.dsl.PathBuilderFactory
import com.querydsl.jpa.JPQLQuery
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.Querydsl

internal class JPQLSortableQuery<T>(
    private val jpqlQuery: JPQLQuery<T>,
    private val entityManager: EntityManager,
) : JPQLQuery<T> by jpqlQuery {

    /**
     * Applies the given [sort] to the [jpqlQuery].
     *
     * @param sort
     * @return the JPQLQuery {@link JPQLQuery}.
     */
    fun applySorting(sort: Sort, domainClass: Class<*>): JPQLQuery<T> {
        val pathBuilder = PathBuilderFactory().create(domainClass)
        val queryDsl = Querydsl(entityManager, pathBuilder)

        return queryDsl.applySorting(sort, this)
    }
}
