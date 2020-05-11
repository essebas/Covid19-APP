package co.sebasdeveloper.pruebaservinformacion.adapter;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import co.sebasdeveloper.pruebaservinformacion.ui.MapFragment;
import co.sebasdeveloper.pruebaservinformacion.ui.CountryListFragment;
import co.sebasdeveloper.pruebaservinformacion.viewmodel.LocationDataViewModel;

public class PageAdapter extends FragmentStateAdapter {

    private LocationDataViewModel locationDataViewModel;
    private MapFragment mapFragment;
    private CountryListFragment countryListFragment;
    private static final String TAG = "PageAdapter";

    public PageAdapter(@NonNull FragmentActivity fragmentActivity, LocationDataViewModel locationDataModel) {
        super(fragmentActivity);
        this.locationDataViewModel = locationDataModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getFragment(position);
    }

    public Fragment getFragment(int position){
        switch (position){
            case 0:
                if(mapFragment == null ){
                    Log.d(TAG, "Se creo el fragmento desde 0");
                    mapFragment = new MapFragment(locationDataViewModel);
                }
                Log.d(TAG, "Se envio el fragmento");
                return mapFragment;
            default:
                if(countryListFragment == null ){
                    Log.d(TAG, "Se creo el fragmento desde 0");
                    countryListFragment = new CountryListFragment(locationDataViewModel);
                }
                Log.d(TAG, "Se envio el fragmento");
                return countryListFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
