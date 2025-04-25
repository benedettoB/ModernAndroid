package com.benedetto.modernandroid.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.benedetto.modernandroid.ui.theme.ModernAndroidTheme
import com.benedetto.modernandroid.ui.theme.MyTheme
import com.benedetto.modernandroid.viewmodel.CounterViewModel

@Composable
internal fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
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


@Preview(showBackground = true)
@Composable
private fun CounterScreenPreview() {
    ModernAndroidTheme {
        CounterScreen()
    }
}