package tracker.route.aparu.kz.routetracker.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import tracker.route.aparu.kz.routetracker.model.Record;

@Dao
public interface RecordDao {

    @Query("SELECT * FROM record  ORDER BY id DESC")
    LiveData<List<Record>> getAll();

    /**
     *
     * @param record - Record
     * @return  id of newly inserted Record
     */
    @Insert
    long insert(Record record);

    @Query("UPDATE record SET pointNumber=pointNumber+1 WHERE id==:recordId")
    void incrementPointNumber(long recordId);
}
