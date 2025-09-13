package com.example.swastify

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

object Routes {
    const val HOME = "home"
    const val COMPLAIN = "complain"
    const val SIGN_IN = "signIn"
    const val ASHA_REGISTRATION = "ashaRegistration"
    const val ASHA_DASHBOARD = "ashaDashboard"
    const val RAISED_COMPLAINTS = "raisedComplaints"
    const val REGISTER_PATIENT_CASE = "registerPatientCase"
    const val WATER_QUALITY = "waterQuality"
    const val ALERTS = "alerts"
    const val SEND_MANUAL_ALERT = "sendManualAlert"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(Routes.COMPLAIN) {
            ComplainScreen(navController = navController)
        }
        composable(Routes.SIGN_IN) {
            SignInScreen(navController = navController)
        }
        composable(Routes.ASHA_REGISTRATION) {
            ASHAWorkerRegistrationScreen(navController = navController)
        }
        composable(Routes.ASHA_DASHBOARD) {
            ASHAWorkerDashboardScreen(navController = navController)
        }
        composable(Routes.RAISED_COMPLAINTS) {
            RaisedComplaintsScreen(navController = navController)
        }
        composable(Routes.REGISTER_PATIENT_CASE) {
            RegisterPatientCaseScreen(navController = navController)
        }
        composable(Routes.WATER_QUALITY) {
            WaterQualityScreen(navController = navController)
        }
        composable(Routes.ALERTS) {
            AlertsScreen(navController = navController)
        }
        composable(Routes.SEND_MANUAL_ALERT) {
            SendManualAlertScreen(navController = navController)
        }
    }
}
