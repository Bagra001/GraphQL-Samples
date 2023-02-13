package de.bagra.graphqlclient.controller

import de.bagra.graphqlclient.client.GraphQLClient
import de.bagra.graphqlclient.model.CarDto
import org.springframework.web.bind.annotation.*


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