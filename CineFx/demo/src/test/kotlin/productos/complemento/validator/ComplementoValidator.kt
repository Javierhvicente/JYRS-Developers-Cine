package productos.complemento.validator

import org.example.demo.productos.complementos.dto.ComplementoDto
import org.example.demo.productos.complementos.errors.ComplementoError
import org.example.demo.productos.complementos.validators.validateComplemento
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComplementoValidator {

    @Test
    @DisplayName("Caso correcto de un complemento de tipo COMIDA FRUTOS SECOS")
    fun casoOkFrutosSecos(){
        val complemento = ComplementoDto("COMIDA", "FRUTOS SECOS", "6.0", "tuerca.png")
        val result = validateComplemento(complemento).isOk
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso correcto de un complemento de tipo COMIDA PATATAS")
    fun casoOkPatatas(){
        val complemento = ComplementoDto("COMIDA", "PATATAS", "6.0", "patatas-fritas.png")
        val result = validateComplemento(complemento).isOk
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso correcto de un complemento de tipo COMIDA PALOMITAS")
    fun casoOkPalomitas(){
        val complemento = ComplementoDto("COMIDA", "PALOMITAS", "6.0", "palomitas-de.maiz.png")
        val result = validateComplemento(complemento).isOk
        assertTrue(result)
    }
    @Test
    @DisplayName("Caso correcto de un complemento de tipo BEBIDA AGUA")
    fun casoOkAgua(){
        val complemento = ComplementoDto("BEBIDA", "AGUA", "6.0", "agua.png")
        val result = validateComplemento(complemento).isOk
        assertTrue(result)
    }


    @Test
    @DisplayName("Caso correcto de un complemento de tipo BEBIDA REFRESCO")
    fun casoOkRefresco(){
        val complemento = ComplementoDto("BEBIDA", "REFRESCO", "6.0","soda.png")
        val result = validateComplemento(complemento).isOk
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso incorrecto de complemento tipo vacio")
    fun casoIncorrectoTipoVacio(){
        val complemento = ComplementoDto("", "REFRESCO", "6.0", "soda.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso incorrecto de nombre vacio")
    fun casoIncorrectoNombreVacio(){
        val complemento = ComplementoDto("BEBIDA", "", "6.0", "agua.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso incorrecto de precio vacío")
    fun casoIncorrectoPrecioVacio(){
        val complemento = ComplementoDto("BEBIDA", "AGUA", "", "agua.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso incorrecto tipo mal puesto")
    fun casoIncorrectoMalTipo(){
        val complemento = ComplementoDto("flipendo", "AGUA", "6.0", "agua.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso incorrecto nombre mal puesto")
    fun casoIncorrectoMalNombre(){
        val complemento = ComplementoDto("BEBIDA", "flipendo", "6.0", "soda.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso incorrecto precio mal puesto")
    fun casoIncorrectoMalPrecio(){
        val complemento = ComplementoDto("BEBIDA", "AGUA", "flipendo", "agua.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso concordancia COMIDA con AGUA daría de dar error")
    fun casoConcordanciaComidaAgua(){
        val complemento = ComplementoDto("COMIDA", "AGUA", "6.0", "agua")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso concordancia COMIDA con REFRESCO daría error")
    fun casoConcordanciaComidaRefresoc(){
        val complemento = ComplementoDto("COMIDA", "REFRESCO", "6.0", "tuerca.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso concordancia BEBIDA con PATATAS daría error")
    fun casoConcordanciaBebidaPatatas(){
        val complemento = ComplementoDto("BEBIDA", "PATATAS", "6.0", "agua.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso concordancia BEBIDA con PALOMITAS daría error")
    fun casoConcordanciaBebidaPalomitas(){
        val complemento = ComplementoDto("BEBIDA", "PALOMITAS", "6.0", "agua.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }

    @Test
    @DisplayName("Caso concordancia BEBIDA con FRUTOS SECOS daría error")
    fun casoConcordanciaBebidaFutosSecos(){
        val complemento = ComplementoDto("BEBIDA", "FRUTOS SECOS", "6.0", "agua.png")
        val result = validateComplemento(complemento).isErr
        assertTrue(result)
    }


}