package dduw.com.mobile.finalreport.data

import java.io.Serializable

data class MovieDto(val id: Int?, val photo: Int, val title: String, val director: String, val review: String, val number: String) : Serializable {
    override fun toString() = "$id $title $director"
}
