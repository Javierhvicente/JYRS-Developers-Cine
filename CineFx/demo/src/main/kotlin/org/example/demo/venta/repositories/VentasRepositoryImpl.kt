package org.example.demo.venta.repositories

import database.LineaVentaEntityButaca
import database.LineaVentaEntityComplemento
import org.example.demo.database.SqlDelightManager
import org.example.demo.productos.butaca.repositories.ButacaRepository
import org.example.demo.productos.complementos.repositories.ComplementoRepository
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Complemento
import org.example.demo.usuarios.repositories.UserRepository
import org.example.demo.venta.mappers.toLineaVenta
import org.example.demo.venta.mappers.toVenta
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import org.lighthousegames.logging.logging
import java.util.*

private val logger = logging()
class VentasRepositoryImpl(
    private val dbManager: SqlDelightManager,
    private val clienteRepository: UserRepository,
    private val butacasRepository: ButacaRepository,
    private val complementosRepository: ComplementoRepository,
): VentasRepository {
    private val db = dbManager.databaseQueries

    /**
     * Obtenemos una venta por su id desde una base de datos.
     * @return Y devuelve un ok si la operación fue exitosa y si no devuelve un null.
     * @param id
     * @since 1.0
     * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
     */

    override fun getById(id: UUID): Venta? {
        logger.debug { "Obteniendo venta por id: $id" }
        if (db.existsVenta(id.toString()).executeAsOne()){
            val venta = db.selectVentaById(id.toString()).executeAsOne()
            val cliente = clienteRepository.findById(venta.cliente_id.toLong())!!
            val lineas = allLineas(venta.id)
            return venta.toVenta(cliente,lineas)
        }
        return null
    }

    /**
     * Función para recuperar todas las líneas de venta asociadas a una venta especifícada por su ID.
     * @return Devuelve una lista de estas líneas y si no salta un error
     * @param venta_id
     * @since 1.0
     * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
     */

    private fun allLineas(venta_id:String):List<LineaVenta>{
        val lineas = mutableListOf<LineaVenta>()
        db.selectAllLineaVentaEntityButacaByVentaId(venta_id).executeAsList()
            .map { it.toLineaVenta(butacasRepository.findById(it.butaca_id)!!) }.forEach { lineas.add(it) }
        db.selectAllLineaVentaEntityComplementoByVentaId(venta_id).executeAsList()
            .map { it.toLineaVenta(complementosRepository.findById(it.complemento_id)!!) }
            .forEach { lineas.add(it) }
        return lineas
    }

    /**
     * Función para guardar una venta.
     * @return Devuelve una venta.
     * @param venta
     * @since 1.0
     * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
     */

    override fun save(venta: Venta): Venta {
        logger.debug { "Guardando venta: $venta" }
        db.transaction {
            db.insertVenta(
                id = venta.id.toString(),
                cliente_id = venta.cliente.id.toString(),
                total = venta.total,
                created_at = venta.createdAt.toString(),
                updated_at = venta.updatedAt.toString()
            )
        }
        venta.lineas.forEach {
            when(it.producto){
                is Complemento ->{
                    db.transaction {
                        db.insertLineaVentaEntityComplemento(
                            id = it.id.toString(),
                            venta_id = venta.id.toString(),
                            complemento_id = it.producto.id,
                            cantidad = it.cantidad.toLong(),
                            precio = it.precio,
                            created_at = it.createdAt.toString(),
                            updated_at =it.updatedAt.toString()
                        )
                    }
                }
                is Butaca->{
                    db.transaction {
                        db.insertLineaVentaEntityButaca(
                            id = it.id.toString(),
                            venta_id = venta.id.toString(),
                            butaca_id = it.producto.id,
                            cantidad = it.cantidad.toLong(),
                            precio = it.precio,
                            created_at = it.createdAt.toString(),
                            updated_at =it.updatedAt.toString()
                        )
                    }
                }
            }
        }
        return venta
    }

    /**
     * Obtenemos todas las ventas registradas en la base de datos.
     * @return Devolvemos la venta registrada que hemos buscado.
     * @since 1.0
     * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
     */

    override fun getAll(): List<Venta> {
        logger.debug { "Obteniendo todas la ventas registradas" }
        return db.selectAllVentas().executeAsList().map {
            it.toVenta(
                cliente = clienteRepository.findById(it.cliente_id.toLong())!!,
                lineas = allLineas(it.id)
            )
        }
    }

    /**
     * Actualizamos una venta existente en la base de datos.
     * @param id
     * @param venta
     * @return Si la venta existe devuelve la venta, si no devuelve null.
     * @since 1.0
     * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
     */

    override fun update(id: UUID, venta: Venta): Venta? {
        logger.debug { "Acualizando venta por id: $id" }
        getById(id)?.let {
            db.transaction {
                db.updateVenta(
                    id = venta.id.toString(),
                    cliente_id = venta.cliente.id.toString(),
                    total = venta.total,
                    updated_at = venta.updatedAt.toString(),
                    is_deleted = 0
                )
            }
            return venta
        }
        return null
    }

    /**
     * Deleteamos una venta existente en la base de datos.
     * @param id
     * @return Devolvemos la venta eliminada si la operación fue exitosa y si no manda un error..
     * @since 1.0
     * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
     */

    override fun delete(id: UUID): Venta? {
        logger.debug { "Borrando venta por id: $id" }
        val delted = getById(id)
        getById(id)?.let {
            db.transaction {
                db.deleteVenta(id.toString())
            }
            return delted
        }
       return null
    }


}