package ch.walica.todo_repeat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.walica.todo_repeat.TodosEvent
import ch.walica.todo_repeat.TodosState

@Composable
fun BottomSheepForm(state: TodosState, onEvent: (TodosEvent) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        TextField(
            value = state.enteredTitle,
            onValueChange = { text -> onEvent(TodosEvent.SetTitle(text)) },
            label = { Text(text = "skill to exercise") }
        )
        TextButton(onClick = { onEvent(TodosEvent.InsertTodo) }) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add exercise")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add")
        }
    }
}