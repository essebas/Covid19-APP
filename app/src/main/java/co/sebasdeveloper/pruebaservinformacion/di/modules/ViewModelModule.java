package co.sebasdeveloper.pruebaservinformacion.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import co.sebasdeveloper.pruebaservinformacion.di.ViewModelKey;
import co.sebasdeveloper.pruebaservinformacion.viewmodel.LocationDataViewModel;
import co.sebasdeveloper.pruebaservinformacion.viewmodel.ViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocationDataViewModel.class)
    abstract ViewModel bLocationDataViewModel(LocationDataViewModel locationDataViewModel);

    @Binds
    abstract ViewModelProvider.Factory binFactory(ViewModelFactory factory);

}
