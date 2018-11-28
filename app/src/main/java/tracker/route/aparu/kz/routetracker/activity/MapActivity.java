package tracker.route.aparu.kz.routetracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tracker.route.aparu.kz.routetracker.R;
import tracker.route.aparu.kz.routetracker.component.DaggerActivityComponent;
import tracker.route.aparu.kz.routetracker.fragment.MyRoutesFragment;
import tracker.route.aparu.kz.routetracker.manager.LocationManager;
import tracker.route.aparu.kz.routetracker.model.Coordinate;
import tracker.route.aparu.kz.routetracker.model.Record;
import tracker.route.aparu.kz.routetracker.module.ActivityModule;
import tracker.route.aparu.kz.routetracker.service.LocationService;
import tracker.route.aparu.kz.routetracker.viewmodel.RoutesViewModel;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.view.Gravity.START;
import static tracker.route.aparu.kz.routetracker.util.Constant.RECORD_ID;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, OnSuccessListener<Location> {

    private static final String TAG = MapActivity.class.getSimpleName();
    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 1;
    public static final String SHARED_PREFS_ROUTE_TRACKER = "SHARED_PREFS_ROUTE_TRACKER";
    private GoogleMap mMap;
    private Location myLocation;
    private boolean recording;
    @Inject
    LocationManager manager;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    SupportMapFragment mapFragment;
    @Inject
    MyRoutesFragment myRoutesFragment;
    @Inject
    RoutesViewModel viewModel;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fab)
    ImageView fab;
    private Marker marker;
    private float zoom = -1;
    private long recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
        getMyLocation();
    }

    private void getMyLocation() {
        if (permissionsGranted(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION})) {
            manager.getLastLocation(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        } else {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSuccess(Location location) {
        myLocation = location;
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapFragment.getMapAsync(this);
        fragmentManager.beginTransaction().add(R.id.container, myRoutesFragment).hide(myRoutesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.container, mapFragment).commit();
        viewModel.getAllRecords().observe(this, records -> {
            myRoutesFragment.updateUI(records);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMap();
    }

    private void updateMap() {
        LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        if (marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(myLatLng));
        } else {
            marker.setPosition(myLatLng);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom == -1 ? 16 : zoom));
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS_ROUTE_TRACKER, MODE_PRIVATE);
        long recordId = preferences.getLong(RECORD_ID, 0);
        if (recordId > 0) {
            this.recordId = recordId;
            preferences.edit().putLong(RECORD_ID, 0).apply();
            stopService(new Intent(this, LocationService.class));
            startRecording(recordId, fab);
        }
        if (recording) {
            viewModel.insertCoordinate(new Coordinate(myLocation, recordId));
            viewModel.incrementRecordPointNumber(recordId);
        }
    }

    private boolean permissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public void startStop(View view) {
        ImageView imageView = (ImageView) view;
        if (!recording) {
            Observable.defer((Callable<ObservableSource<Long>>) () -> Observable.just(viewModel.insertRecord(new Record()))).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).doOnNext(id -> startRecording(id, imageView)).subscribe();
        } else {
            manager.stopUpdates();
            imageView.setImageResource(android.R.drawable.ic_media_play);
            recording = false;
        }
    }

    private void startRecording(long id, ImageView view) {
        recordId = id;
        viewModel.insertCoordinate(new Coordinate(myLocation, recordId));
        viewModel.incrementRecordPointNumber(recordId);
        manager.requestUpdates(new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                Log.i(TAG, "onLocationResult");
                if (lastLocation != null && (myLocation.getLatitude() != lastLocation.getLatitude() || myLocation.getLongitude() != lastLocation.getLongitude())) {
                    myLocation = lastLocation;
                    Toast.makeText(MapActivity.this, "Received new location=>" + lastLocation, Toast.LENGTH_LONG).show();
                    zoom = mMap.getCameraPosition().zoom;
                    updateMap();
                }
            }
        });
        recording = true;
        view.setImageResource(R.drawable.ic_stop);
    }


    public void openDrawer(View view) {
        drawerLayout.openDrawer(START);
    }

    public void showMap(MenuItem item) {
        fragmentManager.beginTransaction().show(mapFragment).hide(myRoutesFragment).commit();
        item.setChecked(true);
        drawerLayout.closeDrawer(START);

    }

    public void showHistory(MenuItem item) {
        fragmentManager.beginTransaction().show(myRoutesFragment).hide(mapFragment).commit();
        item.setChecked(true);
        drawerLayout.closeDrawer(START);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        if (recording) {
            manager.stopUpdates();
            Intent service = new Intent(this, LocationService.class);
            service.putExtra(RECORD_ID, recordId);
            startService(service);
            getSharedPreferences(SHARED_PREFS_ROUTE_TRACKER, MODE_PRIVATE).edit().putLong(RECORD_ID, recordId).apply();
        }
        super.onDestroy();
    }
}
