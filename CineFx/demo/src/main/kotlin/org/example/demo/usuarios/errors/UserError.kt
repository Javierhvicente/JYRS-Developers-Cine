package org.example.demo.usuarios.errors


sealed class UserError(val message: String) {
    class UserNotFound(message: String): UserError(message)
    class ValidateProblem(message: String): UserError(message)
    class StorageError(message: String):UserError(message)
}