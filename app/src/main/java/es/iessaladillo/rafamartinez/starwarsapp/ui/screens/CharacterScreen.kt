package es.iessaladillo.rafamartinez.starwarsapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import es.iessaladillo.rafamartinez.starwarsapp.R
import es.iessaladillo.rafamartinez.starwarsapp.data.models.StarWarsCharacter
import es.iessaladillo.rafamartinez.starwarsapp.viewmodel.StarWarsViewModel

private val starWarsFont = FontFamily(Font(R.font.sf_distant_galaxy))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(navController: NavController, viewModel: StarWarsViewModel) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val characters by viewModel.characters.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.starwars_compose),
                            fontSize = 20.sp,
                            color = Color(0xFF00FFFF),
                            fontFamily = starWarsFont
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF00FFFF)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Busca un personaje...",
                            color = Color(0xFFFF00FF),
                            fontFamily = starWarsFont,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xFFFFFF00),
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color(0xFF00FFFF),
                        focusedLeadingIconColor = Color(0xFFFFFF00),
                        unfocusedLeadingIconColor = Color(0xFFFFFF00),
                        unfocusedIndicatorColor = Color.Gray
                    )
                )

                if (characters.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFFFFF00))
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        val filteredCharacters = characters.filter {
                            it.name.contains(searchText.text, ignoreCase = true)
                        }
                        items(filteredCharacters) { character ->
                            val characterId = character.id
                            CharacterItem(character, onClick = {
                                navController.navigate("characterDetail/$characterId")
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: StarWarsCharacter, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .border(2.dp, Color(0xFF00FFFF), shape = RoundedCornerShape(20.dp))
            .shadow(10.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = getCharacterImage(character.name)),
                contentDescription = character.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(text = character.name, color = Color(0xFFFFFF00), fontSize = 18.sp, fontFamily = starWarsFont)
        }
    }
}

fun getCharacterImage(name: String): Int {
    return when (name) {
        "Luke Skywalker" -> R.drawable.luke_skywalker
        "C-3PO" -> R.drawable.c3po
        "R2-D2" -> R.drawable.r2d2
        "Darth Vader" -> R.drawable.darth_vader
        "Leia Organa" -> R.drawable.leia_organa
        "Owen Lars" -> R.drawable.owen_lars
        "Beru Whitesun lars" -> R.drawable.beru_whitesun_lars
        "R5-D4" -> R.drawable.r5d4
        "Biggs Darklighter" -> R.drawable.biggs_darklighter
        "Obi-Wan Kenobi" -> R.drawable.obiwan_kenobi
        else -> R.drawable.default_movie
    }
}
