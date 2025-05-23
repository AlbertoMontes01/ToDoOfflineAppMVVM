import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todoofflineappmvvm.data.local.dao.ToDoDao
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.tuapp.data.local.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ToDoDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: ToDoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.toDoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetTodos() = runBlocking {
        val todo = ToDoEntity(
            localId = 0,
            id = 1,
            userId = 1,
            title = "Sample Task",
            completed = false
        )

        dao.insertToDo(todo)

        val result = dao.getAllToDo().first()

        // Verificamos que el resultado contenga al menos un ToDo con mismo título
        val found = result.any {
            it.title == todo.title &&
                    it.id == todo.id &&
                    it.userId == todo.userId &&
                    it.completed == todo.completed
        }

        assertTrue("El ToDo insertado no se encontró en la base de datos", found)
    }
}
