package io.cloudflight.platform.spring.jpa.querydsl

import com.querydsl.jpa.JPQLQuery
import io.cloudflight.platform.spring.jpa.querydsl.pagable.Pageable
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

class JPQLPageableQuery<T>(
    private val jpqlQuery: JPQLQuery<T>,
    private val pageable: Pageable,
) : JPQLQuery<T> by jpqlQuery {

    init {
        if (pageable.isUnpaged) {
            LOG.debug("Pageable is unpaged, pagination is not applied")
        } else {
            this.applySorting()
                .applyPagination()
        }
    }

    fun fetchPage(): Page<T> {
        val queryResults = this.fetchResults()

        return PageImpl(queryResults.results, pageable, queryResults.total)
    }

    private fun <T> JPQLQuery<T>.applySorting(): JPQLQuery<T> {
        val sort = pageable.getQSort()
        return if (sort.isSorted) {
            val orderSpecifiers = sort.orderSpecifiers
            this.orderBy(*orderSpecifiers.toTypedArray())
        } else {
            this
        }
    }

    private fun <T> JPQLQuery<T>.applyPagination(): JPQLQuery<T> {
        return if (pageable.isPaged) {
            this.offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
        } else {
            this
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(JPQLPageableQuery::class.java)
    }
}
