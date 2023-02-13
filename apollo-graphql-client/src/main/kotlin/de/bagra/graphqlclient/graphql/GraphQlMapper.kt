package de.bagra.graphqlclient.graphql

import de.bagra.graphqlclient.model.CarDto

object GraphQlMapper {

    fun map(car: CarsQuery.Car): CarDto {
        return CarDto().copy(fin = car.fin(), brand = car.brand(), model = car.model(), engine = car.engine())
    }

    fun map(car: CarsByModelQuery.CarsByModel): CarDto {
        return CarDto().copy(fin = car.fin(), brand = car.brand(), model = car.model(), engine = car.engine())
    }

    fun map(car: CarsByFinQuery.CarsByFin): CarDto {
        return CarDto().copy(fin = car.fin(), brand = car.brand(), model = car.model(), engine = car.engine())
    }

    fun map(car: CarsByBrandQuery.CarsByBrand): CarDto {
        return CarDto().copy(fin = car.fin(), brand = car.brand(), model = car.model(), engine = car.engine())
    }
}