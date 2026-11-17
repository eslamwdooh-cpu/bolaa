
package com.vola.tv.remote
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.vola.tv.Store
import java.net.ServerSocket
import kotlin.concurrent.thread
// VOLA v3 - Mobile Remote via WiFi
// TV opens a local HTTP server on port 8080 on same WiFi.
// Phone opens http://TV_IP:8080/ and gets a remote page: Up/Down/Left/Right/OK/Back/Home/Play/Pause/Volume/Search
// Commands are broadcast inside app via LocalBroadcast / SharedPrefs lastCommand
class RemoteService: Service(){
  var running=false
  override fun onBind(i:Intent?):IBinder? = null
  override fun onStartCommand(intent:Intent?, flags:Int, startId:Int):Int {
    if(running) return START_STICKY
    running=true
    thread{
      try{
        val server=ServerSocket(8080)
        while(running){
          val sock=server.accept()
          thread{
            try{
              val reader=sock.getInputStream().bufferedReader()
              val line=reader.readLine() ?: ""
              val out=sock.getOutputStream()
              val resp:String
              if(line.contains("GET /cmd?")){
                val cmd=line.substringAfter("cmd=").substringBefore(" ").substringBefore("&")
                getSharedPreferences("vola_remote", MODE_PRIVATE).edit().putString("last_cmd", cmd).putLong("last_cmd_time", System.currentTimeMillis()).apply()
                resp="OK "+cmd
              } else {
                // Remote control HTML page for phone
                resp="<html><head><meta name='viewport' content='width=device-width'><title>VOLA Remote</title></head><body style='background:#050816;color:white;text-align:center;font-family:sans-serif'><h1>VOLA - ريموت</h1><p>كود الربط: "+Store.getPairCode(this@RemoteService)+"</p><div style='display:grid;grid-template-columns:repeat(3,80px);gap:10px;justify-content:center'><button onclick="send('UP')">⬆️</button><button onclick="send('OK')">OK</button><button onclick="send('DOWN')">⬇️</button><button onclick="send('LEFT')">⬅️</button><button onclick="send('BACK')">🔙</button><button onclick="send('RIGHT')">➡️</button><button onclick="send('HOME')">🏠</button><button onclick="send('PLAY')">▶️</button><button onclick="send('PAUSE')">⏸️</button></div><input id='q' placeholder='ابحث...' style='margin:16px;padding:10px;width:80%'><button onclick="send('SEARCH:'+document.getElementById('q').value)">بحث</button><script>function send(c){ fetch('/cmd?cmd='+encodeURIComponent(c)).then(()=>{}) }</script></body></html>"
              }
              val bytes=resp.toByteArray(Charsets.UTF_8)
              out.write(("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: "+bytes.size+"\r\n\r\n").toByteArray())
              out.write(bytes); out.flush(); sock.close()
            }catch(e:Exception){}
          }
        }
      }catch(e:Exception){}
    }
    return START_STICKY
  }
  override fun onDestroy(){ running=false; super.onDestroy() }
}
