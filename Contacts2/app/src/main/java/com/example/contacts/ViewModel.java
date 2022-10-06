package com.example.contacts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    MutableLiveData<List<Data>> list = new MutableLiveData<>();
    List<Data> data = new ArrayList<>();

    public ViewModel(@NonNull Application application) {
        super(application);
        MyDB db = new MyDB(getApplication());
        data = db.GetContacts();
        list.postValue(data);
    }
    public void add_data(String n,String P,String E){
        data.add(new Data(n,P,E));
        list.postValue(data);
    }
    public LiveData<List<Data>> getLiveData(){return list;}
}
