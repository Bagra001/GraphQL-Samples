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

    fun getAllCars(): List<CarDto> {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName("cars-query")
            .retrieve("cars")
            .toEntityList(CarDto::class.java)
            .share()
            .block()?.stream()?.toList() ?: emptyList()
    }

    fun getBMWCars(): List<CarDto> {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName("cars-by-brand")
            .variable("marke", "BMW")
            .retrieve("carsByBrand")
            .toEntityList(CarDto::class.java)
            .share()
            .block()?.stream()?.toList() ?: emptyList()
    }

    fun getMercedesCars(): List<CarDto> {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName("cars-by-brand")
            .variable("marke", "Mercedes")
            .retrieve("carsByBrand")
            .toEntityList(CarDto::class.java)
            .share()
            .block()?.stream()?.toList() ?: emptyList()
    }

    fun getCarByFIN(fin: String): CarDto? {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName("cars-by-fin")
            .variable("fahrzeugidnummer", fin)
            .retrieve("carsByFin")
            .toEntity(CarDto::class.java)
            .share()
            .block()
    }

    fun getCarByModel(model: String): List<CarDto> {
        return httpGraphQlClient
            .mutate()
            .build()
            .documentName("cars-by-model")
            .variable("fahrzeugmodel", model)
            .retrieve("carsByModel")
            .toEntityList(CarDto::class.java)
            .share()
            .block()?.stream()?.toList() ?: emptyList()
    }
}