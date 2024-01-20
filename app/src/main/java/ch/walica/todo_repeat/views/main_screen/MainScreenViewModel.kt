package ch.walica.todo_repeat.views.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.todo_repeat.MyApp
import ch.walica.todo_repeat.TodosEvent
import ch.walica.todo_repeat.TodosState
import ch.walica.todo_repeat.database.Todo
import ch.walica.todo_repeat.database.TodoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class MainScreenViewModel(private val dao: TodoDao = MyApp.appModule.db.dao) : ViewModel() {

    private val _todos =
        dao.getTodos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(TodosState())
    val state = combine(_state, _todos) { state, todos ->
        state.copy(
            todos = todos
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodosState())

    fun onEvent(event: TodosEvent) {
        when (event) {
            is TodosEvent.SetTitle -> {
                _state.update { state ->
                    state.copy(
                        enteredTitle = event.enteredTitle
                    )
                }
            }

            is TodosEvent.UpdateTodo -> {
                val todo = event.todo

                viewModelScope.launch {
                    dao.upsertTodo(todo)
                }
            }

            is TodosEvent.InsertTodo -> {
                val title = _state.value.enteredTitle
                val date = ZonedDateTime.now()

                if (title.isBlank()) {
                    return
                }

                val todo = Todo(
                    title = title,
                    date = date.toEpochSecond(),
                    dayOfYear = date.dayOfYear
                )
                viewModelScope.launch {
                    dao.upsertTodo(todo)
                }
                _state.update { state ->
                    state.copy(
                        enteredTitle = ""
                    )
                }

            }

            is TodosEvent.DeleteTodo -> {
                viewModelScope.launch {
                    dao.deleteTodo(event.todo)
                }
            }

        }
    }
}