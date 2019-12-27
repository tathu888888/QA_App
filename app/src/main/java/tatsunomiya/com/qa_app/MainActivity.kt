package tatsunomiya.com.qa_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToolbar : Toolbar
    private var mGenre = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            if (mGenre == 0) {
                Snackbar.make(view, "ジャンルを選択してください", Snackbar.LENGTH_LONG).show()

            } else {
                val user = FirebaseAuth.getInstance().currentUser

                if (user == null) {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)

                }

            }

            val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
            val toggle =
                ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name, R.string.app_name)
            drawer.addDrawerListener(toggle)
            toggle.syncState()

            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener(this)


        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (id == R.id.action_settings) {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.nav_hobby) {
            mToolbar.title="趣味"
            mGenre = 1
        }else if(id == R.id.nav_life) {
            mToolbar.title ="生活"
            mGenre = 2

        }else if(id == R.id.nav_health) {
            mToolbar.title ="健康"
            mGenre =3
        }else if (id == R.id.nav_compter) {
            mToolbar.title="コンピューター"
            mGenre =4
        }

        val drawer =  findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)

        return true
    }
    override fun onResume() {
        super.onResume()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)


        if(mGenre == 0) {
            onNavigationItemSelected(navigationView.menu.getItem(0))
        }

    }
}
