package org.mikehenry.kotlin_playground.domain.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.mikehenry.kotlin_playground.domain.entity.Employee
import org.mikehenry.kotlin_playground.domain.mapper.AddressMapperImpl
import org.mikehenry.kotlin_playground.domain.mapper.DepartmentMapperImpl
import org.mikehenry.kotlin_playground.domain.mapper.EmployeeMapperImpl
import org.mikehenry.kotlin_playground.domain.repository.EmployeeRepository
import org.mikehenry.kotlin_playground.mock.mockEmployee
import org.mikehenry.kotlin_playground.mock.mockEmployeeRequest
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.Stack

/**
 * https://kotest.io/docs/framework/testing-styles.html
 */
class EmployeeServiceKOTest : BehaviorSpec({

    lateinit var employeeRepository: EmployeeRepository
    lateinit var employeeMapper: EmployeeMapperImpl
    lateinit var addressMapper: AddressMapperImpl
    lateinit var departmentMapper: DepartmentMapperImpl
    lateinit var employeeService: EmployeeService
    lateinit var s3Service: S3Service

    val testBucket = "employee-documents"

    beforeAny {
        departmentMapper = DepartmentMapperImpl()
        addressMapper = AddressMapperImpl()
        employeeMapper = EmployeeMapperImpl(addressMapper, departmentMapper)
        employeeRepository = mock(EmployeeRepository::class.java)
        s3Service = mock(S3Service::class.java)
        employeeService = EmployeeService(employeeMapper, employeeRepository, s3Service, testBucket)
    }

    given("an employee service") {
        `when`("an employee is added") {
            `when`(employeeRepository.save(any(Employee::class.java))).thenReturn(mockEmployee())
            val response = employeeService.addEmployee(mockEmployeeRequest())

            then("the response should not be null") {
                response shouldNotBe null
            }
            then("the response should have a department") {
                response.department.departmentName shouldNotBe null
            }
            then("the response should have an address") {
                response.addresses.size shouldBe 1
            }
        }
    }
})

class StackTest : BehaviorSpec() {
    init {
        given("a stack") {
            val stack = Stack<String>()
            `when`("an item is pushed") {
                stack.push("kotlin")
                then("the stack should not be empty") {
                    stack.isEmpty() shouldBe false
                }
            }
            `when`("the stack is popped") {
                stack.pop()
                then("it should be empty") {
                    stack.isEmpty() shouldBe true
                }
            }
        }
    }
}

class SampleStringTests : StringSpec({
    "strings.length should return size of string" {
        "hello".length shouldBe 5
    }
})