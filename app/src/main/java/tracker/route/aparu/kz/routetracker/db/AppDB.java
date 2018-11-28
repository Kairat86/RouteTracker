package tracker.route.aparu.kz.routetracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import tracker.route.aparu.kz.routetracker.db.dao.CoordinateDao;
import tracker.route.aparu.kz.routetracker.db.dao.RecordDao;
import tracker.route.aparu.kz.routetracker.model.Coordinate;
import tracker.route.aparu.kz.routetracker.model.Record;
import tracker.route.aparu.kz.routetracker.util.Converters;

@Database(entities = {Coordinate.class, Record.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDB extends RoomDatabase {
    public abstract CoordinateDao coordinateDao();

    public abstract RecordDao recordDao();
}
