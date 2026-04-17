package com.example.cricketscore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

private const val DEFAULT_PLAYER_COUNT = 11

class MatchSetupViewModel : ViewModel() {
    private val _state = mutableStateOf(
        MatchSetupUiState(
            playersPerTeamInput = DEFAULT_PLAYER_COUNT.toString(),
            teamOnePlayers = List(DEFAULT_PLAYER_COUNT) { "" },
            teamTwoPlayers = List(DEFAULT_PLAYER_COUNT) { "" }
        )
    )
    val state: State<MatchSetupUiState> = _state

    private val _matchSetup = mutableStateOf<MatchSetup?>(null)
    val matchSetup: State<MatchSetup?> = _matchSetup

    fun setTeamOneName(value: String) {
        _state.value = _state.value.copy(teamOneName = value)
    }

    fun setTeamTwoName(value: String) {
        _state.value = _state.value.copy(teamTwoName = value)
    }

    fun setPlayersPerTeam(value: String) {
        val current = _state.value
        val desiredCount = value.toIntOrNull()?.coerceAtLeast(0)

        _state.value = current.copy(
            playersPerTeamInput = value,
            teamOnePlayers = current.teamOnePlayers.resizeTo(desiredCount),
            teamTwoPlayers = current.teamTwoPlayers.resizeTo(desiredCount)
        )
    }

    fun setTotalOvers(value: String) {
        _state.value = _state.value.copy(totalOversInput = value)
    }

    fun setTeamOnePlayer(index: Int, name: String) {
        _state.value = _state.value.copy(
            teamOnePlayers = _state.value.teamOnePlayers.toMutableList().apply {
                this[index] = name
            }
        )
    }

    fun setTeamTwoPlayer(index: Int, name: String) {
        _state.value = _state.value.copy(
            teamTwoPlayers = _state.value.teamTwoPlayers.toMutableList().apply {
                this[index] = name
            }
        )
    }

    fun storeSetupForMatch() {
        val snapshot = _state.value
        _matchSetup.value = MatchSetup(
            teamOneName = snapshot.teamOneName.ifBlank { "Team 1" },
            teamTwoName = snapshot.teamTwoName.ifBlank { "Team 2" },
            playersPerTeam = snapshot.playersPerTeamInput.toIntOrNull() ?: 0,
            totalOvers = snapshot.totalOversInput.toIntOrNull() ?: 0,
            teamOnePlayers = snapshot.teamOnePlayers,
            teamTwoPlayers = snapshot.teamTwoPlayers
        )
    }
}

private fun List<String>.resizeTo(target: Int?): List<String> {
    if (target == null) return this
    return when {
        target < size -> take(target)
        target > size -> this + List(target - size) { "" }
        else -> this
    }
}

data class MatchSetupUiState(
    val teamOneName: String = "",
    val teamTwoName: String = "",
    val playersPerTeamInput: String = "",
    val totalOversInput: String = "",
    val teamOnePlayers: List<String> = emptyList(),
    val teamTwoPlayers: List<String> = emptyList()
)

data class MatchSetup(
    val teamOneName: String,
    val teamTwoName: String,
    val playersPerTeam: Int,
    val totalOvers: Int,
    val teamOnePlayers: List<String>,
    val teamTwoPlayers: List<String>
)
