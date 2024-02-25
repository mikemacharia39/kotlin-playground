package org.mikehenry.kotlin_playground.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "department")
class Department(
    @Column(nullable = false)
    val departmentName: String,
    val departmentDescription: String? = null
) : BaseEntity() {
}