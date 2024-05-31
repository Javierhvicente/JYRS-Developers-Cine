package usuarios.repositories

import database.DatabaseQueries
import org.example.demo.config.Config
import org.example.demo.database.SqlDelightManager
import org.example.demo.locale.encodeToBase64
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.repositories.UserRepositoryImpl
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.lighthousegames.logging.logging
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import kotlin.io.path.Path

private val logger = logging()
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Mock
    lateinit var config: Config
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var dbManager: SqlDelightManager

    @BeforeEach
    fun setUpAll(){
        whenever(config.databaseInit).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)
        println("Iniciando test...")
        dbManager = SqlDelightManager(Config)
        databaseQueries = dbManager.databaseQueries
        userRepository = UserRepositoryImpl(dbManager)
        dbManager.databaseQueries.deleteAllClientes()
    }

    @AfterEach
    fun tearDown(){
        dbManager.databaseQueries.deleteAllClientes()
    }



    @Test
    fun findAll(){
        val cliente = Cliente(
            id = 2,
            nombre = "NombreTest1",
            apellidos = "ApellidoTest1",
            contraseña = "P@ssw0rd!2021",
            email = "ejemplo.usuario1@example.com"
        )
        userRepository.save(cliente)
        val result = userRepository.getAllClientes()
        assertEquals(1, result.size)
    }

    @Test
    fun findById(){
        userRepository.save(
            Cliente(
                id = 2,
                nombre = "NombreTest2",
                apellidos = "ApellidoTest2",
                contraseña = "P@ssw0rd!2021",
                email = "ejemplo.usuario2@example.com"
            )
        )

        val cliente = userRepository.findById(2)

        assertEquals(2, cliente?.id)
        assertEquals("NombreTest2", cliente?.nombre)
        assertEquals("ApellidoTest2", cliente?.apellidos)
    }

    @Test
    fun findByIdNotFound(){
        val cliente = Cliente( id = 39,
            nombre = "PElelepeleple",
            apellidos = "adnajdbasjbdasjhbd",
            contraseña = "P@ssw0rd!2021",
            email = "ejemplo.usuario2@example.com"
        )
        val result = userRepository.findById(33)
        assertEquals(null, result)

    }


    @Test
    fun save(){
        val cliente = userRepository.save(
            Cliente(
                id = 3,
                nombre = "NombreTest3",
                apellidos = "ApellidoTest3",
                contraseña = "P@ssw0rd!2021",
                email = "ejemplo.usuario3@example.com"
            )
        )
        assertEquals(3, cliente?.id)
        assertEquals("NombreTest3", cliente?.nombre)
        assertEquals("ApellidoTest3", cliente?.apellidos)
    }

    @Test
    fun findByEmail(){
        userRepository.save(
            Cliente(
                id = 4,
                nombre = "NombreTest4",
                apellidos = "ApellidoTest4",
                contraseña = "P@ssw0rd!2021",
                email = "ejemplo.usuario4@example.com"
            )
        )
        val cliente = userRepository.findByEmail("ejemplo.usuario4@example.com")
        assertEquals("ejemplo.usuario4@example.com", cliente?.email)
    }

    @Test
    fun findByEmailNotFound(){
        val cliente = Cliente( id = 39,
            nombre = "PElelepeleple",
            apellidos = "adnajdbasjbdasjhbd",
            contraseña = "P@ssw0rd!2021",
            email = "lolololoManolololo@gmail.com"
        )
        val result = userRepository.findByEmail(cliente.email)
        assertEquals(null, result)

    }

    @Test
    fun cambioContraseña(){
        val cliente = Cliente(
                id = 5,
                nombre = "NombreTest5",
                apellidos = "ApellidoTest5",
                contraseña = "0rd!2021po09",
                email = "ejemplo.usuario5@example.com"
        )
        userRepository.save(cliente)
        val nuevaContra = "0rd!202NUEVA"
        val operation = userRepository.cambioContraseña(cliente.email, nuevaContra)
        val result = userRepository.findByEmail(cliente.email)
        assertEquals(nuevaContra.encodeToBase64(), result?.contraseña)
    }

    @Test
    fun cambioContraseñaFail(){
        val cliente = Cliente( id = 39,
            nombre = "PElelepeleple",
            apellidos = "adnajdbasjbdasjhbd",
            contraseña = "P@ssw0rd!2021",
            email = "lolololoManolololo@gmail.com"
        )
        val result = userRepository.cambioContraseña(cliente.email, "P@ssw0rd!2022")
        assertEquals(null, result)
    }

}