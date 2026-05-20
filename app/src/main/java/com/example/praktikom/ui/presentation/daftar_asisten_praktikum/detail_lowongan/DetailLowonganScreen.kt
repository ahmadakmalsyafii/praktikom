package com.example.praktikom.ui.presentation.daftar_asisten_praktikum.detail_lowongan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import java.util.Locale

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
                        color = PrimaryBlue,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = PrimaryBlue
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
                        .navigationBarsPadding()
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
        },
        containerColor = Color.White
    ) { innerPadding ->

        //State Loading
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryOrange)
            }
        }

        //State Error
        uiState.error?.let { errorMessage ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMessage,
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


        uiState.vacancy?.let { vacancy ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .safeContentPadding()
                    .padding(24.dp)
            ) {
                Text(
                    text = vacancy.course?.namaMk ?: "Mata Kuliah",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = PrimaryBlue,
                )
                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = "Deskripsi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PrimaryBlue,
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
                    color = PrimaryBlue,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Mata Kuliah",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E3246)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Predikat nilai minimal",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = PrimaryBlue.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = vacancy.syaratNilaiMinimal,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryBlue
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "IPK Minimal",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E3246)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Indeks Prestasi Kumulatif",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = PrimaryOrange.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = String.format(Locale.getDefault(), "%.2f", vacancy.syaratIpkMinimal),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryOrange
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Semester & Tahun Ajaran",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF1E3246)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Periode aktif rekrutmen",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFFECEFF1),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${vacancy.semester} / ${vacancy.tahunAjaran}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF37474F)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Alur Pendaftaran",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1E3246)
                )
                Spacer(modifier = Modifier.height(16.dp))

                val batasTanggal = if (vacancy.batasWaktuDaftar.length >= 10) {
                    vacancy.batasWaktuDaftar.take(10)
                } else {
                    vacancy.batasWaktuDaftar
                }

                Column {
                    TimelineItem(
                        date = "Hingga $batasTanggal",
                        title = "Seleksi Administrasi",
                        description = "Upload KHS dan lengkapi berkas pendaftaran asisten praktikum.",
                        isLast = false
                    )
                    TimelineItem(
                        date = "Setelah batas pendaftaran",
                        title = "Pengumuman Kelulusan",
                        description = "Hasil seleksi administrasi dan pengumuman asisten praktikum terpilih.",
                        isLast = true
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineItem(
    date: String,
    title: String,
    description: String,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color = PrimaryOrange, shape = CircleShape)
            )
            if (!isLast) {
                // Penyesuaian Kecil: Memberi sedikit celah agar garis vertikal tidak menempel kaku ke bulatan
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(color = Color(0xFFE2E8F0))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLast) 0.dp else 24.dp)
        ) {
            Text(
                text = date,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryOrange
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3246)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }
    }
}