
package com.vola.tv
import android.content.Context
object Store {
  fun prefs(c:Context)=c.getSharedPreferences("vola_v3", Context.MODE_PRIVATE)
  fun toggleFav(c:Context, type:String, id:String, name:String, poster:String){ val p=prefs(c); val set=p.getStringSet("fav_"+type, mutableSetOf())?.toMutableSet() ?: mutableSetOf(); val key=id+"|"+name+"|"+poster; if(set.contains(key)) set.remove(key) else set.add(key); p.edit().putStringSet("fav_"+type, set).apply() }
  fun isFav(c:Context, type:String, id:String):Boolean { val set=prefs(c).getStringSet("fav_"+type, setOf()) ?: setOf(); return set.any{ it.startsWith(id+"|") } }
  fun getFavs(c:Context, type:String):List<Triple<String,String,String>> { val set=prefs(c).getStringSet("fav_"+type, setOf()) ?: setOf(); return set.map{ val parts=it.split("|"); Triple(parts[0], if(parts.size>1) parts[1] else "", if(parts.size>2) parts[2] else "") } }
  fun saveContinue(c:Context, type:String, id:String, name:String, poster:String, pos:Long){ prefs(c).edit().putString("continue_"+type+"_"+id, id+"|"+name+"|"+poster+"|"+pos).apply() }
  fun getContinue(c:Context):List<String>{ return prefs(c).all.filter{ (it.key as String).startsWith("continue_") }.map{ it.value as String } }
  // Profiles
  fun getProfiles(c:Context):List<Profile>{ val set=prefs(c).getStringSet("profiles", setOf()) ?: setOf(); if(set.isEmpty()) return listOf(Profile("main","الأساسي","👤",false), Profile("kids","أطفال","🧒",true)); return set.map{ val p=it.split("|"); Profile(p[0], if(p.size>1) p[1] else "Profile", if(p.size>2) p[2] else "👤", p.size>3 && p[3]=="kid") } }
  fun addProfile(c:Context, name:String, isKid:Boolean){ val p=prefs(c); val set=p.getStringSet("profiles", mutableSetOf())?.toMutableSet() ?: mutableSetOf(); val id="p"+System.currentTimeMillis(); set.add(id+"|"+name+"|"+(if(isKid) "🧒" else "👤")+"|"+(if(isKid) "kid" else "adult")); p.edit().putStringSet("profiles", set).apply() }
  // Plus / License
  fun isPlus(c:Context):Boolean = prefs(c).getBoolean("plus_active", false)
  fun activatePlus(c:Context, code:String):Boolean { // In real product, verify code on your server - here local demo: VOLA-PLUS-XXX
    if(code.trim().uppercase().startsWith("VOLA-")){ prefs(c).edit().putBoolean("plus_active", true).putString("plus_code", code).apply(); return true }; return false }
  fun getTheme(c:Context):String = prefs(c).getString("theme","NEON_SPORT") ?: "NEON_SPORT"
  fun setTheme(c:Context, theme:String){ prefs(c).edit().putString("theme", theme).apply() }
  fun getPairCode(c:Context):String { var code=prefs(c).getString("pair_code",""); if(code.isNullOrEmpty()){ code=(100000..999999).random().toString(); prefs(c).edit().putString("pair_code", code).apply() }; return code!! }
}
