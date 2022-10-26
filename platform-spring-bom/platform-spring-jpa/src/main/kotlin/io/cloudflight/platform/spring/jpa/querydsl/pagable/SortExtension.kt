package io.cloudflight.platform.spring.jpa.querydsl.pagable

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.OrderSpecifier.NullHandling
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.PathBuilderFactory
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Sort
import org.springframework.data.mapping.PropertyPath
import org.springframework.data.querydsl.QSort
import kotlin.reflect.KClass

object SortExtension {
    fun Sort.toQSort(clazz: KClass<Any>): QSort {
        return QSort(addOrderByFrom<Any>(this, clazz))
    }

    /**
     * Converts the [Order] items of the given [Sort] into [OrderSpecifier] and attaches those to the
     * given [JPQLQuery].
     *
     * @param sort must not be null.
     * @param query must not be null.
     * @return
     */
    private fun <T> addOrderByFrom(sort: Sort, clazz: KClass<Any>): List<OrderSpecifier<*>> {
        return sort.map { toOrderSpecifier(it, clazz) }.toList()
    }

    /**
     * Transforms a plain [Order] into a QueryDsl specific [OrderSpecifier].
     *
     * @param order must not be null.
     * @return
     */
    private fun toOrderSpecifier(order: Sort.Order, clazz: KClass<Any>): OrderSpecifier<*> {
        return OrderSpecifier(
            if (order.isAscending) Order.ASC else Order.DESC,
            buildOrderPropertyPathFrom(order, clazz),
            toQueryDslNullHandling(order.nullHandling),
        )
    }

    /**
     * Converts the given [org.springframework.data.domain.Sort.NullHandling] to the appropriate Querydsl
     * [NullHandling].
     *
     * @param nullHandling must not be null.
     * @return
     * @since 1.6
     */
    private fun toQueryDslNullHandling(nullHandling: Sort.NullHandling?): NullHandling {
        return when (nullHandling) {
            Sort.NullHandling.NULLS_FIRST -> NullHandling.NullsFirst
            Sort.NullHandling.NULLS_LAST -> NullHandling.NullsLast
            Sort.NullHandling.NATIVE -> NullHandling.Default
            else -> NullHandling.Default
        }
    }

    /**
     * Creates an [Expression] for the given [Order] property.
     *
     * @param order must not be null.
     * @return
     */
    private fun buildOrderPropertyPathFrom(order: Sort.Order, clazz: KClass<out Comparable<*>>): Expression<out Comparable<*>> {
        val builder = PathBuilderFactory().create(clazz.java)

        var path: PropertyPath? = PropertyPath.from(order.property, builder.type)
        var sortPropertyExpression: Expression<out Comparable<*>> = builder

        while (path?.equals(null) != null) {

            sortPropertyExpression = if (!path.hasNext() && order.isIgnoreCase && String::class.java == path.type) {
                Expressions.stringPath(sortPropertyExpression as Path<*>?, path.segment).lower()
            } else {
                Expressions.path<Comparable<*>>(path.type, sortPropertyExpression as Path<*>?, path.segment)
            }
            path = path.next()
        }

        return sortPropertyExpression
    }
}



