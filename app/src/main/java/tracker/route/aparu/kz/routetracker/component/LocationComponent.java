package tracker.route.aparu.kz.routetracker.component;

import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import tracker.route.aparu.kz.routetracker.manager.LocationManagerImp;
import tracker.route.aparu.kz.routetracker.module.LocationModule;

@Singleton
@Component(modules = LocationModule.class)
public interface LocationComponent {

    void inject(LocationManagerImp manager);
}
