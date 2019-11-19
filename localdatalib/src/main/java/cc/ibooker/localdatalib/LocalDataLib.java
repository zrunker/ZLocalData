package cc.ibooker.localdatalib;

import android.app.Application;
import android.text.TextUtils;

import cc.ibooker.localdatalib.caches.LruCacheUtil;
import cc.ibooker.localdatalib.constances.LdConstants;
import cc.ibooker.localdatalib.constances.LdStaticConstantUtil;
import cc.ibooker.localdatalib.files.AssetsUtil;
import cc.ibooker.localdatalib.files.FileUtil;
import cc.ibooker.localdatalib.sharedps.SharedPreferencesUtil;
import cc.ibooker.localdatalib.sqlite.SQLiteDaoImpl;

/**
 * 本地数据管理类
 *
 * @author 邹峰立
 */
public class LocalDataLib {
    private static Application application;

    // 初始化
    public static void init(Application context) {
        application = context;
    }

    // 获取Application
    public static Application getApplication() {
        return application;
    }

    /**
     * 从Assets中获取数据
     */
    public synchronized static Object readAssetsData() {
        // 从静态常量中获取
        Object obj = LdStaticConstantUtil.getInstance().getData(LdConstants.LOCALDATA_COMMON_ASSETS_KEY);
        // 从缓存中获取
        if (obj == null)
            obj = LruCacheUtil.getInstance().getObject(LdConstants.LOCALDATA_COMMON_ASSETS_KEY);
        // 从文件中获取
        if (obj != null)
            obj = AssetsUtil.getInstance().readAssets(LdConstants.LOCALDATA_COMMON_ASSETS_KEY, LdConstants.LOCALDATA_COMMON_ASSETS_NAME);
        return obj;
    }

    /**
     * 保存数据到Assets
     */
    public synchronized static boolean saveAssetsData(Object obj) {
        if (obj != null) {
            // 保存到静态常量中
            LdStaticConstantUtil.getInstance().addData(LdConstants.LOCALDATA_COMMON_ASSETS_KEY, obj);
            // 保存到缓存中
            LruCacheUtil.getInstance().putObject(LdConstants.LOCALDATA_COMMON_ASSETS_KEY, obj);
            // 保存到文件中
            AssetsUtil.getInstance().writeAssets(LdConstants.LOCALDATA_COMMON_ASSETS_KEY, LdConstants.LOCALDATA_COMMON_ASSETS_NAME, null, obj);
            return true;
        }
        return false;
    }

    /**
     * 保存数据到SP
     *
     * @param key   键值
     * @param value 待保存数据
     */
    public synchronized static boolean saveSpData(String key, Object value) {
        if (!TextUtils.isEmpty(key)) {
            // 保存到静态常量中
            LdStaticConstantUtil.getInstance().addData(key, value);
            // 保存到缓存中
            LruCacheUtil.getInstance().putObject(key, value);
            // 保存到Sp中
            SharedPreferencesUtil.getInstance().putObject(key, value);
        }
        return true;
    }

    /**
     * 获取SP中数据
     *
     * @param key 键值
     */
    public synchronized static Object readSpData(String key) {
        Object obj = null;
        if (!TextUtils.isEmpty(key)) {
            // 读取静态常量
            obj = LdStaticConstantUtil.getInstance().getData(key);
            // 读取缓存
            if (obj == null)
                obj = LruCacheUtil.getInstance().getObject(key);
            // 读取Sp
            if (obj == null)
                obj = SharedPreferencesUtil.getInstance().getObject(key);
        }
        return obj;
    }

    // 清空所有数据
    public static void clearAllData() {
        // 静态常量
        LdStaticConstantUtil.getInstance().clearData();
        // 缓存
        LruCacheUtil.getInstance().clear();
        // 清空SP
        SharedPreferencesUtil.getInstance().clearAllSp();
        // 清空Assets
        AssetsUtil.getInstance().writeAssets(LdConstants.LOCALDATA_COMMON_ASSETS_KEY, LdConstants.LOCALDATA_COMMON_ASSETS_NAME, null, "");
        // 删除所有文件
        FileUtil.deleteDir(FileUtil.ZFILEPATH);
        // 删除数据库
        new SQLiteDaoImpl(LocalDataLib.getApplication()).deleteDbTables();
    }
}
