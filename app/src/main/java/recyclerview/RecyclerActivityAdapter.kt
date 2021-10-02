package recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemEarthBinding
import ru.samitin.lessonmaterial.databinding.ActivityRecyclerItemMarsBinding

class RecyclerActivityAdapter(private var onListItemClickListener:OnListItemClickListener,private var data: List<Data>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return if(viewType==TYPE_EARTH)
            EarthViewHolder(inflater.inflate(R.layout.activity_recycler_item_earth,parent,false)as View)
        else
            MarsViewHolder(inflater.inflate(R.layout.activity_recycler_item_mars,parent,false)as View)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)== TYPE_EARTH){
            holder as EarthViewHolder
            holder.bind(data.get(position))
        }else{
            holder as MarsViewHolder
            holder.bind(data.get(position))
        }
    }

    override fun getItemCount()=data.size

    override fun getItemViewType(position: Int): Int {
        return if (data[position].someDescription.isNullOrBlank())TYPE_MARS else TYPE_EARTH
    }

    inner class EarthViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(data:Data){
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
    inner class MarsViewHolder(view:View): RecyclerView.ViewHolder(view){
        fun bind(data:Data){
            if(layoutPosition!=RecyclerView.NO_POSITION){
                ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                    marsImageView.setOnClickListener{
                        onListItemClickListener.onItemClick(data)
                    }
                }
            }
        }
    }
    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
    }
    interface OnListItemClickListener {
        fun onItemClick(data:Data)
    }


}