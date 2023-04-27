package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.core.types.EntityPath
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface JPQLQueryFactory : com.querydsl.jpa.JPQLQueryFactory {

    fun from(from: EntityPath<*>, pageable: Pageable): JPQLQuery<*>

    fun from(from: EntityPath<*>, sort: Sort): JPQLQuery<*>
}
