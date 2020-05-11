package co.sebasdeveloper.pruebaservinformacion.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.sebasdeveloper.pruebaservinformacion.R;
import co.sebasdeveloper.pruebaservinformacion.adapter.ListAdapter;
import co.sebasdeveloper.pruebaservinformacion.model.LocationDataModel;
import co.sebasdeveloper.pruebaservinformacion.model.ResponseModel;
import co.sebasdeveloper.pruebaservinformacion.viewmodel.LocationDataViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryListFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private LocationDataViewModel locationDataViewModel;
    private ListAdapter listAdapter;

    public CountryListFragment(LocationDataViewModel locationDataViewModel) {
        this.locationDataViewModel = locationDataViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_country_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_country_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        listAdapter = new ListAdapter(getContext());
        recyclerView.setAdapter(listAdapter);
        locationDataViewModel.getModelMutableLiveData("es").observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                listAdapter.setArrayList((ArrayList<LocationDataModel>) responseModel.getData());
            }
        });
        return view;
    }
}
