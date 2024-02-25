package org.mikehenry.kotlin_playground.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.mikehenry.kotlin_playground.domain.enumeration.AddressType

@Entity
@Table(name = "address")
class Address(
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    var addressType: AddressType = AddressType.HOME,

    @Column(name ="address_line_1", nullable = false)
    var addressLine1: String,

    @Column(name ="address_line_2")
    var addressLine2: String? = null,

    @Column(nullable = false)
    var country: String,

    @Column(nullable = false)
    var city: String,

    var state: String? = null,

    @Column(name = "postal_box", nullable = false)
    var postalBox: String
) : BaseEntity() {
}
