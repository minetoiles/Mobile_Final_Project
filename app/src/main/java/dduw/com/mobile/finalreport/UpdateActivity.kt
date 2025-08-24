package dduw.com.mobile.finalreport

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dduw.com.mobile.finalreport.data.MovieDao
import dduw.com.mobile.finalreport.data.MovieDto
import dduw.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding
    lateinit var movieDao : MovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)

        setContentView(updateBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        movieDao = MovieDao(this)

        val updateDto = intent.getSerializableExtra("update_movie") as MovieDto?

        Log.d("UpdateActivity", "updateDto: $updateDto")

        updateBinding.ivPoster.setImageResource(updateDto?.photo?:0)
        updateBinding.udTitle.setHint(updateDto?.title)
        updateBinding.udDirector.setHint(updateDto?.director)
        if (updateDto?.review != null){
            updateBinding.udReview.setHint(updateDto?.review)
        }
        if (updateDto?.number != null){
            updateBinding.udNumber.setHint(updateDto?.number)
        }



        updateBinding.btnUpdate.setOnClickListener {
            val photo = updateDto?.photo?:0
            val title = updateBinding.udTitle.text.toString()
            val director = updateBinding.udDirector.text.toString()
            val review = updateBinding.udReview.text.toString()
            val number = updateBinding.udNumber.text.toString()

            if (title.isEmpty()){
                Toast.makeText(this, "영화 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            if (director.isEmpty()){
                Toast.makeText(this, "감독을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (title.isNotEmpty() && director.isNotEmpty()) {
                val result = movieDao.modifyMovie(MovieDto(updateDto?.id, photo, title, director, review, number))
                if (result > 0){
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "영화 리뷰 수정 실패", Toast.LENGTH_SHORT).show()
                }

            }
        }

        updateBinding.btnUpdateCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }


}