package org.example.demo.productos.models

import kotlinx.serialization.Serializable

/**
 * Representa a un admin.
 * @property id
 * @property nombre
 * @author Raúl Fernández, Javier Hernández, Samuel Cortés, Yahya El Hadri.
 * @since 1.0
 */

class Comida(
    id:String,
    tipo:String,
    precio:Double,
    imagen: String
):Complemento(id,tipo,precio, imagen) {


}
enum class CategoriaComida{
    PALOMITAS,
    FRUTOSSECOS,
    PATATAS
}
