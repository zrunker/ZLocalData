package cc.ibooker.zlocaldata;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Map;

import cc.ibooker.localdatalib.LocalDataLib;
import cc.ibooker.localdatalib.caches.LruCacheUtil;
import cc.ibooker.localdatalib.constances.LdStaticConstantUtil;
import cc.ibooker.localdatalib.sharedps.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 清空本地数据
        LocalDataLib.clearAllData();

        // 读取Assets文件中数据
        LocalDataLib.readAssetsData();

        // 保存数据到Assets文件
        LocalDataLib.saveAssetsData("书客创作");

        // 读取SP中数据
        LocalDataLib.readSpData("ibooker");

        // 保存数据到SP中
        LocalDataLib.saveSpData("ibooker", "书客创作");

        /**
         * 操作静态数据
         */
        // 保存静态数据
        LdStaticConstantUtil.getInstance().addData("ibooker", "书客创作");

        // 读取静态数据
        LdStaticConstantUtil.getInstance().getData("ibooker");

        // 替换静态数据
        LdStaticConstantUtil.getInstance().replace("ibooker", "书客编辑器");

        // 移除静态数据
        LdStaticConstantUtil.getInstance().removeData("ibooker");

        // 清空静态数据
        LdStaticConstantUtil.getInstance().clearData();

        /**
         * 操作内存数据
         */
        // 存入数据到内存
        LruCacheUtil.getInstance().putObject("ibooker", "书客创作");

        // 获取内存数据
        LruCacheUtil.getInstance().getObject("ibooker");

        // 移除内存数据
        LruCacheUtil.getInstance().removeObject("ibooker");

        // 清空内存数据
        LruCacheUtil.getInstance().clear();

        /**
         * 操作SP数据
         */
        // 保存SP数据
        SharedPreferencesUtil.getInstance().putObject("ibooker", "书客创作");

        // 获取SP数据
        SharedPreferencesUtil.getInstance().getObject("ibooker");

        // 移除SP数据
        SharedPreferencesUtil.getInstance().removeSpByKey("ibooker");

        // 清空SP数据
        SharedPreferencesUtil.getInstance().clearAllSp();

        // 更加文件名读取SP内所有数据
        Map<String, ?> map = SharedPreferencesUtil.getInstance().readSharedPreferences("ibookerName", Context.MODE_PRIVATE);

        // 保存MAP到SP
        SharedPreferencesUtil.getInstance().saveSharedPreferences("ibookerName", map);
    }
}
