package de.bagra.graphqlmicroservice.repository

import de.bagra.graphqlmicroservice.entity.CarEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository: CrudRepository<CarEntity, Int> {
    fun getByBrand(brand: String): List<CarEntity>
    fun getByModel(model: String): List<CarEntity>
    fun getByFin(fin: String): CarEntity
}