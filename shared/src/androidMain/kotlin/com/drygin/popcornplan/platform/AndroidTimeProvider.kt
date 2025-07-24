package com.drygin.popcornplan.platform

/**
 * Created by Drygin Nikita on 23.07.2025.
 */
class AndroidTimeProvider : TimeProvider {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}

