package com.example.praktikom.data.remote.dto

import com.example.praktikom.domain.model.Inventory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventoryDto(
    @SerialName("id")
    val id: Int,
    @SerialName("lab_id")
    val labId: Int,
    @SerialName("kode_alat")
    val kodeAlat: String,
    @SerialName("nama_alat")
    val namaAlat: String,
    @SerialName("jumlah_total")
    val jumlahTotal: Int,
    @SerialName("jumlah_tersedia")
    val jumlahTersedia: Int,
    @SerialName("kondisi")
    val kondisi: String? = null,
    @SerialName("foto_url")
    val fotoUrl: String? = null
)

fun InventoryDto.toDomain(): Inventory {
    return Inventory(
        id = id,
        kodeAlat = kodeAlat,
        namaAlat = namaAlat,
        jumlahTotal = jumlahTotal,
        jumlahTersedia = jumlahTersedia,
        kondisi = kondisi ?: "Baik",
        fotoUrl = fotoUrl
    )
}
