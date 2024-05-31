package productos.butaca.validator

import org.example.demo.productos.butaca.dto.ButacaDto
import org.example.demo.productos.butaca.errors.ButacaError
import org.example.demo.productos.butaca.validator.ButacaValidator
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Estado
import org.example.demo.productos.models.Ocupacion
import org.example.demo.productos.models.Tipo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ButacaValidator {
    private val validator = ButacaValidator()

    @Test
    @DisplayName("Validación de un butaca correcta")
    fun casoOk(){
        val butaca = Butaca("A1", estado = Estado.ACTIVA, Tipo.VIP, ocupacion =  Ocupacion.LIBRE, precio = 7.0)
        val result = validator.validarButaca(butaca).value
        assertEquals("A1", result.id)
    }

    @Test
    @DisplayName("Validación de una butaca con id incorrecto")
    fun casoIdIncorrecto(){
        val butaca =  Butaca("AA", estado = Estado.ACTIVA, Tipo.VIP, ocupacion =  Ocupacion.LIBRE, precio = 5.0)
        val result = validator.validarButaca(butaca).error
        assertTrue(result is ButacaError.IdNoValido)
        assertEquals("El ID: ${butaca.id} no es valido, debe ser Letra Número: Ej A2", result.mensage)
    }

    @Test
    @DisplayName("Validación de una buataca Dto completa y correcta con ACTIVA, VIP, LIBRE")
    fun casoDtoOk(){
        val butaca = ButacaDto("A1", "ACTIVA", "VIP", "12.0", "LIBRE", "2024-05-21")
        val result = validator.validarButacaDto(butaca).isOk
        assertTrue(result)
    }

    @Test
    @DisplayName("Validación de una butaca Dto completa y correcta con OCUPADA, NORMAL, OCUPADA")
    fun casoDtoOk2(){
        val butca = ButacaDto("A1", "OCUPADA", "NORMAL", "12.0", "OCUPADA", "2024-05-21")
        val result = validator.validarButacaDto(butca).isOk
        assertTrue(result)
    }

    @Test
    @DisplayName("Validación de una butaca Dto completa y correcta con MANTENIMIENTO, NORMAL, LIBRE")
    fun casoDtoOk3(){
        val butca = ButacaDto("A1", "MANTENIMIENTO", "NORMAL", "12.0", "LIBRE", "2024-05-21")
        val result = validator.validarButacaDto(butca).isOk
        assertTrue(result)
    }

    @Test
    @DisplayName("Validación de una butaca Dto con id incorrecta")
    fun casoDtoIdIncorrecto(){
        val butaca = ButacaDto("AA", "ACTIVA", "VIP", "12.0", "LIBRE", "2024-05-21")
        val result = validator.validarButacaDto(butaca).error
        assertTrue(result is ButacaError.IdNoValido)
        assertEquals("El id no es válido, debe ser letra número EJ: A2", result.mensage)
    }

    @Test
    @DisplayName("Validación de una butaca con estado incorrecto")
    fun casoEstadoIncorrecto(){
        val butaca = ButacaDto("A1", "guapa", "VIP", "12.0", "LIBRE", "2024-05-21")
        val result = validator.validarButacaDto(butaca).error
        assertTrue(result is ButacaError.EstadoNoValido)
        assertEquals("El estado de la butaca no es válido debe ser ACTIVA o MANTENIMIENTO", result.mensage)
    }

    @Test
    @DisplayName("Validación de una butaca con tipo incorrecto")
    fun casoTipoIncorrecto(){
        val butaca = ButacaDto("A1", "ACTIVA", "guna", "12.0", "LIBRE", "2024-05-21")
        val result = validator.validarButacaDto(butaca).error
        assertEquals("El tipo de la butaca debe ser NORMAL o VIP", result.mensage)
        assertTrue(result is ButacaError.TipoNoValido)
    }

    @Test
    @DisplayName("Validación de una butaca con precio no válido")
    fun casoPrecioIncorrecto(){
        val butaca = ButacaDto("A1", "ACTIVA", "VIP", "lolol", "LIBRE", "2024-05-21")
        val result = validator.validarButacaDto(butaca).error
        assertTrue(result is ButacaError.PrecioNoValido)
        assertEquals("El precio de la butaca no es válido", result.mensage)
    }

    @Test
    @DisplayName("Validación de una butaca con formato de fecha no válido")
    fun casoFechaFormatoIncorrecto(){
        val butaca = ButacaDto("A1", "ACTIVA", "VIP", "12.0", "LIBRE", "2024/05/21")
        val result = validator.validarButacaDto(butaca).error
        assertTrue(result is ButacaError.FechaInvalido)
        assertEquals("La fecha introducida no es en el formato correcto Ej: yyyy-MM-dd", result.mensage)
    }

    @Test
    @DisplayName("Validación de una butaca con fecha no válida")
    fun casoFechaNoValida(){
        val butaca = ButacaDto("A1", "ACTIVA", "VIP", "12.0", "LIBRE", "lololo")
        val result = validator.validarButacaDto(butaca).error
        assertTrue(result is ButacaError.FechaInvalido)
        assertEquals("La fecha introducida no es en el formato correcto Ej: yyyy-MM-dd", result.mensage)
    }
}