package recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemEarthBinding
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(private var onListItemClickListener:OnListItemClickListener,private var data: List<Data>):RecyclerView.Adapter<BaseViewHolder>() {


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
            data[position].someDescription.isNullOrBlank() -> TYPE_MARS
           else-> TYPE_EARTH
        }
    }

    inner class EarthViewHolder(view:View): BaseViewHolder(view){
        override fun bind(data:Data){
            if (layoutPosition!=RecyclerView.NO_POSITION){
                ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                    descriptionTextView.text=data.someDescription
                    wikiImageView.setOnClickListener{
                        onListItemClickListener.onItemClick(data)
                    }
                }
            }
        }
    }
    inner class MarsViewHolder(view:View): BaseViewHolder(view){
        override fun bind(data:Data){
            if(layoutPosition!=RecyclerView.NO_POSITION){
                ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                    marsImageView.setOnClickListener{
                        onListItemClickListener.onItemClick(data)
                    }
                }
            }
        }
    }
    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data:Data){
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(data)
            }
        }
    }
    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER=2
    }
    interface OnListItemClickListener {
        fun onItemClick(data:Data)
    }


}