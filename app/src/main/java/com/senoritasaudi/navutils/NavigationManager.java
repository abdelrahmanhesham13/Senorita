package com.senoritasaudi.navutils;

import android.content.Context;
import android.content.Intent;

public class NavigationManager {

    public static void startActivity(Context context , Class<?> activity) {
        context.startActivity(new Intent(context,activity));
    }

}
