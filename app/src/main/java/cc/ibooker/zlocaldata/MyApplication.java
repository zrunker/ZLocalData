package cc.ibooker.zlocaldata;

import android.app.Application;

import cc.ibooker.localdatalib.LocalDataLib;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocalDataLib.init(this);
    }
}
