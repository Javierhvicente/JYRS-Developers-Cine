package org.example.demo.usuarios.viewModel

import javafx.beans.property.SimpleObjectProperty
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Complemento


class CarritoViewModel {

    val state: SimpleObjectProperty<CarritoState> = SimpleObjectProperty(CarritoState())

    fun asignarProductos(butacas: MutableList<Butaca>, complemetos: MutableList<Complemento>) {
        state.value = state.value.copy(
            complementos = complemetos,
            butacas = butacas
        )
    }

    fun asignarTotal(total: String) {
        state.value = state.value.copy(
            total = total
        )
    }

    fun asignarPelicula(pelicula: String) {
        state.value = state.value.copy(
            pelicula = pelicula
        )
        println("Pelicula $pelicula")
    }

    data class CarritoState(
        val pelicula:String="",
        val complementos: List<Complemento> = listOf(),
        val butacas: List<Butaca> = listOf(),
        val total:String = ""
    )
}