package org.example.demo.productos.butaca.mappers

import database.ButacaEntity
import org.example.demo.locale.returnDateTimeString
import org.example.demo.productos.butaca.dto.ButacaDto
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Estado
import org.example.demo.productos.models.Ocupacion
import org.example.demo.productos.models.Tipo
import org.lighthousegames.logging.logging
import java.time.LocalDate

/**
 * Mapea una ButacaEntity en una Butaca.
 * @return Butaca
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */
val logger= logging()
fun ButacaEntity.toButaca():Butaca{
    logger.debug { "Pasando ButacaEntity ${this.id} a Butaca" }
    var _id=this.id
    var _estado:Estado?=null
    var _tipo:Tipo?=null
    var _ocupacion:Ocupacion?=null
    when(this.tipo){
        "NORMAL" -> _tipo = Tipo.NORMAL
        "VIP"-> _tipo = Tipo.VIP
    }
    when(this.estado){
        "ACTIVA" -> _estado = Estado.ACTIVA
        "MANTENIMIENTO"-> _estado=Estado.MANTENIMIENTO
        "OCUPADA" -> _estado = Estado.OCUPADA
    }
    when(this.ocupacion){
        "LIBRE"->_ocupacion=Ocupacion.LIBRE
        "SELECCIONADA"->_ocupacion=Ocupacion.SELECCIONADA
        "INACTIVA"->_ocupacion=Ocupacion.INACTIVA
        "OCUPADA"->_ocupacion=Ocupacion.OCUPADA
        //else->_ocupacion=Ocupacion.SELECCIONADA

    }
    return Butaca(id = _id, estado = _estado!!, tipo = _tipo!!, create = this.createAt.returnDateTimeString(), precio = this.precio.toDouble(), ocupacion = _ocupacion!!)
}

/**
 * Mapea un ButacaDto en una Butaca.
 * @return Butaca
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

fun ButacaDto.toButaca():Butaca{
    logger.debug { "Pasando ButacaDto ${this.id} a Butaca" }
    var _tipo:Tipo?=null
    var _ocupacion:Ocupacion?=null
    when(this.tipo){
        "VIP"->_tipo = Tipo.VIP
        "NORMAL" -> _tipo = Tipo.NORMAL
    }
    var _estado:Estado?=null
    when(this.estado) {
        "ACTIVA" -> _estado = Estado.ACTIVA
        "MANTENIMIENTO" -> _estado = Estado.MANTENIMIENTO
        "OCUPADA" -> _estado = Estado.OCUPADA
    }
    when(this.ocupacion){
        "LIBRE"->_ocupacion= Ocupacion.LIBRE
        "SELECCIONADA"->_ocupacion= Ocupacion.SELECCIONADA
        "INACTIVA"->_ocupacion= Ocupacion.INACTIVA
        "OCUPADA"->_ocupacion= Ocupacion.OCUPADA

    }
    return Butaca(id = this.id, estado = _estado!!, tipo = _tipo!!, precio = this.precio.toDouble(), ocupacion = _ocupacion!!)
}

/**
 * Mapea un Butaca en una ButacaDto.
 * @return ButacaDto
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */


fun Butaca.toButacaDto(): ButacaDto {
    logger.debug { "Pasando Butaca ${this.id} a ButacaDto" }
    var _tipo:String?=null
    when(this.tipo){
        Tipo.VIP -> _tipo ="VIP"
        Tipo.NORMAL -> _tipo = "NORMAL"
    }
    var _estado:String?=null
    when(this.estado){
        Estado.ACTIVA -> _estado = "ACTIVA"
        Estado.MANTENIMIENTO -> _estado = "MANTENIMIENTO"
        Estado.OCUPADA-> _estado = "OCUPADA"
    }
    var _ocupacion:String?=null
    when(this.ocupacion){
        Ocupacion.LIBRE-> _ocupacion = "LIBRE"
        Ocupacion.INACTIVA-> _ocupacion = "INACTIVA"
        Ocupacion.SELECCIONADA-> _ocupacion = "RESERVA"
        Ocupacion.OCUPADA -> _ocupacion = "OCUPADA"

    }
    return ButacaDto(this.id,_estado,_tipo,this.precio.toString(),_ocupacion,this.create.toString())
}