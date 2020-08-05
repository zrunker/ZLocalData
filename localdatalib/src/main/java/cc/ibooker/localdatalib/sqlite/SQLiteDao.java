package cc.ibooker.localdatalib.sqlite;

import android.content.Context;

/**
 * 数据库访问接口
 * Created by 邹封立 on 2017/2/16 0016.
 */
public interface SQLiteDao {

    /**
     * 初始化SQLiteHelper
     *
     * @param context 上下文对象
     */
    void initSQLiteHelper(Context context);

    /**
     * 重置SQLiteHelper
     *
     * @param context 上下文对象
     */
    void resetSQLiteHelper(Context context);

    /**
     * 删除数据库表
     */
    void deleteDbTables();
}
