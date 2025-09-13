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
fun SignInScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(stringResource(id = R.string.asha_worker_tab), stringResource(id = R.string.health_authority_tab))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.sign_in_page_title)) },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTabIndex) {
                0 -> ASHAWorkerLoginForm(navController = navController)
                1 -> HealthAuthorityLoginForm()
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            Button(onClick = { navController.navigate(Routes.ASHA_REGISTRATION) }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.register_button))
            }
        }
    }
}

@Composable
fun ASHAWorkerLoginForm(navController: NavHostController) {
    var ashaId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = ashaId,
            onValueChange = { ashaId = it },
            label = { Text(stringResource(id = R.string.ash_id_hint)) },
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
        Button(onClick = { navController.navigate(Routes.ASHA_DASHBOARD) }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.login_button))
        }
    }
}

@Composable
fun HealthAuthorityLoginForm() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email_hint)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
        Button(onClick = { /* TODO: Implement Health Authority login */ }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.login_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SwastifyTheme {
        SignInScreen(navController = rememberNavController())
    }
}
