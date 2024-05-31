    package org.example.demo.locale

import kotlin.io.encoding.Base64

/**
 * Codificamos una cadena en Base64.
 * @return Devuelve una representación codificada en base64.
 * @since 1.0
 * @author Raúl Fernández, Javier Hernández, Samuel Cortés, Yahya El Hadri
 */

fun String.encodeToBase64(): String{
    val bytes = this.toByteArray()
    return String(java.util.Base64.getEncoder().encode(bytes))
}