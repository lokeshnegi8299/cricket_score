package com.example.cricketscore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoringScreen(matchSetup: MatchSetup?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Scoring Screen",
            style = MaterialTheme.typography.headlineSmall
        )

        if (matchSetup == null) {
            Text("No match setup found.")
            return
        }

        Text("${matchSetup.teamOneName} vs ${matchSetup.teamTwoName}")
        Text("Players per Team: ${matchSetup.playersPerTeam}")
        Text("Total Overs: ${matchSetup.totalOvers}")
        Text("${matchSetup.teamOneName} Players: ${matchSetup.teamOnePlayers.joinToString()}")
        Text("${matchSetup.teamTwoName} Players: ${matchSetup.teamTwoPlayers.joinToString()}")
    }
}
