package recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemEarthBinding
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(private var onListItemClickListener:OnListItemClickListener,private var data: MutableList<Pair<Data, Boolean>>,private val dragListener: OnStartDragListener)
    :RecyclerView.Adapter<BaseViewHolder>(),ItemTouchHelperAdapter {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return when(viewType){
             TYPE_EARTH -> EarthViewHolder(inflater.inflate(R.layout.activity_recycler_item_earth,parent,false)as View)
             TYPE_MARS ->  MarsViewHolder(inflater.inflate(R.layout.activity_recycler_item_mars,parent,false)as View)
            else -> HeaderViewHolder(inflater.inflate(R.layout.activity_recycler_item_header, parent, false) as View)
        }


    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
     holder.bind(data[position])
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val combinedChange = createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData
            if (newData.first.someText != oldData.first.someText)
                ActivityRecyclerItemMarsBinding.bind(holder.itemView).marsTextView.text = newData.first.someText
        }
    }
    fun setItems(newItems: List<Pair<Data, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newItems))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newItems)

    }
    override fun getItemCount()=data.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(Data(1, "Mars", ""), false)

    inner class MarsViewHolder(view:View): BaseViewHolder(view),ItemTouchHelperViewHolder{
        override fun bind(data:Pair<Data, Boolean>){
            if(layoutPosition!=RecyclerView.NO_POSITION){
                val binding=ActivityRecyclerItemMarsBinding.bind(itemView)
                binding.apply {
                    marsImageView.setOnClickListener{ onListItemClickListener.onItemClick(data.first) }
                    addItemImageView.setOnClickListener{addItem()}
                    removeItemImageView.setOnClickListener{remove()}
                    moveItemUp.setOnClickListener{moveItemUp()}
                    moveItemDown.setOnClickListener{moveItemDown()}
                    marsDescriptionTextView.visibility=if (data.second)View.VISIBLE else View.GONE
                    marsTextView.setOnClickListener{toggleText()}
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

        private fun moveItemDown(){
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
        private fun moveItemUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }
        private fun addItem(){
            data.add(layoutPosition,generateItem())
            notifyItemInserted(layoutPosition)
        }
        private fun remove(){
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }
    inner class EarthViewHolder(view:View): BaseViewHolder(view){
        override fun bind(data:Pair<Data, Boolean>){
            if (layoutPosition!=RecyclerView.NO_POSITION){
                ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                    descriptionTextView.text=data.first.someDescription
                    wikiImageView.setOnClickListener{
                        onListItemClickListener.onItemClick(data.first)
                    }
                }
            }
        }
    }
    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(dataItem:Pair<Data, Boolean>){
            itemView.setOnClickListener {
               // onListItemClickListener.onItemClick(data.first)
                data[1] = Pair(Data(1,"Jupiter",""), false)
                notifyItemChanged(1,Pair(Data(1,","),false))
            }
        }
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER=2
    }
    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    inner class DiffUtilCallback(private var oldItems: List<Pair<Data, Boolean>>, private var newItems: List<Pair<Data, Boolean>>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition].first.someText == newItems[newItemPosition].first.someText

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return Change(oldItem, newItem)
        }
    }

}