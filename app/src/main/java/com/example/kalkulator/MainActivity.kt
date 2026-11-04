package com.example.kalkulator

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalkulator.ui.theme.KalkulatorTheme

enum class ButtonCategory {
    NUMBER,
    OPERATOR,
    ACTION,
    EQUALS,
    SCIENTIFIC
}
data class CalcButton(
    val symbol: String,
    val category: ButtonCategory = ButtonCategory.NUMBER
)

class MainActivity : ComponentActivity() {
    val scientificButtonRows = listOf(
        listOf(
            CalcButton("sin(", ButtonCategory.SCIENTIFIC),
            CalcButton("cos(", ButtonCategory.SCIENTIFIC),
            CalcButton("tan(", ButtonCategory.SCIENTIFIC),
            CalcButton("pi", ButtonCategory.SCIENTIFIC)
        ),
        listOf(
            CalcButton("sqrt(", ButtonCategory.SCIENTIFIC),
            CalcButton("^", ButtonCategory.SCIENTIFIC),
            CalcButton("ln(", ButtonCategory.SCIENTIFIC),
            CalcButton("e", ButtonCategory.SCIENTIFIC)
        )
    )

    val basicButtonRows = listOf(
        listOf(
            CalcButton("C", ButtonCategory.ACTION),
            CalcButton("(", ButtonCategory.ACTION),
            CalcButton(")", ButtonCategory.ACTION),
            CalcButton("÷", ButtonCategory.OPERATOR)
        ),
        listOf(
            CalcButton("7"),
            CalcButton("8"),
            CalcButton("9"),
            CalcButton("×", ButtonCategory.OPERATOR)
        ),
        listOf(
            CalcButton("4"),
            CalcButton("5"),
            CalcButton("6"),
            CalcButton("-", ButtonCategory.OPERATOR)
        ),
        listOf(
            CalcButton("1"),
            CalcButton("2"),
            CalcButton("3"),
            CalcButton("+", ButtonCategory.OPERATOR)
        ),
        listOf(
            CalcButton("."),
            CalcButton("0"),
            CalcButton("⌫", ButtonCategory.ACTION),
            CalcButton("=", ButtonCategory.EQUALS)
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen(basicButtonRows = basicButtonRows, scientificButtonRows = scientificButtonRows)
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(basicButtonRows: List<List<CalcButton>>, scientificButtonRows: List<List<CalcButton>>) {
    var expression by rememberSaveable { mutableStateOf("") }

    val onButtonClick: (String) -> Unit = { symbol ->
        when (symbol) {
            "C" -> expression = ""
            "⌫" -> {
                if (expression.isNotEmpty()) {
                    val scientificPrefixes = listOf("sin(", "cos(", "tan(", "sqrt(", "ln(")
                    val matched = scientificPrefixes.firstOrNull { expression.endsWith(it) }
                    expression = if(matched != null) {
                        expression.dropLast(matched.length)
                    } else {
                        expression.dropLast(1)
                    }
                }
            }
            "=" -> {
                // tudududu: dopisać logike
            }
            else -> {
                expression += symbol
            }
        }
    }

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).safeDrawingPadding(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CalculatorDisplay(
            expression = expression,
            result = "Result",
            modifier = Modifier.weight(if (isLandscape) 0.45f else 1f)
        )

        if (isLandscape) {
            Row (
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Keypad(
                    buttonRows = scientificButtonRows,
                    modifier = Modifier.weight(1f),
                    onButtonClick = onButtonClick,
                    isLandscape = true
                )
                Keypad(
                    buttonRows = basicButtonRows,
                    modifier = Modifier.weight(1f),
                    onButtonClick = onButtonClick,
                    isLandscape = true
                )
            }
        } else {
            Keypad(
                buttonRows = basicButtonRows,
                onButtonClick = onButtonClick,
                isLandscape = false
            )
        }
    }
}

@Composable
fun Keypad(modifier: Modifier = Modifier, buttonRows: List<List<CalcButton>>, onButtonClick: (String) -> Unit = {}, isLandscape: Boolean) {
    Column (
        modifier = if (isLandscape) modifier.fillMaxHeight() else modifier.fillMaxWidth().wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        for (row in buttonRows) {
            Row(
                modifier = if (isLandscape) Modifier.fillMaxWidth().weight(1f) else Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                for (btn in row) {
                    CalculatorButton(
                        btn = btn,
                        modifier = if (isLandscape) {
                            Modifier.weight(1f).fillMaxHeight()
                        } else {
                            Modifier.weight(1f).aspectRatio(1f)
                        },
                        onClick = { onButtonClick(btn.symbol) }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(btn: CalcButton, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val (containerColor, contentColor) = when (btn.category) {
        ButtonCategory.NUMBER ->
            MaterialTheme.colorScheme.surfaceContainerHigh to MaterialTheme.colorScheme.onSurface
        ButtonCategory.OPERATOR ->
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        ButtonCategory.ACTION ->
            MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        ButtonCategory.EQUALS ->
            MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        ButtonCategory.SCIENTIFIC ->
            MaterialTheme.colorScheme.surfaceContainerHighest to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = btn.symbol,
            fontSize = 20.sp,
            color = contentColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CalculatorDisplay(expression: String, result: String, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    LaunchedEffect(expression) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = expression,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = result,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Portrait")
@Preview(showBackground = true, name = "Landscape", device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun CalculatorScreenPreview() {
    val scientificButtonRows = listOf(
        listOf(CalcButton("sin"), CalcButton("cos"), CalcButton("tan"), CalcButton("Pi")),
        listOf(CalcButton("sqrt"), CalcButton("x^y"), CalcButton("ln"), CalcButton("e"))
    )
    val basicButtonRows = listOf(
        listOf(CalcButton("C"), CalcButton("("), CalcButton(")"), CalcButton("÷")),
        listOf(CalcButton("7"), CalcButton("8"), CalcButton("9"), CalcButton("×")),
        listOf(CalcButton("4"), CalcButton("5"), CalcButton("6"), CalcButton("-")),
        listOf(CalcButton("1"), CalcButton("2"), CalcButton("3"), CalcButton("+")),
        listOf(CalcButton("."), CalcButton("0"), CalcButton("⌫"), CalcButton("="))
    )

    CalculatorScreen(
        basicButtonRows = basicButtonRows,
        scientificButtonRows = scientificButtonRows
    )
}