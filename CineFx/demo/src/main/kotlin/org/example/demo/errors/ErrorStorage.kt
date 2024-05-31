package org.example.demo.errors

sealed class ErrorStorage(message:String) {
    class FicheroError(message: String):ErrorStorage(message)
}