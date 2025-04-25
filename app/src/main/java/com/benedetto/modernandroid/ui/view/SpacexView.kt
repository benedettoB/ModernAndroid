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
import com.benedetto.core.model.LaunchWrapper
import com.benedetto.modernandroid.viewmodel.LaunchListViewModel


@Composable
internal fun LaunchList(viewModel: LaunchListViewModel = hiltViewModel()) {
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
internal fun LaunchItem(launchWrapper: LaunchWrapper) {

    OutlinedCard(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID: ${launchWrapper.id}")
            Text(text = "SITE: ${launchWrapper.site}")
        }
    }
}

