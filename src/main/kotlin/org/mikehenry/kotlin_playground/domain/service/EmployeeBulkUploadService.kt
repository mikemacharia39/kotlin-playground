package org.mikehenry.kotlin_playground.domain.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.mikehenry.kotlin_playground.api.dto.request.AddressRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.DepartmentRequestDto
import org.mikehenry.kotlin_playground.api.dto.request.EmployeeRequestDto
import org.mikehenry.kotlin_playground.domain.enumeration.AddressType
import org.mikehenry.kotlin_playground.domain.enumeration.EmployeeType
import org.mikehenry.kotlin_playground.domain.extensions.getEmployeeSheet
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class EmployeeBulkUploadService {

    val log = KotlinLogging.logger {}

    fun uploadEmployees(file: MultipartFile) {
        //val fileBulkId = file.originalFilename?.removeSuffix(".xlsx")?.removeSuffix(".xls")

        val workbook = validateBasicFileContents(file)

        CoroutineScope(Dispatchers.IO).launch {
            val employeeSheet = workbook.getEmployeeSheet()
            val headerRow = validateHeaderRow(employeeSheet)

            processBulkUpload(workbook)
        }

    }

    fun processBulkUpload(workbook: Workbook) {
        runBlocking {
            parseEmployeeSheet(workbook).collect { employeeRequestDto ->
                log.info { "Employee: $employeeRequestDto" }
            }
        }
    }

    fun parseEmployeeSheet(workbook: Workbook): Flow<EmployeeRequestDto> {
        val employeeSheet = workbook.getEmployeeSheet()
        val headerRow = validateHeaderRow(employeeSheet)

        val rowIterator = employeeSheet.rowIterator()

        return flow {
            while (rowIterator.hasNext()) {
                val row = rowIterator.next()
                if (row.rowNum == 0) {
                    continue
                }
                // stop processing if we encounter a blank row
                if (row.cellIterator().asSequence().all { it.stringCellValue.isBlank() }) {
                    break
                }
                var employeeRequestDto: EmployeeRequestDto? = null
                try {
                    employeeRequestDto = extractEmployeeRowData(row)
                } catch (e: Exception) {
                    log.error {"Error parsing row ${row.rowNum}: ${e.message}" }
                }

                when (employeeRequestDto) {
                    null -> continue
                    else -> emit(employeeRequestDto)
                }
            }
        }
    }

    fun extractEmployeeRowData(row: Row): EmployeeRequestDto {
        return EmployeeRequestDto(
            firstName = row.getCell(0).stringCellValue,
            lastName = row.getCell(1).stringCellValue,
            emailAddress = row.getCell(2).stringCellValue,
            phoneNumber = row.getCell(3).stringCellValue,
            employeeType = EmployeeType.valueOf(row.getCell(4).stringCellValue),
            department = DepartmentRequestDto(
                departmentName = row.getCell(5).stringCellValue,
                departmentDescription = "Department of ${row.getCell(5).stringCellValue}"
            ),
            addresses = listOf(
                AddressRequestDto(
                    addressType = AddressType.HOME,
                    country = row.getCell(6).stringCellValue,
                    city = row.getCell(7).stringCellValue,
                    addressLine1 = row.getCell(8).stringCellValue,
                    state = row.getCell(9).stringCellValue,
                    postalBox = row.getCell(10).stringCellValue
                )
            )
        )
    }


    fun validateBasicFileContents(multipartFile: MultipartFile): XSSFWorkbook {
        try {
            return XSSFWorkbook(multipartFile.inputStream).also { workbook ->
                if (workbook.numberOfSheets < 1) {
                    throw Exception("Invalid number of sheets. Expected 1, found ${workbook.numberOfSheets}")
                }
                val employeeSheet = workbook.getEmployeeSheet()
                validateHeaderRow(employeeSheet)
            }
        } catch (e: Exception) {
            throw Exception("Invalid file contents: ${e.message}")
        }
    }

    fun validateHeaderRow(employeeSheet: Sheet): Row {
        val headerRow = employeeSheet.getRow(0)
        val headerRowCellCount = headerRow.physicalNumberOfCells

        if (headerRowCellCount < 11) {
            throw Exception("Invalid header row cell count. Expected 11, found $headerRowCellCount")
        }

        val headerRowCellValues = headerRow.cellIterator().asSequence().map { it.stringCellValue }.toList()

        if (headerRowCellValues != listOf(
                "First Name",
                "Last Name",
                "Email Address",
                "Phone Number",
                "Employee Type",
                "Department",
                "Country",
                "City",
                "Address Line 1",
                "State",
                "Postal Box"
            )
        ) {
            throw Exception("Invalid header row cell values. Expected [\"First Name\", \"Last Name\", \"Email Address\", \"Phone Number\", \"Employee Type\", \"Address Line 1\", \"Address Line 2\", \"City\", \"State\", \"Zip Code\"], found $headerRowCellValues")
        }

        return headerRow
    }
}