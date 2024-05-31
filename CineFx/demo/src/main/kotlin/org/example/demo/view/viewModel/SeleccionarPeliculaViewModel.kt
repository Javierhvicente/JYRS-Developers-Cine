package org.example.demo.usuarios.viewModel

import javafx.beans.property.SimpleObjectProperty
import org.example.demo.usuarios.viewModel.SeleccionarAsientoViewModel.ButacasState

class SeleccionarPeliculaViewModel {

    val state: SimpleObjectProperty<PeliculaState> = SimpleObjectProperty(PeliculaState())

    data class PeliculaState(
        val pelicula:String=""
    )
}