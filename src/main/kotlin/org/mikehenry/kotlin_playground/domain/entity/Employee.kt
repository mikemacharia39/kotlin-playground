package org.mikehenry.kotlin_playground.domain.entity

import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import javax.persistence.*

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