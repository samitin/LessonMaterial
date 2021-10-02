package ru.samitin.lessonmaterial.ui.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.samitin.lessonmaterial.R

class ApiBottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_bottom)
        val bottomNV: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNV.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                        .replace(R.id.activity_api_bottom_container, EarchFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_mars -> {

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                        .replace(R.id.activity_api_bottom_container, MarsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_weather -> {

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                        .replace(R.id.activity_api_bottom_container, WeatherFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
        bottomNV.selectedItemId = R.id.bottom_view_earth
        bottomNV.getOrCreateBadge(R.id.bottom_view_earth).apply {
            number = 100;
            badgeGravity = BadgeDrawable.TOP_END
            maxCharacterCount = 3
        }

    }

}



