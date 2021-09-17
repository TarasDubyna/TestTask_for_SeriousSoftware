package tarikdev.app.testtask_serioussoftware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tarikdev.app.testtask_serioussoftware.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}