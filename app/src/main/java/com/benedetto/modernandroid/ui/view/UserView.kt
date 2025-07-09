package com.benedetto.modernandroid.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benedetto.core.model.User
import com.benedetto.modernandroid.state.UserUiState
import com.benedetto.modernandroid.viewmodel.UserViewModel


@Composable
internal fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
    val usersUiState by viewModel.usersUiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

        when (usersUiState) {
            is UserUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    strokeWidth = 4.dp
                )
            }

            is UserUiState.Success -> {
                val users = (usersUiState as UserUiState.Success).users

                LazyColumn {
                    items(users) { user ->
                        UserItem(user)
                    }
                }
            }

            is UserUiState.Error -> {
                val message = (usersUiState as UserUiState.Error).message
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error: $message")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.retry() }) {
                        Text("Retry")
                    }
                }
            }
        }


    }
}

@Composable
internal fun UserItem(user: User) {
    OutlinedCard(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "USER ID: ${user.userId}")
            Text(text = "ID: ${user.id}")
            Text(text = "TITLE: ${user.title}")
            Text(text = "BODY: ${user.body}")

        }
    }
}


