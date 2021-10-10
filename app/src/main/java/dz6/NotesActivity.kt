package dz6

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerBinding
import java.util.*

class NotesActivity: AppCompatActivity() {

    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var binding: ActivityRecyclerBinding
    private lateinit var adapter:NoteRecyclerAdapter
    private var positionNote:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val date=Calendar.getInstance()
        val data = arrayListOf(Pair(NoteData("название", NoteDate(date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.MONTH),date.get(Calendar.YEAR)),"описание"),false))
        data.add(0, Pair(NoteData("название", NoteDate(date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.MONTH),date.get(Calendar.YEAR)),"описание"),false))

        adapter= NoteRecyclerAdapter(
                object : NoteRecyclerAdapter.OnListItemClickListener{
                    override fun onItemClick(data: NoteData,position:Int) {
                        Toast.makeText(this@NotesActivity, data.noteName+position, Toast.LENGTH_SHORT).show()
                        positionNote=position
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.redactorContainer,RedactorFragment.newInstance(data))
                                .commit()
                    }
                },data,
                object : NoteRecyclerAdapter.OnStartDragListener{
                    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                        itemTouchHelper.startDrag(viewHolder)
                    }
                })
        val fab: FloatingActionButton =findViewById(R.id.recyclerActivityFAB)
        binding.recyclerView.adapter=adapter
        fab.setOnClickListener{adapter.appendItem()}
        itemTouchHelper= ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    fun redactorNoteFromFragment(data: NoteData){
        adapter.redactorItem(data,positionNote)
    }

}


