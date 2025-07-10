package com.benedetto.modernandroid.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benedetto.domain.model.Profile

//function for a single profile item
@Composable
internal fun ProfileItem(data: Profile, imageAction: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(Color.White)
    ) {
        // Horizontal divider
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Row for profile image, name, and email
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            imageAction()

            // Column for name, email, and the id/body section
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                // Row for name and email
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = data.name,
                        fontSize = 11.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(0.6f) // Takes 60% of the width
                    )

                    Text(
                        text = data.email,
                        fontSize = 11.sp,
                        color = Color.Black
                    )
                }

                Column(
                    modifier = Modifier.padding(top = 2.dp) // Reduced padding to bring ID closer to name/email
                ) {
                    Text(
                        text = data.id.toString(),
                        fontSize = 11.sp,
                        color = Color.Black
                    )

                    Text(
                        text = data.body,
                        fontSize = 11.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp), // Adjusted padding between ID and body
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }

        // Horizontal divider
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}