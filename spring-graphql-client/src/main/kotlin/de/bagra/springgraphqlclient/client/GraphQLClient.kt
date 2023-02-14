package de.bagra.springgraphqlclient.client

import de.bagra.springgraphqlclient.model.CarDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.graphql.client.HttpGraphQlClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient


@Component
class GraphQLClient(@Value("\${graphql.server.baseurl}") final var qraphQlServerBaseUrl: String) {

    private val webClient = WebClient.create(qraphQlServerBaseUrl)
    private val httpGraphQlClient = HttpGraphQlClient.builder(webClient)
        .build()

    fun getAllCars(): List<CarDto?> {
        return queryList("cars-query", "cars")
    }

    fun getBMWCars(): List<CarDto?> {
        return queryList("cars-by-brand", "carsByBrand","marke", "BMW" )
    }

    fun getMercedesCars(): List<CarDto?> {
        return queryList("cars-by-brand", "carsByBrand","marke", "Mercedes" )
    }

    fun getCarByFIN(fin: String): CarDto? {
        return query("cars-by-fin", "carsByFin","fahrzeugidnummer", fin )
    }

    fun getCarByModel(model: String): CarDto? {
        return query("cars-by-model", "carsByModel","fahrzeugmodel", model )
    }
    
    private fun query(documentName: String, retrieveName: String, variableKey: String, variableValue: String): CarDto? {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName(documentName)
            .variable(variableKey, variableValue)
            .retrieve(retrieveName)
            .toEntity(CarDto::class.java)
            .share()
            .block()
    }

    private fun queryList(documentName: String, retrieveName: String, variableKey: String, variableValue: String): List<CarDto?> {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName(documentName)
            .variable(variableKey, variableValue)
            .retrieve(retrieveName)
            .toEntityList(CarDto::class.java)
            .share()
            .block()?.stream()?.toList() ?: emptyList()
    }

    private fun queryList(documentName: String, retrieveName: String): List<CarDto?> {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName(documentName)
            .retrieve(retrieveName)
            .toEntityList(CarDto::class.java)
            .share()
            .block()?.stream()?.toList() ?: emptyList()
    }
}