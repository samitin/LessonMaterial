package recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemEarthBinding
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(private var onListItemClickListener:OnListItemClickListener,private var data: MutableList<Pair<Data, Boolean>>)
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

    override fun getItemCount()=data.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position==0 -> TYPE_HEADER
            data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
           else-> TYPE_EARTH
        }
    }
    
    fun appendItem(){
        data.add(genereteItem())
        notifyItemInserted(itemCount-1)
    }

    private fun genereteItem()= Pair(Data("Mars",""),false)

    inner class MarsViewHolder(view:View): BaseViewHolder(view),ItemTouchHelperViewHolder{
        override fun bind(data:Pair<Data, Boolean>){
            if(layoutPosition!=RecyclerView.NO_POSITION){
                ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                    marsImageView.setOnClickListener{ onListItemClickListener.onItemClick(data.first) }
                    addItemImageView.setOnClickListener{addItem()}
                    removeItemImageView.setOnClickListener{remove()}
                    moveItemUp.setOnClickListener{moveItemUp()}
                    moveItemDown.setOnClickListener{moveItemDown()}
                    marsDescriptionTextView.visibility=if (data.second)View.VISIBLE else View.GONE
                    marsTextView.setOnClickListener{toggleText()}
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
            data.add(layoutPosition,genereteItem())
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
           itemView.setBackgroundColor(0)
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
        override fun bind(data:Pair<Data, Boolean>){
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(data.first)
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


}