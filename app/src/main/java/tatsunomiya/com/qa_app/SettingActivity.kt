package tatsunomiya.com.qa_app

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_setting.*
import tatsunomiya.com.qa_app.Const.Companion.NameKEY
import tatsunomiya.com.qa_app.Const.Companion.UsersPath

class SettingActivity : AppCompatActivity() {

    private lateinit var mDataBaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sp.getString(NameKEY, "")
                nameText.setText(name)

        mDataBaseReference = FirebaseDatabase.getInstance().reference


        title="設定"

        changeButton.setOnClickListener{v ->
            val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            val user = FirebaseAuth.getInstance().currentUser

            if(user ==null) {
                Snackbar.make(v,"ログインしていません", Snackbar.LENGTH_LONG).show()

            }else {
                val name = nameText.text.toString()
                val userRef = mDataBaseReference.child(UsersPath).child(user.uid)
                val data = HashMap<String,String>()
                data["name"] = name
                userRef.setValue(data)


                val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                val editor = sp.edit()
                editor.putString(NameKEY,name)
                editor.commit()

                Snackbar.make(v,"表示名を変更しました", Snackbar.LENGTH_LONG).show()
            }
        }

        logoutButton.setOnClickListener { v ->
            FirebaseAuth.getInstance().signOut()
            nameText.setText("")
            Snackbar.make(v,"ログアウトしました", Snackbar.LENGTH_LONG).show()

        }


    }
}
