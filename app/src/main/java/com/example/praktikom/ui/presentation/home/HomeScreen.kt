package com.example.praktikom.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.praktikom.R
import com.example.praktikom.domain.model.Announcement
import com.example.praktikom.domain.model.Schedule

@Composable
fun HomeScreen(
    onNavigateToDaftarAsprak: () -> Unit,
    onNavigateToJadwal: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }


        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.logo_praktikom), // Pastikan ada icon logo
                    contentDescription = "Logo",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PRAKTIKOM",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3246)
                )
            }
        }


        item {
            val pagerState = rememberPagerState(pageCount = { uiState.banners.size })
            Box(modifier = Modifier.fillMaxWidth()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) { page ->
                    val banner = uiState.banners[page]
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = banner.imageUrl,
                            contentDescription = banner.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                        startY = 100f
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(text = banner.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = banner.description, color = Color.White, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp)
                        )
                    }
                }
            }
        }


        item {
            Text(
                text = "Halo, ${uiState.userName}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E3246)
            )
        }


        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionCard(icon = Icons.Default.ListAlt, title = "Daftar Asisten Praktikum", onClick = onNavigateToDaftarAsprak)
                    ActionCard(icon = Icons.Default.Class, title = "Jadwal Praktikum", onClick = onNavigateToJadwal)
                }


                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Jadwal Prakt... >",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF1E3246)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        uiState.schedules.forEach { schedule ->
                            ScheduleItem(schedule)
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Pengumuman", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3246))
                        Icon(imageVector = Icons.Default.FilterAlt, contentDescription = "Filter", tint = Color(0xFF1E3246))
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    uiState.announcements.forEach { announcement ->
                        AnnouncementItem(announcement)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun ActionCard(icon: ImageVector, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F6F8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF1E3246), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E3246))
        }
    }
}

@Composable
fun ScheduleItem(schedule: Schedule) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFFF5F6F8), RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = schedule.room, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
        Column(horizontalAlignment = Alignment.End) {
            Text(text = schedule.day, fontSize = 10.sp, color = Color.Gray)
            Text(text = schedule.time, fontSize = 10.sp, color = Color.DarkGray)
        }
    }
}

@Composable
fun AnnouncementItem(announcement: Announcement) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = announcement.dateDay, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(text = announcement.dateMonth, fontSize = 12.sp, color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(text = announcement.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = announcement.message, fontSize = 12.sp, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}