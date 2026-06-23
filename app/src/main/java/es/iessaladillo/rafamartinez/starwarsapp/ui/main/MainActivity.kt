package es.iessaladillo.rafamartinez.starwarsapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import es.iessaladillo.rafamartinez.starwarsapp.ui.screens.CharacterDetailScreen
import es.iessaladillo.rafamartinez.starwarsapp.ui.screens.CharacterScreen
import es.iessaladillo.rafamartinez.starwarsapp.viewmodel.StarWarsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: StarWarsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "characters") {
                composable("characters") { CharacterScreen(navController, viewModel) }
                composable("characterDetail/{id}") { backStackEntry ->
                    val characterId = requireNotNull(backStackEntry.arguments?.getString("id"))
                    CharacterDetailScreen(navController, viewModel, characterId)
                }
            }
        }
    }
}
