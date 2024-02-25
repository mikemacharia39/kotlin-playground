package org.mikehenry.kotlin_playground.domain.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

import org.mikehenry.kotlin_playground.configuration.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@NoArg
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    val id: Long = 0,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "date_created", updatable = false)
    @CreatedDate
    var dateCreated: LocalDateTime = LocalDateTime.now(),

    @Column(name = "date_modified")
    @LastModifiedBy
    var dateModified: LocalDateTime = LocalDateTime.now()
) {
}