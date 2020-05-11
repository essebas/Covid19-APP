package co.sebasdeveloper.pruebaservinformacion;

import android.app.Application;

import co.sebasdeveloper.pruebaservinformacion.di.components.AppComponent;
import co.sebasdeveloper.pruebaservinformacion.di.components.DaggerAppComponent;

public class BaseApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
