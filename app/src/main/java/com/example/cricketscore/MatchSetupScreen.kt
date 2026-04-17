package com.example.cricketscore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun MatchSetupScreen(
    state: State<MatchSetupUiState>,
    onTeamOneNameChange: (String) -> Unit,
    onTeamTwoNameChange: (String) -> Unit,
    onPlayerCountChange: (String) -> Unit,
    onOversChange: (String) -> Unit,
    onTeamOnePlayerNameChange: (Int, String) -> Unit,
    onTeamTwoPlayerNameChange: (Int, String) -> Unit,
    onStartMatch: () -> Unit
) {
    val uiState = state.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        item {
            Text(
                text = "Quick Match Setup",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.teamOneName,
                onValueChange = onTeamOneNameChange,
                label = { Text("Team 1 Name") },
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.teamTwoName,
                onValueChange = onTeamTwoNameChange,
                label = { Text("Team 2 Name") },
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.playersPerTeamInput,
                onValueChange = onPlayerCountChange,
                label = { Text("Number of Players per Team") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.totalOversInput,
                onValueChange = onOversChange,
                label = { Text("Total Overs") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }

        item {
            TeamPlayerInputsCard(
                title = "Team 1 Players",
                players = uiState.teamOnePlayers,
                onPlayerNameChange = onTeamOnePlayerNameChange
            )
        }

        item {
            TeamPlayerInputsCard(
                title = "Team 2 Players",
                players = uiState.teamTwoPlayers,
                onPlayerNameChange = onTeamTwoPlayerNameChange
            )
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onStartMatch
            ) {
                Text("Start Match")
            }
        }
    }
}

@Composable
private fun TeamPlayerInputsCard(
    title: String,
    players: List<String>,
    onPlayerNameChange: (Int, String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            if (players.isEmpty()) {
                Text(text = "Set player count to generate fields.")
            } else {
                players.forEachIndexed { index, name ->
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = { onPlayerNameChange(index, it) },
                        label = { Text("Player ${index + 1}") },
                        singleLine = true
                    )
                }
            }
        }
    }
}
