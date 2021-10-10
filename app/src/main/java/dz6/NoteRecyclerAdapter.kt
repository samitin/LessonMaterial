package dz6

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import recyclerview.*
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemNoteBinding
import java.util.*


class NoteRecyclerAdapter (private var onListItemClickListener:OnListItemClickListener,private var data: MutableList<Pair<NoteData, Boolean>>,private val dragListener: OnStartDragListener)
    :RecyclerView.Adapter<BaseViewHolder>(),ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return   NoteViewHolder(inflater.inflate(R.layout.activity_recycler_item_note,parent,false)as View)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount()=data.size

    override fun getItemViewType(position: Int)= if (position==0) TYPE_HEADER else TYPE_NOTE

    fun appendItem(){
        data.add(genereteItem())
        notifyItemInserted(itemCount-1)
    }
    fun redactorItem(noteData:NoteData,position:Int){
        data.get(position).first.noteName=noteData.noteName
        data.get(position).first.noteDate=noteData.noteDate
        data.get(position).first.noteDescription=noteData.noteDescription
        notifyItemChanged(position)
    }

    private fun genereteItem():Pair<NoteData, Boolean>{
        val date=Calendar.getInstance()
        return Pair(NoteData("название",NoteDate(date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.MONTH),date.get(Calendar.YEAR)),"description"),false)}

    inner class NoteViewHolder(view:View): BaseViewHolder(view),ItemTouchHelperViewHolder{
        override fun bind(data:Pair<NoteData, Boolean>){
            if(layoutPosition!=RecyclerView.NO_POSITION){
                val binding=ActivityRecyclerItemNoteBinding.bind(itemView)
                binding.apply {
                    redactorItemImageView.setOnClickListener{onListItemClickListener.onItemClick(data.first,layoutPosition)}
                    removeItemImageView.setOnClickListener{remove()}
                    noteDescriptionTextView.text=data.first.noteDescription
                    noteDescriptionTextView.visibility=if (data.second)View.VISIBLE else View.GONE
                    noteTextViewDate.text="дата ${data.first.noteDate?.day}:${1+ data.first.noteDate?.month!!}:${data.first.noteDate?.year}"
                    noteTextView.text=data.first.noteName
                    noteTextView.setOnClickListener{toggleText()}
                }
                binding.dragHandleImageView.setOnTouchListener { _, event ->
                    if(MotionEventCompat.getActionMasked(event)== MotionEvent.ACTION_DOWN)
                        dragListener.onStartDrag(this)
                    false
                }
            }
        }

        private fun toggleText() {
            data[layoutPosition]=data[layoutPosition].let { it.first to !it.second }
            notifyItemChanged(layoutPosition)
        }

        private fun redactorItem(){}
        private fun addItem(){
            data.add(layoutPosition,genereteItem())
            notifyItemInserted(layoutPosition)
        }
        private fun remove(){
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() =itemView.setBackgroundColor(Color.LTGRAY)

        override fun onItemClear() =itemView.setBackgroundColor(Color.WHITE)
    }
    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data:Pair<NoteData, Boolean>){
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(data.first,0)
            }
        }
    }
    companion object {
        private const val TYPE_NOTE = 0
        private const val TYPE_HEADER=1
    }
    interface OnListItemClickListener {
        fun onItemClick(data: NoteData,position:Int)
    }

    override fun onItemMove(fromPosition: Int, toPosiyion: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if(toPosiyion>fromPosition)toPosiyion-1 else toPosiyion,this)
        }
        notifyItemMoved(fromPosition,toPosiyion)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

}