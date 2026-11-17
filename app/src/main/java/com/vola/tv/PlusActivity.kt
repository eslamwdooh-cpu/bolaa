
package com.vola.tv
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vola.tv.databinding.ActivityPlusBinding
// Plus: CINEMA + MINIMAL themes, 5 TVs family, no ads, priority
class PlusActivity: AppCompatActivity(){
  lateinit var b: ActivityPlusBinding
  override fun onCreate(s:Bundle?){ super.onCreate(s); b=ActivityPlusBinding.inflate(layoutInflater); setContentView(b.root)
    b.status.text=if(Store.isPlus(this)) "Plus مفعّل ✅ - كل الثيمات مفتوحة" else "انت على النسخة العادية - NEON SPORT فقط"
    b.btnThemeCinema.setOnClickListener{ if(Store.isPlus(this)){ Store.setTheme(this,"CINEMA"); Toast.makeText(this,"تم تفعيل ثيم CINEMA 🎬",Toast.LENGTH_LONG).show() } else Toast.makeText(this,"ثيم CINEMA للـ Plus بس",Toast.LENGTH_LONG).show() }
    b.btnThemeMinimal.setOnClickListener{ if(Store.isPlus(this)){ Store.setTheme(this,"MINIMAL"); Toast.makeText(this,"تم تفعيل ثيم MINIMAL",Toast.LENGTH_LONG).show() } else Toast.makeText(this,"ثيم MINIMAL للـ Plus بس",Toast.LENGTH_LONG).show() }
    b.btnThemeNeon.setOnClickListener{ Store.setTheme(this,"NEON_SPORT"); Toast.makeText(this,"تم تفعيل NEON SPORT",Toast.LENGTH_LONG).show() }
    b.btnActivate.setOnClickListener{ val code=b.etCode.text.toString().trim(); if(Store.activatePlus(this, code)){ b.status.text="Plus مفعّل ✅"; Toast.makeText(this,"تم تفعيل Plus!",Toast.LENGTH_LONG).show() } else Toast.makeText(this,"كود غلط - جرب VOLA-PLUS-XXX",Toast.LENGTH_LONG).show() }
  }
}
