package org.example.demo.productos.butaca.errors


sealed class ButacaError(val mensage:String) {
    class IdNoValido(mensage: String):ButacaError(mensage)
    class EstadoNoValido(message: String): ButacaError(message)
    class TipoNoValido(mensage: String): ButacaError(mensage)
    class OcupacionNoValiado(mensage: String): ButacaError(mensage)
    class PrecioNoValido(mensage: String): ButacaError(mensage)
    class FechaInvalido(mensage: String):ButacaError(mensage)
    class FicheroNoValido(mensage: String):ButacaError(mensage)
    class ButacaNoEncontrada(mensage: String):ButacaError(mensage)
    class ButacaNoBorradas(mensage: String):ButacaError(mensage)
    class ButacaNoActualizadas(mensage: String):ButacaError(mensage)
    class StorageError(mensage: String):ButacaError(mensage)
}