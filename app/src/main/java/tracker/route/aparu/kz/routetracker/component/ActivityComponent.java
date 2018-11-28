package tracker.route.aparu.kz.routetracker.component;

import javax.inject.Singleton;

import dagger.Component;
import tracker.route.aparu.kz.routetracker.activity.MapActivity;
import tracker.route.aparu.kz.routetracker.module.ActivityModule;

@Singleton
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MapActivity activity);
}
