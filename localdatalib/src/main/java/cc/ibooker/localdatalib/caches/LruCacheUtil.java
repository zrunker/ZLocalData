package cc.ibooker.localdatalib.caches;

import android.util.LruCache;

import java.util.ArrayList;

import static cc.ibooker.localdatalib.constances.LdConstants.LOCALDATA_LRUCACHE_KEYS;

/**
 * Object内存缓存类
 *
 * @author 邹峰立
 */
public class LruCacheUtil {
    private LruCache<String, Object> mLruCache;
    private static LruCacheUtil lruCacheUtil;

    public static LruCacheUtil getInstance() {
        if (lruCacheUtil == null) {
            synchronized (LruCacheUtil.class) {
                lruCacheUtil = new LruCacheUtil();
                lruCacheUtil.init();
            }
        }
        return lruCacheUtil;
    }

    // 初始化mLruCache
    private LruCacheUtil init() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();// 获取系统分配给应用的总内存大小
        int mCacheSize = maxMemory / 10;// 设置图片内存缓存占用十分之一
        mLruCache = new LruCache<>(mCacheSize);
        return this;
    }

    // 获取Object
    public Object getObject(String key) {
        if (mLruCache != null)
            return mLruCache.get(key);
        return null;
    }

    // 保存Object
    public void putObject(String key, Object obj) {
        if (mLruCache != null) {
            mLruCache.put(key, obj);
            // 保存key
            saveKey(key);
        }
    }

    // 移除Object
    public void removeObject(String key) {
        if (mLruCache != null) {
            mLruCache.remove(key);
            // 移除key
            removeKey(key);
        }
    }

    // 清空数据
    public void clear() {
        if (mLruCache != null) {
            ArrayList<String> keys = getKeys();
            if (keys != null && keys.size() > 0)
                for (String key : keys)
                    mLruCache.remove(key);
            mLruCache.remove(LOCALDATA_LRUCACHE_KEYS);
        }
    }

    /**
     * 获取所有Key集合
     */
    private ArrayList<String> getKeys() {
        ArrayList<String> keys = null;
        if (mLruCache != null) {
            Object obj = mLruCache.get(LOCALDATA_LRUCACHE_KEYS);
            if (obj != null)
                keys = (ArrayList<String>) obj;
        }
        return keys;
    }

    /**
     * 保存Key
     *
     * @param key 待保存的Key
     */
    private void saveKey(String key) {
        ArrayList<String> keys = getKeys();
        if (keys != null && keys.size() > 0)
            keys.add(key);
    }

    /**
     * 移除Key
     *
     * @param key 待移除的Key
     */
    private void removeKey(String key) {
        ArrayList<String> keys = getKeys();
        if (keys != null && keys.size() > 0)
            keys.remove(key);
    }
}
