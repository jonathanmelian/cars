package com.example.demo.mapper

import com.example.demo.controller.dto.Car
import com.example.demo.controller.dto.CreateCarRequest
import com.example.demo.model.CarEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CarMapper {

    fun toDto(carEntity: CarEntity): Car

    fun toEntity(createCarRequest: CreateCarRequest): CarEntity


}