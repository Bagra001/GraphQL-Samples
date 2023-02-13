package de.bagra.graphqlmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphqlMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<GraphqlMicroserviceApplication>(*args)
}
