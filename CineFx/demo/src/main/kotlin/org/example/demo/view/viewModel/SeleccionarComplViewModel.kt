package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.complementos.services.ComplementoService
import org.example.demo.productos.complementos.storage.images.StorageImageImpl
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Comida
import org.example.demo.productos.models.Complemento
import org.example.demo.routes.RoutesManager
import java.io.File
import java.lang.Exception

class SeleccionarComplViewModel(
    private val servicio : ComplementoService,
    private val storageImage: StorageImageImpl
) {

    val state: SimpleObjectProperty<ComplementoState> = SimpleObjectProperty(ComplementoState())

    init {
        if (servicio.getAll().value.isEmpty()){
            servicio.import(File("data","complemento.csv")).onSuccess {
                initState(it)
            }
        }else{
            initState(servicio.getAll().value)
        }
    }
    fun updateState(complemento: Complemento){
        var imagen = Image(RoutesManager.getResourceAsStream("images/interrogacion.png"))
        storageImage.loadImage(complemento.imagen).onSuccess {
            imagen = Image(it.absoluteFile.toURI().toString())
        }
        state.value = state.value.copy(
            tipo = when(complemento){
                is Bebida-> "Bebida"
                is Comida-> "Comida"
                else -> "No detectado"
            },
            nombre = complemento.id,
            precio = when(complemento) {
                is Bebida -> complemento.precio.toDefaultMoneyString()
                is Comida -> complemento.precio.toDefaultMoneyString()
                else -> "No detectado"
            },
            imagen = imagen,
            complemento = complemento

        )
    }

    fun filtrarComplementos(precio: String,nombre: String):List<Complemento>{

        return state.value.complementos
            .asSequence()
            .filter {
               it.id.contains(nombre)
            }.filter {
                if (precio=="All") true else{
                    when(it){
                        is Bebida->it.precio.toDefaultMoneyString() == precio
                        is Comida->it.precio.toDefaultMoneyString()== precio
                        else -> it.id.contains(nombre)
                    }
                }
            }.toList()

    }


    fun borrarSeleccionado(){
        val lista= mutableListOf<Complemento>()
        state.value = state.value.copy(
            complementosSeleccionados = lista
        )
    }

    private fun initState(lista: List<Complemento>) {
        val listaTipos= mutableListOf<String>("All")

        val listaPrecio = mutableListOf("All")
        lista.forEach {
            when(it){
                is Bebida-> listaPrecio.add(it.precio.toDefaultMoneyString())
                is Comida-> listaPrecio.add(it.precio.toDefaultMoneyString())
            }
        }

        state.value = state.value.copy(
            complementos = lista,
            tipos = listaTipos.distinct().sorted(),
            precios = listaPrecio.distinct().sorted()
        )
    }

    fun actualizarSeleccionados(complemmentosSeleccionas: MutableList<Complemento>) {
        state.value = state.value.copy(
            complementosSeleccionados = complemmentosSeleccionas
        )
    }

    data class ComplementoState(
        val tipo:String="" ,
        val nombre:String="",
        val precio:String="",
        val cantidad:Int=0,
        val imagen:Image = Image(RoutesManager.getResourceAsStream("images/interrogacion.png")),
        val complementos:List<Complemento> = listOf(),
        val tipos:List<String> = listOf(),
        val precios:List<String> = listOf(),
        val complementosSeleccionados : MutableList<Complemento> = mutableListOf(),
        val complemento:Complemento?=null
    )
}