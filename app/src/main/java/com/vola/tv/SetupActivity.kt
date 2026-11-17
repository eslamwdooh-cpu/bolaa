
package com.vola.tv
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vola.tv.databinding.ActivitySetupBinding
import kotlin.concurrent.thread
class SetupActivity: AppCompatActivity(){
  lateinit var b: ActivitySetupBinding
  override fun onCreate(s:Bundle?){ super.onCreate(s); b=ActivitySetupBinding.inflate(layoutInflater); setContentView(b.root)
    b.btnConnect.setOnClickListener{
      val server=b.etServer.text.toString().trim(); val user=b.etUser.text.toString().trim(); val pass=b.etPass.text.toString().trim()
      if(server.isEmpty()||user.isEmpty()||pass.isEmpty()){ Toast.makeText(this,"اكتب السيرفر واليوزر والباسورد",Toast.LENGTH_LONG).show(); return@setOnClickListener }
      b.status.text="جاري الاتصال..."
      thread{ try{ val live=IptvApi.getLiveStreams(server,user,pass); val vod=IptvApi.getVod(server,user,pass); val series=IptvApi.getSeries(server,user,pass); runOnUiThread{ getSharedPreferences("vola", MODE_PRIVATE).edit().putString("server",server).putString("user",user).putString("pass",pass).apply(); b.status.text="تمام! لايف: "+live.size+" - أفلام: "+vod.size+" - مسلسلات: "+series.size; Toast.makeText(this,"تم حفظ اشتراكك - VOLA v3",Toast.LENGTH_LONG).show(); finish() } }catch(e:Exception){ runOnUiThread{ b.status.text="فشل: "+e.message } } }
    }
  }
}
