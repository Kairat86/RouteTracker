package tracker.route.aparu.kz.routetracker.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tracker.route.aparu.kz.routetracker.db.AppDB;
import tracker.route.aparu.kz.routetracker.db.dao.CoordinateDao;
import tracker.route.aparu.kz.routetracker.db.dao.RecordDao;

@Module
public class DBModule {

    @Singleton
    private final AppDB db;

    public DBModule(Application app) {
        db = Room.databaseBuilder(app, AppDB.class, "coordinates").build();
    }

    @Provides
    @Singleton
    CoordinateDao provideCoordinatesDao() {
        return db.coordinateDao();
    }

    @Provides
    @Singleton
    RecordDao provideRecordDao() {
        return db.recordDao();
    }
}
