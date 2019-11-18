package cc.ibooker.localdatalib.constances;

import android.os.Build;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态常量管理类
 *
 * @author 邹峰立
 */
public class LdStaticConstantUtil {
    private static LdStaticConstantUtil LdStaticConstantUtil;
    private static Map<String, Object> map;

    // 单列构造方法
    public static LdStaticConstantUtil getInstance() {
        if (LdStaticConstantUtil == null)
            synchronized (LdStaticConstantUtil.class) {
                LdStaticConstantUtil = new LdStaticConstantUtil();
            }
        return LdStaticConstantUtil;
    }

    // 初始化Map
    private synchronized Map<String, Object> initMap() {
        if (map == null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                map = new ArrayMap<>();
            else
                map = new HashMap<>();
        return map;
    }

    /**
     * 清空数据
     */
    public synchronized LdStaticConstantUtil clearData() {
        initMap().clear();
        return this;
    }

    /**
     * 移除数据
     *
     * @param key 键值
     */
    public synchronized LdStaticConstantUtil removeData(String key) {
        initMap().remove(key);
        return this;
    }

    /**
     * 添加数据
     *
     * @param key  键值
     * @param data 待保存数据
     */
    public synchronized LdStaticConstantUtil addData(String key, Object data) {
        initMap().put(key, data);
        return this;
    }

    /**
     * 替换数据
     *
     * @param key  键值
     * @param data 待保存数据
     */
    public synchronized LdStaticConstantUtil replace(String key, Object data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            initMap().replace(key, data);
        else
            addData(key, data);
        return this;
    }

    /**
     * 根据key获取数据
     *
     * @param key 键值
     */
    public synchronized Object getData(String key) {
        return initMap().get(key);
    }

}
