package de.bagra.graphqlmicroservice.controller

import de.bagra.graphqlmicroservice.model.CarDto
import de.bagra.graphqlmicroservice.service.CarSalesService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CarSalesController(var carSalesService: CarSalesService) {
    
    @MutationMapping
    fun car(@Argument car: CarDto): CarDto {
        return carSalesService.save(car)
    }
    
    @QueryMapping
    fun cars(): List<CarDto> {
        return carSalesService.getAllCars();
    }

    @QueryMapping
    fun carsByFin(@Argument fin: String): CarDto {
        return carSalesService.getByFin(fin);
    }

    @QueryMapping
    fun carsByBrand(@Argument brand: String): List<CarDto> {
        return carSalesService.getByBrand(brand);
    }

    @QueryMapping
    fun carsByModel(@Argument model: String): List<CarDto> {
        return carSalesService.getByModel(model);
    }
}