package com.example.demo.rest

import com.example.demo.controller.dto.Car
import com.example.demo.controller.dto.CreateCarRequest
import com.example.demo.controller.dto.ModifyCarRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CarControllerTest {

    companion object {
        const val AFTER_MIGRATE = "classpath:db/migration/afterMigrate.sql"
    }

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var mapper: ObjectMapper

    @Test
    @Sql(AFTER_MIGRATE)
    fun testCreateCar() {
        val input = CreateCarRequest(
            brand = "Ford",
            model = "Mustang",
            color = "Red"
        )
        val car = doCreate(input)
        assertThat(car.brand).isEqualTo(input.brand)
        assertThat(car.model).isEqualTo(input.model)
        assertThat(car.color).isEqualTo(input.color)
        assertThat(car.id).isNotNull()
    }

    @Test
    @Sql(AFTER_MIGRATE)
    fun testGetOne() {
        val input = CreateCarRequest(
            brand = "Ford",
            model = "Mustang",
            color = "Red"
        )
        val car = doCreate(input)
        val found = findOne(car.id!!)
        assertThat(found.brand).isEqualTo(input.brand)
        assertThat(found.model).isEqualTo(input.model)
        assertThat(found.color).isEqualTo(input.color)
        assertThat(found.id).isEqualTo(car.id)
    }

    @Test
    @Sql(AFTER_MIGRATE)
    fun TestUpdateOne() {
        val og_input = CreateCarRequest(
                brand = "Ford",
                model = "Mustang",
                color = "Red"
        )
        val updated_input = ModifyCarRequest(
                brand = "Hyundai",
                model = "Getz",
                color = "Green"
        )
        val created_car = doCreate(og_input)
        var updated_car = doUpdate(created_car.id!!, updated_input)

        assertThat(created_car.id == updated_car.id)
        assertThat(created_car.brand == updated_car.brand)
        assertThat(created_car.model == updated_car.model)
        assertThat(created_car.color == updated_car.color)
    }


    fun doCreate(input: CreateCarRequest): Car {
        return mockMvc.post("/cars") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(input)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn().response.contentAsString.let {
            mapper.readValue(it, Car::class.java)
        }
    }

    fun findOne(id: Int): Car {
        return mockMvc.get("/cars/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn().response.contentAsString.let {
            mapper.readValue(it, Car::class.java)
        }
    }

    fun doUpdate(id: Int, input: ModifyCarRequest) : Car {
        return mockMvc.get("/cars/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON)}
        }.andReturn().response.contentAsString.let {
            mapper.readValue(it, Car::class.java)
        }
    }

}
