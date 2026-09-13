package com.example.algorithmic_pasticceria.presentation.levels

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BakeryCustomer(val id: Int, val ticketNumber: Int, val emoji: String, val name: String)

@Composable
fun QueueSortingScreen(onBack: () -> Unit) {
    var customers by remember {
        mutableStateOf(
            listOf(
                BakeryCustomer(1, 42, "\uD83D\uDC08", "Mario the Cat"),
                BakeryCustomer(2, 15, "🐶", "Bruno the Dog"),
                BakeryCustomer(3, 88, "🦊", "Luigi the Fox"),
                BakeryCustomer(4, 23, "🐻", "Pippo the Bear")
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFF9F0)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack) { Text("← Torna alla Mappa") }
            Text("Line Sorting (Customer Tickets)", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.width(64.dp))
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Visual Queue
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            customers.forEach { customer ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.size(110.dp, 140.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(customer.emoji, fontSize = 36.sp)
                        Text("#${customer.ticketNumber}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 18.sp)
                        Text(customer.name, fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Single-step Swap Button to test mechanics
        Button(
            onClick = {
                // Example swap test: swap first two customers if out of order
                if (customers.size >= 2 && customers[0].ticketNumber > customers[1].ticketNumber) {
                    customers = listOf(customers[1], customers[0], customers[2], customers[3])
                }
            }
        ) {
            Text("Step: Compare & Swap First Two")
        }
    }
}