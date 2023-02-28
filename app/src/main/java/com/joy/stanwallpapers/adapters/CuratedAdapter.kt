package com.joy.stanwallpapers.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.joy.stanwallpapers.MainActivity
import com.joy.stanwallpapers.Models.Photo
import com.joy.stanwallpapers.R
import com.joy.stanwallpapers.listeners.OnRecyclerClickListener
import com.squareup.picasso.Picasso

class CuratedAdapter: RecyclerView.Adapter<CuratedViewHolder>{
    lateinit var context:Context
    lateinit var list:List<Photo>
    lateinit var listener: OnRecyclerClickListener

    constructor(context: Context, list: List<Photo>, listener: OnRecyclerClickListener) : super() {
        this.context = context
        this.list = list
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuratedViewHolder {
        return CuratedViewHolder(LayoutInflater.from(context).inflate(R.layout.home_list , parent , false))
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: CuratedViewHolder, position: Int) {
        Picasso.get().load(list[position].src?.medium).placeholder(R.drawable.placeholder).into(holder.image_container)
        holder.home_list_container.setOnClickListener (View.OnClickListener {
             listener.onClick(list.get(holder.adapterPosition))
        })
    }

}

class CuratedViewHolder : RecyclerView.ViewHolder{
    lateinit var home_list_container: CardView
    lateinit var image_container: ImageView
    constructor(itemView: View):super(itemView){
        home_list_container = itemView.findViewById(R.id.home_list_container)
        image_container = itemView.findViewById(R.id.image_container)

    }



}