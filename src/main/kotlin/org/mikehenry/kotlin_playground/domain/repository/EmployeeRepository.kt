package org.mikehenry.kotlin_playground.domain.repository

import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository: JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
}