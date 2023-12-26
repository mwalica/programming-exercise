package ch.walica.todo_repeat

import android.content.Context
import androidx.room.Room
import ch.walica.todo_repeat.database.TodosDatabase

interface AppModule {
    val db: TodosDatabase
}

class AppModuleImpl(private val appContext: Context) : AppModule {
    override val db: TodosDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            TodosDatabase::class.java,
            "todos_db"
        ).build()
    }

}

