package es.iessaladillo.rafamartinez.starwarsapp.data.remote
import es.iessaladillo.rafamartinez.starwarsapp.data.remote.responses.CharactersResponse
import es.iessaladillo.rafamartinez.starwarsapp.data.remote.responses.FilmResponse
import es.iessaladillo.rafamartinez.starwarsapp.data.remote.responses.PlanetResponse
import es.iessaladillo.rafamartinez.starwarsapp.data.remote.responses.SpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StarWarsApi {
    @GET("people/")
    suspend fun getCharacters(): CharactersResponse

    @GET("planets/{id}/")
    suspend fun getPlanet(@Path("id") id: String): PlanetResponse

    @GET("films/{id}/")
    suspend fun getFilm(@Path("id") id: String): FilmResponse

    @GET("species/{id}/")
    suspend fun getSpecies(@Path("id") id: String): SpeciesResponse
}

