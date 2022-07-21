package com.example.widgetpoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<WidgetVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cv = findViewById<ComposeView>(R.id.root_compose_view)
        cv.apply {
            setContent {
                val state by viewModel.stateFlow().collectAsState()
                AudioWidget(state = state){
                    viewModel.updateCheckedState(it)
                }
            }
        }
    }
}