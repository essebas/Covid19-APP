package co.sebasdeveloper.pruebaservinformacion;
import co.sebasdeveloper.pruebaservinformacion.model.LocationDataModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import co.sebasdeveloper.pruebaservinformacion.model.LocationDataModel;
import co.sebasdeveloper.pruebaservinformacion.model.ResponseModel;
import co.sebasdeveloper.pruebaservinformacion.viewmodel.LocationDataViewModel;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCircleClickListener, GoogleMap.OnMarkerClickListener {

    @Inject
    ViewModelProvider.Factory viewFactory;

    private GoogleMap mMap;
    private LocationDataViewModel locationDataViewModel;
    private final static String TAG = "MapsActivity";
    private CircleOptions circleOptions;
    private List<LocationDataModel> locationDataModels;
    private Bitmap smallMarker;
    private List<Marker> listMarkers;
    private CustomInfoWindow customInfoWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ((BaseApplication) getApplication()).getAppComponent().inject(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationDataViewModel = new ViewModelProvider(this, viewFactory).get(LocationDataViewModel.class);
        circleOptions = new CircleOptions()
                .strokeColor(getColorWithAlpha(getResources().getColor(R.color.colorInfo), 0.9f))
                .strokeWidth(5)
                .fillColor(getColorWithAlpha(getResources().getColor(R.color.colorInfo), 0.2f))
                .clickable(true);
        locationDataModels = new ArrayList<>();

        int height = 1;
        int width = 1;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        customInfoWindow = new CustomInfoWindow(this);
        mMap.setInfoWindowAdapter(customInfoWindow);
        mMap.setOnMarkerClickListener(this);

        //Test
        //Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //marker.showInfoWindow();
        //Test
        locationDataViewModel.getModelMutableLiveData("us").observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                locationDataModels = responseModel.getData();
                addGeoreferences(locationDataModels);
            }
        });

        LatLng usa = new LatLng(41.4850147, -99.1629576);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(usa));
    }

    public void addGeoreferences(List<LocationDataModel> dataList){
        int index = 0;
        LatLng latgn = null;
        for (LocationDataModel data : dataList) {
            latgn = new LatLng(data.getLatitude(), data.getLongitude());
            circleOptions.center(new LatLng(data.getLatitude(), data.getLongitude()));
            circleOptions.radius(data.getConfirmed()+80000);
            mMap.addMarker(new MarkerOptions()
                    .position(latgn)
                    .title(index + "")
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            );
            mMap.addCircle(new CircleOptions()
                    .center(latgn)
                    .radius(data.getConfirmed()+80000)
                    .strokeColor(getColorWithAlpha(getResources().getColor(R.color.colorInfo), 0.9f))
                    .strokeWidth(5)
                    .fillColor(getColorWithAlpha(getResources().getColor(R.color.colorInfo), 0.2f))
                    .clickable(true)
                    .zIndex(index)
            );
            index++;
        }
    }

    public void showGeoreferenceData(int index){
        LocationDataModel ldm = locationDataModels.get(index);
        Snackbar snack = Snackbar.make(findViewById(R.id.map), "Prueba, circulo " + ldm.getConfirmed() , Snackbar.LENGTH_LONG);
        Log.d(TAG, "Resultado/ Confirmados: " + ldm.getConfirmed() + " Ciudad: " + ldm.getLocation());
        snack.show();
    }

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    @Override
    public void onCircleClick(Circle circle) {
        Log.d(TAG, "Marcado: ");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            LocationDataModel data = locationDataModels.get(Integer.parseInt(marker.getTitle()));
            this.customInfoWindow.setLocationDataModel(data);
        }catch (Exception ex){
            Log.e(TAG, ex.getMessage());
        }
        return false;
    }
}
