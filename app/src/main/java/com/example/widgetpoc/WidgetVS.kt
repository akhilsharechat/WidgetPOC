package com.example.widgetpoc

data class WidgetVS(
    val items: List<WidgetData> = emptyList(),
    val mapper: Map<String, RadioBtnHolder> = emptyMap()
)

data class WidgetData(
    val id: String,
    val name: String,
    val author: String,
    val postCount: Long,
    val position: Int
)

data class RadioBtnHolder(val isChecked: Boolean = false)