package com.example.mobiletest.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiletest.MainPageUiState
import com.example.mobiletest.model.Location
import com.example.mobiletest.model.OriginAndDestinationPair
import com.example.mobiletest.model.Segment

object BookingPage {
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        state: MainPageUiState,
        onRefreshClick: () -> Unit = {},
        onItemClick: (Segment) -> Unit = {}
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
                            SegmentInfoItem(
                                segment = state.data[index],
                                onItemClick = onItemClick
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { onRefreshClick() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text(text = "Refresh")
            }
        }
    }

    @Composable
    fun SegmentInfoItem(
        modifier: Modifier = Modifier,
        segment: Segment,
        onItemClick: (Segment) -> Unit
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple()
                ){
                    onItemClick.invoke(segment)
                }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Segment ID: ${segment.id}", style = MaterialTheme.typography.headlineLarge)
                Text(
                    "Origin City: ${segment.originAndDestinationPair.origin.displayName} (${segment.originAndDestinationPair.origin.code})",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text("Origin Code: ${segment.originAndDestinationPair.origin.code}", style = MaterialTheme.typography.bodySmall)
                Text(
                    "Destination City: ${segment.originAndDestinationPair.destination.displayName} (${segment.originAndDestinationPair.destination.code})",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text("Destination Code: ${segment.originAndDestinationPair.destination.code}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
fun BookingPagePreview() {
    BookingPage.Content(
        state = MainPageUiState.Success(
            data = listOf(
                Segment(
                    id = 1,
                    originAndDestinationPair = OriginAndDestinationPair(
                        origin = Location("LAX", "Los Angeles", "https://www.example.com"),
                        destination = Location("JFK", "New York", "https://www.example.com")
                    )
                ),
                Segment(
                    id = 2,
                    originAndDestinationPair = OriginAndDestinationPair(
                        origin = Location("JFK", "New York", "https://www.example.com"),
                        destination = Location("LAX", "Los Angeles", "https://www.example.com")
                    )
                )
            )
        )
    )
}