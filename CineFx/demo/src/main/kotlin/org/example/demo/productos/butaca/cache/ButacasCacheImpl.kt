package org.example.demo.productos.butaca.cache

import org.example.demo.cache.CacheImpl
import org.example.demo.productos.models.Butaca

class ButacasCacheImpl(size:Int): CacheImpl<String, Butaca>(size)