package ch.walica.todo_repeat.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.walica.todo_repeat.TodosEvent
import ch.walica.todo_repeat.database.Todo
import java.time.ZonedDateTime

@Composable
fun ListItem(
    todo: Todo,
    onEvent: (TodosEvent) -> Unit,
    icon: ImageVector,
    desc: String,
    delayed: Boolean,
    delete: Boolean = false
) {
    Row() {
        Text(
            text = todo.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 16.sp,
                color = if (todo.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                    alpha = 0.8f
                )
            ),
            modifier = Modifier
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                )
                .clickable {
                    onEvent(
                        TodosEvent.UpdateTodo(
                            todo.copy(
                                dayOfYear = ZonedDateTime.now().dayOfYear,
                                selected = !todo.selected
                            )
                        )
                    )
                }

        )
        Spacer(modifier = Modifier.weight(1f))
        if (delete) {
            IconButton(
                onClick = { onEvent(TodosEvent.DeleteTodo(todo)) }
            ) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete todo from db")
            }
        }
        IconButton(
            onClick = {
                onEvent(
                    TodosEvent.UpdateTodo(
                        todo.copy(
                            delayed = delayed,
                            date = ZonedDateTime.now().toEpochSecond()
                        )
                    )
                )
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = desc,
            )
        }
    }
}