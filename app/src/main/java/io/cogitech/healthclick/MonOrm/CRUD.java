package io.cogitech.healthclick.MonOrm;

import android.content.Context;

public interface CRUD {
    boolean save(Context context, String attribu, String value);

    String find(Context context, String attribu);
}
