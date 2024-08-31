import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = input,
            onValueChange = { input = it },
            textStyle = TextStyle(color = Color.Black, fontSize = 32.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                listOf("7", "8", "9", "/").forEach { symbol ->
                    CalculatorButton(symbol) { input += symbol }
                }
            }
            Row {
                listOf("4", "5", "6", "*").forEach { symbol ->
                    CalculatorButton(symbol) { input += symbol }
                }
            }
            Row {
                listOf("1", "2", "3", "-").forEach { symbol ->
                    CalculatorButton(symbol) { input += symbol }
                }
            }
            Row {
                listOf("0", ".", "+").forEach { symbol ->
                    CalculatorButton(symbol) { input += symbol }
                }
                CalculatorButton("=") {
                    result = try {
                        val expression = input.replace(Regex("[^0-9+\\-*/.]"), "")
                        val evalResult = evaluateExpression(expression)
                        evalResult.toString()
                    } catch (e: Exception) {
                        "Error"
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        BasicText(
            text = "Result: $result",
            style = TextStyle(fontSize = 32.sp)
        )
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .size(64.dp)
    ) {
        Text(text, fontSize = 24.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorApp()
}

fun evaluateExpression(expression: String): Double {
    return try {
        val tokens = expression.split("+", "-", "*", "/")
        if (tokens.size == 1) {
            expression.toDoubleOrNull() ?: 0.0
        } else {
            // Simplesmente para demonstração, um único operador é suportado
            // Para uma calculadora mais completa, considere usar uma biblioteca ou um parser
            val firstNum = tokens[0].toDouble()
            val secondNum = tokens[1].toDouble()
            when {
                expression.contains("+") -> firstNum + secondNum
                expression.contains("-") -> firstNum - secondNum
                expression.contains("*") -> firstNum * secondNum
                expression.contains("/") -> firstNum / secondNum
                else -> 0.0
            }
        }
    } catch (e: Exception) {
        0.0
    }
}
