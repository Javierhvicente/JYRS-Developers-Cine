package org.example.demo.productos.models

/**
 * Representa una bebida.
 * @property id
 * @property nombre
 * @author Raúl Fernández, Javier Hernández, Samuel Cortés, Yahya El Hadri.
 * @since 1.0
 */

class Bebida(
    id:String,
    tipo:String,
    precio:Double,
    imagen: String
):Complemento(id,tipo,precio, imagen) {
}

enum class CategoriaBebida{
    AGUA,
    REFRESCOS
}