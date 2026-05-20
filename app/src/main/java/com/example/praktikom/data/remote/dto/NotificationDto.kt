package com.example.praktikom.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.praktikom.domain.model.Announcement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.Locale

@Serializable
data class NotificationDto(
    val id: Int,
    @SerialName("class_id") val classId: String,
    val judul: String,
    val pesan: String,
    @SerialName("created_at") val createdAt: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun NotificationDto.toDomain(): Announcement {
    val date = ZonedDateTime.parse(this.createdAt)
    return Announcement(
        id = this.id.toString(),
        title = this.judul,
        message = this.pesan,
        dateDay = date.dayOfMonth.toString(),
        dateMonth = date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    )
}