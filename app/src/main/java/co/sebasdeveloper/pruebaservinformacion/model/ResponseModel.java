package co.sebasdeveloper.pruebaservinformacion.model;

import java.util.List;

public class ResponseModel {

    private int code;
    private List<LocationDataModel> data;

    public ResponseModel() {
    }

    public ResponseModel(int code, List<LocationDataModel> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LocationDataModel> getData() {
        return data;
    }

    public void setData(List<LocationDataModel> data) {
        this.data = data;
    }
}
