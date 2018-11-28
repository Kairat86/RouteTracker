package tracker.route.aparu.kz.routetracker.manager;

import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.tasks.OnSuccessListener;

public interface LocationManager {

    void getLastLocation(OnSuccessListener<Location> clbk);

    void requestUpdates(LocationCallback clbk);

    void stopUpdates();
}
