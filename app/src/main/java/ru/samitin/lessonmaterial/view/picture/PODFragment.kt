package ru.samitin.lessonmaterial.view.picture



import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.view.*
import android.widget.Toast

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.FragmentMainStartBinding
import ru.samitin.lessonmaterial.ui.api.ApiBottomActivity
import ru.samitin.lessonmaterial.ui.api.showSnackBar
import ru.samitin.lessonmaterial.utils.showComponents
import ru.samitin.lessonmaterial.view.MainActivity
import ru.samitin.lessonmaterial.view.chips.SettingsFragment
import ru.samitin.lessonmaterial.viewModel.AppState
import ru.samitin.lessonmaterial.viewModel.OneBigFatViewModel


class PODFragment : Fragment() {
    private var _bainding: FragmentMainStartBinding?=null
    private val binding
        get()=_bainding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: OneBigFatViewModel by lazy {
        ViewModelProvider(this).get(OneBigFatViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bainding= FragmentMainStartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderDATA(it) })
        inicialiseChips()
        setBottomAppBar(view)

       // bottomSheetBehavior=BottomSheetBehavior.from(binding.includeLayout.bottomSheetContainer)
       // bottomSheetBehavior.state=BottomSheetBehavior.STATE_HIDDEN
        searchWikipedia()

       //createBihavior()
    }




    private fun inicialiseChips() {
        binding.chipGroupMain.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId){
                 R.id.main_chip_today ->  { viewModel.getPODFromServer(TODAY) }
                R.id.main_chip_yestoday ->{viewModel.getPODFromServer(YESTERDAY) }
                R.id.main_chip_before_yestoday ->{viewModel.getPODFromServer(BEFORE_YESTERDAY) }
                else ->viewModel.getPODFromServer(TODAY)
            }
        }
    }

    fun createBihavior(){
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
                    BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                TODO("not implemented")
            }
        })
    }
    fun searchWikipedia(){
        binding.inputLayout.setEndIconOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW).apply {
                data= Uri.parse("https://ru.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            }
            startActivity(intent)
        }
    }
    private fun renderDATA(data: AppState){
        when (data){
            is AppState.SuccessPOD->{

                data.serverResponseData
                binding.imageView.load(data.serverResponseData.url){
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.progres_image_animation)
                }
                binding.tvDescription.text=data.serverResponseData.explanation
                Toast.makeText(context,"Succes",Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading->{
                Toast.makeText(context,"Load",Toast.LENGTH_SHORT).show()
                binding.imageView.load(R.drawable.progres_image_animation){}
            }
            is AppState.Error->{
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                binding.imageView.load(R.drawable.progres_image_animation){
                    error(R.drawable.ic_load_error_vector)
                }
                binding.constraintConteiner.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                ) {
                    inicialiseChips()
                }
            }//TODO HW
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bainding==null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav ->{activity?.let { startActivity(Intent(it, ApiBottomActivity::class.java))
                Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            }
            }
            R.id.app_bar_search -> requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).addToBackStack("").commit()
            // у нашего бургера такой вот id внутри android
            android.R.id.home-> BottomNavigationDraverFragment().show(requireActivity().supportFragmentManager,"TAG")

        }
        return super.onOptionsItemSelected(item)
    }

    private var isMain=true
    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                showComponents(R.layout.fragment_main_end,binding.constraintConteiner,requireContext())
                isMain = false
                binding.bottomAppBar.navigationIcon = null // лучше придумать замену бургеру
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                showComponents(R.layout.fragment_main_start,binding.constraintConteiner,requireContext())
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_hamburger_menu_bottom_bar
                    )
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }
     companion object{
        fun newInstance(): PODFragment {
            return PODFragment()
        }
        private const val TODAY = 0
        private const val YESTERDAY = 1
        private const val BEFORE_YESTERDAY = 2
    }
}