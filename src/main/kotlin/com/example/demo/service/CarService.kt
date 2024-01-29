package com.example.demo.service

import com.example.demo.controller.dto.Car
import com.example.demo.controller.dto.CreateCarRequest
import com.example.demo.controller.dto.ModifyCarRequest
import com.example.demo.repository.CarRepository
import com.example.demo.model.CarEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.NoSuchElementException

@Service
class CarService(
    private val carRepository: CarRepository
    ) {

    fun create(createCarRequest: CreateCarRequest): Car {
        val carEntity = CarEntity().apply {
            brand = createCarRequest.brand
            model = createCarRequest.model
            color = createCarRequest.color
        }

        val savedEntity = carRepository.save(carEntity)

        return Car(
            id = savedEntity.carsId,
            brand = savedEntity.brand,
            model = savedEntity.model,
            color = savedEntity.color
        )
    }

    fun getCars(pageable: Pageable): Page<Car> {
        val carEntities = carRepository.findAll(pageable)
        val cars = carEntities.map { carEntity ->
            Car(
                id = carEntity.carsId,
                brand = carEntity.brand,
                model = carEntity.model,
                color = carEntity.color
            )
        }
        return PageImpl(cars.content, pageable, carEntities.totalElements)
    }

    fun getCar(carId: Int): Car {
        val carEntity = carRepository.findById(carId).orElseThrow { NoSuchElementException() }
        return Car(
            id = carEntity.carsId,
            brand = carEntity.brand,
            model = carEntity.model,
            color = carEntity.color
        )
    }

    fun modifyCar(carId: Int, carInformation: ModifyCarRequest): Car {
        try {
            getCar(carId)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException("can't edit a car that doesn't exist")
        }

        val editedCarEntity = CarEntity().apply {
            carsId = carId
            brand = carInformation.brand
            model = carInformation.model
            color = carInformation.color
        }

        val updatedEntry = carRepository.save(editedCarEntity)

        return Car(
            id = updatedEntry.carsId,
            brand = updatedEntry.brand,
            model = updatedEntry.model,
            color = updatedEntry.color
        )
    }
}