package io.cloudflight.platform.spring.jpa.entity

import java.time.OffsetDateTime
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
internal class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    val createdDate: OffsetDateTime = OffsetDateTime.now()
}