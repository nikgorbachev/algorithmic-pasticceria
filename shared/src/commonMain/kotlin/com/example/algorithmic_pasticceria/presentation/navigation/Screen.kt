package com.example.algorithmic_pasticceria.presentation.navigation

sealed interface Screen {
    data object BakeryFloor : Screen
    data object CustomerQueue : Screen
    data object CashRegister : Screen
    data object PastryShowcase : Screen
}