package com.example.swastify

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.FilterDrama
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SettingsInputComponent
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swastify.ui.theme.SwastifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterQualityScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(stringResource(id = R.string.manual_entry_tab), stringResource(id = R.string.iot_sensor_connect_tab))

    Scaffold(
        topBar = { WaterQualityTopAppBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                0 -> ManualEntryTab()
                1 -> IoTSensorConnectTab()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterQualityTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.water_quality_title), fontWeight = FontWeight.Bold) },
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
fun ManualEntryTab() {
    var ph by remember { mutableStateOf("") }
    var turbidity by remember { mutableStateOf("") }
    var dissolvedOxygen by remember { mutableStateOf("") }
    var chlorine by remember { mutableStateOf("") }
    var hardness by remember { mutableStateOf("") }
    var bacterialPresence by remember { mutableStateOf("") }
    var tds by remember { mutableStateOf("") }
    var nitrates by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        WaterParameterInput(label = stringResource(id = R.string.ph_label), value = ph, onValueChange = { ph = it }, icon = Icons.Filled.Colorize, keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        WaterParameterInput(label = stringResource(id = R.string.turbidity_label), value = turbidity, onValueChange = { turbidity = it }, icon = Icons.Filled.FilterDrama, keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        WaterParameterInput(label = stringResource(id = R.string.dissolved_oxygen_label), value = dissolvedOxygen, onValueChange = { dissolvedOxygen = it }, icon = Icons.Filled.AcUnit, keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        WaterParameterInput(label = stringResource(id = R.string.chlorine_label), value = chlorine, onValueChange = { chlorine = it }, icon = Icons.Filled.Science, keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        WaterParameterInput(label = stringResource(id = R.string.hardness_label), value = hardness, onValueChange = { hardness = it }, icon = Icons.Filled.Compress, keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        WaterParameterInput(label = stringResource(id = R.string.bacterial_presence_label), value = bacterialPresence, onValueChange = { bacterialPresence = it }, icon = Icons.Filled.BrokenImage)
        WaterParameterInput(label = stringResource(id = R.string.tds_label), value = tds, onValueChange = { tds = it }, icon = Icons.Filled.Opacity, keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        WaterParameterInput(label = stringResource(id = R.string.nitrates_label), value = nitrates, onValueChange = { nitrates = it }, icon = Icons.Filled.Thermostat, keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { /* TODO: Submit manual entry */ }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.submit_button))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IoTSensorConnectTab() {
    val sensors = listOf("Sensor A", "Sensor B", "Sensor C") // Dummy sensors
    var selectedSensor by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = selectedSensor != null,
            onExpandedChange = { expanded -> if (expanded) selectedSensor = sensors[0] else selectedSensor = null },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedSensor ?: "",
                onValueChange = { },
                readOnly = true,
                label = { Text(stringResource(id = R.string.select_iot_sensor)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = selectedSensor != null) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = selectedSensor != null,
                onDismissRequest = { selectedSensor = null }
            ) {
                sensors.forEach { sensor ->
                    DropdownMenuItem(onClick = {
                        selectedSensor = sensor
                        selectedSensor = null // Close dropdown
                    }, text = { Text(sensor) })
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { /* TODO: Connect to IoT sensor */ }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.connect_button))
        }
    }
}

@Composable
fun WaterParameterInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    keyboardType: androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun WaterQualityScreenPreview() {
    SwastifyTheme {
        WaterQualityScreen(navController = rememberNavController())
    }
}
