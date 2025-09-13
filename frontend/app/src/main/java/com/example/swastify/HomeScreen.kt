package com.example.swastify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
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
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { HomeTopAppBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Sliding Carousel Placeholder
            Text(
                text = "Sliding Carousel Placeholder",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
            )

            // News and Educational Modules Section
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(getSampleEducationalModules()) { module ->
                    EducationModuleCard(module = module)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name), fontWeight = FontWeight.Bold) },
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

            // Complain Button
            IconButton(onClick = { navController.navigate(Routes.COMPLAIN) }) {
                Icon(Icons.Filled.Info, contentDescription = "Complain")
            }

            // Sign In Button
            IconButton(onClick = { navController.navigate(Routes.SIGN_IN) }) {
                Icon(Icons.Filled.Person, contentDescription = "Sign In")
            }
        }
    )
}

@Composable
fun EducationModuleCard(module: EducationalModule) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = module.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = module.title,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = module.description,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class EducationalModule(val title: String, val description: String, val imageResId: Int)

fun getSampleEducationalModules(): List<EducationalModule> {
    return listOf(
        EducationalModule(
            title = "Importance of Hand Hygiene",
            description = "Learn why washing your hands properly is crucial for preventing the spread of diseases.",
            imageResId = R.drawable.ic_launcher_foreground // Placeholder image
        ),
        EducationalModule(
            title = "Access to Clean Water",
            description = "Understand the impact of clean water on community health and how to ensure its availability.",
            imageResId = R.drawable.ic_launcher_foreground // Placeholder image
        ),
        EducationalModule(
            title = "Malaria Prevention Techniques",
            description = "Discover effective ways to prevent malaria in your community, including mosquito control and early diagnosis.",
            imageResId = R.drawable.ic_launcher_foreground // Placeholder image
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SwastifyTheme {
        HomeScreen(navController = rememberNavController())
    }
}
