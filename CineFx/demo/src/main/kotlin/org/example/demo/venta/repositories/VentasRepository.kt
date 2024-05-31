package org.example.demo.venta.repositories

import org.example.demo.venta.models.Venta
import java.util.*

interface VentasRepository {
    fun getById(id: UUID): Venta?
    fun save(venta: Venta): Venta
    fun getAll(): List<Venta>
    fun update(id: UUID, venta: Venta): Venta?
    fun delete(id: UUID): Venta?
}