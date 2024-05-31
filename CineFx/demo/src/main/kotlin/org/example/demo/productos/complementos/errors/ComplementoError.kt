package org.example.demo.productos.complementos.errors

sealed class ComplementoError(val mensaje: String) {
    class FicheroNoValido(mensage: String): ComplementoError(mensage)
    class ComplementoNoEncontrado(mensage: String): ComplementoError(mensage)
    class ComplementoNoActualizado(mensage: String): ComplementoError(mensage)
    class ComplememntoImageError(mensage: String):ComplementoError(mensage)
    class ValidationError(mensage: String): ComplementoError(mensage)

}