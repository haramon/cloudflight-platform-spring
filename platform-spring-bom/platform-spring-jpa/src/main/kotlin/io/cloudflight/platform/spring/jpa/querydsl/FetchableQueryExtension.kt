package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.FetchableQuery
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPQLQuery
import io.cloudflight.platform.spring.jpa.querydsl.pagable.Pageable

object FetchableQueryExtension {
    fun <T, Q : FetchableQuery<T, Q>> FetchableQuery<T, Q>.fetchExists(): Boolean {
        return this.select(Expressions.ONE).fetchFirst() == 1
    }

    fun <T> JPQLQuery<T>.applyPagination(pageable: Pageable): JPQLPageableQuery<T> {
        return JPQLPageableQuery(this, pageable)
    }
}