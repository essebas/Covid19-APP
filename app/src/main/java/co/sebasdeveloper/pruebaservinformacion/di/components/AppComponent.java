package co.sebasdeveloper.pruebaservinformacion.di.components;

import javax.inject.Singleton;

import co.sebasdeveloper.pruebaservinformacion.MainActivity;
import co.sebasdeveloper.pruebaservinformacion.MapsActivity;
import co.sebasdeveloper.pruebaservinformacion.di.modules.ContextModule;
import co.sebasdeveloper.pruebaservinformacion.di.modules.NetworkModule;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface AppComponent {
    void inject(MapsActivity mapsActivity);
}
