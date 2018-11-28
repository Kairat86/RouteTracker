package tracker.route.aparu.kz.routetracker.component;

import javax.inject.Singleton;

import dagger.Component;
import tracker.route.aparu.kz.routetracker.module.DBModule;
import tracker.route.aparu.kz.routetracker.repo.Repository;

@Singleton
@Component(modules = DBModule.class)
public interface DBComponent {

    void inject(Repository repository);
}
