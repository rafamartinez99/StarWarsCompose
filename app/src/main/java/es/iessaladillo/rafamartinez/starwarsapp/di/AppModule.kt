package es.iessaladillo.rafamartinez.starwarsapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.iessaladillo.rafamartinez.starwarsapp.data.remote.StarWarsApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

val json = Json { ignoreUnknownKeys = true }

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideStarWarsApi(retrofit: Retrofit): StarWarsApi {
        return retrofit.create(StarWarsApi::class.java)
    }
}
