package org.mikehenry.kotlin_playground.mock

import io.github.serpro69.kfaker.Faker
import org.mikehenry.kotlin_playground.api.dto.request.AddressRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.DepartmentRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.domain.entity.Address
import org.mikehenry.kotlin_playground.domain.entity.Department
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.mikehenry.kotlin_playground.domain.enumeration.AddressType
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeStatus
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import java.time.LocalDate

val faker = Faker()

fun mockEmployee(): Employee =
    Employee(
        firstName = faker.name.firstName(),
        lastName = faker.name.lastName(),
        emailAddress = faker.internet.email(),
        phoneNumber = faker.phoneNumber.phoneNumber(),
        employeeType = EmployeeType.FULL_TIME,
        employeeStatus = EmployeeStatus.ACTIVE,
        department = Department(
            departmentName = "Science",
            departmentDescription = "Science Department"
        ),
        addresses = listOf(mockAddress())
    )

fun mockAddress(): Address =
    Address(
        addressType = AddressType.HOME,
        addressLine1 = faker.address.streetAddress(),
        addressLine2 = "",
        country = faker.address.country(),
        city = faker.address.city(),
        state = "",
        postalBox = faker.address.postcode()
    )

fun mockEmployeeRequest(): EmployeeRequestDto =
    EmployeeRequestDto(
        firstName = faker.name.firstName(),
        lastName = "Henry",
        dateOfBirth = LocalDate.of(1990, 10, 24),
        emailAddress = faker.internet.email(),
        addresses = listOf(
            AddressRequestDto(
                AddressType.HOME,
                addressLine1 = "Home",
                addressLine2 = "",
                country = "Kenya",
                city = "Naivasha",
                state = "",
                postalBox = "Box"
            )
        ),
        phoneNumber = "07101988888",
        department = DepartmentRequestDto("SCIENCE")
    )