package tracker.route.aparu.kz.routetracker.module;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    private final Context activity;

    public LocationModule(Context activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    FusedLocationProviderClient provideFusedLocationProviderClient() {
        return LocationServices.getFusedLocationProviderClient(activity);
    }

    @Provides
    @Singleton
    LocationRequest provideLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        return locationRequest;
    }
}
