package com.example.cycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cycle.ui.theme.CycleTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CycleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

//fun calculateFor(n: Int, a: Double): String {
//    var sum = 0.0
//    var denominator = 1.0
//    for (k in 0..n) {
//        denominator *= (a + k)
//        sum += 1.0 / denominator
//    }
//    return "S = $sum"
//}

fun calculateWhile(n: Int, a: Double): String {
    var sum = 0.0
    var denominator = 1.0
    var k = 0
    while (k <= n) {
        denominator *= (a + k)
        sum += 1.0 / denominator
        k++
    }
    return "S = ${"%.4f".format(sum)}"
}

//fun calculateDoWhile(n: Int, a: Double): String {
//    var sum = 0.0
//    var denominator = 1.0
//    var k = 0
//    do {
//        denominator *= (a + k)
//        sum += 1.0 / denominator
//        k++
//    } while (k <= n)
//    return "S = $sum"
//}

@Composable
fun DemoText(message: String, fontSize: TextUnit) {
    Text(
        text = message,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var n by remember { mutableStateOf("") }
    var a by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        DemoText(
            message = "Вычисление суммы ряда:",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "S = 1/a + 1/(a(a+1)) + ... + 1/(a(a+1)...(a+n))",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = n,
            onValueChange = { n = it },
            label = { Text("Введите n (натуральное число)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // <-- добавлено
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF27A6F5),
                unfocusedBorderColor = Color(0xFFF268DC)
            )
        )

        OutlinedTextField(
            value = a,
            onValueChange = { a = it },
            label = { Text("Введите a (действительное число)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), // <-- добавлено
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF27A6F5),
                unfocusedBorderColor = Color(0xFFF268DC)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = {
                    if (n.isEmpty() || a.isEmpty()) {
                        result = "Заполните оба поля!"
                    } else {
                        val nValue = n.toIntOrNull()
                        val aValue = a.replace(",", ".").toDoubleOrNull()

                        if (nValue == null || nValue < 0) {
                            result = "Ошибка: n должно быть натуральным числом!"
                        } else if (aValue == null) {
                            result = "Ошибка: введите корректное число для a!"
                        } else if (aValue == 0.0) {
                            result = "Ошибка: a не может быть равно 0 (деление на ноль запрещено)!"
                        } else {

//                            val resFor = calculateFor(nValue, aValue)
                            val resWhile = calculateWhile(nValue, aValue)
//                            val resDoWhile = calculateDoWhile(nValue, aValue)
//                            result = "$resFor\n$resWhile\n$resDoWhile"
                            result = "$resWhile"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF27A6F5),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f) // Занимает половину ширины
            ) {
                Text("Вычислить", fontSize = 16.sp)
            }


            Button(
                onClick = {
                    n = ""
                    a = ""
                    result = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF268DC),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Очистить", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (result.isNotEmpty()) {
            Text(
                text = result,
                modifier = Modifier
                    .background(
                        color = Color(0xFFF0F8FF),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF27A6F5),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DemoScreenPreview() {
    CycleTheme {
        DemoScreen()
    }
}