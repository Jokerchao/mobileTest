package com.example.mobiletest.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiletest.MainPageUiState

object BookingPage {
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        state: MainPageUiState,
        onRefreshClick: () -> Unit = {}
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            when (state) {
                is MainPageUiState.Loading -> {
                    Text(
                        text = "Loading",
                        fontSize = 20.sp,
                    )
                }

                is MainPageUiState.Error -> {
                    Text(
                        text = "Error: ${state.throwable.message}",
                        fontSize = 20.sp,
                    )
                }

                is MainPageUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(state.data) { index, item ->
                            Text(text = item, fontSize = 15.sp, color = Color.Blue)
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { onRefreshClick() },
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(text = "Refresh")
            }
        }
    }
}

@Preview
@Composable
fun BookingPagePreview() {
    BookingPage.Content(
        state = MainPageUiState.Success(
            data = listOf("Item 1", "Item 2", "Item 3")
        )
    )
}