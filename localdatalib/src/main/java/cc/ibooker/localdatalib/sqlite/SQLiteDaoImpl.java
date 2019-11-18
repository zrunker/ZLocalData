package cc.ibooker.localdatalib.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.Set;

/**
 * 数据库访问接口实现类
 * Created by 邹峰立 on 2017/2/16 0016.
 */
public class SQLiteDaoImpl implements SQLiteDao {
    private SQLiteHelper dbHelper;

    public SQLiteDaoImpl(Context context) {
        initSQLiteHelper(context);
    }

    @Override
    public void initSQLiteHelper(Context context) {
        dbHelper = SQLiteHelper.getSqliteHelper(context);
    }

    /**
     * 删除数据库表
     */
    @Override
    public synchronized void deleteDbTable() {
        // 获取一个可写的数据库
        SQLiteDatabase db = dbHelper.openDatabase();
        // 删除数据表
        Set<String> sqlDropTableList = SQLiteConstant.sqlDropTableList;
        for (String str : sqlDropTableList)
            if (!TextUtils.isEmpty(str))
                db.execSQL(str);
        dbHelper.closeDatabase();
    }
}
