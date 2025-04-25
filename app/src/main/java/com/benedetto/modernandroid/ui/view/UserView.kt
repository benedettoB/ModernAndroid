package com.benedetto.modernandroid.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benedetto.core.model.User
import com.benedetto.modernandroid.viewmodel.UserViewModel

/*
    LazyColumn for List
    Compose-friendly ViewModel injection
*/

@Composable
internal fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
    val users by viewModel.usersList.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        LazyColumn {
            items(users) { user ->
                UserItem(user)
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


