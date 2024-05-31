package org.example.demo.locale

import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Convertimos LocalDateTime en una cadena de texto con un formato especifíco.
 * @return Devuelve una representación formateada en texto de la fecha y hora.
 * @since 1.0
 * @author Raúl Fernández, Javier Hernández, Samuel Cortés, Yahya El Hadri
 */


fun LocalDate.toShortSpanishFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return this.format(formatter)
}

/**
 * Convertimos un valor 'Double' en una cadena de texto formateada como una cantidad de dinero de dinero en la moneda indicada en el sistema.
 * @return Devuelve una representación formateada en texto del valor 'Double'.
 * @since 1.0
 * @author Raúl Fernández, Javier Hernández, Samuel Cortés, Yahya El Hadri
 */

fun Double.toDefaultMoneyString():String{
    return NumberFormat.getCurrencyInstance(Locale("es", "ES")).format(this)
}
fun String.returnDateTimeString():LocalDate{
    val cadena=this.split("/")
    return LocalDate.of(cadena[2].toInt(),cadena[1].toInt(),cadena[0].toInt())
}

fun LocalDateTime.toShortSpanishFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return this.format(formatter)
}