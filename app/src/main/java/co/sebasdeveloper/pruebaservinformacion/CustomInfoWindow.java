package co.sebasdeveloper.pruebaservinformacion;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import co.sebasdeveloper.pruebaservinformacion.model.LocationDataModel;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private LocationDataModel locationDataModel;
    private static final String TAG = "CustomInfoWindow";
    private String pathAPIFlagsStyle = "/flat/";
    private String pathAPIFlagsSize = "64.png";
    private String pathAPIFlags = "https://www.countryflags.io/";

    private View view;
    private TextView title;
    private TextView subTitle;
    private ImageView imgCountry;
    private TextView value_contagios;
    private TextView value_fallecidos;
    private TextView value_recuperados;

    public CustomInfoWindow(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        initComponents();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        String path = pathAPIFlags + locationDataModel.getCountry_code() + pathAPIFlagsStyle + pathAPIFlagsSize;
        title.setText(locationDataModel.getLocation());
        subTitle.setText(locationDataModel.getCountry_code());
        value_contagios.setText("" + locationDataModel.getConfirmed());
        value_fallecidos.setText("" + locationDataModel.getDead());
        if(locationDataModel.getRecovered() == null){
            value_recuperados.setText("0");
        }else {
            value_recuperados.setText("" + locationDataModel.getRecovered());
        }
        String state = locationDataModel.getLocation().toLowerCase();
        if(state.contains(" ")){
            state = state.replace(" ", "-");
        }
        String path2 = "https://cdn.civil.services/us-states/flags/"+state+"-large.png";
        Log.d(TAG, path2);
        Picasso.get().load(path2).centerCrop().resize(150,100).into(imgCountry, new MarkerCallback(marker,path2,imgCountry));
        Log.d(TAG, "Response: " + locationDataModel.getCountry_code() + ", " + locationDataModel.getLocation() + ", " + locationDataModel.getConfirmed());
        return view;
    }

    public void setLocationDataModel(LocationDataModel locationDataModel) {
        this.locationDataModel = locationDataModel;
    }

    public void initComponents(){
        view = ((Activity)context).getLayoutInflater().inflate(R.layout.info_window_adapter, null);
        title = view.findViewById(R.id.title_state);
        subTitle = view.findViewById(R.id.title_country);
        imgCountry = view.findViewById(R.id.img_country);
        value_contagios = view.findViewById(R.id.text_contagiados_value);
        value_fallecidos = view.findViewById(R.id.text_fallecidos_value);
        value_recuperados = view.findViewById(R.id.text_recuperados_value);
    }
}
