package cache

import org.example.demo.cache.CacheImpl
import org.example.demo.usuarios.models.Cliente
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestCacheImpl {
    private lateinit var cache: CacheImpl<Int, Cliente>

    @BeforeEach
    fun initialize(){
        cache = CacheImpl(5)

    }

    @Test
    fun getAndPut() {
        val cliente = Cliente(
            id = 1,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )

        cache.put(1, cliente)
        val result = cache.get(1)

        assertTrue(result.isOk)
        assertEquals(cliente.id, result.value.id)
        assertEquals(cliente.nombre, result.value.nombre)
        assertEquals(cliente.apellidos, result.value.apellidos)
        assertEquals(cliente.email, result.value.email)
        assertEquals(cliente.contraseña, result.value.contraseña)
    }

    @Test
    fun getNoEstaEnCache() {
        val result = cache.get(2)
        assertTrue(result.isErr)
        assertEquals("No existe el valor en la cache", result.error.message)
    }

    @Test
    fun removeOk() {
        val cliente = Cliente(
            id = 1,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )


        cache.put(1, cliente)
        val resultadoRemove=cache.remove(1)
        val resultadoGet=cache.get(1)

        assertTrue(resultadoGet.isErr)
        assertEquals(cliente,resultadoRemove.value)
        assertTrue(resultadoGet.isErr)
    }

    @Test
    fun removeNoEstaEnCache() {

        val resul=cache.remove(1)

        assertTrue(resul.isErr)
        assertEquals("No existe el valor en la cache",resul.error.message)

    }

    @Test
    fun clear() {
        val cliente = Cliente(
            id = 1,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )
        cache.put(1,cliente)
        cache.clear()
        val resul = cache.get(1)

        assertTrue(resul.isErr)
    }

    @Test
    fun removeUltimo(){
        val cliente = Cliente(
            id = 1,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )

        val cliente2 = Cliente(
            id = 2,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )

        val cliente3 = Cliente(
            id = 3,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )

        val cliente4 = Cliente(
            id = 4,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )

        val cliente5 = Cliente(
            id = 5,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )
        val cliente6 = Cliente(
            id = 6,
            nombre = "nombre",
            apellidos = "apellido",
            contraseña = "123456789",
            email = "nombreapellido@gmail.com"
        )

        cache.put(1,cliente)
        cache.put(2,cliente2)
        cache.put(3,cliente3)
        cache.put(4,cliente4)
        cache.put(5,cliente5)
        cache.put(4,cliente4)
        cache.put(6,cliente6)

        assertTrue(cache.get(1).isErr)
        assertTrue(cache.get(2).isOk)
        assertTrue(cache.get(3).isOk)
        assertTrue(cache.get(4).isOk)
        assertTrue(cache.get(5).isOk)
        assertTrue(cache.get(6).isOk)
    }
}