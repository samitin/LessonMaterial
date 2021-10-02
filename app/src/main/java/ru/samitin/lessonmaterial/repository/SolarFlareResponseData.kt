package ru.samitin.lessonmaterial.repository

class SolarFlareResponseData (
    val flrID: String,
    val beginTime: String,
    val peakTime: String,
    val endTime: String? = null,
    val classType: String,
    val sourceLocation: String,
    val activeRegionNum: Long,
    val link: String
)