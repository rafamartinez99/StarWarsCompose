package es.iessaladillo.rafamartinez.starwarsapp.data.remote.responses

import es.iessaladillo.rafamartinez.starwarsapp.data.models.StarWarsCharacter
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponse(val results: List<StarWarsCharacter>)
