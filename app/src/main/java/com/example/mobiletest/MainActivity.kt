package com.example.mobiletest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.mobiletest.ui.theme.BookingPage
import com.example.mobiletest.ui.theme.MobileTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initObserver()
        setContent {
            val state = viewModel.uiStateFlow.collectAsState()
            MobileTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookingPage.Content(
                        modifier = Modifier.padding(innerPadding),
                        state = state.value,
                        onRefreshClick = { viewModel.fetchBookingData() },
                        onItemClick = { segment ->
                            viewModel.checkTicketExpire()
                        }
                    )
                }
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.toastFlow.collect { msg ->
                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchBookingData()
    }
}
