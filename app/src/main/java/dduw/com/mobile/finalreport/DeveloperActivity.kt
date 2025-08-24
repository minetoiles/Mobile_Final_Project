package dduw.com.mobile.finalreport

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dduw.com.mobile.finalreport.databinding.ActivityDeveloperBinding

class DeveloperActivity : AppCompatActivity() {
    lateinit var developerBinding: ActivityDeveloperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        developerBinding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(developerBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        developerBinding.btnBack.setOnClickListener {
            finish()
        }
    }

}