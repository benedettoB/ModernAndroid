package com.benedetto.modernandroid.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.benedetto.modernandroid.R
import com.benedetto.modernandroid.state.ProfileUiState
import com.benedetto.modernandroid.viewmodel.ProfileViewModel


@Composable
internal fun ProfilesScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is ProfileUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    strokeWidth = 4.dp
                )
            }

            is ProfileUiState.Success -> {
                val profiles = (uiState as ProfileUiState.Success).profiles
                Text(
                    text = stringResource(R.string.prototype_cells),
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
                LazyColumn {
                    items(profiles, key = { profile -> profile.id }) { profile ->
                        ProfileItem(data = profile) { ImageAction() }
                    }
                }
            }

            is ProfileUiState.Error -> {
                val message = (uiState as ProfileUiState.Error).message
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error: $message")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.retry() }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
        }
    }
}


@Composable
internal fun ImageAction() {
    // Add state to hold the image URI
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // Launcher for picking images from gallery
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    // Modified Image section with click action
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .clickable {
                // Launch gallery picker when clicked
                pickImageLauncher.launch("image/*")
            }
    ) {
        if (imageUri != null) {
            // Show selected image if available
            AsyncImage(
                model = imageUri,
                contentDescription = "Selected profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Show placeholder if no image is selected
            Image(
                painter = painterResource(id = R.drawable.ic_silhouette),
                contentDescription = "Silhouette of profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
