package productos.butaca.repositories


import org.example.demo.config.Config
import org.example.demo.database.SqlDelightManager
import org.example.demo.productos.butaca.repositories.ButacaRepositoryImpl
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Estado
import org.example.demo.productos.models.Ocupacion
import org.example.demo.productos.models.Tipo
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.lighthousegames.logging.logging
private val logger = logging()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestButacaRepository {
    private lateinit var dbManager : SqlDelightManager
    private lateinit var butacaRepository : ButacaRepositoryImpl

    @BeforeEach
    fun setUpAll(){
        println("Iniciando test...")
        dbManager = SqlDelightManager(Config)
        logger.debug { "inicializado" }
        butacaRepository = ButacaRepositoryImpl(dbManager)
    }

    @Test
    fun findAll() {
        val butaca = butacaRepository.findAll()

        assertEquals(0, butaca.size)
    }

    @Test
    fun findById() {
         butacaRepository.save(
            Butaca(
                id = "A1",
                tipo = Tipo.NORMAL,
                estado = Estado.ACTIVA,
                precio = 5.0,
                ocupacion = Ocupacion.LIBRE
            )
        )
        val butaca = butacaRepository.findById("A1")

        assertEquals("A1", butaca?.id)
        assertEquals(Tipo.NORMAL, butaca?.tipo)
        assertEquals(Estado.ACTIVA, butaca?.estado)

    }

    @Test
    fun findByIdNotFound() {
        val butaca = butacaRepository.findById("5")

        assertEquals(null, butaca)
    }

    @Test
    fun save() {
        val butaca = butacaRepository.save(
            Butaca(
                id = "A1",
                tipo = Tipo.NORMAL,
                estado = Estado.ACTIVA,
                precio = 5.0,
                ocupacion = Ocupacion.LIBRE
            )
        )

        assertEquals("A1", butaca.id)
        assertEquals(Tipo.NORMAL, butaca.tipo)
        assertEquals(Estado.ACTIVA, butaca.estado)
    }

    @Test
    fun update() {
        val butaca = Butaca(
            id = "A1",
            tipo = Tipo.NORMAL,
            estado = Estado.ACTIVA,
            precio = 5.0,
            ocupacion = Ocupacion.LIBRE
        )
        butacaRepository.save(butaca)
        val butacaActualizada = Butaca(id = "A1",
            tipo = Tipo.VIP,
            estado = Estado.ACTIVA,
            precio = 8.0,
            ocupacion = Ocupacion.LIBRE)

        val result = butacaRepository.update(
            "A1",
            butacaActualizada
        )

        assertEquals("A1", result?.id)
        assertEquals(Tipo.VIP, result?.tipo)
        assertEquals(8.0, result?.precio)
    }

    @Test
    fun updateNotFound() {
        val butaca = butacaRepository.update(
            "15",
            Butaca(
                id = "A1",
                estado = Estado.ACTIVA,
                tipo = Tipo.VIP,
                ocupacion =  Ocupacion.LIBRE,
                precio = 5.0
            )

        )

        assertEquals(null, butaca)
    }

    @Test
    fun deleteAll(){
        val result = butacaRepository.deleteAll()
        assertEquals(0, result.)
    }
}