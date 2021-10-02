package ru.samitin.lessonmaterial.ui.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import ru.samitin.lessonmaterial.BuildConfig
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.FragmentEarchBinding
import ru.samitin.lessonmaterial.utils.zoomImageView
import ru.samitin.lessonmaterial.viewModel.AppState
import ru.samitin.lessonmaterial.viewModel.OneBigFatViewModel

class EarchFragment :Fragment() {

    private var _binding: FragmentEarchBinding? = null
    private val binding: FragmentEarchBinding
        get() {
            return _binding!!
        }


    private val viewModel : OneBigFatViewModel by lazy {
        ViewModelProvider(this).get(OneBigFatViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner,{ render(it) })
        viewModel.getEpic()
    }

    private fun render(appState: AppState) {
        when(appState){
            is AppState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is AppState.Loading -> {
                binding.customImageView.load(R.drawable.progres_image_animation)
            }
            is AppState.SuccessEarthEpic -> {
                // немного магии датамайнинга
                val strDate = appState.serverResponseData.last().date.split(" ").first()
                val image =appState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-","/",true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"

                binding.customImageView.load(url)
                binding.customImageView.setOnClickListener{
                    zoomImageView(binding.customImageView)
                }
            }
        }
    }



   /* private fun showAVideoUrl(videoUrl: String) = with(binding) {
        customImageView.visibility = View.GONE
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    companion object{
        fun newInstance(): EarchFragment {
            return EarchFragment()
        }
        private const val TODAY = 0
        private const val YESTERDAY = 1
        private const val BEFORE_YESTERDAY = 2
    }
}