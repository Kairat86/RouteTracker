package tracker.route.aparu.kz.routetracker.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import tracker.route.aparu.kz.routetracker.component.DaggerDBComponent;
import tracker.route.aparu.kz.routetracker.db.dao.CoordinateDao;
import tracker.route.aparu.kz.routetracker.db.dao.RecordDao;
import tracker.route.aparu.kz.routetracker.model.Coordinate;
import tracker.route.aparu.kz.routetracker.model.Record;
import tracker.route.aparu.kz.routetracker.module.DBModule;

public class Repository {

    @Inject
    CoordinateDao coordinateDao;
    @Inject
    RecordDao recordDao;

    public Repository(Application app) {
        DaggerDBComponent.builder().dBModule(new DBModule(app)).build().inject(this);
    }

    public LiveData<List<Coordinate>> getCoordinatesByRecordId(int recordId) {
        return coordinateDao.getByRecordId(recordId);
    }

    public void insertCoordinate(Coordinate coordinate) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                coordinateDao.insert(coordinate);
            }
        }.start();
    }

    public LiveData<List<Record>> getAllRecords() {
        return recordDao.getAll();
    }

    public long insertRecord(Record record) {
        return recordDao.insert(record);
    }

    public void incrementRecordPointNumber(long recordId) {
        new Thread() {
            @Override
            public void run() {
                recordDao.incrementPointNumber(recordId);
            }
        }.start();
    }
}
