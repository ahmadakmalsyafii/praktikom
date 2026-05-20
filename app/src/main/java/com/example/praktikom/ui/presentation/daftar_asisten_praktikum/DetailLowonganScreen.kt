package com.example.praktikom.ui.presentation.daftar_asisten_praktikum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.praktikom.ui.theme.PrimaryBlue
import com.example.praktikom.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLowonganScreen(
    vacancyId: Int,
    onNavigateToForm: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: DetailLowonganViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Rekrutmen Asisten Praktikum",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3246),
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color(0xFF1E3246)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            uiState.vacancy?.let { vacancy ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { onNavigateToForm(vacancy.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Text(
                            text = "Daftar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = PrimaryOrange
                    )
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.error ?: "Terjadi kesalahan",
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadVacancyDetail() },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Text("Coba Lagi")
                        }
                    }
                }
                uiState.vacancy != null -> {
                    val vacancy = uiState.vacancy!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp)
                    ) {
                        Text(
                            text = vacancy.course?.namaMk ?: "Mata Kuliah",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color(0xFF1E3246)
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        // Deskripsi Pekerjaan
                        Text(
                            text = "Deskripsi Pekerjaan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF1E3246)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = vacancy.description ?: "Tidak ada deskripsi untuk lowongan ini.",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Syarat dan Ketentuan
                        Text(
                            text = "Syarat dan Ketentuan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF1E3246)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFC))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Predikat Mata Kuliah", fontSize = 14.sp, color = Color.Gray)
                                    Text("Minimal ${vacancy.syaratNilaiMinimal}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3246))
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("IPK Minimal", fontSize = 14.sp, color = Color.Gray)
                                    Text(String.format("%.2f", vacancy.syaratIpkMinimal), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3246))
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Semester / Tahun Ajaran", fontSize = 14.sp, color = Color.Gray)
                                    Text("${vacancy.semester} / ${vacancy.tahunAjaran}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3246))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        // Alur Pendaftaran
                        Text(
                            text = "Alur Pendaftaran",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF1E3246)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            TimelineItem(
                                date = "Hingga ${vacancy.batasWaktuDaftar.take(10)}",
                                title = "Seleksi Administrasi",
                                description = "Upload KHS dan lengkapi berkas pendaftaran asisten praktikum."
                            )
                            TimelineItem(
                                date = "Setelah batas pendaftaran",
                                title = "Pengumuman Kelulusan",
                                description = "Hasil seleksi administrasi dan pengumuman asisten praktikum terpilih."
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineItem(date: String, title: String, description: String) {
    Column {
        Text(
            text = date,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryOrange
        )
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3246)
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
