package cc.ibooker.localdatalib.files;

import android.app.Application;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import cc.ibooker.localdatalib.LocalDataLib;
import cc.ibooker.localdatalib.constances.LdConstants;

/**
 * Assets管理类
 *
 * @author 邹峰立
 */
public class AssetsUtil {
    private static AssetsUtil assetsUtil;

    public static AssetsUtil getInstance() {
        if (assetsUtil == null)
            synchronized (AssetsUtil.class) {
                assetsUtil = new AssetsUtil();
            }
        return assetsUtil;
    }

    /**
     * 将数据写入Assets
     *
     * @param obj 待保存数据
     */
    public synchronized boolean writeAssets(Object obj) {
        return writeAssets(LdConstants.LOCALDATA_COMMON_ASSETS_KEY, LdConstants.LOCALDATA_COMMON_ASSETS_NAME, "", obj);
    }

    /**
     * 将数据写入Assets
     *
     * @param key Assets KEY
     * @param obj 待保存数据
     */
    public synchronized boolean writeAssets(String key, Object obj) {
        return writeAssets(key, LdConstants.LOCALDATA_COMMON_ASSETS_NAME, "", obj);
    }

    /**
     * 将数据写入Assets
     *
     * @param key        Assets KEY
     * @param assetsName Assets 名称
     * @param obj        待保存数据
     */
    public synchronized boolean writeAssets(String key, String assetsName, Object obj) {
        return writeAssets(key, assetsName, "", obj);
    }

    /**
     * 将数据写入Assets
     *
     * @param key        Assets KEY
     * @param assetsName Assets 名称
     * @param comments   Assets 描述
     * @param obj        待保存数据
     */
    public synchronized boolean writeAssets(String key, String assetsName, String comments, Object obj) {
        boolean bool = false;
        Application application = LocalDataLib.getApplication();
        if (application != null && obj != null) {
            FileOutputStream fos = null;
            try {
                // 构建Properties
                Properties properties = new Properties();
                // Properties添加数据
                properties.put(key, obj);
                fos = application.openFileOutput(assetsName, Context.MODE_PRIVATE);
                // 将数据写入文件（流）
                properties.store(fos, comments);
                bool = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null)
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        return bool;
    }

    /**
     * 读取Assets中数据
     *
     * @param key Assets KEY
     */
    public synchronized Object readAssets(String key) {
        return readAssets(key, LdConstants.LOCALDATA_COMMON_ASSETS_NAME);
    }

    /**
     * 读取Assets中数据
     *
     * @param key        Assets KEY
     * @param assetsName Assets 名称
     */
    public synchronized Object readAssets(String key, String assetsName) {
        Application application = LocalDataLib.getApplication();
        Object obj = null;
        if (application != null) {
            FileInputStream fis = null;
            try {
                // 构建Properties
                Properties properties = new Properties();
                fis = application.openFileInput(assetsName);
                // 加载文件
                properties.load(fis);
                obj = properties.get(key);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null)
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        return obj;
    }

}
