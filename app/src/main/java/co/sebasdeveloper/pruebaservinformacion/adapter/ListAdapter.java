package co.sebasdeveloper.pruebaservinformacion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import co.sebasdeveloper.pruebaservinformacion.R;
import co.sebasdeveloper.pruebaservinformacion.databinding.DataBinding;
import co.sebasdeveloper.pruebaservinformacion.model.LocationDataModel;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListAdapterView>{

    private Context context;
    private LayoutInflater layoutInflater;
    private LocationDataModel locationDataModel;
    private ArrayList<LocationDataModel> arrayList;

    public ListAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }

    public ListAdapter(Context context, LocationDataModel locationDataModel) {
        this.context = context;
        this.locationDataModel = locationDataModel;
        this.arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        DataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.listlayout,parent,false);
        return new ListAdapterView(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterView holder, int position) {
        LocationDataModel locationDataModel = arrayList.get(position);
        holder.txv_rank.setText(String.valueOf(position+1));
        holder.bind(locationDataModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<LocationDataModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    class ListAdapterView extends RecyclerView.ViewHolder{

        private DataBinding dataBinding;
        private TextView txv_rank;

        public ListAdapterView(DataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
            iniatializeComponents();
        }

        public void bind(LocationDataModel locationDataModel){
            this.dataBinding.setData(locationDataModel);
            dataBinding.executePendingBindings();
        }

        public DataBinding getDataBinding() {
            return dataBinding;
        }

        private void iniatializeComponents(){
            this.txv_rank = dataBinding.getRoot().findViewById(R.id.txv_rank);
        }

    }

}
