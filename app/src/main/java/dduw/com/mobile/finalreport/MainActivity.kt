package dduw.com.mobile.finalreport

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dduw.com.mobile.finalreport.data.MovieDao
import dduw.com.mobile.finalreport.databinding.ActivityMainBinding

//과제명: 영화 리뷰 앱
//분반: 01분반
//학번: 20231580 성명: 손민별
//제출일: 2025년 06월 26일

class MainActivity : AppCompatActivity() {
    val ADDACTIVITY_CODE = 100
    val UPDATEACTIVITY_CODE = 200

    val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var movieDao: MovieDao
    lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        movieDao = MovieDao(this)

        //val allMovies: ArrayList<MovieDto> = movieDao.getAllMovie()
        adapter = MovieAdapter(movieDao.getAllMovie())

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter.setOnItemLongClickListener(object : MovieAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                val id = adapter.movieList.get(position).id ?: 0
                val title = adapter.movieList.get(position).title
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("영화 리뷰 삭제")
                    setMessage("$title 리뷰를 삭제하시겠습니까?")
                    setPositiveButton("삭제") { dialog, which ->
                        movieDao.removeMovie(id)
                        adapter.movieList = movieDao.getAllMovie()
                        adapter.notifyDataSetChanged()
                    }
                    setNegativeButton("취소") { dialog, _ ->
                        dialog.cancel()
                    }
                    setCancelable(false)
                }
                val dialog: Dialog = builder.create()
                dialog.show()
                return true
            }
        })

        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val dto = adapter.movieList[position]
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)

                intent.putExtra("update_movie", dto)
                startActivityForResult(intent, UPDATEACTIVITY_CODE)
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.movie -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivityForResult(intent, ADDACTIVITY_CODE)
                true
            }

            R.id.user -> {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.developer -> {
                val intent = Intent(this, DeveloperActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.exit -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADDACTIVITY_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    adapter.movieList = movieDao.getAllMovie()
                    adapter.notifyDataSetChanged()
                }
            }
        } else if (requestCode == UPDATEACTIVITY_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    adapter.movieList = movieDao.getAllMovie()
                    adapter.notifyDataSetChanged()
                }
            }

        }
    }
}
