package cc.ibooker.localdatalib.sharedps;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import cc.ibooker.localdatalib.LocalDataLib;
import cc.ibooker.localdatalib.constances.LdConstants;

import static android.content.Context.MODE_MULTI_PROCESS;

/**
 * SharedPreferences管理类 - 对跨进程处理不是很好
 *
 * @author 邹峰立
 */
@Deprecated
public class SharedPreferencesUtil {
    private HashMap<String, SharedPreferences> mapCache = new HashMap<>();
    private static SharedPreferencesUtil sharedPreferencesUtil;

    public static SharedPreferencesUtil getInstance() {
        if (sharedPreferencesUtil == null)
            synchronized (SharedPreferencesUtil.class) {
                sharedPreferencesUtil = new SharedPreferencesUtil();
            }
        return sharedPreferencesUtil;
    }

    // 创建SharedPreferences
    public SharedPreferences createSharedPreferences(@NonNull final String name, int mode) {
        if (mode != Context.MODE_PRIVATE && mode != Context.MODE_APPEND && mode != MODE_MULTI_PROCESS)
            return null;
        Application application = LocalDataLib.getApplication();
        SharedPreferences sharedPreferences = mapCache.get(name);
        if (sharedPreferences == null && application != null) {
            sharedPreferences = application.getSharedPreferences(name, mode);
            mapCache.put(name, sharedPreferences);
        }
        return sharedPreferences;
    }

    // 保存数据
    private boolean saveData(@NonNull final SharedPreferences.Editor editor, @NonNull final String key, @NonNull final Object obj) {
        if (obj instanceof String)
            editor.putString(key, obj.toString());
        if (obj instanceof Boolean)
            editor.putBoolean(key, (Boolean) obj);
        if (obj instanceof Integer)
            editor.putInt(key, (Integer) obj);
        if (obj instanceof Float)
            editor.putFloat(key, (Float) obj);
        if (obj instanceof Long)
            editor.putLong(key, (Long) obj);
        return editor.commit();
    }

    /**
     * 保存Object数据
     *
     * @param key 键值
     * @param obj 待保存的值
     */
    public boolean putObject(@NonNull final String key, @NonNull final Object obj) {
        SharedPreferences sharedPreferences = createSharedPreferences(
                LdConstants.LOCALDATA_COMMON_SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null)
            return saveData(sharedPreferences.edit(), key, obj);
        return false;
    }

    /**
     * 保存Object数据
     *
     * @param name sp名称
     * @param key  键值
     * @param obj  待保存的值
     */
    public boolean putObject(@NonNull final String name, @NonNull final String key, @NonNull final Object obj) {
        SharedPreferences sharedPreferences = createSharedPreferences(
                name, Context.MODE_PRIVATE);
        if (sharedPreferences != null)
            return saveData(sharedPreferences.edit(), key, obj);
        return false;
    }

    /**
     * 保存Object数据
     *
     * @param key  键值
     * @param obj  待保存的值
     * @param mode sp模式
     */
    public boolean putObject(@NonNull final String key, @NonNull final Object obj, int mode) {
        SharedPreferences sharedPreferences = createSharedPreferences(
                LdConstants.LOCALDATA_COMMON_SHAREDPREFERENCES_NAME, mode);
        if (sharedPreferences != null)
            return saveData(sharedPreferences.edit(), key, obj);
        return false;
    }

    /**
     * 保存Object数据
     *
     * @param name sp名称
     * @param key  键值
     * @param obj  待保存的值
     * @param mode sp模式
     */
    public boolean putObject(@NonNull final String name, @NonNull final String key, @NonNull final Object obj, int mode) {
        SharedPreferences sharedPreferences = createSharedPreferences(
                name, mode);
        if (sharedPreferences != null)
            return saveData(sharedPreferences.edit(), key, obj);
        return false;
    }

    /**
     * 获取Object数据
     *
     * @param key 键值
     */
    public Object getObject(@NonNull final String key) {
        for (Map.Entry<String, ?> entry : mapCache.entrySet()) {
            SharedPreferences sharedPreferences = (SharedPreferences) entry.getValue();
            Map<String, ?> map = sharedPreferences.getAll();
            Object obj = map.get(key);
            if (obj != null)
                return obj;
        }
        return null;
    }

    // 获取SharedPreferences中所有数据
    public Map<String, ?> readSharedPreferences(@NonNull final String name, int mode) {
        if (mode != Context.MODE_PRIVATE && mode != Context.MODE_APPEND && mode != MODE_MULTI_PROCESS)
            return null;
        Application application = LocalDataLib.getApplication();
        if (application != null) {
            SharedPreferences sharedPreferences = application.getSharedPreferences(name, mode);
            return sharedPreferences.getAll();
        }
        return null;
    }

    // 保存数据到SharedPreferences
    public boolean saveSharedPreferences(@NonNull final String name, @NonNull final Map<String, ?> map) {
        return saveSharedPreferences(name, Context.MODE_PRIVATE, map);
    }

    // 保存数据到SharedPreferences
    public boolean saveSharedPreferences(@NonNull final String name, int mode, @NonNull final Map<String, ?> map) {
        SharedPreferences sharedPreferences = createSharedPreferences(name, mode);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                Object obj = entry.getValue();
                if (obj instanceof String)
                    editor.putString(entry.getKey(), obj.toString());
                if (obj instanceof Boolean)
                    editor.putBoolean(entry.getKey(), (Boolean) obj);
                if (obj instanceof Integer)
                    editor.putInt(entry.getKey(), (Integer) obj);
                if (obj instanceof Float)
                    editor.putFloat(entry.getKey(), (Float) obj);
                if (obj instanceof Long)
                    editor.putLong(entry.getKey(), (Long) obj);
            }
            return editor.commit();
        }
        return false;
    }

    // 清空所有SharedPreference
    public void clearAllSp() {
        if (mapCache != null && mapCache.size() > 0) {
            for (HashMap.Entry<String, SharedPreferences> entry : mapCache.entrySet()) {
                SharedPreferences sharedPreferences = mapCache.get(entry.getKey());
                if (sharedPreferences != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }
            }
            mapCache.clear();
        }
    }

    // 清空指定SharedPreferences
    public void removeSpByKey(@NonNull final String key) {
        if (mapCache != null && mapCache.size() > 0) {
            for (HashMap.Entry<String, SharedPreferences> entry : mapCache.entrySet()) {
                if (entry.getKey().equals(key)) {
                    SharedPreferences sharedPreferences = mapCache.get(entry.getKey());
                    if (sharedPreferences != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        mapCache.remove(entry.getKey());
                        break;
                    }
                }
            }
        }
    }

}
