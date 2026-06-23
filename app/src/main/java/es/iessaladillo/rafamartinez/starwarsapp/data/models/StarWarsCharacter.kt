package es.iessaladillo.rafamartinez.starwarsapp.data.models

import es.iessaladillo.rafamartinez.starwarsapp.data.remote.responses.FilmResponse
import es.iessaladillo.rafamartinez.starwarsapp.utils.extractId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StarWarsCharacter(
    val name: String,
    val homeworld: String,
    val films: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    @SerialName("birth_year") val birthYear: String,
    val height: String,
    val gender: String,
    @SerialName("hair_color") val hairColor: String,
    val mass: String,
    @SerialName("eye_color") val eyeColor: String,
    @SerialName("skin_color") val skinColor: String,
    val species: List<String>,
    val url: String,
    val filmDetails: List<FilmResponse> = emptyList()
) {
    val id: String
        get() = url.extractId()

    val formattedSpecies: String
        get() = if (species.isEmpty()) "Human" else species.joinToString(", ")
}
