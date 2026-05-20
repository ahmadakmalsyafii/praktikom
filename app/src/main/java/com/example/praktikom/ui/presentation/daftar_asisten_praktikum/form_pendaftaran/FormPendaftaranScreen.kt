package com.example.praktikom.ui.presentation.daftar_asisten_praktikum

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.praktikom.ui.theme.PrimaryBlue
import com.example.praktikom.ui.theme.PrimaryOrange
import com.example.praktikom.ui.theme.SecondaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPendaftaranScreen(
    vacancyId: Int,
    onNavigateToRiwayat: () -> Unit,
    onBack: () -> Unit,
    viewModel: FormPendaftaranViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.submitSuccess) {
        if (uiState.submitSuccess) {
            Toast.makeText(context, "Pendaftaran berhasil dikirim!", Toast.LENGTH_LONG).show()
            onNavigateToRiwayat()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val contentResolver = context.contentResolver
            val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
            val name = getFileName(context, uri) ?: "transkrip.pdf"
            if (bytes != null) {
                viewModel.onFileSelected(name, bytes)
            }
        }
    }

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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = PrimaryBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { viewModel.submitForm() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    enabled = !uiState.isSubmitting
                ) {
                    if (uiState.isSubmitting) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Lanjut",
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
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryOrange
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Data diri",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = PrimaryBlue
                    )

                    // prefilled dari supabase auth
                    uiState.user?.let { user ->
                        OutlinedTextField(
                            value = user.nama,
                            onValueChange = {},
                            label = { Text("Nama Lengkap") },
                            textStyle = LocalTextStyle.current.copy(color = PrimaryBlue),
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = SecondaryBlue,
                                focusedLabelColor = PrimaryBlue,
                                unfocusedLabelColor = SecondaryBlue,
                                cursorColor = PrimaryBlue,
                            )
                        )

                        OutlinedTextField(
                            value = user.nim,
                            onValueChange = {},
                            label = { Text("NIM") },
                            textStyle = LocalTextStyle.current.copy(color = PrimaryBlue),
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = SecondaryBlue,
                                focusedLabelColor = PrimaryBlue,
                                unfocusedLabelColor = SecondaryBlue,
                                cursorColor = PrimaryBlue,
                            )
                        )

                        OutlinedTextField(
                            value = user.prodi,
                            onValueChange = {},
                            label = { Text("Program Studi") },
                            textStyle = LocalTextStyle.current.copy(color = PrimaryBlue),
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = SecondaryBlue,
                                focusedLabelColor = PrimaryBlue,
                                unfocusedLabelColor = SecondaryBlue,
                                cursorColor = PrimaryBlue,
                            )
                        )

                        OutlinedTextField(
                            value = user.email,
                            onValueChange = {},
                            label = { Text("Email (wajib email ub)") },
                            textStyle = LocalTextStyle.current.copy(color = PrimaryBlue),
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = SecondaryBlue,
                                focusedLabelColor = PrimaryBlue,
                                unfocusedLabelColor = SecondaryBlue,
                                cursorColor = PrimaryBlue,
                            )
                        )
                    }


                    OutlinedTextField(
                        value = uiState.grade,
                        onValueChange = { viewModel.onGradeChange(it) },
                        label = { Text("Nilai Mata Kuliah Syarat") },
                        textStyle = LocalTextStyle.current.copy(color = PrimaryBlue),
                        placeholder = { Text("Contoh: A, B+, dll.") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = SecondaryBlue,
                            focusedLabelColor = PrimaryBlue,
                            unfocusedLabelColor = SecondaryBlue,
                            cursorColor = PrimaryBlue,
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Upload KHS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF1E3246)
                    )


                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFC))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { fileLauncher.launch("application/pdf") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3246)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Upload,
                                    contentDescription = "Upload",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Add File", color = Color.White)
                            }
                            
                            Text(
                                text = uiState.fileName ?: "Belum ada file dipilih",
                                color = if (uiState.fileName != null) Color(0xFF1E3246) else Color.Gray,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .weight(1f),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = it.getString(index)
                }
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}
