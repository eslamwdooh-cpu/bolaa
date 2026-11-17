
package com.vola.tv
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.vola.tv.databinding.ActivityDetailsBinding
import kotlin.concurrent.thread
class DetailsActivity: AppCompatActivity(){
  lateinit var b: ActivityDetailsBinding
  override fun onCreate(s:Bundle?){ super.onCreate(s); b=ActivityDetailsBinding.inflate(layoutInflater); setContentView(b.root); val sid=intent.getStringExtra("seriesId")?:""; val name=intent.getStringExtra("name")?:""; val poster=intent.getStringExtra("poster")?:""; val plot=intent.getStringExtra("plot")?:""; b.title.text=name; b.plot.text=plot; if(poster.isNotEmpty()) Glide.with(this).load(poster).into(b.poster); b.rv.layoutManager=LinearLayoutManager(this); val p=getSharedPreferences("vola", MODE_PRIVATE); val server=p.getString("server","")!!; val user=p.getString("user","")!!; val pass=p.getString("pass","")!!; thread{ val eps=IptvApi.getSeriesInfo(server,user,pass,sid); runOnUiThread{ b.status.text="حلقات: "+eps.size; b.rv.adapter=EpisodeAdapter(eps){ ep-> val url=IptvApi.episodeUrl(server,user,pass,ep.id,ep.containerExt); val i=Intent(this, PlayerActivity::class.java); i.putExtra("url",url); i.putExtra("title",ep.title); i.putExtra("type","episode"); i.putExtra("id",ep.id); startActivity(i) } } } }
}
