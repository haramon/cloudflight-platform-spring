package io.cloudflight.platform.spring.jpa.autoconfigure

import io.cloudflight.platform.spring.jpa.querydsl.JPQLQueryFactory
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable

@SpringBootTest(classes = [AutoConfigureTestApplication::class])
internal class SpringBootTest @Autowired constructor(
    private val jpqlQueryFactory: JPQLQueryFactory
) {

    @Test
    fun test() {
        Assertions.assertThat(true).isTrue()
    }
}