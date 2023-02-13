package de.bagra.graphqlmicroservice.service

import de.bagra.graphqlmicroservice.entity.CarEntity
import de.bagra.graphqlmicroservice.model.CarDto
import de.bagra.graphqlmicroservice.repository.CarRepository
import org.springframework.stereotype.Service
import java.util.stream.StreamSupport

@Service
class CarSalesService(private var carRepository: CarRepository) {
    
    fun save(car: CarDto): CarDto {
        return CarEntity.toDto(carRepository.save(CarEntity.fromDto(car)))
    }
    
    fun getAllCars(): List<CarDto> {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false).map { car -> CarEntity.toDto(car) }.toList()
    }

    fun getByBrand(brand: String): List<CarDto> {
        return carRepository.getByBrand(brand).stream().map { car -> CarEntity.toDto(car) }.toList()
    }

    fun getByModel(model: String): List<CarDto> {
        return carRepository.getByModel(model).stream().map { car -> CarEntity.toDto(car) }.toList()
    }

    fun getByFin(fin: String): CarDto {
        return CarEntity.toDto(carRepository.getByFin(fin))
    }
}