package dduw.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import dduw.com.mobile.finalreport.R

class MovieDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    companion object{
        const val DB_NAME = "movie_db"
        const val TABLE_NAME = "movie_table"
        const val COL_PHOTO = "photo"
        const val COL_TITLE = "title"
        const val COL_DIRECTOR = "director"
        const val COL_REVIEW = "review"
        const val COL_NUMBER = "number"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_PHOTO INTEGER," +
                "$COL_TITLE TEXT, " +
                "$COL_DIRECTOR TEXT, " +
                "$COL_REVIEW TEXT, " +
                "$COL_NUMBER REAL)"
        db?.execSQL(createQuery)

        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie01}, '아바타 물의 길', '제임스 카메론', '놀라운 기술력, 3시간이 아쉬울 정도', '4.5')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie02}, '인셉션', '크리스토퍼 놀란', '꿈에 대한 소재를 잘 활용한 영화', '4.8')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie03}, '그대들은 어떻게 살 것인가', '미야자키 하야오', '해석을 봐야만 이해가 가지만 그림체가 역시 지브리..', '4.0')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie04}, '인터스텔라', '크리스토퍼 놀란', '', '')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, ${R.mipmap.movie05}, '날씨의 아이', '신카이 마코토', '', '4.0')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropQuery)
        onCreate(db)
    }
}