package com.tes.vodle.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tes.vodle.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class VodleClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SocialLoginClient

    @Singleton
    @Provides
    @VodleClient
    fun provideVodleClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.apply {
            addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    @SocialLoginClient
    fun provideSocialLoginClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.apply {
            addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    private val gson: Gson = GsonBuilder().disableHtmlEscaping().create()

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}
