package com.example.praktikom.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable data object AuthGraph : Route

    @Serializable data object Splash : Route
    @Serializable data object Login  : Route

    @Serializable data object MainGraph : Route

    @Serializable data object Home      : Route
    @Serializable data object Kelas     : Route
    @Serializable data object PinjamBarang    : Route
    @Serializable data object Profil    : Route

    @Serializable data object PendaftaranAsisten    : Route

    @Serializable data object JadwalPraktikum    : Route
    @Serializable data class  DetailLowongan(val vacancyId: Int) : Route
    @Serializable data class  FormPendaftaran(val vacancyId: Int) : Route
    @Serializable data object RiwayatPendaftaran    : Route
    @Serializable data class  Presensi(val assignmentId: String) : Route
    @Serializable data class  Pengumuman(val assignmentId: String) : Route
    @Serializable data object Inventaris            : Route
    @Serializable data class KelasDetailScreen(
        val classId: Int,
        val subject: String,
        val timeInfo: String,
        val room: String
    ) : Route

    @Serializable data class PresensiScreen(
        val classId: Int
    ) : Route
}