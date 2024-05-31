package org.example.demo.cache

import com.github.michaelbull.result.Result
import org.example.demo.cache.errors.CacheError

interface Cache<K, T> {
    fun get(key: K): Result<T, CacheError>
    fun put(key: K, value: T): Result<T, Nothing>
    fun remove(key: K): Result<T, CacheError>
    fun clear(): Result<Unit, Nothing>
}