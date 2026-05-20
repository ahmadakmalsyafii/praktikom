package com.example.praktikom.ui.presentation.presensi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.praktikom.domain.model.PresenceSession
import com.example.praktikom.ui.theme.PrimaryBlue
import com.example.praktikom.ui.theme.SecondaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresensiScreen(
    viewModel: PresensiViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Presensi",
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.topic,
                onValueChange = { viewModel.onTopicChange(it) },
                placeholder = { Text("Nama topik", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true }) {
                OutlinedTextField(
                    value = state.date,
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    placeholder = { Text("Tanggal pelaksanaan", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledBorderColor = Color.LightGray,
                        disabledPlaceholderColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.weight(1f).clickable { showStartTimePicker = true }) {
                    OutlinedTextField(
                        value = state.startTime,
                        onValueChange = { },
                        readOnly = true,
                        enabled = false,
                        placeholder = { Text("Jam mulai", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            disabledBorderColor = SecondaryBlue,
                            disabledPlaceholderColor = Color.Gray
                        )
                    )
                }
                Box(modifier = Modifier.weight(1f).clickable { showEndTimePicker = true }) {
                    OutlinedTextField(
                        value = state.endTime,
                        onValueChange = { },
                        readOnly = true,
                        enabled = false,
                        placeholder = { Text("Jam selesai", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            disabledBorderColor = SecondaryBlue,
                            disabledPlaceholderColor = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.createPresence() },
                enabled = !state.isCreating,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(24.dp)
            ) {
                if (state.isCreating) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Buat presensi", color = Color.White, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F5F9), shape = RoundedCornerShape(8.dp))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (state.selectedTab == 0) PrimaryBlue else Color.Transparent)
                        .clickable { viewModel.onTabSelected(0) }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Berlangsung",
                        color = if (state.selectedTab == 0) Color.White else Color.Gray,
                        fontWeight = if (state.selectedTab == 0) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (state.selectedTab == 1) PrimaryBlue else Color.Transparent)
                        .clickable { viewModel.onTabSelected(1) }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Riwayat",
                        color = if (state.selectedTab == 1) Color.White else Color.Gray,
                        fontWeight = if (state.selectedTab == 1) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val listToShow = if (state.selectedTab == 0) state.ongoingSessions else state.historySessions

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(listToShow) { session ->
                        PresenceSessionCard(session)
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date   = java.util.Date(millis)
                        val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                        viewModel.onDateChange(format.format(date))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showStartTimePicker) {
        TimePickerModal(
            onTimeSelected = { hour, minute ->
                val h = hour.toString().padStart(2, '0')
                val m = minute.toString().padStart(2, '0')
                viewModel.onStartTimeChange("$h:$m")
                showStartTimePicker = false
            },
            onDismiss = { showStartTimePicker = false }
        )
    }

    if (showEndTimePicker) {
        TimePickerModal(
            onTimeSelected = { hour, minute ->
                val h = hour.toString().padStart(2, '0')
                val m = minute.toString().padStart(2, '0')
                viewModel.onEndTimeChange("$h:$m")
                showEndTimePicker = false
            },
            onDismiss = { showEndTimePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState()
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(timePickerState.hour, timePickerState.minute)
            }) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}

@Composable
fun PresenceSessionCard(session: PresenceSession) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(70.dp)
            ) {
                var dayOfWeek  = ""
                var dayOfMonth = ""
                try {
                    val sdfIn  = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH)
                    val sdfDay = java.text.SimpleDateFormat("EEEE",       java.util.Locale.ENGLISH)
                    val sdfNum = java.text.SimpleDateFormat("dd",          java.util.Locale.ENGLISH)
                    val dateObj = sdfIn.parse(session.date)!!
                    dayOfWeek  = sdfDay.format(dateObj)
                    dayOfMonth = sdfNum.format(dateObj)
                } catch (e: Exception) {
                    dayOfWeek  = ""
                    dayOfMonth = if (session.date.length >= 10) session.date.substring(8, 10) else "??"
                }

                Text(text = dayOfWeek,  color = Color(0xFFE3634B), fontSize = 12.sp)
                Text(text = dayOfMonth, color = Color(0xFF476479), fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp)
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = session.topic,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${session.startTime} - ${session.endTime}",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }

            Icon(Icons.Default.MoreHoriz, contentDescription = "More", tint = Color.Gray)
        }
    }
}
