package ch.walica.todo_repeat

import ch.walica.todo_repeat.database.Todo

data class TodosState(
    val todos: List<Todo> = emptyList(),
    val enteredTitle: String = "",
)