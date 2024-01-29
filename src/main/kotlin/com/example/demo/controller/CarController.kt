package com.example.demo.controller

import com.example.demo.controller.dto.Car
import com.example.demo.controller.dto.CreateCarRequest
import com.example.demo.service.CarService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cars")
class CarController(val carService: CarService) {

    @PostMapping
    fun createCar(@RequestBody request: CreateCarRequest): Car {
        return carService.create(request)
    }

    @GetMapping
    fun getCars(pageable: Pageable): Page<Car> {
        return carService.getCars(pageable)
    }

    @GetMapping("/{carId}")
    fun getCar(@PathVariable carId: Int): Car {
        return carService.getCar(carId)
    }

    @PutMapping("/{carId}")
    fun updateCar(@PathVariable carId: Int, @RequestBody request: CreateCarRequest): Car {
        return carService.updateCar(carId, request)
    }
}