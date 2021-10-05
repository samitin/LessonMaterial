package recyclerview

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.samitin.lessonmaterial.R

class RecyclerActivity : AppCompatActivity() {
    private var isNewList = false
    private lateinit var adapter: RecyclerActivityAdapter
    lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val data = arrayListOf(
            Pair(Data(0,"Mars", ""),false)
        )
        data.add(0,Pair(Data(1,"Mars", ""),false))

        val recycler=findViewById<RecyclerView>(R.id.recyclerView)

            adapter=RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener{
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },data,
            object : RecyclerActivityAdapter.OnStartDragListener{
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            })
        val fab:FloatingActionButton=findViewById(R.id.recyclerActivityFAB)
        recycler.adapter=adapter
        fab.setOnClickListener{adapter.appendItem()}
        itemTouchHelper= ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recycler)
        val recyclerActivityDiffUtilFAB:FloatingActionButton=findViewById(R.id.recyclerActivityDiffUtilFAB)
        recyclerActivityDiffUtilFAB.setOnClickListener {
            Toast.makeText(this@RecyclerActivity, "recyclerActivityDiffUtilFAB", Toast.LENGTH_SHORT).show()
            changeAdapterData()
        }

    }
    private fun changeAdapterData() {
        try {
            adapter.setItems(createItemList(isNewList))
        }catch (e:Exception){
            e.message?.let { Log.e("recycler", it) }
        }
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                    Pair(Data(0, "Header"), false),
                    Pair(Data(1, "Mars", ""), false),
                    Pair(Data(2, "Mars", ""), false),
                    Pair(Data(3, "Mars", ""), false),
                    Pair(Data(4, "Mars", ""), false),
                    Pair(Data(5, "Mars", ""), false),
                    Pair(Data(6, "Mars", ""), false)
            )
            true -> listOf(
                    Pair(Data(0, "Header"), false),
                    Pair(Data(1, "Mars", ""), false),
                    Pair(Data(2, "Jupiter", ""), false),
                    Pair(Data(3, "Mars", ""), false),
                    Pair(Data(4, "Neptune", ""), false),
                    Pair(Data(5, "Saturn", ""), false),
                    Pair(Data(6, "Mars", ""), false)
            )
        }
    }
}