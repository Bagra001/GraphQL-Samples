package de.bagra.graphqlclient.client

import CarsByBrandQuery
import CarsByFinQuery
import CarsByModelQuery
import CarsQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import de.bagra.graphqlclient.graphql.*
import de.bagra.graphqlclient.model.CarDto
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture


@Component
class GraphQLClient(@Value("\${graphql.server.baseurl}") final var qraphQlServerBaseUrl: String) {

    private final var httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()
                val builder: Request.Builder = original.newBuilder().method(original.method, original.body)
                chain.proceed(builder.build())
            })
            .build()

    var apolloClient: ApolloClient =  ApolloClient.builder()
    .serverUrl(this.qraphQlServerBaseUrl)
    .okHttpClient(httpClient)
    .build();

    fun getAllCars(): List<CarDto> {
        val completableFuture: CompletableFuture<Response<CarsQuery.Data>>? = toCompletableFuture(apolloClient.query(CarsQuery()))
        return if (validaData(completableFuture) && completableFuture?.get()?.data!!.cars() != null && completableFuture.get().data!!.cars()!!.isNotEmpty()) completableFuture.get().data!!.cars()!!.stream().map{ car -> GraphQlMapper.map(car)}.toList() else emptyList()
    }

    fun getBMWCars(): List<CarDto> {
        val completableFuture: CompletableFuture<Response<CarsByBrandQuery.Data>>? = toCompletableFuture(apolloClient.query(CarsByBrandQuery("BMW")))
        return if (validaData(completableFuture) && completableFuture?.get()?.data!!.carsByBrand() != null && completableFuture.get().data!!.carsByBrand()!!.isNotEmpty()) completableFuture.get().data!!.carsByBrand()!!.stream().map{ car -> GraphQlMapper.map(car)}.toList() else emptyList()
    }
    
    fun getMercedesCars(): List<CarDto> {
        val completableFuture: CompletableFuture<Response<CarsByBrandQuery.Data>>? = toCompletableFuture(apolloClient.query(CarsByBrandQuery("Mercedes")))
        return if (validaData(completableFuture) && completableFuture?.get()?.data!!.carsByBrand() != null && completableFuture.get().data!!.carsByBrand()!!.isNotEmpty()) completableFuture.get().data!!.carsByBrand()!!.stream().map{ car -> GraphQlMapper.map(car)}.toList() else emptyList()
    }

    fun getCarByFIN(fin: String): CarDto? {
        val completableFuture: CompletableFuture<Response<CarsByFinQuery.Data>>? = toCompletableFuture(apolloClient.query(CarsByFinQuery(fin)))
        return if (validaData(completableFuture) && completableFuture?.get()?.data!!.carsByFin() != null) GraphQlMapper.map(completableFuture.get().data!!.carsByFin()!!) else null
    }

    fun getCarByModel(model: String): List<CarDto> {
        val completableFuture: CompletableFuture<Response<CarsByModelQuery.Data>>? = toCompletableFuture(apolloClient.query(CarsByModelQuery(model)))
        return if (validaData(completableFuture) && completableFuture?.get()?.data!!.carsByModel() != null && completableFuture.get().data!!.carsByModel()!!.isNotEmpty()) completableFuture.get().data!!.carsByModel()!!.stream().map{ car -> GraphQlMapper.map(car)}.toList() else emptyList()
    }

    fun <T> toCompletableFuture(@NotNull apolloCall: ApolloCall<T>): CompletableFuture<Response<T>>? {
        val completableFuture: CompletableFuture<Response<T>> = CompletableFuture<Response<T>>()
        completableFuture.whenComplete { _: Response<T>?, _: Throwable? ->
            if (completableFuture.isCancelled) {
                completableFuture.cancel(true)
            }
        }
        apolloCall.enqueue(object : ApolloCall.Callback<T>() {
            override fun onResponse(@NotNull response: Response<T>) {
                completableFuture.complete(response)
            }

            override fun onFailure(@NotNull e: ApolloException) {
                completableFuture.completeExceptionally(e)
            }
        })
        return completableFuture
    }
    
    fun <T> validaData(completableFuture: CompletableFuture<Response<T>>?): Boolean {
        return completableFuture?.get() != null && completableFuture.get().data != null
    } 
}