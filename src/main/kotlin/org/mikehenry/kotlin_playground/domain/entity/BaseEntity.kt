package org.mikehenry.kotlin_playground.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    val id: Long = 0,

    @Column(name = "date_created", updatable = false)
    @CreatedDate
    var dateCreated: LocalDateTime = LocalDateTime.now(),

    @Column(name = "date_modified")
    @LastModifiedBy
    var dateModified: LocalDateTime = LocalDateTime.now()
) {
}