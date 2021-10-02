package ru.samitin.lessonmaterial.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.FragmentWeatherBinding
import ru.samitin.lessonmaterial.repository.SolarFlareResponseData
import ru.samitin.lessonmaterial.viewModel.AppState
import ru.samitin.lessonmaterial.viewModel.OneBigFatViewModel

class WeatherFragment : Fragment(){

    private var _binding : FragmentWeatherBinding?=null
    private val binding
    get() = _binding!!

    private val viewModel :OneBigFatViewModel by lazy {
        ViewModelProvider(this).get(OneBigFatViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner,{ renderData(it)})
        viewModel.getSolarFlare(MONTH)
    }
    private fun renderData(data : AppState){
        when(data){
            is AppState.SuccessWeather ->{
                binding.conteinerWeather.show()
                binding.loadingLayout.hide()
                Toast.makeText(context,"Succes",Toast.LENGTH_SHORT).show()
                val infoAll=StringBuilder()
                for (info in data.solarFlareResponseData)
                    infoAll.append(getSolarFairInfo(info))
                 binding.weatherTextInfo.text=infoAll.toString()
            }
            is AppState.Loading ->{
                binding.conteinerWeather.hide()
                binding.loadingLayout.show()
                Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show()
            }
            is AppState.Error ->{
                binding.conteinerWeather.hide()
                binding.loadingLayout.show()
                Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()
                binding.conteinerWeather.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) {
                    viewModel.getSolarFlare(0)
                }

            }
        }
    }
    private fun getSolarFairInfo(serverResponseData: SolarFlareResponseData) : String{
        var info=""
        with(serverResponseData){
            info="\n \n fliD = $flrID \n"+
                    "begin time = $beginTime \n"+
                    "peak time = $peakTime \n"+
                    "end time = $endTime \n"+
                    "class type = $classType \n"+
                    "source location = $sourceLocation \n"+
                    "active region num = $activeRegionNum \n"+
                    "link = $link \n"
        }
        return info
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }

        private const val TODAY = 0
        private const val MONTH = 30
    }
}