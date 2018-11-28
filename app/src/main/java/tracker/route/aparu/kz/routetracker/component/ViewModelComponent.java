package tracker.route.aparu.kz.routetracker.component;

import javax.inject.Singleton;

import dagger.Component;
import tracker.route.aparu.kz.routetracker.module.ViewModelModule;
import tracker.route.aparu.kz.routetracker.viewmodel.RoutesViewModel;

@Singleton
@Component(modules = ViewModelModule.class)
public interface ViewModelComponent {

    void inject(RoutesViewModel model);
}
