package dduw.com.mobile.finalreport

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dduw.com.mobile.finalreport.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    lateinit var userBinding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        userBinding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(userBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pref = getSharedPreferences("save_state", 0)
        userBinding.etName.setText(pref.getString("name", ""))
        userBinding.etAge.setText(pref.getString("age", ""))
        userBinding.tvBirth.setText(pref.getString("birth", ""))

        val savedBirth = pref.getString("birth", "")
        if (!savedBirth.isNullOrEmpty()) {
            val date = savedBirth.split(".")
            if (date.size == 3) {
                val year = date[0].toInt()
                val month = date[1].toInt() - 1
                val day = date[2].toInt()
                val calendar = java.util.Calendar.getInstance()
                calendar.set(year, month, day)

                userBinding.calendarView.setDate(calendar.timeInMillis)
            }
        }

        userBinding.btnYear.setOnClickListener {
            val age = userBinding.etAge.text.toString()
            val calendar = java.util.Calendar.getInstance()
            val birthYear = 2025 - age.toInt()
            calendar.set(birthYear, 0, 1)

            userBinding.calendarView.setDate(calendar.timeInMillis)
        }

        userBinding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = java.util.Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val birth = java.text.SimpleDateFormat("yyyy.MM.dd").format(selectedDate.time)
            userBinding.tvBirth.text = birth
        }

        userBinding.btnOk.setOnClickListener {
            finish()
        }

        userBinding.btnUserCancel.setOnClickListener {
            finish()
        }



    }

    override fun onPause() {
        super.onPause()
        val pref: SharedPreferences = getSharedPreferences("save_state", 0)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString("name", userBinding.etName.text.toString())
        editor.putString("age", userBinding.etAge.text.toString())
        editor.putString("birth", userBinding.tvBirth.text.toString())
        editor.commit()
    }


}