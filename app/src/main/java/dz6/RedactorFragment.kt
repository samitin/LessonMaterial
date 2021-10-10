package dz6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import ru.samitin.lessonmaterial.databinding.FragmentRedactorBinding

class RedactorFragment : Fragment(){
    private var _binding: FragmentRedactorBinding? = null
    private val binding: FragmentRedactorBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentRedactorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteData = arguments?.getParcelable<NoteData>(REDACTOR_NOTE)
        if (noteData!=null){
            binding.redactorNameEditText.setText(noteData.noteName)
            binding.redactorDate.init(noteData.noteDate!!.year,noteData.noteDate!!.month,noteData.noteDate!!.day, DatePicker.OnDateChangedListener { view, year, monthOfYear, dayOfMonth ->  })
            binding.redactorDescriptionEditText.setText(noteData.noteDescription )
        }
        binding.redactorButton.setOnClickListener {
            val noteDate=NoteDate(binding.redactorDate.dayOfMonth,binding.redactorDate.month,binding.redactorDate.year)
            val noteData=NoteData(
                    binding.redactorNameEditText.text.toString(),
                    noteDate,
                    binding.redactorDescriptionEditText.text.toString()
            )
            val activity=requireActivity() as NotesActivity
            activity.redactorNoteFromFragment(noteData)
            activity.supportFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    companion object {
        fun newInstance(noteData:NoteData):RedactorFragment {
            val redactor=RedactorFragment()
            val bundle=Bundle()
            bundle.putParcelable(REDACTOR_NOTE,noteData)
            redactor.arguments=bundle
            return redactor
        }
        const val REDACTOR_NOTE="REDACTOR_NOTE"
    }

}