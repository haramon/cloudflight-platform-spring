package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.types.EntityPath
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager

internal class JPAQueryFactory(
    private val jpaQueryFactory: com.querydsl.jpa.JPQLQueryFactory,
    private val entityManager: EntityManager,
) : com.querydsl.jpa.JPQLQueryFactory by jpaQueryFactory, JPQLQueryFactory {

    override fun from(from: EntityPath<*>, pageable: Pageable): JPQLQuery<*> {
        val domainClass = from.annotatedElement as Class<*>
        return this.from(from).applyPagination(pageable, domainClass)
    }

    private fun <T> JPQLQuery<T>.applyPagination(pageable: Pageable, domainClass: Class<*>): JPQLQuery<T> {
        return JPQLPageableQuery(this, entityManager).applyPagination(pageable, domainClass)
    }
}