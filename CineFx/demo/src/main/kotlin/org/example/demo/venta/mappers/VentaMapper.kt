package org.example.demo.venta.mappers

import database.LineaVentaEntityButaca
import database.LineaVentaEntityComplemento
import database.VentaEntity
import kotlinx.serialization.Serializable
import org.example.demo.productos.butaca.dto.ButacaDto
import org.example.demo.productos.butaca.mappers.toButaca
import org.example.demo.productos.butaca.mappers.toButacaDto
import org.example.demo.productos.complementos.dto.ComplementoDto
import org.example.demo.productos.complementos.mappers.toComplemento
import org.example.demo.productos.complementos.mappers.toComplementoDto
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Complemento
import org.example.demo.usuarios.mappers.toUsuario
import org.example.demo.usuarios.mappers.toUsuarioDto
import org.example.demo.usuarios.models7.Usuario
import org.example.demo.venta.dto.LineaVentaDto
import org.example.demo.venta.dto.VentaDto
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import org.lighthousegames.logging.logging
import java.time.LocalDateTime
import java.util.*

val logger= logging()

/**
 * Convertimos una entidad de linea de venta de butaca.
 * @return Devuelve una linea de venta.
 * @since 1.0
 * @param butaca
 * @author Samuel Cortés, Yahya El Hadri, Javier Hernández, Raúl Fernández.
 */

fun LineaVentaEntityButaca.toLineaVenta(butaca:Butaca):LineaVenta{
    logger.debug { "Mapeando lineaDeVentaEntity ${this.id} a LineaVenta" }
    return LineaVenta(
        id = UUID.fromString(this.id),
        producto = butaca,
        cantidad = this.cantidad.toInt(),
        precio = this.precio,
        createdAt = LocalDateTime.parse(this.created_at),
        updatedAt = LocalDateTime.parse(this.updated_at)
    )
}

/**
 * Convertimos una entidad de linea de venta de complementos.
 * @return Devuelve una linea de venta.
 * @since 1.0
 * @param complemento
 * @author Samuel Cortés, Yahya El Hadri, Javier Hernández, Raúl Fernández.
 */

fun LineaVentaEntityComplemento.toLineaVenta(complemento:Complemento):LineaVenta{
    logger.debug { "Mapeando lineaDeVentaEntity ${this.id} a LineaVenta" }
    return LineaVenta(
        id = UUID.fromString(this.id),
        producto = complemento,
        cantidad = this.cantidad.toInt(),
        precio = this.precio,
        createdAt = LocalDateTime.parse(this.created_at),
        updatedAt = LocalDateTime.parse(this.updated_at)
    )
}

/**
 * Convertimos una entidad de venta en un objeto de dominio.
 * @return Devuelve una venta.
 * @since 1.0
 * @param cliente
 * @param lineas
 * @author Samuel Cortés, Yahya El Hadri, Javier Hernández, Raúl Fernández.
 */

fun VentaEntity.toVenta(cliente: Usuario, lineas:List<LineaVenta>):Venta{
    logger.debug { "Mapeando VentaEntity ${this.id} a Venta" }
    return Venta(
        id = UUID.fromString(this.id),
        cliente = cliente,
        lineas = lineas,
        createdAt = LocalDateTime.parse(this.created_at),
        updatedAt = LocalDateTime.parse(this.updated_at),
    )
}

/**
 * Convertimos un objeto de dominio 'LineaVenta' en un objeto de transferencia de datos.
 * @return
 * @since 1.0
 * @author Samuel Cortés, Yahya El Hadri, Javier Hernández, Raúl Fernández.
 */

fun LineaVenta.toLineaVentaDto():LineaVentaDto{
    logger.debug { "Mapeando LineaVenta ${this.id} a LineaVentaDto" }
    return LineaVentaDto(
        id = this.id.toString(),
        producto = when(this.producto){
            is Complemento->this.producto.toComplementoDto()
            is Butaca->this.producto.toButacaDto()
            else->throw Exception("Producto no detectado")
        },
        precio = this.precio.toString(),
        cantidad = this.cantidad.toString(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}

/**
 * Convertimos un objeto de transferencia de datos 'LineaVentaDto' en un dominio 'LineaVenta'.
 * @return Devuelve una linea de venta.
 * @since 1.0
 * @author Samuel Cortés, Yahya El Hadri, Javier Hernández, Raúl Fernández.
 */

fun LineaVentaDto.toLineaVenta():LineaVenta{
    logger.debug { "Mapeando LineaVentaDto ${this.id} a LineaVenta" }
    return LineaVenta(
        id = UUID.fromString(this.id),
        producto = when(this.producto){
            is ComplementoDto->this.producto.toComplemento()
            is ButacaDto->this.producto.toButaca()
            else->throw Exception("Producto no detectado")
        },
        precio = this.precio.toDouble(),
        cantidad = this.cantidad.toInt(),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.updatedAt)
    )
}

/**
 * Convertimos un objeto de transferencia de datos 'VentaDto' en un objeto de dominio de 'Venta'.
 * @return Devuelve una venta.
 * @since 1.0
 * @author Samuel Cortés, Yahya El Hadri, Javier Hernández, Raúl Fernández.
 */

fun VentaDto.toVenta():Venta{
    logger.debug { "Mapeando VentaDto ${this.id} a Venta" }
    return Venta(
        id= UUID.fromString(this.id),
        cliente = this.cliente.toUsuario(),
        lineas = this.lineas.map { it.toLineaVenta() },
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.updatedAt)
    )
}

/**
 * Convertimos un objeto de dominio 'Venta' en un objeto de transferencia de datos 'VentaDto'.
 * @return Devuelve una ventaDto.
 * @since 1.0
 * @author Samuel Cortés, Yahya El Hadri, Javier Hernández, Raúl Fernández.
 */

fun Venta.toVentaDto():VentaDto{
    logger.debug { "Mapeando Venta ${this.id} a VentaDto" }
    return VentaDto(
        id= this.id.toString(),
        cliente = this.cliente.toUsuarioDto(),
        lineas = this.lineas.map { it.toLineaVentaDto() },
        total = this.total.toString(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}