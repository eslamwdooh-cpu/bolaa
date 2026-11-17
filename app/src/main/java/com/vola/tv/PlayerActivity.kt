
package com.vola.tv
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.vola.tv.databinding.ActivityPlayerBinding
class PlayerActivity: AppCompatActivity(){
  var player:ExoPlayer?=null; lateinit var b: ActivityPlayerBinding; var type=""; var id=""; var titleStr=""; var poster=""
  override fun onCreate(s:Bundle?){ super.onCreate(s); b=ActivityPlayerBinding.inflate(layoutInflater); setContentView(b.root); val url=intent.getStringExtra("url")?:""; titleStr=intent.getStringExtra("title")?:"VOLA"; type=intent.getStringExtra("type")?:""; id=intent.getStringExtra("id")?:""; poster=intent.getStringExtra("poster")?:""; b.title.text=titleStr; player=ExoPlayer.Builder(this).build(); b.playerView.player=player; player?.setMediaItem(MediaItem.fromUri(url)); player?.prepare(); player?.play(); b.btnFav.setOnClickListener{ Store.toggleFav(this,type,id,titleStr,poster); b.btnFav.text=if(Store.isFav(this,type,id)) "★ مفضل" else "☆ مفضلة" }; b.btnFav.text=if(Store.isFav(this,type,id)) "★ مفضل" else "☆ مفضلة" }
  override fun onPause(){ super.onPause(); player?.let{ if(type.isNotEmpty() && id.isNotEmpty()) Store.saveContinue(this,type,id,titleStr,poster,it.currentPosition) } }
  override fun onDestroy(){ super.onDestroy(); player?.release() }
}
