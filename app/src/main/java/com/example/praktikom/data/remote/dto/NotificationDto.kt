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
    @SerialName("class_id") val classId: Int,          // int4 in DB, not String
    @SerialName("asisten_id") val asistenId: String? = null, // uuid, nullable
    val judul: String,
    val pesan: String,
    @SerialName("created_at") val createdAt: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun NotificationDto.toDomain(): Announcement {
    
    val normalized = this.createdAt
        .replace(" ", "T")
        .let { if (it.endsWith("+00")) it + ":00" else it }
    val date = ZonedDateTime.parse(normalized)
    return Announcement(
        id = this.id.toString(),
        title = this.judul,
        message = this.pesan,
        dateDay = date.dayOfMonth.toString(),
        dateMonth = date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    )
}