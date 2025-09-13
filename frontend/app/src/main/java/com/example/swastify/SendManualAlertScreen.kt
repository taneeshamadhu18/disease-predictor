package com.example.swastify

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun SendManualAlertScreen(navController: NavHostController) {
    var title by remember { mutableStateOf("\"") }
    var description by remember { mutableStateOf("\"") }

    Scaffold(
        topBar = { SendManualAlertTopAppBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(id = R.string.alert_title_hint)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(id = R.string.alert_description_hint)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { /* TODO: Submit alert */ navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.submit_button))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendManualAlertTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.send_manual_alert_title), fontWeight = FontWeight.Bold) },
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

@Preview(showBackground = true)
@Composable
fun SendManualAlertScreenPreview() {
    SwastifyTheme {
        SendManualAlertScreen(navController = rememberNavController())
    }
}
