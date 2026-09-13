package com.example.algorithmic_pasticceria.presentation.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algorithmic_pasticceria.presentation.navigation.Screen

data class AlgorithmMenuItem(
    val id: String,
    val title: String,
    val italianSubtitle: String,
    val complexity: String,
    val isAvailable: Boolean = true
)

@Composable
fun SortingChapterMenuScreen(
    onSelectAlgorithm: (String) -> Unit,
    onBack: () -> Unit
) {
    val algorithms = listOf(
        AlgorithmMenuItem("bubble", "Bubble Sort", "Scambio a Bolle tra Vicini", "O(n²)"),
        AlgorithmMenuItem("insertion", "Insertion Sort", "Inserimento al Posto Giusto", "O(n²)", isAvailable = false),
        AlgorithmMenuItem("quicksort", "Quick Sort", "La Scelta del Pivot Capotavola", "O(n log n)", isAvailable = false),
        AlgorithmMenuItem("mergesort", "Merge Sort", "Dividi la Coda a Metà", "O(n log n)", isAvailable = false)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF4E8))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 560.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF9)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onBack) {
                        Text("← Mappa", color = Color(0xFF8B4513), fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "CAPITOLO 1",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC08552),
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text("Menù degli Algoritmi", fontSize = 26.sp, fontWeight = FontWeight.Black, color = Color(0xFF3E2723))
                Text("Coda alla Cassa: Tecniche di Ordinamento", fontSize = 13.sp, color = Color(0xFF795548))
                Spacer(modifier = Modifier.height(24.dp))

                // Algorithm List
                algorithms.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable(enabled = item.isAvailable) { onSelectAlgorithm(item.id) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.isAvailable) Color(0xFFF7EDE2) else Color(0xFFEFEFEF)
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = if (item.isAvailable) Color(0xFF2B1704) else Color.Gray
                                )
                                Text(
                                    text = item.italianSubtitle,
                                    fontSize = 12.sp,
                                    color = if (item.isAvailable) Color(0xFF7A5C43) else Color.LightGray
                                )
                            }
                            Text(
                                text = if (item.isAvailable) item.complexity else "In Forno ⏳",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (item.isAvailable) Color(0xFFD97706) else Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}