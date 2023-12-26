package ch.walica.todo_repeat.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    val title: String,
    val delayed: Boolean = false,
    val archive: Boolean = false,
    val date: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0


)