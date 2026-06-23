package es.iessaladillo.rafamartinez.starwarsapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import es.iessaladillo.rafamartinez.starwarsapp.R
import es.iessaladillo.rafamartinez.starwarsapp.data.models.StarWarsCharacter
import es.iessaladillo.rafamartinez.starwarsapp.viewmodel.StarWarsViewModel

private val starWarsFont = FontFamily(Font(R.font.sf_distant_galaxy))

@Composable
fun CharacterDetailScreen(
    navController: NavController,
    viewModel: StarWarsViewModel,
    characterId: String
) {
    val character = viewModel.characters.collectAsState().value.find { it.id == characterId }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (character == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFFFF00))
            }
        } else {
            CharacterDetailContent(navController, character)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailContent(navController: NavController, character: StarWarsCharacter) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Star Wars",
                        fontSize = 24.sp,
                        color = Color(0xFF00FFFF),
                        fontFamily = starWarsFont
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF00FF00)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = getCharacterImage(character.name)),
                            contentDescription = character.name,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(60.dp))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            character.name,
                            fontSize = 24.sp,
                            color = Color(0xFFFFFF00),
                            fontFamily = starWarsFont
                        )
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    CharacterDetailRow("Nacimiento", character.birthYear)
                    CharacterDetailRow("Altura", character.height)
                    CharacterDetailRow("Género", character.gender)
                    CharacterDetailRow("Color de pelo", character.hairColor)
                    CharacterDetailRow("Masa", character.mass)
                    CharacterDetailRow("Color de ojos", character.eyeColor)
                    CharacterDetailRow("Color de piel", character.skinColor)
                    CharacterDetailRow("Especie", character.formattedSpecies)
                    CharacterDetailRow("Lugar de origen", character.homeworld)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Películas",
                    fontSize = 20.sp,
                    color = Color(0xFFFFFF00),
                    fontFamily = starWarsFont,
                    modifier = Modifier.padding(16.dp)
                )
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(character.filmDetails) { film ->
                        Card(
                            modifier = Modifier
                                .width(400.dp)
                                .padding(8.dp)
                                .border(2.dp, Color(0xFF00FFFF), shape = RoundedCornerShape(16.dp))
                                .shadow(10.dp, shape = RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = getFilmImage(film.title)),
                                    contentDescription = film.title,
                                    modifier = Modifier.size(220.dp)
                                )
                                Column {
                                    Text(
                                        text = film.title,
                                        fontSize = 16.sp,
                                        color = Color(0xFFFFFF00),
                                        fontFamily = starWarsFont
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = "Episodio ${film.episodeId}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = "Estreno: ${film.releaseDate}",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, color = Color(0xFF00FFFF), fontFamily = starWarsFont)
        Text(text = value, fontSize = 16.sp, color = Color(0xFFFF00FF))
    }
}


fun getFilmImage(title: String): Int {
    return when (title) {
        "A New Hope" -> R.drawable.a_new_hope
        "The Empire Strikes Back" -> R.drawable.empire_strikes_back
        "Return of the Jedi" -> R.drawable.return_of_the_jedi
        "The Phantom Menace" -> R.drawable.the_phantom_menace
        "Attack of the Clones" -> R.drawable.attack_of_the_clones
        "Revenge of the Sith" -> R.drawable.revenge_of_the_sith
        "The Force Awakens" -> R.drawable.the_force_awakens
        "The Last Jedi" -> R.drawable.the_last_jedi
        "The Rise of Skywalker" -> R.drawable.the_rise_of_skywalker
        else -> R.drawable.default_movie
    }
}



