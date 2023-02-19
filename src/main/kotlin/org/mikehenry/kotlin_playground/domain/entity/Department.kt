package org.mikehenry.kotlin_playground.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "department")
class Department(
    @Column(nullable = false)
    val departmentName: String,
    val departmentDescription: String? = null
) : BaseEntity() {
}