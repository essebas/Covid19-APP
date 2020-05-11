package co.sebasdeveloper.pruebaservinformacion.viewmodel;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.sebasdeveloper.pruebaservinformacion.model.LocationDataModel;
import co.sebasdeveloper.pruebaservinformacion.model.ResponseModel;
import co.sebasdeveloper.pruebaservinformacion.repository.LocationDataRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocationDataViewModel extends ViewModel {

    private LocationDataRepository locationDataRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<ResponseModel> modelMutableLiveData;
    private static final String TAG = "LocationDataViewModel";

    @Inject
    public LocationDataViewModel(LocationDataRepository locationDataRepository) {
        this.locationDataRepository = locationDataRepository;
    }

    public MutableLiveData<ResponseModel> getModelMutableLiveData(String countryCode) {
        if(modelMutableLiveData == null){
            modelMutableLiveData = new MutableLiveData<ResponseModel>();
            loadData(countryCode);
        }
        return modelMutableLiveData;
    }

    public void loadData(String countryCode){
        disposable.add(locationDataRepository.modelSingleProvinces()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<ResponseModel>(){
                @Override
                public void onSuccess(ResponseModel responseModel) {
                    List<LocationDataModel> temp = responseModel.getData();
                    responseModel.setData(searchByCountry(temp,countryCode));
                    getModelMutableLiveData(countryCode).setValue(responseModel);
                    Log.d(TAG, "Respuesta: " + responseModel.getData().size());
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "Error caused by: " + e.getMessage());
                }
            })
        );
    }

    public List<LocationDataModel> searchByCountry(List<LocationDataModel> listData, String countryCode){
        List<LocationDataModel> filterData = new ArrayList<>();
        for (LocationDataModel data : listData) {
            if(data.getCountry_code().equals(countryCode)){
                filterData.add(data);
            }
        }
        return filterData;
    }
}


