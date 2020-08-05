package cc.ibooker.localdatalib.sharedps;

import android.support.annotation.NonNull;

import net.grandcentrix.tray.AppPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;

import cc.ibooker.localdatalib.LocalDataLib;

/**
 * https://github.com/grandcentrix/tray
 * SP处理类，支持跨进程
 *
 * @author 邹峰立
 */
public class TraySpUtil {
    private static TraySpUtil traySpUtil;

    public static TraySpUtil getInstance() {
        if (traySpUtil == null)
            synchronized (TraySpUtil.class) {
                traySpUtil = new TraySpUtil();
            }
        return traySpUtil;
    }

    /**
     * 保存Object数据
     *
     * @param key 键值
     * @param obj 待保存的值
     */
    public boolean putObject(@NonNull final String key, @NonNull final Object obj) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        if (obj instanceof String)
            appPreferences.put(key, obj.toString());
        if (obj instanceof Boolean)
            appPreferences.put(key, (Boolean) obj);
        if (obj instanceof Integer)
            appPreferences.put(key, (Integer) obj);
        if (obj instanceof Float)
            appPreferences.put(key, (Float) obj);
        if (obj instanceof Long)
            appPreferences.put(key, (Long) obj);
        return false;
    }

    /**
     * 获取Object数据
     *
     * @param key 键值
     */
    public Object getObject(@NonNull final String key) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.getString(key, null);
    }

    /**
     * 获取String数据
     *
     * @param key 键值
     */
    public String getString(@NonNull final String key) {
        String value = null;
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        try {
            value = appPreferences.getString(key);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取String数据
     *
     * @param key          键值
     * @param defaultValue 默认值
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.getString(key, defaultValue);
    }

    /**
     * 获取Boolean数据
     *
     * @param key 键值
     */
    public boolean getBoolean(@NonNull final String key) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        try {
            return appPreferences.getBoolean(key);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取Boolean数据
     *
     * @param key          键值
     * @param defaultValue 默认值
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 获取Integer数据
     *
     * @param key 键值
     */
    public int getInt(@NonNull final String key) {
        int value = 0;
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        try {
            value = appPreferences.getInt(key);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取Integer数据
     *
     * @param key          键值
     * @param defaultValue 默认值
     */
    public long getInt(@NonNull final String key, final int defaultValue) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.getInt(key, defaultValue);
    }

    /**
     * 获取Integer数据
     *
     * @param key 键值
     */
    public long getLong(@NonNull final String key) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        try {
            return appPreferences.getLong(key);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取Integer数据
     *
     * @param key          键值
     * @param defaultValue 默认值
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.getLong(key, defaultValue);
    }

    /**
     * 获取Float数据
     *
     * @param key 键值
     */
    public float getFloat(@NonNull final String key) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        try {
            return appPreferences.getFloat(key);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取Float数据
     *
     * @param key          键值
     * @param defaultValue 默认值
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.getFloat(key, defaultValue);
    }

    // 清空指定SharedPreferences
    public boolean removeSpByKey(@NonNull final String key) {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.remove(key);
    }

    // 清空所有SharedPreference
    public boolean clearAllSp() {
        AppPreferences appPreferences = new AppPreferences(LocalDataLib.getApplication());
        return appPreferences.clear();
    }
}
