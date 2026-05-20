package com.example.praktikom.ui.presentation.daftar_asisten_praktikum

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
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
import com.example.praktikom.domain.model.Vacancy
import com.example.praktikom.ui.theme.PrimaryBlue
import com.example.praktikom.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarAsistenScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToRiwayat: () -> Unit,
    showBackButton: Boolean = false,
    onBack: (() -> Unit)? = null,
    viewModel: DaftarAsistenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lowongan Asisten",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3246)
                    )
                },
                navigationIcon = {
                    if (showBackButton && onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Kembali",
                                tint = Color(0xFF1E3246)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToRiwayat) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Riwayat Pendaftaran",
                            tint = PrimaryOrange
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFC))
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
                            onClick = { viewModel.loadVacancies() },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Text("Coba Lagi")
                        }
                    }
                }
                uiState.vacancies.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Tidak ada lowongan aktif saat ini.",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.vacancies) { vacancy ->
                            VacancyCard(
                                vacancy = vacancy,
                                onClick = { onNavigateToDetail(vacancy.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VacancyCard(
    vacancy: Vacancy,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = vacancy.course?.namaMk ?: "Mata Kuliah",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1E3246)
            )
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF2F4F7))
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Ketentuan Khusus",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column {
                    Text(text = "Predikat MK", fontSize = 12.sp, color = Color.Gray)
                    Text(text = vacancy.syaratNilaiMinimal, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3246))
                }
                Column {
                    Text(text = "IPK Minimal", fontSize = 12.sp, color = Color.Gray)
                    Text(text = String.format("%.2f", vacancy.syaratIpkMinimal), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3246))
                }
                Column {
                    Text(text = "Semester", fontSize = 12.sp, color = Color.Gray)
                    Text(text = "${vacancy.semester} (${vacancy.tahunAjaran})", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3246))
                }
            }
        }
    }
}
