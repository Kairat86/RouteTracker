package tracker.route.aparu.kz.routetracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import tracker.route.aparu.kz.routetracker.component.DaggerViewModelComponent;
import tracker.route.aparu.kz.routetracker.model.Coordinate;
import tracker.route.aparu.kz.routetracker.model.Record;
import tracker.route.aparu.kz.routetracker.module.ViewModelModule;
import tracker.route.aparu.kz.routetracker.repo.Repository;

public class RoutesViewModel extends AndroidViewModel {

    @Inject
    Repository repo;

    public RoutesViewModel(@NonNull Application application) {
        super(application);
        DaggerViewModelComponent.builder().viewModelModule(new ViewModelModule(application)).build().inject(this);
    }

    public LiveData<List<Coordinate>> getCoordinatesByRecordId(int id) {
        return repo.getCoordinatesByRecordId(id);
    }

    public void insertCoordinate(Coordinate coordinate) {
        repo.insertCoordinate(coordinate);
    }

    public LiveData<List<Record>> getAllRecords() {
        return repo.getAllRecords();
    }

    public long insertRecord(Record record) {
        return repo.insertRecord(record);
    }

    public void incrementRecordPointNumber(long recordId) {
        repo.incrementRecordPointNumber(recordId);
    }
}
