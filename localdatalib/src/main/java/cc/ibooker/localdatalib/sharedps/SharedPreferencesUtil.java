package cc.ibooker.localdatalib.sharedps;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cc.ibooker.localdatalib.LocalDataLib;
import cc.ibooker.localdatalib.constances.LdConstants;

import static android.content.Context.MODE_MULTI_PROCESS;

/**
 * SharedPreferences管理类 - 对跨进程处理不是很好
 * SP 只能保存基本数据类型
 *
 * @author 邹峰立
 */
public class SharedPreferencesUtil {
    private HashMap<String, SharedPreferences> spCache = getSpCache();
    private static SharedPreferencesUtil sharedPreferencesUtil;

    public static SharedPreferencesUtil getInstance() {
        if (sharedPreferencesUtil == null)
            synchronized (SharedPreferencesUtil.class) {
                sharedPreferencesUtil = new SharedPreferencesUtil();
            }
        return sharedPreferencesUtil;
    }

    /**
     * 将JSON字符串转换成HashMap
     *
     * @param jsonStr 待转换JSON
     */
    private HashMap<String, SharedPreferences> getJsonToHashMap(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            HashMap<String, SharedPreferences> map = new HashMap<>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                map.put(key, (SharedPreferences) value);
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新spCache
    private HashMap<String, SharedPreferences> getSpCache() {
        try {
            if (spCache == null) {
                Application application = LocalDataLib.getApplication();
                if (application != null) {
                    SharedPreferences sharedPreferences = application.getSharedPreferences(LdConstants.LOCALDATA_COMMON_SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
                    String jsonStr = sharedPreferences.getString(LdConstants.LOCALDATA_SHAREDPREFERENCES_SPCACHE_KEY, null);
                    if (!TextUtils.isEmpty(jsonStr)) {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        spCache = getJsonToHashMap(jsonObject.optString(LdConstants.LOCALDATA_SHAREDPREFERENCES_SPCACHE_KEY));
                    }
                }
            }
            if (spCache == null) {
                spCache = new HashMap<>();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(LdConstants.LOCALDATA_SHAREDPREFERENCES_SPCACHE_KEY, spCache);
                putObject(LdConstants.LOCALDATA_SHAREDPREFERENCES_SPCACHE_KEY, jsonObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (spCache == null)
                spCache = new HashMap<>();
        }
        return spCache;
    }

    // 创建SharedPreferences
    public SharedPreferences createSharedPreferences(String name, int mode) {
        if (mode != Context.MODE_PRIVATE && mode != Context.MODE_APPEND && mode != MODE_MULTI_PROCESS)
            return null;
        Application application = LocalDataLib.getApplication();
        SharedPreferences sharedPreferences = spCache.get(name);
        if (sharedPreferences == null && application != null) {
            sharedPreferences = application.getSharedPreferences(name, mode);
            spCache.put(name, sharedPreferences);
        }
        return sharedPreferences;
    }

    // 保存数据
    private boolean saveData(SharedPreferences.Editor editor, String key, Object obj) {
        if (obj instanceof String)
            editor.putString(key, (String) obj);
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
    public boolean putObject(String key, Object obj) {
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
    public boolean putObject(String name, String key, Object obj) {
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
    public boolean putObject(String key, Object obj, int mode) {
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
    public boolean putObject(String name, String key, Object obj, int mode) {
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
    public Object getObject(String key) {
        if (spCache != null && spCache.size() > 0)
            for (Map.Entry<String, ?> entry : spCache.entrySet()) {
                SharedPreferences sharedPreferences = (SharedPreferences) entry.getValue();
                Map<String, ?> map = sharedPreferences.getAll();
                Object obj = map.get(key);
                if (obj != null)
                    return obj;
            }
        else {
            SharedPreferences sharedPreferences = createSharedPreferences(LdConstants.LOCALDATA_COMMON_SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
            Map<String, ?> map = sharedPreferences.getAll();
            Object obj = map.get(key);
            if (obj != null)
                return obj;
        }
        return null;
    }

    // 获取SharedPreferences中所有数据
    public Map<String, ?> readSharedPreferences(String name, int mode) {
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
    public boolean saveSharedPreferences(String name, Map<String, ?> map) {
        return saveSharedPreferences(name, Context.MODE_PRIVATE, map);
    }

    // 保存数据到SharedPreferences
    public boolean saveSharedPreferences(String name, int mode, Map<String, ?> map) {
        SharedPreferences sharedPreferences = createSharedPreferences(name, mode);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                Object obj = entry.getValue();
                if (obj instanceof String)
                    editor.putString(entry.getKey(), (String) obj);
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
        if (spCache != null && spCache.size() > 0) {
            for (HashMap.Entry<String, SharedPreferences> entry : spCache.entrySet()) {
                SharedPreferences sharedPreferences = spCache.get(entry.getKey());
                if (sharedPreferences != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }
            }
            spCache.clear();
        } else {
            SharedPreferences sharedPreferences = createSharedPreferences(LdConstants.LOCALDATA_COMMON_SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }

    // 清空指定SharedPreferences
    public void removeSpByKey(String key) {
        if (spCache != null && spCache.size() > 0) {
            for (HashMap.Entry<String, SharedPreferences> entry : spCache.entrySet()) {
                if (entry.getKey().equals(key)) {
                    SharedPreferences sharedPreferences = spCache.get(entry.getKey());
                    if (sharedPreferences != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        spCache.remove(entry.getKey());
                        break;
                    }
                }
            }
        } else {
            SharedPreferences sharedPreferences = createSharedPreferences(LdConstants.LOCALDATA_COMMON_SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }

}
