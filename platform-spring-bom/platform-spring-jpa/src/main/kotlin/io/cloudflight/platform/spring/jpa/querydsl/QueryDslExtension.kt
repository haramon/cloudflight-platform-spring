package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.FetchableQuery
import com.querydsl.core.types.dsl.Expressions

object QueryDslExtension {
    fun <T, Q : FetchableQuery<T, Q>> FetchableQuery<T, Q>.fetchExists(): Boolean {
        return this.select(Expressions.ONE).fetchFirst() == 1
    }
}