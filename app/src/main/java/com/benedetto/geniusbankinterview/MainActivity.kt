package com.benedetto.geniusbankinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.benedetto.domain.model.LaunchWrapper
import com.benedetto.domain.model.Transaction
import com.benedetto.domain.model.User
import com.benedetto.geniusbankinterview.viewmodel.CounterViewModel
import com.benedetto.geniusbankinterview.viewmodel.LaunchListViewModel
import com.benedetto.geniusbankinterview.viewmodel.TransactionViewModel
import com.benedetto.geniusbankinterview.viewmodel.UserViewModel
import com.benedetto.geniusbankinterview.ui.theme.GeniusBankInterviewTheme
import com.benedetto.geniusbankinterview.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeniusBankInterviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //CounterScreen()
                    //TransactionScreen()
                    UserScreen()
                    //LaunchList()

                }
            }
        }
    }
}


@Composable
private fun LaunchList(viewModel: LaunchListViewModel = hiltViewModel()) {
    val launchWrappers by viewModel.launchList.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        LazyColumn {
            items(launchWrappers) { launchWrapper ->
                LaunchItem(launchWrapper)
            }
        }
    }
}

@Composable
private fun LaunchItem(launchWrapper: LaunchWrapper) {

    OutlinedCard(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID: ${launchWrapper.id}")
            Text(text = "SITE: ${launchWrapper.site}")
        }
    }
}


/*
    LazyColumn for List
    Compose-friendly ViewModel injection
*/

@Composable
private fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
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
private fun UserItem(user: User) {
    OutlinedCard(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "USER ID: ${user.userId}")
            Text(text = "ID: ${user.id}")
            Text(text = "TITLE: ${user.title}")
            Text(text = "BODY: ${user.body}")

        }
    }
}


@Composable
private fun TransactionScreen(viewModel: TransactionViewModel = hiltViewModel()) {
    val transactions by viewModel.transactions.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        LazyColumn {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
private fun TransactionItem(transaction: Transaction) {
    OutlinedCard(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID: ${transaction.id}")
            Text(text = "Amount: ${transaction.amount}")
            Text(text = "Description: ${transaction.description}")
        }
    }
}


@Composable
private fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    val count by viewModel.count.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        MyTheme {

            OutlinedCard {
                Text(text = "Count: $count", style = MaterialTheme.typography.headlineMedium)
                ElevatedButton(onClick = { viewModel.increment() }) {
                    Text("Increment")
                }
            }
        }
    }
}

//✅ Task: Try using Material 3 components like ElevatedButton, OutlinedCard, etc.
@Composable
private fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            background = Color(0xFF121212)
        ),
        typography = Typography,
        content = content
    )
}


@Preview(showBackground = true)
@Composable
private fun CounterScreenPreview() {
    GeniusBankInterviewTheme {
        CounterScreen()
    }
}