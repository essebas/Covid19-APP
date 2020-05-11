package co.sebasdeveloper.pruebaservinformacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import co.sebasdeveloper.pruebaservinformacion.adapter.PageAdapter;
import co.sebasdeveloper.pruebaservinformacion.model.ResponseModel;
import co.sebasdeveloper.pruebaservinformacion.viewmodel.LocationDataViewModel;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewFactory;

    private LocationDataViewModel locationDataViewModel;
    private final static String TAG = "MainActivity";

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //((BaseApplication) getApplication()).getAppComponent().inject(this);

        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setUserInputEnabled(false);
        //locationDataViewModel = new ViewModelProvider(this, viewFactory).get(LocationDataViewModel.class);

        pageAdapter = new PageAdapter(this, locationDataViewModel);
        viewPager2.setAdapter(pageAdapter);
        tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Mapa");
                        tab.setIcon(R.drawable.ic_map_black_24dp);
                        break;
                    case 1:
                        tab.setText("Lista");
                        tab.setIcon(R.drawable.ic_list_black_24dp);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)
                        );
                        badgeDrawable.setVisible(true);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);
                Log.d(TAG, "La pagina ha cambiado a: " + position);
            }



        });

        /*locationDataViewModel.getModelMutableLiveData("us").observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {

            }
        });*/

    }
}
