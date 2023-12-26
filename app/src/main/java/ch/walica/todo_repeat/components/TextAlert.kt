package ch.walica.todo_repeat.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextAlert(msg: String = "List is empty") {
    Text(
        text = msg,
        style = TextStyle(textAlign = TextAlign.Center),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
}