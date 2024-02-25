package org.mikehenry.kotlin_playground.domain.specification

import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate
import org.mikehenry.kotlin_playground.domain.entity.Address
import org.mikehenry.kotlin_playground.domain.entity.Department
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.springframework.data.jpa.domain.Specification

fun searchEmployee(
    employeeName: String?,
    employeeIds: List<Long>?,
    phoneNumber: String?,
    departmentName: String?,
    address: String?,
): Specification<Employee> {
    return Specification { root, _, criteriaBuilder ->
        val predicates = mutableListOf<Predicate>()
        if (employeeName != null) {
            predicates.add(
                criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstName"), "%$employeeName%"),
                    criteriaBuilder.like(root.get("lastName"), "%$employeeName%")
                )
            )
        }
        if (employeeIds != null) {
            predicates.add(root.get<Long>("employeeId").`in`(employeeIds))
        }
        if (phoneNumber != null) {
            predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%$phoneNumber%"))
        }
        if (departmentName != null) {
            val departmentJoin: Join<Employee, Department> = root.join("department", JoinType.INNER)
            predicates.add(criteriaBuilder.like(departmentJoin.get("departmentName"), "%$departmentName%"))
        }
        if (address != null) {
            val addressJoin: Join<Employee, Address> = root.join("addresses", JoinType.INNER)
            predicates.add(
                criteriaBuilder.or(
                    criteriaBuilder.like(addressJoin.get("addressLine1"), "%$address%"),
                    criteriaBuilder.like(addressJoin.get("addressLine2"), "%$address%"),
                    criteriaBuilder.like(addressJoin.get("city"), "%$address%"),
                    criteriaBuilder.like(addressJoin.get("state"), "%$address%"),
                    criteriaBuilder.like(addressJoin.get("country"), "%$address%"),
                    criteriaBuilder.like(addressJoin.get("postalBox"), "%$address%")
                )
            )
        }
        criteriaBuilder.and(*predicates.toTypedArray())
    }
}
