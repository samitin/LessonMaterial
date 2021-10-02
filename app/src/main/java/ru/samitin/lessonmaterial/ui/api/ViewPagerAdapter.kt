package ru.samitin.lessonmaterial.ui.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val WEATHER_FRAGMENT = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    private val fragments= arrayOf(EarchFragment(),MarsFragment(),WeatherFragment())

    override fun getItem(position: Int): Fragment {
        return when(position){
            EARTH_FRAGMENT -> EarchFragment()
            MARS_FRAGMENT -> MarsFragment()
            WEATHER_FRAGMENT -> WeatherFragment()
            else -> EarchFragment()
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Earth"
            1-> "Mars"
            2-> "Weather"
            else -> ""
        }
    }
}