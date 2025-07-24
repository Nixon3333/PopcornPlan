package com.drygin.popcornplan.platform

/**
 * Created by Drygin Nikita on 23.07.2025.
 */
interface TimeProvider {
    fun currentTimeMillis(): Long
}