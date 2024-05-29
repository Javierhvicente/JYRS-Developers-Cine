package usuarios.repositories

import org.example.demo.database.SqlDelightManager
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.repositories.UserRepositoryImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AUserRepositoryTest {

    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var dbManager: SqlDelightManager

    @BeforeEach
    fun setUpAll(){
        println("Iniciando test...")
        dbManager = SqlDelightManager
        dbManager.initialize()
        userRepository = UserRepositoryImpl()
    }

    @AfterEach
    fun tearDown(){
        dbManager.clearData()
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
                apellidos = "ApellidoTest2",
                contraseña = "P@ssw0rd!2021",
                email = "ejemplo.usuario4@example.com"
            )
        )

        val cliente = userRepository.findByEmail("ejemplo.usuario4@example.com")
        assertEquals("ejemplo.usuario4@example.com", cliente?.email)
    }

    @Test
    fun cambioContraseña(){
        val cliente = userRepository.save(
            Cliente(
                id = 4,
                nombre = "NombreTest4",
                apellidos = "ApellidoTest2",
                contraseña = "P@ssw0rd!2021",
                email = "ejemplo.usuario4@example.com"
            )
        )
        val nuevaContra = "P@ssw0rd!2021NUEVA"
        cliente.contraseña = nuevaContra

        assertEquals("P@ssw0rd!2021NUEVA", cliente.contraseña)
    }

}