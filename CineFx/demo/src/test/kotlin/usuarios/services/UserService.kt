package usuarios.services

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.demo.cache.errors.CacheError
import org.example.demo.database.SqlDelightManager
import org.example.demo.locale.encodeToBase64
import org.example.demo.usuarios.cache.CacheUsuario
import org.example.demo.usuarios.errors.UserError
import org.example.demo.usuarios.models.Admin
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.repositories.UserRepository
import org.example.demo.usuarios.services.UserServiceImpl
import org.example.demo.usuarios.validator.validateUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
@ExtendWith(MockitoExtension::class)
class UserService {
    @Mock
    lateinit var repository: UserRepository
    @Mock
    lateinit var cacheUsuario: CacheUsuario
    @InjectMocks
    lateinit var serviceImpl: UserServiceImpl

    @Test
    fun findAll(){
        val cliente = listOf(Cliente(1, "NombreTest1", "ApellidoTest1", "P@ssw0rd!2021", "ejemplo1@gmail.com"))
        whenever(repository.getAllClientes()).thenReturn(cliente)
        val result = serviceImpl.findAll()
        assertAll(
            { assertEquals(1, result.value.size)},
            { assertEquals("NombreTest1", result.value[0].nombre)}
        )
        verify(repository, times(1)).getAllClientes()
    }

    @Test
    fun getById(){
        val cliente = Cliente(id = -1L, "Manolo", "Escobar", "&2ht1XL$1t1X", "manolo123@gmail.es")
        whenever(cacheUsuario.get(-1L)).thenReturn(Ok(cliente))
        val result = serviceImpl.findById(-1L)
        assertAll(
            { assertTrue(result.isOk)},
            { assertEquals(result.value, cliente)}
        )
        verify(cacheUsuario, times(1)).get(-1L)
        verify(repository, times(0)).findById(-1L)
        verify(cacheUsuario, times(0)).put(-1L, cliente)
    }

    @Test
    fun saveOk(){
        val cliente = Cliente(id = -1L, "Manolo", "Lama", "123456789Aa@", "manololama@gmail.com")
        whenever(repository.save(cliente)).thenReturn(cliente)
        val result = serviceImpl.save(cliente)
        assertAll(
            { assertTrue(result.isOk)},
            { assertEquals(cliente.nombre, result.value.nombre)},
            { assertEquals(cliente.contraseña, result.value.contraseña)}
        )
        verify(repository, times(1)).save(cliente)
    }

    @Test
    fun saveNotOk(){
        val cliente = Cliente(id = -1L, "Manolo", "Lama", "123", "manololama@gmail.com")
        val result = serviceImpl.save(cliente).error
        assertTrue(result is UserError.ValidateProblem)
        assertEquals("No se ha podido guardar debido a un error de valdiación", result.message)
    }

    @Test
    fun getByEmail(){
        val cliente = Cliente(id = -1L, "Manolo", "Escobar", "&2ht1XL$1t1X", "manolo123@gmail.es")
        whenever(repository.findByEmail("manolo123@gmail.es")).thenReturn(cliente)
        val result = serviceImpl.findByEmail("manolo123@gmail.es")
        assertAll(
            { assertTrue(result.isOk) },
            { assertEquals(result.value, cliente)}
        )
        verify(repository, times(1)).findByEmail(cliente.email)
    }

    @Test
    fun getByEmailFail(){
        val cliente = Cliente(id = -1L, "Manolo", "Escobar", "&2ht1XL$1t1X", "manolo123@gmail.es")
        val message = "No se ha encontrado usuario con email ${cliente.email}"
        whenever(repository.findByEmail(cliente.email)).thenReturn(null)
        val result = serviceImpl.findByEmail("manolo123@gmail.es").error
        assertAll(
            { assertTrue(result is UserError.UserNotFound)},
            { assertEquals(message, result.message)}
        )
        verify(repository, times(1)).findByEmail(cliente.email)
    }

}
