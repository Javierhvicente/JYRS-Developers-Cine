package productos.complemento.repositories

import org.example.demo.config.Config
import org.example.demo.database.SqlDelightManager
import org.example.demo.productos.butaca.repositories.ButacaRepositoryImpl
import org.example.demo.productos.complementos.repositories.ComplementoRepositoryImpl
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.CategoriaBebida
import org.example.demo.productos.models.CategoriaComida
import org.example.demo.productos.models.Comida
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.lighthousegames.logging.logging
private val logger = logging()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestComplementoRepository {
    private lateinit var dbManager : SqlDelightManager
    private lateinit var complementoRepository : ComplementoRepositoryImpl

    @BeforeEach
    fun setUpAll(){
        println("Iniciando test...")
        dbManager = SqlDelightManager(Config)
        logger.debug { "inicializado" }
        complementoRepository = ComplementoRepositoryImpl(dbManager)
    }

    @AfterAll
    fun tearDown() {
        dbManager.clearData()
    }

    @Test
    fun findAll() {
        val complementos = complementoRepository.findAll()

        assertEquals(0, complementos.size)
    }

    @Test
    fun findById() {
        complementoRepository.save(Comida("FRUTOS SECOS",CategoriaComida.FRUTOSSECOS.toString(), imagen = "tuerca.png", precio = 2.5))
        val complemento = complementoRepository.findById("FRUTOS SECOS")

        assertEquals("FRUTOS SECOS", complemento?.id)
        assertEquals(CategoriaComida.FRUTOSSECOS, complemento?.tipo)
    }

    @Test
    fun findByIdNotFound() {
        val complemento = complementoRepository.findById("5")

        assertEquals(null, complemento)
    }

    @Test
    fun save() {
        val complemento = complementoRepository.save(
            Bebida(
                id = "AGUA",
                imagen = "agua.png",
                tipo = CategoriaBebida.AGUA.toString(),
                precio = 2.0
            )
        )

        assertEquals("AGUA", complemento.id)
        assertEquals(CategoriaBebida.AGUA, complemento.tipo)
    }

    @Test
    fun update() {
        complementoRepository.save(Bebida("AGUA", CategoriaBebida.AGUA.toString(), imagen = "agua.png", precio = 2.0))
        val complemento = complementoRepository.update(
            "AGUA",
            Bebida(
                id = "AGUA",
                tipo = CategoriaBebida.AGUA.toString(),
                imagen = "agua.png",
                precio = 3.0
            ),
            imagen = "agua.png"
        )

        assertEquals("AGUA", complemento?.id)
        assertEquals(CategoriaBebida.AGUA, complemento?.tipo)
        assertEquals(3.0, complemento?.precio)
    }

    @Test
    fun updateNotFound() {
        val complemento = complementoRepository.update(
            "15",
            Bebida(
                id = "REFRESCO",
                tipo = CategoriaBebida.AGUA.toString(),
                imagen = "agua.png",
                precio = 3.0
            ),
            imagen = "agua.png"
        )

        assertEquals(null, complemento)
    }

    @Test
    fun delete() {
        complementoRepository.save(Bebida("AGUA", CategoriaBebida.AGUA.toString(), imagen = "agua.png", precio = 3.0))
        val complemento = complementoRepository.delete("AGUA")
        assertEquals("AGUA", complemento?.id)
    }

    @Test
    fun deleteNotFound() {
        val complemento = complementoRepository.delete("20")
        assertEquals(null, complemento)
    }
}