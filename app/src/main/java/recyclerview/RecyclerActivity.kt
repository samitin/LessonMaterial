package recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.samitin.lessonmaterial.R

class RecyclerActivity : AppCompatActivity() {
    lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val data = arrayListOf(
            Pair(Data("Mars", ""),false)
        )
        data.add(0,Pair(Data("Mars", ""),false))

        val recycler=findViewById<RecyclerView>(R.id.recyclerView)

            val adapter=RecyclerActivityAdapter(
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
    }
}