package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.types.EntityPath
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Pageable

interface JPQLQueryFactory : com.querydsl.jpa.JPQLQueryFactory {

    fun from(from: EntityPath<*>, pageable: Pageable): JPQLQuery<*>
}
