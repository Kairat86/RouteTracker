package tracker.route.aparu.kz.routetracker.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import tracker.route.aparu.kz.routetracker.model.Coordinate;

@Dao
public interface CoordinateDao {

    @Query("SELECT * FROM coordinate WHERE recordId==:recordId")
    LiveData<List<Coordinate>> getByRecordId(int recordId);

    @Insert
    void insert(Coordinate coordinate);
}
