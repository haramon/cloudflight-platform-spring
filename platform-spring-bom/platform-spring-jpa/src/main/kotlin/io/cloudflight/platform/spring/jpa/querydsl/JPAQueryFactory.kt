package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.types.EntityPath
import com.querydsl.jpa.JPQLQuery
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

internal class JPAQueryFactory(
    private val jpqlQueryFactory: com.querydsl.jpa.JPQLQueryFactory,
    private val entityManager: EntityManager,
) : com.querydsl.jpa.JPQLQueryFactory by jpqlQueryFactory, JPQLQueryFactory {

    override fun from(from: EntityPath<*>, pageable: Pageable): JPQLQuery<*> {
        val domainClass = from.annotatedElement as Class<*>
        return JPQLPageableQuery(this.from(from), entityManager).applyPagination(pageable, domainClass)
    }

    override fun from(from: EntityPath<*>, sort: Sort): JPQLQuery<*> {
        val domainClass = from.annotatedElement as Class<*>
        return JPQLSortableQuery(this.from(from), entityManager).applySorting(sort, domainClass)
    }
}