package com.drygin.popcornplan.network.api

import io.ktor.client.HttpClient

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
interface HttpClientProvider {
    fun getClient(): HttpClient
}