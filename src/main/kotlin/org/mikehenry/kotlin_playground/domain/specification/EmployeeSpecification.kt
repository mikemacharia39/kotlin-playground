package org.mikehenry.kotlin_playground.domain.specification

import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate
import org.mikehenry.kotlin_playground.api.dto.params.EmployeeSearchParams
import org.mikehenry.kotlin_playground.domain.entity.Address
import org.mikehenry.kotlin_playground.domain.entity.Department
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.springframework.data.jpa.domain.Specification

fun searchEmployee(
    employeeSearchParams: EmployeeSearchParams
): Specification<Employee> {
    return Specification { root, _, criteriaBuilder ->
        val predicates = mutableListOf<Predicate>()
        if (employeeSearchParams.employeeName != null) {
            predicates.add(
                criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstName"), "%${employeeSearchParams.employeeName}%"),
                    criteriaBuilder.like(root.get("lastName"), "%${employeeSearchParams.employeeName}%")
                )
            )
        }
        if (employeeSearchParams.employeeIds != null) {
            predicates.add(root.get<Long>("employeeId").`in`(employeeSearchParams.employeeIds))
        }
        if (employeeSearchParams.phoneNumber != null) {
            predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%${employeeSearchParams.phoneNumber}%"))
        }
        if (employeeSearchParams.departmentName != null) {
            val departmentJoin: Join<Employee, Department> = root.join("department", JoinType.INNER)
            predicates.add(criteriaBuilder.like(departmentJoin.get("departmentName"), "%${employeeSearchParams.departmentName}%"))
        }
        if (employeeSearchParams.address != null) {
            val addressJoin: Join<Employee, Address> = root.join("addresses", JoinType.INNER)
            predicates.add(
                criteriaBuilder.or(
                    criteriaBuilder.like(addressJoin.get("addressLine1"), "%${employeeSearchParams.address}%"),
                    criteriaBuilder.like(addressJoin.get("addressLine2"), "%${employeeSearchParams.address}%"),
                    criteriaBuilder.like(addressJoin.get("city"), "%${employeeSearchParams.address}%"),
                    criteriaBuilder.like(addressJoin.get("state"), "%${employeeSearchParams.address}%"),
                    criteriaBuilder.like(addressJoin.get("country"), "%${employeeSearchParams.address}%"),
                    criteriaBuilder.like(addressJoin.get("postalBox"), "%${employeeSearchParams.address}%")
                )
            )
        }
        criteriaBuilder.and(*predicates.toTypedArray())
    }
}
