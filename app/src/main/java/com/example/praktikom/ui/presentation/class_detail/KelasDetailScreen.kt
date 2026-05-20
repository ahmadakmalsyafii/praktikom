package com.example.praktikom.ui.presentation.class_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KelasDetailScreen(
    viewModel: KelasDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToPresensi: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail kelas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = state.subject,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "${state.timeInfo}  •  ${state.room}",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { viewModel.onAnnouncementDialogToggle(true) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
                    modifier = Modifier.weight(1f).height(64.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Chat, contentDescription = "Announcement", tint = Color(0xFF1E293B))
                        Text("Pengumuman", color = Color(0xFF1E293B), fontSize = 12.sp)
                    }
                }
                Button(
                    onClick = { onNavigateToPresensi(state.classId) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
                    modifier = Modifier.weight(1f).height(64.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Assignment, contentDescription = "Presensi", tint = Color(0xFF1E293B))
                        Text("Presensi", color = Color(0xFF1E293B), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "List Mahasiswa",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F5F9), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("No.",      modifier = Modifier.weight(0.15f), fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                Text("Nama/NIM", modifier = Modifier.weight(0.60f), fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                Text("Kehadiran",modifier = Modifier.weight(0.25f), fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    itemsIndexed(state.students) { index, student ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (index % 2 == 0) Color.Transparent else Color(0xFFF8FAFC))
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = String.format("%02d", index + 1),
                                modifier = Modifier.weight(0.15f),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )
                            Column(modifier = Modifier.weight(0.60f)) {
                                Text(text = student.name, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E293B))
                                Text(text = student.nim,  fontSize = 10.sp, color = Color.Gray)
                            }
                            Text(
                                text = "${student.presencePercentage}%",
                                modifier = Modifier.weight(0.25f),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp
                            )
                        }
                        Divider(color = Color(0xFFE2E8F0), thickness = 0.5.dp)
                    }
                }
            }
        }
    }

    if (state.showAnnouncementDialog) {
        AnnouncementDialog(
            title       = state.announcementTitle,
            onTitleChange = viewModel::onAnnouncementTitleChange,
            desc        = state.announcementDesc,
            onDescChange  = viewModel::onAnnouncementDescChange,
            onDismiss   = { viewModel.onAnnouncementDialogToggle(false) },
            onSend      = viewModel::sendAnnouncement,
            isSending   = state.isSendingAnnouncement
        )
    }
}

@Composable
fun AnnouncementDialog(
    title: String,
    onTitleChange: (String) -> Unit,
    desc: String,
    onDescChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSend: () -> Unit,
    isSending: Boolean
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pengumuman",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Judul", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    placeholder = { Text("Ketik judul pengumuman...", fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor   = Color(0xFFF8FAFC),
                        unfocusedContainerColor = Color(0xFFF8FAFC),
                        focusedBorderColor      = Color.Transparent,
                        unfocusedBorderColor    = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Deskripsi", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = desc,
                    onValueChange = onDescChange,
                    placeholder = { Text("Ketik pengumuman yang ingin di umumkan...", fontSize = 12.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor   = Color(0xFFF8FAFC),
                        unfocusedContainerColor = Color(0xFFF8FAFC),
                        focusedBorderColor      = Color.Transparent,
                        unfocusedBorderColor    = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onSend,
                    enabled = !isSending && desc.isNotBlank() && title.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    if (isSending) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Kirim", color = Color.White)
                    }
                }
            }
        }
    }
}
