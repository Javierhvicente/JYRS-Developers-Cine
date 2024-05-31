package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.complementos.services.ComplementoService
import org.example.demo.productos.complementos.storage.ComplementoStorage
import org.example.demo.productos.complementos.storage.images.StorageImageImpl
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Comida
import org.example.demo.productos.models.Complemento
import org.example.demo.routes.RoutesManager
import java.io.File

class GestionComplementoViewModel(
    val servicio:ComplementoService,
    val storage:ComplementoStorage,
    val storageImagen:StorageImageImpl
) {

    val state: SimpleObjectProperty<ComplementoState> = SimpleObjectProperty(ComplementoState())

    init {
        iniciar()

    }

    fun iniciar() {
        if (servicio.getAll().value.isEmpty()) {
            servicio.import(File("data", "complemento.csv")).onSuccess {
                initState(it)
            }
        } else {
            initState(servicio.getAll().value)
        }
    }

    fun exportar(file: File): Boolean {
        if (file.toPath().toString().contains(".json")) {
            storage.saveJson(file, state.value.complementos).mapBoth(
                success = {
                    return true
                },
                failure = {
                    return false
                }
            )
        } else if (file.toPath().toString().contains(".csv")) {
            storage.saveCsv(file, state.value.complementos).mapBoth(
                success = {
                    return true
                },
                failure = {
                    return false
                }
            )
        }
        return true
    }

    fun importar(file: File): Boolean {
        var lista = listOf<Complemento>()
        if (file.toPath().toString().contains(".json")) {
            storage.loadJson(file).mapBoth(
                success = {
                    lista = it
                },
                failure = {
                    return false
                }
            )
        } else if (file.toPath().toString().contains(".csv")) {
            storage.loadCsv(file).mapBoth(
                success = {
                    lista = it
                },
                failure = {
                    return false
                }
            )
        }
        lista.forEach {
            servicio.update(it.id, it, it.imagen)
        }
        initState(lista)
        return true
    }

    fun updateState(complemento: Complemento) {
        var imagen = Image(RoutesManager.getResourceAsStream("images/interrogacion.png"))
        storageImagen.loadImage(complemento.imagen).onSuccess {
            imagen = Image(it.absoluteFile.toURI().toString())
        }
        state.value = state.value.copy(
            tipo = complemento.tipo,
            nombre = complemento.id,
            precio = complemento.precio.toDefaultMoneyString(),
            imagen = imagen,
            complementoSeleccionado = complemento
        )
    }

    fun eliminarComplemento(id: String) {
        servicio.delete(id)
    }

    fun actualizarComplementos() {
        state.value = state.value.copy(
            complementoSeleccionado = null,
            complementos = servicio.getAll().value,
            tipo = "",
            nombre = "",
            precio = "",
            imagen = Image(RoutesManager.getResourceAsStream("images/interrogacion.png"))
        )
    }

    fun filtrarComplementos(precio: String, nombre: String): List<Complemento> {
        return state.value.complementos
            .asSequence()
            .filter {
                it.id.contains(nombre)
            }.filter {
                if (precio == "All") true else {
                    when (it) {
                        is Bebida -> it.precio.toDefaultMoneyString() == precio
                        is Comida -> it.precio.toDefaultMoneyString() == precio
                        else -> it.id.contains(nombre)
                    }
                }
            }.toList()
    }

    fun initState(lista: List<Complemento>) {
        val listaPrecio = mutableListOf("All")
        lista.forEach {
            when (it) {
                is Bebida -> listaPrecio.add(it.precio.toDefaultMoneyString())
                is Comida -> listaPrecio.add(it.precio.toDefaultMoneyString())
            }
        }

        state.value = state.value.copy(
            complementos = lista,
            precios = listaPrecio.distinct().sorted()
        )
    }


    data class ComplementoState(
        val complementoSeleccionado: Complemento? = null,
        val tipo: String = "",
        val nombre: String = "",
        val precio: String = "",
        val imagen: Image = Image(RoutesManager.getResourceAsStream("images/interrogacion.png")),
        val complementos: List<Complemento> = listOf(),
        val precios: List<String> = listOf(),
    )
}