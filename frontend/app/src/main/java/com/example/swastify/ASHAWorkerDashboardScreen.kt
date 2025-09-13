package com.example.swastify

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.WaterDrop
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
fun ASHAWorkerDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = { ASHADashboardTopAppBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.asha_name_placeholder, "Rekha Devi"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = stringResource(id = R.string.ash_id_placeholder, "ASH-12345"), style = MaterialTheme.typography.bodyMedium)
                    Text(text = stringResource(id = R.string.asha_village_placeholder, "Haripur"), style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DashboardButton(icon = Icons.Filled.Info, text = stringResource(id = R.string.raised_complaints_button)) { navController.navigate(Routes.RAISED_COMPLAINTS) }
                DashboardButton(icon = Icons.Filled.MedicalServices, text = stringResource(id = R.string.register_patient_case_button)) { navController.navigate(Routes.REGISTER_PATIENT_CASE) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DashboardButton(icon = Icons.Filled.WaterDrop, text = stringResource(id = R.string.water_quality_test_button)) { navController.navigate(Routes.WATER_QUALITY) }
                DashboardButton(icon = Icons.Filled.Notifications, text = stringResource(id = R.string.alerts_button)) { navController.navigate(Routes.ALERTS) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ASHADashboardTopAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.asha_dashboard_title), fontWeight = FontWeight.Bold) },
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
fun DashboardButton(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .width(160.dp)
            .height(120.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = text, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ASHAWorkerDashboardScreenPreview() {
    SwastifyTheme {
        ASHAWorkerDashboardScreen(navController = rememberNavController())
    }
}
