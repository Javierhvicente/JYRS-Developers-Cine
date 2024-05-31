package org.example.demo.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.DatabaseQueries
import org.example.database.AppDatabase
import org.example.demo.config.Config

import org.lighthousegames.logging.logging



private val logger = logging()

/**
 * Para manejar la base de datos.
 * @property databaseQueries
 * @author Yahya El Hadri, Samuel Cortés, Raúl Fernández, Javier Hernández
 * @since 1.0
 */

class SqlDelightManager(
    private val config: Config
) {
    val databaseQueries: DatabaseQueries by lazy { initQueries() }

    init {
        logger.debug { "Inicializando la base de datos con SqlDelight" }
        if (config.databaseRemoveData) {
            clearData()
        }
        initialize()
    }

    /**
     * Inicia el gestor de base de datos.
     * @return databaseQueries
     * @author Yahya El Hadri, Samuel Cortés, Raúl Fernández, Javier Hernández
     * @since 1.0
     */

     fun initQueries(): DatabaseQueries {
        val driver = if (Config.databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${Config.databaseUrl}" }
            JdbcSqliteDriver(Config.databaseUrl)
        }

        AppDatabase.Schema.create(driver)
        val database = AppDatabase(driver)

        return database.databaseQueries
    }

    /**
     * Borramos los datos de la base de datos.
     * @author Yahya El Hadri, Samuel Cortés, Raúl Fernández, Javier Hernández
     * @since 1.0
     */

    fun clearData() {
        logger.debug { "Borrando datos de la base de datos" }
        databaseQueries.transaction {
            //databaseQueries.deleteAllUsers()
            databaseQueries.deleteAllButacaEntity()
            databaseQueries.deleteAllComplemetoEntity()
            databaseQueries.removeAllVentas()
            databaseQueries.removeAllLineaVentaEntityButaca()
            databaseQueries.removeAllLineaVentaEntityComplemento()
        }
    }

    /**
     * Inicia el gestor de base de datos.
     * @return databaseQueries
     * @author Yahya El Hadri, Samuel Cortés, Raúl Fernández, Javier Hernández
     * @since 1.0
     */

    private fun initialize() {
        logger.debug { "SqlDeLightClient.initialize()" }
        if (Config.databaseInit) {
            databaseQueries.transaction {
                databaseQueries.deleteAdmin()
                databaseQueries.InsertTheAdmin()
            }
        }
    }
}