package com.example.swastify

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swastify.ui.theme.SwastifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(navController: NavHostController) {
    Scaffold(
        topBar = { AlertsTopAppBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(getSampleAlerts()) { alert ->
                AlertCard(alert = alert)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.alerts_title), fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(Routes.SEND_MANUAL_ALERT) }) {
                Icon(Icons.Filled.AddAlert, contentDescription = stringResource(id = R.string.send_manual_alert_button))
            }
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
fun AlertCard(alert: Alert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = alert.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = alert.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = alert.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

data class Alert(val title: String, val description: String, val date: String)

fun getSampleAlerts(): List<Alert> {
    return listOf(
        Alert(
            title = "Vaccination Drive",
            description = "A village-wide vaccination drive will be conducted on October 26th at the community center. Please ensure all eligible members participate.",
            date = "2025-10-20"
        ),
        Alert(
            title = "Water Contamination Warning",
            description = "Recent tests indicate high levels of contamination in the well near the old temple. Please use boiled water for consumption until further notice.",
            date = "2025-10-18"
        ),
        Alert(
            title = "Malaria Awareness Camp",
            description = "A malaria awareness camp will be held on November 5th. Learn about prevention, symptoms, and treatment.",
            date = "2025-10-15"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AlertsScreenPreview() {
    SwastifyTheme {
        AlertsScreen(navController = rememberNavController())
    }
}
