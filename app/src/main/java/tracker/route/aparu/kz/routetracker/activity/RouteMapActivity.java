package tracker.route.aparu.kz.routetracker.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import tracker.route.aparu.kz.routetracker.R;
import tracker.route.aparu.kz.routetracker.model.Coordinate;
import tracker.route.aparu.kz.routetracker.viewmodel.RoutesViewModel;

import static tracker.route.aparu.kz.routetracker.util.Constant.RECORD_ID;

public class RouteMapActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ViewModelProviders.of(this).get(RoutesViewModel.class).getCoordinatesByRecordId(getIntent().getIntExtra(RECORD_ID, 0)).observe(this, coordinates -> {
            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            for (Coordinate coordinate : coordinates) {
                LatLng point = new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
                options.add(point);
            }
            googleMap.addPolyline(options);
            Coordinate coordinate = coordinates.get(0);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(coordinate.getLatitude(), coordinate.getLongitude())));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        });
    }
}
