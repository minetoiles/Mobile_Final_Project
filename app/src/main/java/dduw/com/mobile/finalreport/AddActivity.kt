package dduw.com.mobile.finalreport

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dduw.com.mobile.finalreport.data.MovieDao
import dduw.com.mobile.finalreport.data.MovieDto
import dduw.com.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var addBinding: ActivityAddBinding
    lateinit var movieDao: MovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        movieDao = MovieDao(this)
        val adapter = MovieAdapter(movieDao.getAllMovie())

        addBinding.iv1.setImageResource(R.mipmap.movie06)
        addBinding.iv2.setImageResource(R.mipmap.movie07)
        addBinding.iv3.setImageResource(R.mipmap.movie08)

        addBinding.btnAdd.setOnClickListener {
            val title = addBinding.etTitle.text.toString()
            val director = addBinding.etDirector.text.toString()
            val review = addBinding.etReview.text.toString()
            val number = addBinding.etNumber.text.toString()

            var photo = 0
            if (addBinding.poster1.isChecked == true) {
                photo = R.mipmap.movie06
            } else if (addBinding.poster2.isChecked == true){
                photo = R.mipmap.movie07
            } else if (addBinding.poster3.isChecked == true){
                photo = R.mipmap.movie08
            } else {
                Toast.makeText(this, "영화 포스터를 선택하세요.", Toast.LENGTH_SHORT).show()
            }

            if (title.isEmpty()){
                Toast.makeText(this, "영화 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            if (director.isEmpty()){
                Toast.makeText(this, "감독을 입력하세요.", Toast.LENGTH_SHORT).show()
            }


            if (title.isNotEmpty() && director.isNotEmpty() && photo != 0) {
                val result = movieDao.addMovie(MovieDto(null, photo, title, director, review, number))
                if (result > 0){
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "영화 리뷰 추가 실패", Toast.LENGTH_SHORT).show()
                }

            }

        }

        addBinding.btnAddCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }



    }


}