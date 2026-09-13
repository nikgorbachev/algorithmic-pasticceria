package com.example.algorithmic_pasticceria.presentation.levels

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algorithmic_pasticceria.presentation.components.AnimalAvatar
import com.example.algorithmic_pasticceria.presentation.components.AnimalType

data class LessonCustomer(
    val id: Int,
    val ticket: Int,
    val type: AnimalType,
    val name: String,
    val rankBadge: String? = null
)

enum class CodeLanguage(val label: String) {
    PYTHON("Python"),
    KOTLIN("Kotlin"),
    JAVASCRIPT("JavaScript"),
    PSEUDO("Pseudocode")
}

private val STEP_CODE_LINES = mapOf(
    CodeLanguage.PSEUDO to listOf(
        "1: for i in 0 until n - 1:",
        "2:     for j in 0 until n - i - 1:",
        "3:         if queue[j] > queue[j + 1]:",
        "4:             swap(queue[j], queue[j + 1])",
        "5: // customer locked in position"
    ),
    CodeLanguage.PYTHON to listOf(
        "1: for i in range(n - 1):",
        "2:     for j in range(n - i - 1):",
        "3:         if queue[j] > queue[j + 1]:",
        "4:             queue[j], queue[j + 1] = queue[j + 1], queue[j]",
        "5: # customer locked in position"
    ),
    CodeLanguage.KOTLIN to listOf(
        "1: for (i in 0 until n - 1) {",
        "2:     for (j in 0 until n - i - 1) {",
        "3:         if (queue[j] > queue[j + 1]) {",
        "4:             queue.swap(j, j + 1)",
        "5: // customer locked in position"
    ),
    CodeLanguage.JAVASCRIPT to listOf(
        "1: for (let i = 0; i < n - 1; i++) {",
        "2:     for (let j = 0; j < n - i - 1; j++) {",
        "3:         if (queue[j] > queue[j + 1]) {",
        "4:             [queue[j], queue[j + 1]] = [queue[j + 1], queue[j]];",
        "5: // customer locked in position"
    )
)

private val FULL_RUNNABLE_CODE = mapOf(
    CodeLanguage.PYTHON to """
# --- Bubble Sort: Algorithmic Pasticceria ---
def bubble_sort(queue):
    arr = list(queue)
    n = len(arr)
    for i in range(n - 1):
        for j in range(n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
    return arr

# Mario (42), Bruno (15), Pippo (88), Luigi (23)
customer_tickets = [42, 15, 88, 23]
customers_sorted = bubble_sort(customer_tickets)

print("Original queue:", customer_tickets)
print("Sorted queue:  ", customers_sorted)
""".trimIndent(),

    CodeLanguage.KOTLIN to """
// --- Bubble Sort: Algorithmic Pasticceria ---
fun bubbleSort(queue: List<Int>): List<Int> {
    val arr = queue.toMutableList()
    val n = arr.size
    for (i in 0 until n - 1) {
        for (j in 0 until n - i - 1) {
            if (arr[j] > arr[j + 1]) {
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
            }
        }
    }
    return arr
}

fun main() {
    val customerTickets = listOf(42, 15, 88, 23)
    val customersSorted = bubbleSort(customerTickets)

    println("Original queue: " + customerTickets)
    println("Sorted queue:   " + customersSorted)
}
""".trimIndent(),

    CodeLanguage.JAVASCRIPT to """
// --- Bubble Sort: Algorithmic Pasticceria ---
function bubbleSort(queue) {
    const arr = [...queue];
    const n = arr.length;
    for (let i = 0; i < n - 1; i++) {
        for (let j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
            }
        }
    }
    return arr;
}

const customerTickets = [42, 15, 88, 23];
const customersSorted = bubbleSort(customerTickets);

console.log("Original queue:", customerTickets);
console.log("Sorted queue:  ", customersSorted);
""".trimIndent(),

    CodeLanguage.PSEUDO to """
algorithm BubbleSort(queue):
    input: list of ticket numbers
    output: sorted list in ascending order

    n = length(queue)
    for i from 0 to n - 2:
        for j from 0 to n - i - 2:
            if queue[j] > queue[j + 1]:
                swap(queue[j], queue[j + 1])
    return queue
""".trimIndent()
)

sealed interface FullLessonStep {
    val activeLine: Int
    val message: String
    val queueSnapshot: List<LessonCustomer>
    val comparingIndices: Pair<Int, Int>?

    data class CheckOuter(
        val i: Int,
        override val queueSnapshot: List<LessonCustomer>,
        override val message: String
    ) : FullLessonStep {
        override val activeLine = 1
        override val comparingIndices = null
    }

