
package com.vola.tv
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vola.tv.databinding.ActivityMainBinding
import com.vola.tv.remote.RemoteService
class MainActivity: AppCompatActivity(){
  lateinit var b: ActivityMainBinding
  override fun onCreate(s:Bundle?){
    super.onCreate(s); b=ActivityMainBinding.inflate(layoutInflater); setContentView(b.root)
    startService(Intent(this, RemoteService::class.java))
    val theme=Store.getTheme(this)
    b.title.text="VOLA - "+theme+" - v3 FULL"
    b.themeLabel.text="الثيم الحالي: "+theme+(if(Store.isPlus(this)) " - Plus ✅" else " - عادي")
    val prefs=getSharedPreferences("vola", MODE_PRIVATE); val server=prefs.getString("server","")?:""
    b.status.text=if(server.isEmpty()) "لسه مدخلتش اشتراك" else "متصل: "+server
    b.btnSetup.setOnClickListener{ startActivity(Intent(this, SetupActivity::class.java)) }
    b.btnLive.setOnClickListener{ open("live") }
    b.btnMovies.setOnClickListener{ open("vod") }
    b.btnSeries.setOnClickListener{ open("series") }
    b.btnFavs.setOnClickListener{ open("favs") }
    b.btnContinue.setOnClickListener{ open("continue") }
    b.btnProfiles.setOnClickListener{ startActivity(Intent(this, ProfilesActivity::class.java)) }
    b.btnRemote.setOnClickListener{ startActivity(Intent(this, RemoteActivity::class.java)) }
    b.btnPlus.setOnClickListener{ startActivity(Intent(this, PlusActivity::class.java)) }
    b.btnDemo.setOnClickListener{ val i=Intent(this, PlayerActivity::class.java); i.putExtra("url","https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"); i.putExtra("title","VOLA Demo"); startActivity(i) }
  }
  fun open(tab:String){ val i=Intent(this, LibraryActivity::class.java); i.putExtra("tab",tab); startActivity(i) }
}
