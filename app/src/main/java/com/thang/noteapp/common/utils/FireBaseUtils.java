package com.thang.noteapp.common.utils;

import com.google.firebase.database.DatabaseReference;

public class FireBaseUtils {

    public void insert(String key, DatabaseReference reference, Object object){
        reference.child(key).setValue(object);
    }

    public void update(String key, DatabaseReference reference, Object object) {
        assert key != null;
        reference.child(key).setValue(object);
    }

    public void delete(String key, DatabaseReference reference) {
        assert key != null;
        reference.child(key).removeValue();
    }

}
