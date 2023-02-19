package org.mikehenry.kotlin_playground.api.dto.request

import io.swagger.annotations.ApiModelProperty
import org.springframework.http.HttpStatus

data class RequestErrors(
    @ApiModelProperty(example = "https://mikehenry-maina.com/problems/kotlin-playgroung/constraint-violation")
    val type: String? = null,

    @ApiModelProperty(example = "Bad format for parameter 'since'")
    val title: String? = null,

    @ApiModelProperty(example = "400")
    val status: HttpStatus = HttpStatus.BAD_REQUEST,

    @ApiModelProperty(example = "error.http.400")
    val message: String? = null,

    @ApiModelProperty(example = "31317")
    val params: String? = null
) {
}