package com.example.swastify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swastify.ui.theme.SwastifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaisedComplaintsScreen(navController: NavHostController) {
    Scaffold(
        topBar = { ComplaintsTopAppBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(getSampleComplaints()) { complaint ->
                ComplaintCard(complaint = complaint)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplaintsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.raised_complaints_title), fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            // Language Dropdown
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.Language, contentDescription = "Language")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Placeholder languages. These should come from string resources.
                DropdownMenuItem(text = { Text("Assamese") }, onClick = { /* TODO */ expanded = false })
                DropdownMenuItem(text = { Text("Manipuri") }, onClick = { /* TODO */ expanded = false })
                DropdownMenuItem(text = { Text("Khasi") }, onClick = { /* TODO */ expanded = false })
                DropdownMenuItem(text = { Text("Nagamese") }, onClick = { /* TODO */ expanded = false })
                DropdownMenuItem(text = { Text("Mizo") }, onClick = { /* TODO */ expanded = false })
                DropdownMenuItem(text = { Text("English") }, onClick = { /* TODO */ expanded = false })
                DropdownMenuItem(text = { Text("Hindi") }, onClick = { /* TODO */ expanded = false })
            }
        }
    )
}

@Composable
fun ComplaintCard(complaint: Complaint) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = complaint.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = complaint.contact, style = MaterialTheme.typography.bodyMedium)
                    Text(text = complaint.village, style = MaterialTheme.typography.bodySmall)
                }
                IconButton(onClick = { /* TODO: Mark as solved */ }) {
                    Icon(Icons.Filled.CheckCircle, contentDescription = "Mark as Solved")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = complaint.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = complaint.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Button(onClick = { /* TODO: Mark as Solved */ }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Text(stringResource(id = R.string.mark_as_solved_button))
            }
        }
    }
}

data class Complaint(
    val name: String,
    val contact: String,
    val village: String,
    val description: String,
    val imageResId: Int // Placeholder image resource ID
)

fun getSampleComplaints(): List<Complaint> {
    return listOf(
        Complaint(
            name = "Priya Sharma",
            contact = "+91 98765 43210",
            village = "Rampur",
            description = "No clean drinking water available in our locality for the past week. Many children are falling ill.",
            imageResId = R.drawable.ic_launcher_foreground // Dummy image
        ),
        Complaint(
            name = "Amit Singh",
            contact = "+91 87654 32109",
            village = "Sundergaon",
            description = "Garbage not collected for days, causing foul smell and unhygienic conditions near the market area.",
            imageResId = R.drawable.ic_launcher_foreground // Dummy image
        ),
        Complaint(
            name = "Anita Devi",
            contact = "+91 76543 21098",
            village = "Bhagalpur",
            description = "Drainage system is blocked, leading to waterlogging in front of my house. Mosquito menace is increasing.",
            imageResId = R.drawable.ic_launcher_foreground // Dummy image
        )
    )
}

@Preview(showBackground = true)
@Composable
fun RaisedComplaintsScreenPreview() {
    SwastifyTheme {
        RaisedComplaintsScreen(navController = rememberNavController())
    }
}
