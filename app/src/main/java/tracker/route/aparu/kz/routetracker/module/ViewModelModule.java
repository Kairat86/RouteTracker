package tracker.route.aparu.kz.routetracker.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tracker.route.aparu.kz.routetracker.repo.Repository;

@Module
public class ViewModelModule {

    private final Application app;

    public ViewModelModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Repository provideRepository() {
        return new Repository(app);
    }
}
