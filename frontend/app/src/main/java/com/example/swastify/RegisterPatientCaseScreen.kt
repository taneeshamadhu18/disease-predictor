package com.example.swastify

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swastify.ui.theme.SwastifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPatientCaseScreen(navController: NavHostController) {
    var selectedSymptoms by remember { mutableStateOf(setOf<String>()) }
    var selectedAgeGroup by remember { mutableStateOf<String?>(null) }
    var selectedGender by remember { mutableStateOf<String?>(null) }
    var selectedOutcome by remember { mutableStateOf<String?>(null) }
    var selectedLocation by remember { mutableStateOf<String?>(null) }

    val symptoms = listOf("Diarrhoea", "Fever", "Vomiting", "Abdominal Pain", "Cough", "Headache")
    val ageGroups = listOf("0-5", "6-14", "15-59", "60+")
    val genders = listOf("Male", "Female", "Other")
    val outcomes = listOf("At Home", "Referred to PHC", "Hospitalized")
    val locations = listOf("Village A", "Village B", "Village C") // Dummy locations

    Scaffold(
        topBar = { RegisterPatientTopAppBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = stringResource(id = R.string.symptoms_label), style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(symptoms) { symptom ->
                    FilterChip(
                        selected = selectedSymptoms.contains(symptom),
                        onClick = {
                            selectedSymptoms = if (selectedSymptoms.contains(symptom)) {
                                selectedSymptoms - symptom
                            } else {
                                selectedSymptoms + symptom
                            }
                        },
                        label = { Text(symptom) },
                        leadingIcon = if (selectedSymptoms.contains(symptom)) {
                            { Icon(Icons.Filled.Check, contentDescription = "Selected", Modifier.size(FilterChipDefaults.IconSize)) }
                        } else {
                            null
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Age Group Dropdown
            Text(text = stringResource(id = R.string.age_group_label), style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
            ExposedDropdownMenuBox(
                expanded = selectedAgeGroup != null,
                onExpandedChange = { expanded -> if (expanded) selectedAgeGroup = ageGroups[0] else selectedAgeGroup = null },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedAgeGroup ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.select_age_group)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = selectedAgeGroup != null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = selectedAgeGroup != null,
                    onDismissRequest = { selectedAgeGroup = null }
                ) {
                    ageGroups.forEach { ageGroup ->
                        DropdownMenuItem(onClick = {
                            selectedAgeGroup = ageGroup
                            selectedAgeGroup = null // Close dropdown
                        }, text = { Text(ageGroup) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Gender Radio Buttons
            Text(text = stringResource(id = R.string.gender_label), style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
            Row(Modifier.fillMaxWidth().selectableGroup(), horizontalArrangement = Arrangement.SpaceAround) {
                genders.forEach { gender ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (gender == selectedGender),
                                onClick = { selectedGender = gender },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (gender == selectedGender),
                            onClick = null // null recommended for accessibility with selectableGroup
                        )
                        Text(
                            text = gender,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Outcome Dropdown
            Text(text = stringResource(id = R.string.outcome_label), style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
            ExposedDropdownMenuBox(
                expanded = selectedOutcome != null,
                onExpandedChange = { expanded -> if (expanded) selectedOutcome = outcomes[0] else selectedOutcome = null },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedOutcome ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.select_outcome)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = selectedOutcome != null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = selectedOutcome != null,
                    onDismissRequest = { selectedOutcome = null }
                ) {
                    outcomes.forEach { outcome ->
                        DropdownMenuItem(onClick = {
                            selectedOutcome = outcome
                            selectedOutcome = null // Close dropdown
                        }, text = { Text(outcome) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Location Dropdown
            Text(text = stringResource(id = R.string.location_label), style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
            ExposedDropdownMenuBox(
                expanded = selectedLocation != null,
                onExpandedChange = { expanded -> if (expanded) selectedLocation = locations[0] else selectedLocation = null },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedLocation ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.select_location)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = selectedLocation != null) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = selectedLocation != null,
                    onDismissRequest = { selectedLocation = null }
                ) {
                    locations.forEach { location ->
                        DropdownMenuItem(onClick = {
                            selectedLocation = location
                            selectedLocation = null // Close dropdown
                        }, text = { Text(location) })
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { /* TODO: Implement save logic */ navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.save_button))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPatientTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.register_patient_case_title), fontWeight = FontWeight.Bold) },
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
fun RegisterPatientCaseScreenPreview() {
    SwastifyTheme {
        RegisterPatientCaseScreen(navController = rememberNavController())
    }
}
