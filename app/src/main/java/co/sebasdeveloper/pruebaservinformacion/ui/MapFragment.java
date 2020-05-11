package co.sebasdeveloper.pruebaservinformacion.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import co.sebasdeveloper.pruebaservinformacion.CustomInfoWindow;
import co.sebasdeveloper.pruebaservinformacion.R;
import co.sebasdeveloper.pruebaservinformacion.model.LocationDataModel;
import co.sebasdeveloper.pruebaservinformacion.model.ResponseModel;
import co.sebasdeveloper.pruebaservinformacion.viewmodel.LocationDataViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnCircleClickListener, GoogleMap.OnMarkerClickListener{

    private LocationDataViewModel locationDataViewModel;
    private View view;
    private GoogleMap mMap;
    private final static String TAG = "MapFragment";
    private CircleOptions circleOptions;
    private List<LocationDataModel> locationDataModels;
    private Bitmap smallMarker;
    private List<Marker> listMarkers;
    private CustomInfoWindow customInfoWindow;

    public MapFragment(LocationDataViewModel locationDataViewModel) {
        this.locationDataViewModel = locationDataViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setVisibility(View.INVISIBLE);
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

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        customInfoWindow = new CustomInfoWindow(getContext());
        mMap.setInfoWindowAdapter(customInfoWindow);
        mMap.setOnMarkerClickListener(this);

        /*locationDataViewModel.getModelMutableLiveData("us").observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                locationDataModels = responseModel.getData();
                addGeoreferences(locationDataModels);
            }
        });*/

        LatLng usa = new LatLng(41.4850147, -99.1629576);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(usa));
    }

    @Override
    public void onCircleClick(Circle circle) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
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
        Snackbar snack = Snackbar.make(view.findViewById(R.id.map), "Prueba, circulo " + ldm.getConfirmed() , Snackbar.LENGTH_LONG);
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

}
