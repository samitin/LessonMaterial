package ru.samitin.lessonmaterial.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.ActivityApiBinding

private const val EARTH = 0
private const val MARS = 1
private const val WEATHER = 2

class ApiActivity : AppCompatActivity() {

    private lateinit var bilder:ActivityApiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bilder= ActivityApiBinding.inflate(layoutInflater)
        setContentView(bilder.root)

        bilder.viewPager.adapter=ViewPagerAdapter(supportFragmentManager)
        bilder.tabLayout.setupWithViewPager(bilder.viewPager)
        setHighlightedTab(EARTH)

        bilder.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }
    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@ApiActivity)

        bilder.tabLayout.getTabAt(EARTH)?.customView = null
        bilder.tabLayout.getTabAt(MARS)?.customView = null
        bilder.tabLayout.getTabAt(WEATHER)?.customView = null

        when (position) {
            EARTH -> {
                setEarthTabHighlighted(layoutInflater)
            }
            MARS -> {
                setMarsTabHighlighted(layoutInflater)
            }
            WEATHER -> {
                setWeatherTabHighlighted(layoutInflater)
            }
            else -> {
                setEarthTabHighlighted(layoutInflater)
            }
        }
    }

    private fun setEarthTabHighlighted(layoutInflater: LayoutInflater) {
        val earth = layoutInflater.inflate(R.layout.activity_api_costom_tab_earch, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(ContextCompat.getColor(this@ApiActivity, R.color.colorAccent))

        bilder.tabLayout.getTabAt(EARTH)?.customView = earth
        bilder.tabLayout.getTabAt(MARS)?.customView = layoutInflater.inflate(R.layout.activity_api_costom_tab_mars, null)
        bilder.tabLayout.getTabAt(WEATHER)?.customView = layoutInflater.inflate(R.layout.activity_api_costom_tab_weather, null)
    }

    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
        val mars = layoutInflater.inflate(R.layout.activity_api_costom_tab_mars, null)
        mars.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(ContextCompat.getColor(this@ApiActivity, R.color.colorAccent))

        bilder.tabLayout.getTabAt(EARTH)?.customView = layoutInflater.inflate(R.layout.activity_api_costom_tab_earch, null)
        bilder.tabLayout.getTabAt(MARS)?.customView = mars
        bilder.tabLayout.getTabAt(WEATHER)?.customView =layoutInflater.inflate(R.layout.activity_api_costom_tab_weather, null)
    }

    private fun setWeatherTabHighlighted(layoutInflater: LayoutInflater) {
        val weather =
            layoutInflater.inflate(R.layout.activity_api_costom_tab_weather, null)
        weather.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(ContextCompat.getColor(this@ApiActivity, R.color.colorAccent))

        bilder.tabLayout.getTabAt(EARTH)?.customView =
            layoutInflater.inflate(R.layout.activity_api_costom_tab_earch, null)
        bilder.tabLayout.getTabAt(MARS)?.customView =
            layoutInflater.inflate(R.layout.activity_api_costom_tab_mars, null)
        bilder.tabLayout.getTabAt(WEATHER)?.customView = weather
    }
}


