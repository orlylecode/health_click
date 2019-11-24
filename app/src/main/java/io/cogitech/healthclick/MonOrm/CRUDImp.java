package io.cogitech.healthclick.MonOrm;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class CRUDImp implements CRUD {

    @Override
    public boolean save(Context context, String attribu, String value) {
        try {
            SharedPreferences settings = context.getSharedPreferences(DataBaseConfig.DATABASE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(attribu, value);
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String find(Context context, String attribu) {
        SharedPreferences settings = context.getSharedPreferences(DataBaseConfig.DATABASE_NAME, MODE_PRIVATE);
        return settings.getString(attribu, "");
    }
}
