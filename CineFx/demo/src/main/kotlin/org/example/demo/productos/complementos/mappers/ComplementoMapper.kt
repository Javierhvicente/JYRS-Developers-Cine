package org.example.demo.productos.complementos.mappers

import database.ComplementoEntity
import org.example.demo.productos.complementos.dto.ComplementoDto
import org.example.demo.productos.complementos.exceptions.ComplementoException
import org.example.demo.productos.models.*
import org.lighthousegames.logging.logging

/**
 * Mapea un ComplementoEntity en un complemento.
 * @return comida, bebida
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */
val logger=logging()
fun ComplementoEntity.toComplemento(): Complemento {
    logger.debug { "Pasando ComplementoEntity ${this.id} a Complemento" }
    val _nombre: String = this.id
    val _tipo: String = this.tipo
    when (_tipo) {
        "COMIDA" -> {
               return Comida(_nombre,_tipo,this.precio.toDouble(),this.imagen)
        }
        "BEBIDA" -> {
                return Bebida(_nombre, _tipo,this.precio.toDouble(),this.imagen)
        }

    }
    throw ComplementoException.TipoInvalido("Tipo no valido")
}

/**
 * Mapea un ComplementoDto en un complemento.
 * @return comida, bebida
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

fun ComplementoDto.toComplemento(): Complemento {
    logger.debug { "Pasando ComplementoDto ${this.nombre} a Complemento" }
    when(this.tipoComplemento){
        "COMIDA" -> return Comida(this.nombre,this.tipoComplemento,this.precio.toDouble(),this.imagen)
        "BEBIDA" -> return Bebida(this.nombre,this.tipoComplemento,this.precio.toDouble(),this.imagen)
    }
    throw ComplementoException.TipoInvalido("Tipo no valido")
}

/**
 * Mapear un objeto complemento a un objeto toComplementoDto.
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández.
 * @since 1.0
 */

fun Complemento.toComplementoDto():ComplementoDto{
    return ComplementoDto(
        tipoComplemento = this.tipo,
        nombre = this.id,
        precio = this.precio.toString(),
        imagen=this.imagen
    )
}
