package co.sebasdeveloper.pruebaservinformacion.repository;

import javax.inject.Inject;

import co.sebasdeveloper.pruebaservinformacion.model.ResponseModel;
import co.sebasdeveloper.pruebaservinformacion.remote.LocationDataService;
import io.reactivex.Single;

public class LocationDataRepository {

    private LocationDataService locationDataService;

    @Inject
    public LocationDataRepository(LocationDataService locationDataService) {
        this.locationDataService = locationDataService;
    }

    public Single<ResponseModel> modelSingleCountries(){
        return locationDataService.getCountriesLocationData();
    }

    public Single<ResponseModel> modelSingleProvinces(){
        return locationDataService.getProvincesLocationData();
    }

}

