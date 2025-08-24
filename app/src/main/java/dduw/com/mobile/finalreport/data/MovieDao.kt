package dduw.com.mobile.finalreport.data

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns

class MovieDao (context: Context) {
    val helper: MovieDBHelper

    init {
        helper = MovieDBHelper(context)
    }

    @Suppress("Range")
    fun getAllMovie(): ArrayList<MovieDto> {
        val db = helper.readableDatabase
        val cursor = db.query(MovieDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val movies = arrayListOf<MovieDto>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val photo = getInt(getColumnIndex(MovieDBHelper.COL_PHOTO))
                val title = getString(getColumnIndex(MovieDBHelper.COL_TITLE))
                val director = getString(getColumnIndex(MovieDBHelper.COL_DIRECTOR))
                val review = getString(getColumnIndex(MovieDBHelper.COL_REVIEW))
                val number = getString(getColumnIndex(MovieDBHelper.COL_NUMBER))
                val dto = MovieDto(id, photo, title, director, review, number)
                movies.add(dto)
            }

        }
        cursor.close()
        helper.close()
        return movies
    }

    fun addMovie(dto: MovieDto) : Long {
        val db = helper.writableDatabase

        val newRow = ContentValues()
        newRow.put(MovieDBHelper.COL_PHOTO, dto.photo)
        newRow.put(MovieDBHelper.COL_TITLE, dto.title)
        newRow.put(MovieDBHelper.COL_DIRECTOR, dto.director)
        newRow.put(MovieDBHelper.COL_REVIEW, dto.review)
        newRow.put(MovieDBHelper.COL_NUMBER, dto.number)
        val result = db.insert(MovieDBHelper.TABLE_NAME, null, newRow)

        helper.close()
        return result
    }

    fun removeMovie(id: Int) : Int {
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID} = ?"
        val whereArgs = arrayOf(id.toString())
        val result = db.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }

    fun modifyMovie(dto: MovieDto) : Int {
        val db = helper.writableDatabase

        val updateRow = ContentValues()
        updateRow.put(MovieDBHelper.COL_PHOTO, dto.photo)
        updateRow.put(MovieDBHelper.COL_TITLE, dto.title)
        updateRow.put(MovieDBHelper.COL_DIRECTOR, dto.director)
        updateRow.put(MovieDBHelper.COL_REVIEW, dto.review)
        updateRow.put(MovieDBHelper.COL_NUMBER, dto.number)
        val whereClause = "${BaseColumns._ID} = ?"
        val whereArgs = arrayOf(dto.id.toString())
        val result = db.update(MovieDBHelper.TABLE_NAME, updateRow, whereClause, whereArgs)

        helper.close()
        return result

    }
}