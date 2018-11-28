package tracker.route.aparu.kz.routetracker.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import tracker.route.aparu.kz.routetracker.R;
import tracker.route.aparu.kz.routetracker.activity.MapActivity;
import tracker.route.aparu.kz.routetracker.manager.LocationManager;
import tracker.route.aparu.kz.routetracker.manager.LocationManagerImp;
import tracker.route.aparu.kz.routetracker.model.Coordinate;
import tracker.route.aparu.kz.routetracker.model.Record;
import tracker.route.aparu.kz.routetracker.viewmodel.RoutesViewModel;

import static tracker.route.aparu.kz.routetracker.util.Constant.RECORD_ID;

public class LocationService extends Service {
    private static final String TAG = LocationService.class.getSimpleName();
    private static final String CHANNEL_ID = "channel_route_tracker";
    private static final int NOTIFICATION_ID = 1;
    LocationManager manager;


    @Override
    public IBinder onBind(Intent intent) {return null;}

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "RouteTracker", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle(getString(R.string.app_name)).setContentText(getString(R.string.recording)).setSmallIcon(R.mipmap.ic_launcher_round).setChannelId(CHANNEL_ID).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MapActivity.class), 0)).setDefaults(0).build();
        notificationManager.notify(NOTIFICATION_ID, notification);
        startForeground(NOTIFICATION_ID, notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manager = new LocationManagerImp(getApplicationContext());
        ViewModel viewModel = new RoutesViewModel(getApplication());
        long recordId = intent.getLongExtra(RECORD_ID, 0);
        if (recordId == 0) {
            recordId = ((RoutesViewModel) viewModel).insertRecord(new Record());
        }

        final long finalRecordId = recordId;
        manager.requestUpdates(new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.i(TAG, "omLocationResult");
                ((RoutesViewModel) viewModel).insertCoordinate(new Coordinate(locationResult.getLastLocation(), finalRecordId));
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        manager.stopUpdates();
        super.onDestroy();
    }
}
