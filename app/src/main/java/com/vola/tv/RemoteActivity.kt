
package com.vola.tv
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vola.tv.databinding.ActivityRemoteBinding
import android.text.format.Formatter
// Shows TV IP + Pair Code - phone connects on same WiFi to http://IP:8080
class RemoteActivity: AppCompatActivity(){
  lateinit var b: ActivityRemoteBinding
  override fun onCreate(s:Bundle?){ super.onCreate(s); b=ActivityRemoteBinding.inflate(layoutInflater); setContentView(b.root)
    val wm=applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val ip=Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
    b.ipText.text="على نفس الواي فاي، افتح من موبايلك:\nhttp://"+ip+":8080"
    b.codeText.text="كود الربط: "+Store.getPairCode(this)
    b.infoText.text="من الموبايل تقدر: تقلب قنوات، تشغل/توقف، تعلي/توطي، تبحث، وترجع للرئيسية. الموبايل مش بياخد مكان جهاز TV."
  }
}
