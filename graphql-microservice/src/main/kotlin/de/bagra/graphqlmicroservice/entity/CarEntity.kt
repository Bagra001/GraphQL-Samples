package de.bagra.graphqlmicroservice.entity

import de.bagra.graphqlmicroservice.model.CarDto
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "CAR")
data class CarEntity(@Id @Column @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int? = null, @Column val fin: String = "", @Column val brand: String = "", @Column val model: String = "", @Column val engine: String = "") {
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CarEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }

    companion object {
        fun fromDto(carDto: CarDto) = CarEntity(null, carDto.fin, carDto.brand, carDto.model, carDto.engine)
        fun toDto(car: CarEntity) = CarDto(car.fin, car.brand, car.model, car.engine)
    }
}