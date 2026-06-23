package es.iessaladillo.rafamartinez.starwarsapp.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmResponse(
    val title: String,
    @SerialName("episode_id") val episodeId: Int,
    @SerialName("release_date") val releaseDate: String
)

