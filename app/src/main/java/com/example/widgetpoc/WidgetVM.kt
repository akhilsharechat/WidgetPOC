package com.example.widgetpoc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class WidgetVM : ViewModel(), ContainerHost<WidgetVS, Nothing> {

    override val container by lazy {
        container<WidgetVS, Nothing>(
            initialState = initialState()
        ) { intent {  } }
    }

    fun initialState(): WidgetVS = WidgetVS()

    fun stateFlow(): StateFlow<WidgetVS> = container.stateFlow

    init {
        initData()
    }


    fun initData() = intent {
        val widgetData1 = WidgetData("1", "abhishek", "kumar", 500L, 1)
        val widgetData2 = WidgetData("2","anant", "raman", 53L, 2)
        val widgetData3 = WidgetData("3","abhi", "rathore", 504530L, 3)

        val list = listOf(widgetData1, widgetData2, widgetData3)
        val map = mutableMapOf<String, RadioBtnHolder>()
        list.forEach {
            map[it.id] = RadioBtnHolder()
        }

        reduce { state.copy(items = list, mapper = map) }
    }

    fun updateCheckedState(id: String) = intent {
        val updatedMap = state.mapper.toMutableMap()
        updatedMap[id] = updatedMap[id]!!.copy(isChecked = updatedMap[id]!!.isChecked.not())
        reduce {
            state.copy(mapper = updatedMap)
        }
    }
}