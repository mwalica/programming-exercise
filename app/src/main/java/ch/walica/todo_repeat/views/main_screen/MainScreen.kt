package ch.walica.todo_repeat.views.main_screen


import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.IndeterminateCheckBox
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.walica.todo_repeat.TodosEvent
import ch.walica.todo_repeat.components.BottomSheepForm
import ch.walica.todo_repeat.components.ListItem
import ch.walica.todo_repeat.components.TextAlert
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val viewModel = viewModel<MainScreenViewModel>()
    val state by viewModel.state.collectAsState()

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()


    BottomSheetScaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Programming exercise") },
                actions = {
                    IconButton(onClick = {
                        val date = ZonedDateTime.now().toEpochSecond()
                        state.todos.filter { todo ->
                            todo.delayed && todo.date + 60 < date
                        }.forEach { todo ->
                            viewModel.onEvent(
                                TodosEvent.UpdateTodo(
                                    todo.copy(
                                        date = date,
                                        delayed = false
                                    )
                                )
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "refresh exercise"
                        )
                    }

                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Show bottom sheet"
                        )
                    }
                }
            )
        },
        sheetPeekHeight = 42.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheepForm(state, viewModel::onEvent)
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(
                text = "Skills to exercise",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)

            ) {
                LazyColumn(
                    modifier = Modifier.padding(8.dp)
                ) {
                    val todos = state.todos.filter { todo -> !todo.delayed }
                    if (todos.isEmpty()) {
                        item {
                            TextAlert()
                        }
                        return@LazyColumn
                    }

                    itemsIndexed(todos) { index, todo ->

                        Column {
                            ListItem(
                                todo = todo,
                                onEvent = viewModel::onEvent,
                                icon = Icons.Rounded.IndeterminateCheckBox,
                                desc = "To delayed",
                                delayed = true
                            )
                            if (index < todos.lastIndex) {
                                Divider(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                            }
                        }
                    }

                }
            }
            //end card
            Text(
                text = "Delayed skills",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(8.dp)
                ) {
                    val todos = state.todos.filter { todo -> todo.delayed }
                    if (todos.isEmpty()) {
                        item {
                            TextAlert()
                        }
                        return@LazyColumn
                    }
                    itemsIndexed(todos) { index, todo ->

                        Column {
                            ListItem(
                                todo = todo,
                                onEvent = viewModel::onEvent,
                                icon = Icons.Rounded.CheckBox,
                                desc = "To exercise",
                                delayed = false
                            )
                            if (index < todos.lastIndex) {
                                Divider(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}