package com.example.demo.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "cars")
class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cars_id")
    var carsId: Int? = null

    @Column(name = "brand", nullable = false)
    var brand: String? = null

    @Column(name = "model", nullable = false)
    var model: String? = null

    @Column(name = "color", nullable = false)
    var color: String? = null
}