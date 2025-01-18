package org.mikehenry.kotlin_playground.api.controller

import jakarta.validation.Valid
import mu.KotlinLogging
import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.domain.service.EmployeeService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * This controller is used by the thymeleaf template engine to render the web pages.
 */

@Controller
@RequestMapping("/")
class HomeController(
    private val employeeService: EmployeeService
) {

    val log = KotlinLogging.logger {}

    @GetMapping(path = ["","/", "/index"])
    fun getHomePage(): String {
        return "index"
    }

    @GetMapping("/contact")
    fun getContactPage(): String {
        return "contact"
    }

    @GetMapping("/privacy")
    fun getPrivacyPage(): String {
        return "privacy"
    }

    @GetMapping("/employees")
    fun getEmployeesPage(model: Model): String {
        val paging = PageRequest.of(0, 100).withSort(
            Sort.by(Sort.Order.desc("id")))
        val employees = employeeService.getAllEmployees(paging)
        val employeesList = employees.content
        model.addAttribute("employees", employeesList)
        return "employees"
    }

    @GetMapping("/employees/new")
    fun getNewEmployeePage(model: Model): String {
        val employeeRequestDto: EmployeeRequestDto = EmployeeRequestDto()
        model.addAttribute("employeeRequestDto", employeeRequestDto)
        return "new_employee"
    }

    @PostMapping("/employees/new")
    fun createNewEmployee(@Valid @ModelAttribute employeeRequestDto: EmployeeRequestDto,
                          bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "new_employee"
        }
        log.info { "EmployeeRequestDto: $employeeRequestDto" }
        employeeService.addEmployee(employeeRequestDto)
        return "redirect:/employees"
    }

    @GetMapping("/employees/{employeeId}/edit")
    fun showEditEmployeePage(model: Model, @PathVariable employeeId: Long): String {
        val employee = employeeService.getEmployee(employeeId)
        model.addAttribute("employeeResponseDto", employee)
        return "edit_employee"
    }

    @PostMapping("/employees/{employeeId}/edit")
    fun updateEmployee(@Valid @ModelAttribute employeeRequestDto: EmployeeRequestDto,
                       bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "edit_employee"
        }
        log.info { "EmployeeRequestDto: $employeeRequestDto" }
        // there is no updateEmployee method in EmployeeService
        return "redirect:/employees"
    }

    @GetMapping("/employees/{employeeId}/delete")
    fun deleteEmployee(@PathVariable employeeId: Long): String {
        // there is no deleteEmployee method in EmployeeService
        log.info { "Employee ID  $employeeId deleted" }
        return "redirect:/employees"
    }
}