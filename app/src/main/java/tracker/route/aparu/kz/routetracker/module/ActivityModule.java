package tracker.route.aparu.kz.routetracker.module;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.SupportMapFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tracker.route.aparu.kz.routetracker.fragment.MyRoutesFragment;
import tracker.route.aparu.kz.routetracker.manager.LocationManager;
import tracker.route.aparu.kz.routetracker.manager.LocationManagerImp;
import tracker.route.aparu.kz.routetracker.viewmodel.RoutesViewModel;

@Module
public class ActivityModule {

    private final FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager() {
        return new LocationManagerImp(activity);
    }

    @Provides
    @Singleton
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @Provides
    @Singleton
    SupportMapFragment provideSupportMapFragment() {
        return SupportMapFragment.newInstance();
    }

    @Provides
    @Singleton
    MyRoutesFragment provideMyRoutesFragment() {
        return MyRoutesFragment.newInstance();
    }

    @Provides
    @Singleton
    RoutesViewModel provideViewModel() {
        return ViewModelProviders.of(activity).get(RoutesViewModel.class);
    }

    @Provides
    @Singleton
    ViewModel provideRoutesViewModel() {
        return ViewModelProviders.of(activity).get(RoutesViewModel.class);
    }
}
