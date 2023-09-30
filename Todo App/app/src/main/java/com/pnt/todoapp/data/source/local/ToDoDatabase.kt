import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.RoomDatabase
import com.pnt.todoapp.data.source.local.LocalTask
import com.pnt.todoapp.data.source.local.TaskDao

/**
 * The Room Database that contains the com.pnt.todoapp.data.Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [LocalTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase() : RoomDatabase(), Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    abstract fun taskDao(): TaskDao
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

//    companion object CREATOR : Parcelable.Creator<ToDoDatabase> {
//        override fun createFromParcel(parcel: Parcel): ToDoDatabase {
//            return ToDoDatabase(parcel)
//        }
//
//        override fun newArray(size: Int): Array<ToDoDatabase?> {
//            return arrayOfNulls(size)
//        }
//    }
}
