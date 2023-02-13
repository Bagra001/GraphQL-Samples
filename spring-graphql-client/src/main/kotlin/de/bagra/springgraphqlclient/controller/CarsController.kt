package de.bagra.springgraphqlclient.controller

import de.bagra.springgraphqlclient.client.GraphQLClient
import de.bagra.springgraphqlclient.model.CarDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CarsController(var graphQLClient: GraphQLClient) {
    
    @PostMapping(path = ["cars"])
    fun addCars() {
        //TODO
    }

    @GetMapping(path = ["cars"])
    fun getAllCars(): List<CarDto> {
        return graphQLClient.getAllCars()
    }
    
    @GetMapping(path = ["cars/bmw"])
    fun getBmwCars(): List<CarDto> {
        return graphQLClient.getBMWCars()
    }

    @GetMapping(path = ["cars/mercedes"])
    fun getMercedesCars(): List<CarDto> {
        return graphQLClient.getMercedesCars()
    }

    @GetMapping(path = ["cars/fin/{fin}"])
    fun getCarsByFin(@PathVariable fin: String): CarDto? {
        return graphQLClient.getCarByFIN(fin)
    }

    @GetMapping(path = ["cars/model/{model}"])
    fun getCarsByModel(@PathVariable model: String): List<CarDto> {
        return graphQLClient.getCarByModel(model)
    }
}