package ru.samitin.lessonmaterial.viewModel

import ru.samitin.lessonmaterial.repository.EarthEpicServerResponseData
import ru.samitin.lessonmaterial.repository.MarsPhotosServerResponseData
import ru.samitin.lessonmaterial.repository.PODServerResponseData
import ru.samitin.lessonmaterial.repository.SolarFlareResponseData

sealed class AppState {
    data class SuccessPOD(val serverResponseData: PODServerResponseData) : AppState()
    data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : AppState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()
    data class SuccessWeather(val solarFlareResponseData:List<SolarFlareResponseData>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}