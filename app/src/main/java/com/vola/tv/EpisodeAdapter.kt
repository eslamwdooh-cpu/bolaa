
package com.vola.tv
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vola.tv.databinding.ItemEpisodeBinding
class EpisodeAdapter(val items:List<Episode>, val onClick:(Episode)->Unit): RecyclerView.Adapter<EpisodeAdapter.H>(){ class H(val b:ItemEpisodeBinding): RecyclerView.ViewHolder(b.root); override fun onCreateViewHolder(p:ViewGroup,t:Int)=H(ItemEpisodeBinding.inflate(LayoutInflater.from(p.context),p,false)); override fun getItemCount()=items.size; override fun onBindViewHolder(h:H,pos:Int){ val episode=items[pos]; h.b.title.text="S"+episode.season+" E"+episode.episodeNum+" - "+episode.title; h.b.sub.text=episode.info; h.itemView.setOnClickListener{ onClick(episode) } } }
