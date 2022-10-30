package io.cloudflight.platform.spring.jpa.autoconfigure

import com.querydsl.jpa.JPQLQueryFactory
import com.querydsl.jpa.JPQLTemplates
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.AutoConfiguration
import io.cloudflight.platform.spring.jpa.querydsl.JPAQueryFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean

@AutoConfiguration
@ConditionalOnClass(value = [JPQLQueryFactory::class])
class QueryDslAutoConfiguration {

    @Bean
    fun jpaQueryFactory(entityManager: EntityManager): JPQLQueryFactory {
        return JPAQueryFactory(
            // https://github.com/querydsl/querydsl/issues/3428
            com.querydsl.jpa.impl.JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager),
            entityManager,
        )
    }
}