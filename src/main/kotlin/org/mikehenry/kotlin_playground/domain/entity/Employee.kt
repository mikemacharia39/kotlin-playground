package org.mikehenry.kotlin_playground.domain.entity

import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "employee")
class Employee(

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false)
    val emailAddress: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    val employeeType: EmployeeType
):BaseEntity() {

}