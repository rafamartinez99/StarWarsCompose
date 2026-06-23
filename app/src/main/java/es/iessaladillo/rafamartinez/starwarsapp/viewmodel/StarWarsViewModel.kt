package es.iessaladillo.rafamartinez.starwarsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.iessaladillo.rafamartinez.starwarsapp.data.models.StarWarsCharacter
import es.iessaladillo.rafamartinez.starwarsapp.data.remote.StarWarsApi
import es.iessaladillo.rafamartinez.starwarsapp.utils.extractId
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarWarsViewModel @Inject constructor(
    private val api: StarWarsApi
) : ViewModel() {

    private val _characters = MutableStateFlow<List<StarWarsCharacter>>(emptyList())
    val characters = _characters.asStateFlow()

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            try {
                val apiCharacters = api.getCharacters().results

                val homeworldIds = apiCharacters.map { it.homeworld.extractId() }.distinct()
                val speciesIds = apiCharacters.flatMap { it.species }.map { it.extractId() }.distinct()
                val filmIds = apiCharacters.flatMap { it.films }.map { it.extractId() }.distinct()

                val homeworldsMap = async { homeworldIds.associateWith { api.getPlanet(it).name } }
                val speciesMap = async { speciesIds.associateWith { api.getSpecies(it).name } }
                val filmsMap = async { filmIds.associateWith { api.getFilm(it) } }

                val updatedCharacters = apiCharacters.map { character ->
                    async {
                        val homeworldName = requireNotNull(homeworldsMap.await()[character.homeworld.extractId()])
                        val speciesList = character.species.mapNotNull { speciesMap.await()[it.extractId()] }
                        val filmDetails = character.films.mapNotNull { filmsMap.await()[it.extractId()] }

                        character.copy(
                            homeworld = homeworldName,
                            species = speciesList,
                            filmDetails = filmDetails
                        )
                    }
                }.awaitAll()

                _characters.value = updatedCharacters

            } catch (e: Exception) {
                println("Error al cargar personajes: ${e.message}")
            }
        }
    }

}
