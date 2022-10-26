package io.cloudflight.platform.spring.jpa.querydsl.pagable

import io.cloudflight.platform.spring.jpa.querydsl.pagable.SortExtension.toQSort
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.querydsl.QSort
import kotlin.reflect.KClass

class Pageable(
    private val pageable: Pageable,
    private val qSort: QSort,
) : Pageable by pageable {

    companion object {
        fun of(pageable: Pageable, clazz: KClass<Any>): io.cloudflight.platform.spring.jpa.querydsl.pagable.Pageable {
            return Pageable(pageable, pageable.sort.toQSort(clazz))
        }
    }

    override fun getSort(): Sort {
        return qSort
    }

    fun getQSort(): QSort {
        return qSort
    }

}
