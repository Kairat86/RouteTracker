package tracker.route.aparu.kz.routetracker.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import tracker.route.aparu.kz.routetracker.component.DaggerLocationComponent;
import tracker.route.aparu.kz.routetracker.module.LocationModule;

@SuppressLint("MissingPermission")
public class LocationManagerImp implements LocationManager {

    private LocationCallback clbk;

    @Inject
    FusedLocationProviderClient locationProviderClient;

    @Inject
    LocationRequest locationRequest;

    public LocationManagerImp(Context activity) {
        DaggerLocationComponent.builder().locationModule(new LocationModule(activity)).build().inject(this);
    }

    public void getLastLocation(OnSuccessListener<Location> clbk) {
        locationProviderClient.getLastLocation().addOnSuccessListener(clbk);
    }

    @Override
    public void requestUpdates(LocationCallback clbk) {
        this.clbk = clbk;
        locationProviderClient.requestLocationUpdates(locationRequest, clbk, null);
    }

    @Override
    public void stopUpdates() {
        if (clbk != null) locationProviderClient.removeLocationUpdates(clbk);
    }

}
