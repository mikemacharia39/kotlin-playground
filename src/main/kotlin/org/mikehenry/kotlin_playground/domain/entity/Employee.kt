package org.mikehenry.kotlin_playground.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.CascadeType.MERGE
import jakarta.persistence.CascadeType.PERSIST
import jakarta.persistence.Table
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeStatus
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType

@Entity
@Table(name = "employee")
class Employee(
    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false)
    val emailAddress: String,

    @Column(nullable = false)
    val phoneNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    val employeeType: EmployeeType = EmployeeType.FULL_TIME,

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", nullable = false)
    val employeeStatus: EmployeeStatus = EmployeeStatus.ACTIVE,

    @OneToOne(cascade = [PERSIST, MERGE])
    @JoinTable(
        name = "employee_department",
        joinColumns = [JoinColumn(name = "employee_id")],
        inverseJoinColumns = [JoinColumn(name = "department_id")]
    )
    val department: Department,

    @OneToMany(cascade = [PERSIST, MERGE])
    @JoinTable(
        name = "employee_address",
        joinColumns = [JoinColumn(name = "employee_id")],
        inverseJoinColumns = [JoinColumn(name = "address_id")]
    )
    val addresses: List<Address> = emptyList()
) : BaseEntity() {
}