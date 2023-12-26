package ch.walica.todo_repeat.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodosDatabase : RoomDatabase() {
    abstract val dao: TodoDao
}