package com.example.composetipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetipcalculator.ui.theme.ComposeTipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTipCalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipCalculatorApp()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorApp() {

    var billInput by remember { mutableStateOf("") }
    val amount = billInput.toDoubleOrNull() ?: 0.0

    var tipInput by remember { mutableStateOf("") }
    val tipPercentage = tipInput.toDoubleOrNull() ?: 0.0
    val tipAmount = calculateTip(amount, tipPercentage)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Calculate tip"
        )
        EditNumberField(
            value = billInput,
            onValueChange = { billInput = it },
            label = "Bill amount",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        EditNumberField(
            value = tipInput,
            onValueChange = { tipInput = it },
            label = "Tip percentage",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = tipAmount,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(vertical = 8.dp)

        )
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorAppPreview() {
    ComposeTipCalculatorTheme {
        TipCalculatorApp()
    }
}

fun calculateTip(
    amount: Double,
    tipPercentage: Double,
): String {
    val tip = amount * (tipPercentage / 100)
    return NumberFormat.getCurrencyInstance().format(tip)
}