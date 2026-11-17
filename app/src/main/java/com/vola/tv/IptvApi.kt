
package com.vola.tv
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
data class Category(val id:String, val name:String)
data class Channel(val id:String, val name:String, val icon:String, val categoryId:String, val streamId:String, val epgId:String="")
data class Vod(val id:String, val name:String, val poster:String, val backdrop:String, val categoryId:String, val ext:String, val rating:String, val plot:String, val year:String)
data class SeriesItem(val id:String, val name:String, val poster:String, val backdrop:String, val categoryId:String, val rating:String, val plot:String)
data class Episode(val id:String, val title:String, val season:String, val episodeNum:String, val containerExt:String, val info:String)
data class EpgEvent(val title:String, val start:String, val end:String, val desc:String)
data class Profile(val id:String, val name:String, val avatar:String, val isKid:Boolean)
object IptvApi {
  private val client = OkHttpClient()
  fun base(server:String):String { val s=server.trim().removePrefix("http://").removePrefix("https://").trimEnd('/'); return "http://"+s }
  private fun get(server:String,user:String,pass:String,action:String,extra:String=""):String {
    val url = base(server)+"/player_api.php?username="+user+"&password="+pass+"&action="+action+extra
    val req = Request.Builder().url(url).header("User-Agent","VOLA/3.0").build()
    return client.newCall(req).execute().body?.string() ?: "[]"
  }
  fun getCategories(server:String,user:String,pass:String,type:String):List<Category>{ val arr=JSONArray(get(server,user,pass,type)); val out=mutableListOf<Category>(); for(i in 0 until arr.length()){ val o=arr.getJSONObject(i); out.add(Category(o.optString("category_id"), o.optString("category_name"))) }; return out }
  fun getLiveStreams(server:String,user:String,pass:String,catId:String=""):List<Channel>{ val extra=if(catId.isNotEmpty()) "&category_id="+catId else ""; val arr=JSONArray(get(server,user,pass,"get_live_streams",extra)); val out=mutableListOf<Channel>(); for(i in 0 until arr.length()){ val o=arr.getJSONObject(i); out.add(Channel(o.optString("stream_id"), o.optString("name"), o.optString("stream_icon"), o.optString("category_id"), o.optString("stream_id"), o.optString("epg_channel_id"))) }; return out }
  fun getVod(server:String,user:String,pass:String,catId:String=""):List<Vod>{ val extra=if(catId.isNotEmpty()) "&category_id="+catId else ""; val arr=JSONArray(get(server,user,pass,"get_vod_streams",extra)); val out=mutableListOf<Vod>(); for(i in 0 until arr.length()){ val o=arr.getJSONObject(i); val info=o.optJSONObject("info") ?: JSONObject(); out.add(Vod(o.optString("stream_id"), o.optString("name"), o.optString("stream_icon"), o.optString("backdrop_path"), o.optString("category_id"), o.optString("container_extension"), info.optString("rating"), info.optString("plot"), info.optString("releasedate"))) }; return out }
  fun getSeries(server:String,user:String,pass:String,catId:String=""):List<SeriesItem>{ val extra=if(catId.isNotEmpty()) "&category_id="+catId else ""; val arr=JSONArray(get(server,user,pass,"get_series",extra)); val out=mutableListOf<SeriesItem>(); for(i in 0 until arr.length()){ val o=arr.getJSONObject(i); out.add(SeriesItem(o.optString("series_id"), o.optString("name"), o.optString("cover"), o.optString("backdrop_path"), o.optString("category_id"), o.optString("rating"), o.optString("plot"))) }; return out }
  fun getSeriesInfo(server:String,user:String,pass:String,seriesId:String):List<Episode>{ val obj=JSONObject(get(server,user,pass,"get_series_info","&series_id="+seriesId)); val eps=obj.optJSONObject("episodes") ?: return emptyList(); val out=mutableListOf<Episode>(); val keys=eps.keys(); while(keys.hasNext()){ val season=keys.next(); val arr=eps.getJSONArray(season); for(i in 0 until arr.length()){ val o=arr.getJSONObject(i); val info=o.optJSONObject("info") ?: JSONObject(); out.add(Episode(o.optString("id"), o.optString("title"), season, o.optString("episode_num"), o.optString("container_extension"), info.optString("plot"))) } }; return out.sortedBy{ it.episodeNum.toIntOrNull() ?: 0 } }
  fun getShortEpg(server:String,user:String,pass:String,streamId:String):List<EpgEvent>{ val obj=JSONObject(get(server,user,pass,"get_short_epg","&stream_id="+streamId)); val arr=obj.optJSONArray("epg_listings") ?: return emptyList(); val out=mutableListOf<EpgEvent>(); for(i in 0 until arr.length()){ val o=arr.getJSONObject(i); out.add(EpgEvent(o.optString("title"), o.optString("start"), o.optString("end"), o.optString("description"))) }; return out }
  fun liveUrl(server:String,user:String,pass:String,id:String):String = base(server)+"/live/"+user+"/"+pass+"/"+id+".m3u8"
  fun vodUrl(server:String,user:String,pass:String,id:String,ext:String):String = base(server)+"/movie/"+user+"/"+pass+"/"+id+"."+ext
  fun episodeUrl(server:String,user:String,pass:String,id:String,ext:String):String = base(server)+"/series/"+user+"/"+pass+"/"+id+"."+ext
}
