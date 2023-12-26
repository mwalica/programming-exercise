package ch.walica.todo_repeat

import ch.walica.todo_repeat.database.Todo

sealed interface TodosEvent {

    data class SetTitle(val enteredTitle: String) : TodosEvent
    data object InsertTodo : TodosEvent
    data class UpdateTodo(val todo: Todo) : TodosEvent
    data class DeleteTodo(val todo: Todo) : TodosEvent
}
