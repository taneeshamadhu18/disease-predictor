package com.example.swastify

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swastify.ui.theme.SwastifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ASHAWorkerRegistrationScreen(navController: NavHostController) {
    var ashaId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.asha_registration_page_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
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
                value = ashaId,
                onValueChange = { ashaId = it },
                label = { Text(stringResource(id = R.string.ash_id_hint)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(id = R.string.name_hint)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text(stringResource(id = R.string.contact_number_hint)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = village,
                onValueChange = { village = it },
                label = { Text(stringResource(id = R.string.village_hint)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password_hint)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                // TODO: Implement registration logic
                navController.popBackStack() // For now, just go back
            }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.register_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ASHAWorkerRegistrationScreenPreview() {
    SwastifyTheme {
        ASHAWorkerRegistrationScreen(navController = rememberNavController())
    }
}
