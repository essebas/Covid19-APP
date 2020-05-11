package co.sebasdeveloper.pruebaservinformacion.remote;

import co.sebasdeveloper.pruebaservinformacion.model.ResponseModel;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface LocationDataService {

    @GET("countries")
    Single<ResponseModel> getCountriesLocationData();

    @GET("provinces")
    Single<ResponseModel> getProvincesLocationData();

}
