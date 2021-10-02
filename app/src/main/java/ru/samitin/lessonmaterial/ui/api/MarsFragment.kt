package ru.samitin.lessonmaterial.ui.api

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.textview.MaterialTextView
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.FragmentMarsBinding
import ru.samitin.lessonmaterial.repository.MarsPhotosServerResponseData
import ru.samitin.lessonmaterial.utils.zoomImageView
import ru.samitin.lessonmaterial.viewModel.AppState
import ru.samitin.lessonmaterial.viewModel.OneBigFatViewModel


class MarsFragment : Fragment(){
    private var _binding : FragmentMarsBinding?=null
    private val binding
        get() = _binding!!

    private val viewModel : OneBigFatViewModel by lazy {
        ViewModelProvider(this).get(OneBigFatViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentMarsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner,{
            renderData(it)
        })

        viewModel.getMarsPicture()
    }
    private fun renderData(data : AppState){
        when(data){
            is AppState.SuccessMars ->{
                binding.conteinerMarsPhotos.show()
                binding.loadingLayout.hide()
                Toast.makeText(context,"Succes",Toast.LENGTH_SHORT).show()
                data.serverResponseData?.let { getMarsWeatherInfo(it) }
            }
            is AppState.Loading ->{
                Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show()
                binding.conteinerMarsPhotos.hide()
                binding.loadingLayout.show()
            }
            is AppState.Error ->{
                binding.conteinerMarsPhotos.hide()
                binding.loadingLayout.show()
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                binding.conteinerMarsPhotos.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) {
                    viewModel.getMarsPicture()
                }
            }
        }
    }
    private fun getMarsWeatherInfo(serverResponseData: MarsPhotosServerResponseData){
        for (marsPhoto in serverResponseData.photos!!) {
            val dateText = MaterialTextView(requireContext())
            dateText.text = "Дата земли : ${marsPhoto.earth_date}"
            val image:AppCompatImageView =
                LayoutInflater.from(requireContext()).inflate(R.layout.my_image,binding.conteinerMarsPhotos,false) as AppCompatImageView
            

            image.load(marsPhoto.imgSrc){
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.progres_image_animation)
            }
            image.setOnClickListener {
                zoomImageView(it as AppCompatImageView)
            }
            binding.conteinerMarsPhotos.addView(image)
            binding.conteinerMarsPhotos.addView(dateText)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}


