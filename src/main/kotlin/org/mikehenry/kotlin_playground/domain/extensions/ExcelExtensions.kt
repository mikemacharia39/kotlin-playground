package org.mikehenry.kotlin_playground.domain.extensions

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

fun Workbook.getEmployeeSheet(): Sheet = this.getSheet("Employees")