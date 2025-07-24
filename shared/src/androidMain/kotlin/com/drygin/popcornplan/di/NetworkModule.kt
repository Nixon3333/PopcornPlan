package com.drygin.popcornplan.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Drygin Nikita on 22.07.2025.
 */
val networkModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        val apiKey: String = get()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("trakt-api-version", "2")
                    .addHeader("trakt-api-key", apiKey)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.trakt.tv/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
}
