package com.benedetto.modernandroid.ui.view.common
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benedetto.modernandroid.ui.navigation.ScreenItem

//CustomGrid() creates an advanced grid layout
@Composable
fun CustomGrid(
    items: List<ScreenItem>,
    columns: Int = 2,
    modifier: Modifier = Modifier,
    content: @Composable (ScreenItem) -> Unit
){
    val rows = (items.size + columns -1) / columns

    Column(modifier = modifier.padding(8.dp)) {
        for(row in 0 until rows){
            Row(modifier = Modifier.fillMaxWidth()){
                for(col in 0 until columns){
                    val index = row * columns + col
                    if(index < items.size){
                        Box(modifier = Modifier.weight(1f).padding(8.dp)){
                            content(items[index])
                        }
                    }else{
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

























