
package com.vola.tv
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vola.tv.databinding.ActivityProfilesBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vola.tv.databinding.ItemEpisodeBinding
class ProfilesActivity: AppCompatActivity(){
  lateinit var b: ActivityProfilesBinding
  override fun onCreate(s:Bundle?){ super.onCreate(s); b=ActivityProfilesBinding.inflate(layoutInflater); setContentView(b.root); b.title.text="VOLA - بروفايلات"; b.rv.layoutManager=LinearLayoutManager(this); refresh()
    b.btnAdd.setOnClickListener{ val name=b.etName.text.toString().trim(); if(name.isNotEmpty()){ Store.addProfile(this, name, b.cbKid.isChecked); b.etName.setText(""); refresh() } }
  }
  fun refresh(){ val list=Store.getProfiles(this); b.rv.adapter=object: RecyclerView.Adapter<RecyclerView.ViewHolder>(){ override fun onCreateViewHolder(p:ViewGroup,t:Int):RecyclerView.ViewHolder{ val vb=ItemEpisodeBinding.inflate(LayoutInflater.from(p.context),p,false); return object: RecyclerView.ViewHolder(vb.root){} }; override fun getItemCount()=list.size; override fun onBindViewHolder(h:RecyclerView.ViewHolder,pos:Int){ val vb=ItemEpisodeBinding.bind(h.itemView); val profile=list[pos]; vb.title.text=profile.avatar+" "+profile.name; vb.sub.text=if(profile.isKid) "بروفايل أطفال - محتوى آمن" else "بروفايل عادي"; h.itemView.setOnClickListener{ getSharedPreferences("vola_v3", MODE_PRIVATE).edit().putString("active_profile", profile.id).apply(); finish() } } } }
}
