@file:OptIn(ExperimentalMaterial3Api::class)

package com.jukebox.swiss_tournament.tournament_creation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jukebox.swiss_tournament.data.model.Player


@Composable
fun CreateTournamentScreen (
    viewModel: CreateTournamentViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
            .padding(8.dp)
    ){
        TextField(
            value = viewModel.name,
            label = { Text("Name des Tuniers") },
            onValueChange = {viewModel.name = it},
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = ""+viewModel.numOfRounds,
            label = { Text("Anzahl der Runden") },
            onValueChange = {viewModel.numOfRounds = it.toInt()},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        MoreTournamentSettings(viewModel)
        Divider()
        PlayerList(viewModel)
    }
}

@Composable
fun PlayerList(viewModel: CreateTournamentViewModel) {
    @Composable
    fun NameBox (
        name : String,
        modifier : Modifier = Modifier
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
                .border(1.dp, MaterialTheme.colorScheme.tertiary)
                .padding(start = 4.dp)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Spieler-Liste",
            style = MaterialTheme.typography.headlineMedium,
        )
        LazyColumn {
            items(viewModel.players) {
                Row(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                        .fillMaxWidth()
                ) {
                    NameBox(name = it.firstName, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    NameBox(name = it.lastName, modifier = Modifier.weight(1f))
                }
            }
        }
        if(viewModel.isAddingPlayer) {
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()

            ) {
                TextField(
                    value = viewModel.addedPlayerFirstName,
                    onValueChange = {viewModel.addedPlayerFirstName = it},
                    label = {Text("Vorname")},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = viewModel.addedPlayerLastName,
                    onValueChange = {viewModel.addedPlayerLastName = it},
                    label = { Text("Nachname")},
                    modifier = Modifier.weight(1f)
                )
            }
            Button(onClick = { viewModel.addPlayer()}) {
                Text(text = "Fertig")
            }
        } else {
            Button(onClick = { viewModel.isAddingPlayer = true }) {
                Text(text = "Spieler hinzufügen")
            }
        }
    }
}


@Composable
fun MoreTournamentSettings(
    viewModel: CreateTournamentViewModel,
    modifier: Modifier = Modifier
) {
    var isOpen by rememberSaveable { mutableStateOf(false) }


    if (isOpen) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            TextField(
                value = viewModel.city,
                label = { Text(text = "Stadt")},
                onValueChange = { viewModel.city = it},
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewModel.federation,
                label = { Text(text = "Federation")},
                onValueChange = { viewModel.federation = it},
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewModel.startDate,
                label = { Text(text = "Start Datum (TT.MM.YYYY)")},
                onValueChange = { viewModel.startDate = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewModel.endDate,
                label = { Text(text = "End Datum (TT.MM.YYYY)")},
                onValueChange = { viewModel.endDate = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewModel.arbiters,
                label = { Text(text = "Schiedsrichter")},
                onValueChange = { viewModel.arbiters = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Button(
        onClick = {
            isOpen = !isOpen
        },
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = if (isOpen) {"Weniger Einstellungen..."} else {"Mehr Einstellungen ..."},
        )
    }


}
