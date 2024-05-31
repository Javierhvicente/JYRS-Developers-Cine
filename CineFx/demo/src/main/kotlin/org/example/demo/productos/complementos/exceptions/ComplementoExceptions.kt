package org.example.demo.productos.complementos.exceptions

sealed class ComplementoException(message: String): Exception(message) {
    class TipoInvalido(message: String): ComplementoException(message)
}