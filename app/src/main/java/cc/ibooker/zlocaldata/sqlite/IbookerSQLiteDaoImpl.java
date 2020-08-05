package cc.ibooker.zlocaldata.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cc.ibooker.localdatalib.sqlite.SQLiteHelper;

/**
 * 数据库访问接口实现类
 * <p>
 * Created by 邹峰立 on 2017/2/16 0016.
 */
public class IbookerSQLiteDaoImpl implements IbookerSQLiteDao {
    protected SQLiteHelper dbHelper;

    // 无参构造方法，一定要手动执行initSQLiteHelper，用来继承实现
    public IbookerSQLiteDaoImpl() {
        IbookerSQLiteContants.addSQL();
    }

    public IbookerSQLiteDaoImpl(Context context) {
        IbookerSQLiteContants.addSQL();
        initSQLiteHelper(context);
    }

    @Override
    public void initSQLiteHelper(Context context) {
        dbHelper = SQLiteHelper.getSqliteHelper(context);
    }

    @Override
    public void resetSQLiteHelper(Context context) {
        if (dbHelper != null)
            dbHelper = null;
        initSQLiteHelper(context);
    }

    @Override
    public void deleteDbTables() {
        // 获取一个可写的数据库
        SQLiteDatabase db = dbHelper.openDatabase();
        // 删除数据表
        db.execSQL(IbookerSQLiteContants.SQL_DROP_USER_TABLE);
        dbHelper.closeDatabase();
    }
}