    data class Compare(
        val first: Int,
        val second: Int,
        override val queueSnapshot: List<LessonCustomer>,
        override val message: String
    ) : FullLessonStep {
        override val activeLine = 3
        override val comparingIndices = first to second
    }

    data class Swap(
        val first: Int,
        val second: Int,
        override val queueSnapshot: List<LessonCustomer>,
        override val message: String
    ) : FullLessonStep {
        override val activeLine = 4
        override val comparingIndices = first to second
    }

    data class Locked(
        val index: Int,
        override val queueSnapshot: List<LessonCustomer>,
        override val message: String
    ) : FullLessonStep {
        override val activeLine = 5
        override val comparingIndices = null
    }
}

private data class ToyState(
    val queue: List<LessonCustomer>,
    val firstIdx: Int?,
    val secondIdx: Int?,
    val message: String
)

@Composable
fun SortingNotebookScreen(algorithmId: String, onBack: () -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    var copyNotice by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(CodeLanguage.PYTHON) }

    val baseCustomers = remember {
        listOf(
            LessonCustomer(1, 42, AnimalType.CAT, "Mario"),
            LessonCustomer(2, 15, AnimalType.DOG, "Bruno"),
            LessonCustomer(3, 88, AnimalType.BEAR, "Pippo"),
            LessonCustomer(4, 23, AnimalType.FOX, "Luigi")
        )
    }

    // --- State 1: Discovery Toy (Single Pass + Max 1 & Max 2) ---
    var toyStepIndex by remember { mutableStateOf(0) }
    val toySteps = remember(baseCustomers) {
        listOf(
            ToyState(
                queue = baseCustomers,
                firstIdx = null,
                secondIdx = null,
                message = "Press 'Next Step' to inspect the line pair by pair."
            ),
            ToyState(
                queue = baseCustomers,
                firstIdx = 0,
                secondIdx = 1,
                message = "Pair 1: Mario (#42) vs Bruno (#15). Since 42 > 15, we push 42 to the right!"
            ),
            ToyState(
                queue = listOf(baseCustomers[1], baseCustomers[0], baseCustomers[2], baseCustomers[3]),
                firstIdx = 0,
                secondIdx = 1,
                message = "Swapped! The larger ticket (#42) has moved forward to the right."
            ),
            ToyState(
                queue = listOf(baseCustomers[1], baseCustomers[0], baseCustomers[2], baseCustomers[3]),
                firstIdx = 1,
                secondIdx = 2,
                message = "Pair 2: Mario (#42) vs Pippo (#88). 42 is smaller than 88, so no swap. 88 is now our traveling maximum!"
            ),
            ToyState(
                queue = listOf(baseCustomers[1], baseCustomers[0], baseCustomers[2], baseCustomers[3]),
                firstIdx = 2,
                secondIdx = 3,
                message = "Pair 3: Pippo (#88) vs Luigi (#23). 88 > 23, so we push 88 to the rightmost spot!"
            ),
            ToyState(
                queue = listOf(
                    baseCustomers[1],
                    baseCustomers[0],
                    baseCustomers[3],
                    baseCustomers[2].copy(rankBadge = "Max 1")
                ),
                firstIdx = null,
                secondIdx = null,
                message = "See? The maximum element (#88) did become the rightmost one! It is guaranteed to be Max 1."
            ),
            ToyState(
                queue = listOf(
                    baseCustomers[1],
                    baseCustomers[3],
                    baseCustomers[0].copy(rankBadge = "Max 2"),
                    baseCustomers[2].copy(rankBadge = "Max 1")
                ),
                firstIdx = null,
                secondIdx = null,
                message = "Repeating the exact same pass locks the 2nd largest (#42) right beside it as Max 2!"
            )
        )
    }
    val currentToyState = toySteps[toyStepIndex]

    // --- State 2: Full Algorithm Stepper (Synchronized with Code) ---
    val fullSteps = remember(baseCustomers) {
        val list = baseCustomers.toMutableList()
        val result = mutableListOf<FullLessonStep>()
        val n = list.size

        for (i in 0 until n - 1) {
            result.add(
                FullLessonStep.CheckOuter(
                    i = i,
                    queueSnapshot = list.toList(),
                    message = "Pass ${i + 1} of ${n - 1}: Bubbling the next largest customer into position."
                )
            )
            for (j in 0 until n - i - 1) {
                result.add(
                    FullLessonStep.Compare(
                        first = j,
                        second = j + 1,
                        queueSnapshot = list.toList(),
                        message = "Comparing queue[$j] (#${list[j].ticket}) with queue[${j + 1}] (#${list[j + 1].ticket})."
                    )
                )
                if (list[j].ticket > list[j + 1].ticket) {
                    val tmp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = tmp
                    result.add(
                        FullLessonStep.Swap(
                            first = j,
                            second = j + 1,
                            queueSnapshot = list.toList(),
                            message = "Swapping #${list[j].ticket} and #${list[j + 1].ticket}."
                        )
                    )
                }
            }
            val lockedIdx = n - 1 - i
            val lockedCustomer = list[lockedIdx].copy(rankBadge = "Max ${i + 1}")
            list[lockedIdx] = lockedCustomer
            result.add(
                FullLessonStep.Locked(
                    index = lockedIdx,
                    queueSnapshot = list.toList(),
                    message = "Customer #${lockedCustomer.ticket} locked into final place."
                )
            )
        }
        result
    }
    var fullStepIndex by remember { mutableStateOf(0) }
    val currentFullStep = fullSteps.getOrNull(fullStepIndex)
    val fullDisplayQueue = currentFullStep?.queueSnapshot ?: baseCustomers

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFDF9))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp, vertical = 24.dp)
    ) {
        // --- 1. Top Navigation Bar ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E3C1B))
            ) {
                Text("← Menu")
            }
            Text(
                text = "Chapter 1: Bubble Sort",
                fontWeight = FontWeight.Black,
                fontSize = 22.sp,
                color = Color(0xFF2B1704)
            )
            Spacer(modifier = Modifier.width(72.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- UPFRONT: The Real-World Problem Statement ---
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7EDE2)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "🎯 The Problem",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF8B4513)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "We have a list of N customers waiting in line with ticket numbers in completely random, unordered positions. Our goal is to rearrange them from smallest to largest in increasing order.",
                    fontSize = 14.sp,
                    color = Color(0xFF3E2723),
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- PART 1: The Bubble Mechanism (A Simpler Subproblem) ---
        Text(
            text = "Part 1: The Bubble Mechanism",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Before trying to sort the entire list all at once, let's solve a simpler problem first: can we at least guarantee finding the single largest number and parking it at the very end?\n\nThis is what the bubble mechanism does: we compare two adjacent elements, and push the greater one to the right. If we repeat this for every neighbor across the line, the absolute maximum is guaranteed to end up at the rightmost spot. Do you see why that is? Let's step through the 1st pass:",
            fontSize = 14.sp,
            color = Color(0xFF3E2723),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EBD9)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = currentToyState.message,
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3E2723),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Row 1 Customer Cards (The 1-Pass Toy)
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            currentToyState.queue.forEachIndexed { idx, customer ->
                val isComparing = currentToyState.firstIdx == idx || currentToyState.secondIdx == idx
                val borderColor by animateColorAsState(
                    if (isComparing) Color(0xFFE76F51) else if (customer.rankBadge != null) Color(0xFF2A9D8F) else Color(0xFFE8D5B7)
                )

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (customer.rankBadge != null) Color(0xFFE8F5E9) else Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isComparing) 8.dp else 2.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                    ),
                    modifier = Modifier.width(106.dp).height(132.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AnimalAvatar(type = customer.type)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "#${customer.ticket}",
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = Color(0xFF2B1704)
                        )
                        Text(customer.name, fontSize = 11.sp, color = Color.Gray)

                        if (customer.rankBadge != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(color = Color(0xFF2A9D8F), shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = customer.rankBadge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (toyStepIndex >= 5) {
            Text(
                text = "✨ See? The max one did become the rightmost one!",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A9D8F)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Stepper Controls for Row 1
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = { if (toyStepIndex > 0) toyStepIndex-- },
                enabled = toyStepIndex > 0,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7A5C43))
            ) {
                Text("Previous")
            }
            Button(
                onClick = { if (toyStepIndex < toySteps.size - 1) toyStepIndex++ },
                enabled = toyStepIndex < toySteps.size - 1,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE76F51))
            ) {
                Text(if (toyStepIndex == toySteps.size - 2) "See Pass 2 (Max 2)" else "Next Step")
            }
            OutlinedButton(onClick = { toyStepIndex = 0 }) {
                Text("Reset Pass")
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // --- BUBBLE SORT: Connecting the Mechanism to the Full Solution ---
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7EDE2)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "💡 How This Solves the Whole Problem",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF8B4513)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "1. Remember our goal: we want to sort the entire line from min to max, not just park one number at the end.",
                    fontSize = 13.sp,
                    color = Color(0xFF3E2723),
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "2. Strategy: first put the absolute maximum to the far right. Then put the 2nd highest right beside it, then the 3rd highest next to those, and so on.",
                    fontSize = 13.sp,
                    color = Color(0xFF3E2723),
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "3. As we just verified: whenever we swap adjacent pairs across the array, we are mathematically guaranteed to land the largest element at the right end.",
                    fontSize = 13.sp,
                    color = Color(0xFF3E2723),
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "4. So if we simply repeat this bubble mechanism N times, we're done! The k-th maximum automatically lands in the k-th rightmost spot. Let's see the entire sort in action:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF9C4221),
                    lineHeight = 19.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // --- PART 2: Full Algorithm Playback ---
        Text(
            text = "Part 2: Full Algorithm Synchronized Playback",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Step through the complete algorithm below and watch each pass lock the next maximum into place while matching each line of code.",
            fontSize = 13.sp,
            color = Color(0xFF7A5C43)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EBD9)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = currentFullStep?.message ?: "Press 'Next Step' to begin sorting.",
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3E2723),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Row 2 Customer Cards (Full Algorithm)
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            fullDisplayQueue.forEachIndexed { idx, customer ->
                val isComparing = currentFullStep?.comparingIndices?.let { it.first == idx || it.second == idx } ?: false
                val borderColor by animateColorAsState(
                    if (isComparing) Color(0xFFE76F51) else if (customer.rankBadge != null) Color(0xFF2A9D8F) else Color(0xFFE8D5B7)
                )

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (customer.rankBadge != null) Color(0xFFE8F5E9) else Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isComparing) 10.dp else 2.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                    ),
                    modifier = Modifier.width(106.dp).height(132.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AnimalAvatar(type = customer.type)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "#${customer.ticket}",
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = Color(0xFF2B1704)
                        )
                        Text(customer.name, fontSize = 11.sp, color = Color.Gray)

                        if (customer.rankBadge != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(color = Color(0xFF2A9D8F), shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = customer.rankBadge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Synchronized Code Block Header & Language Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Synchronized Line Stepper:", fontWeight = FontWeight.Bold, color = Color(0xFF7A5C43))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEFE6DD))
                    .padding(2.dp)
            ) {
                CodeLanguage.entries.forEach { lang ->
                    val isSelected = selectedLanguage == lang
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) Color(0xFF5E3C1B) else Color.Transparent)
                            .clickable {
                                selectedLanguage = lang
                                copyNotice = false
                            }
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = lang.label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else Color(0xFF5E3C1B)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Synchronized Code Stepper Window
        val activeLines = STEP_CODE_LINES[selectedLanguage] ?: STEP_CODE_LINES.getValue(CodeLanguage.PYTHON)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF231F1C), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            activeLines.forEachIndexed { lineIdx, lineText ->
                val lineNumber = lineIdx + 1
                val isCurrent = currentFullStep?.activeLine == lineNumber
                val bgLine by animateColorAsState(
                    if (isCurrent) Color(0xFFE76F51).copy(alpha = 0.35f) else Color.Transparent
                )
                val textLineColor by animateColorAsState(
                    if (isCurrent) Color(0xFFFFD166) else Color(0xFFB8ADA1)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(bgLine, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = lineText,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        color = textLineColor,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Stepper Controls for Row 2
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { if (fullStepIndex > 0) fullStepIndex-- },
                enabled = fullStepIndex > 0,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7A5C43))
            ) {
                Text("Previous Step")
            }

            Button(
                onClick = { if (fullStepIndex < fullSteps.size - 1) fullStepIndex++ },
                enabled = fullStepIndex < fullSteps.size - 1,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE76F51))
            ) {
                Text("Next Step")
            }

            OutlinedButton(onClick = { fullStepIndex = 0 }) {
                Text("Restart Full Sort")
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // --- Complete Runnable Implementation & Copy Section ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Complete Runnable Implementation", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF7A5C43))
                Text("Copy & paste into your local editor or terminal", fontSize = 12.sp, color = Color.Gray)
            }

            Button(
                onClick = {
                    val codeToCopy = FULL_RUNNABLE_CODE[selectedLanguage] ?: ""
                    clipboardManager.setText(AnnotatedString(codeToCopy))
                    copyNotice = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A9D8F)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (copyNotice) "✓ Copied!" else "📋 Copy Code")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF231F1C)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = FULL_RUNNABLE_CODE[selectedLanguage] ?: "",
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp,
                color = Color(0xFFFFD166),
                lineHeight = 19.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}