package com.example.composetipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetipcalculator.ui.theme.ComposeTipCalculatorTheme
import java.text.NumberFormat
import kotlin.math.ceil

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

    var roundUp by remember { mutableStateOf(false) }

    var tipInput by remember { mutableStateOf("") }
    val tipPercentage = tipInput.toDoubleOrNull() ?: 0.0
    val tipAmount = calculateTip(amount, tipPercentage, roundUp)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip)
        )
        EditNumberField(
            value = billInput,
            onValueChange = { billInput = it },
            label = R.string.bill_amount,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        EditNumberField(
            value = tipInput,
            onValueChange = { tipInput = it },
            label = R.string.tip_percentage,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = stringResource(R.string.tip_amount, tipAmount),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(vertical = 8.dp)

        )
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }
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
    roundUp: Boolean = false,
): String {
    val tip = amount * (tipPercentage / 100)
    if (roundUp) {
        return NumberFormat.getCurrencyInstance().format(ceil(tip))
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}