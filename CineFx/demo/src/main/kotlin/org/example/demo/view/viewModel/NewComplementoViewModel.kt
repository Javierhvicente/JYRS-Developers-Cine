package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.mapBoth
import javafx.beans.property.SimpleObjectProperty
import org.example.demo.productos.complementos.services.ComplementoService
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Comida
import org.example.demo.productos.models.Complemento
import org.example.demo.usuarios.viewModel.GestionButacaViewModel.GestButacasState
import org.koin.dsl.koinApplication

class NewComplementoViewModel(
    private val service:ComplementoService
) {
    val state: SimpleObjectProperty<NuevoComplementoState> = SimpleObjectProperty(NuevoComplementoState())

    fun createComplemento(tipo:String,nombre: String,precio: String,imagen:String){
        when(tipo){
            "COMIDA"->service.create(Comida(nombre,"COMIDA",precio.toDouble(),imagen))
            "BEBIDA"->service.create(Bebida(nombre,"BEBIDA",precio.toDouble(),imagen))
        }
    }
    fun allComplementos():List<Complemento>{
        return service.getAll().value
    }
    fun comprobarExistencia(id:String):Boolean{
        service.getById(id).mapBoth(
            success = {
                return true
            },
            failure = {
                return false
            }
        )
    }

    data class NuevoComplementoState(
        val tipos:List<String> = listOf("COMIDA","BEBIDA")
    )
}