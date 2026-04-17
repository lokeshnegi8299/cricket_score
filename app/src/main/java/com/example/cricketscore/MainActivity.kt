package com.example.cricketscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cricketscore.ui.theme.CricketScoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CricketScoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val setupViewModel: MatchSetupViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "setup") {
                        composable("setup") {
                            MatchSetupScreen(
                                state = setupViewModel.state,
                                onTeamOneNameChange = setupViewModel::setTeamOneName,
                                onTeamTwoNameChange = setupViewModel::setTeamTwoName,
                                onPlayerCountChange = setupViewModel::setPlayersPerTeam,
                                onOversChange = setupViewModel::setTotalOvers,
                                onTeamOnePlayerNameChange = setupViewModel::setTeamOnePlayer,
                                onTeamTwoPlayerNameChange = setupViewModel::setTeamTwoPlayer,
                                onStartMatch = {
                                    setupViewModel.storeSetupForMatch()
                                    navController.navigate("scoring")
                                }
                            )
                        }
                        composable("scoring") {
                            val matchSetup by setupViewModel.matchSetup
                            ScoringScreen(matchSetup = matchSetup)
                        }
                    }
                }
            }
        }
    }
}
