package co.sebasdeveloper.pruebaservinformacion;
import android.widget.ImageView;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MarkerCallback implements Callback {
    private Marker marker=null;
    private String URL;
    private ImageView userPhoto;

    public MarkerCallback(Marker marker, String URL, ImageView userPhoto) {
        this.marker = marker;
        this.URL = URL;
        this.userPhoto = userPhoto;
    }

    @Override
    public void onSuccess() {
        if (marker != null && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();

            Picasso.get().load(URL).centerCrop().resize(80,50).into(userPhoto);

            marker.showInfoWindow();
        }
    }

    @Override
    public void onError(Exception e) {

    }
}
