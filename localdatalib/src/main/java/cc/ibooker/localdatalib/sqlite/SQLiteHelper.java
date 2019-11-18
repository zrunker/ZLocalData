package cc.ibooker.localdatalib.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * OpenHelper作用：
 * 1.提供onCreate onUpgrade等创建数据库和更新数据库方法
 * 2.提供了获取数据库对象的函数
 * Created by 邹峰立 on 2017/2/16 0016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private AtomicInteger mOpenCounter = new AtomicInteger(0);
    private SQLiteDatabase mDatabase;

    private static SQLiteHelper SQLiteHelper;

    /**
     * 获取SQLiteHelper，单例模式
     *
     * @param context 上下文对象
     */
    public static synchronized SQLiteHelper getSqliteHelper(Context context) {
        if (SQLiteHelper == null)
            SQLiteHelper = new SQLiteHelper(context);
        return SQLiteHelper;
    }

    /**
     * 打开数据库
     */
    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = SQLiteHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * 关闭数据库
     */
    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }

    /**
     * 构造函数
     *
     * @param context 上下文对象
     * @param name    创建数据库的名称
     * @param factory 游标工厂
     * @param version 创建数据库版本 >= 1
     */
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private SQLiteHelper(Context context) {
        // 创建数据库
        super(context, SQLiteConstant.DB_NAME, null, SQLiteConstant.DB_VERSION);
    }

    /**
     * 当数据库创建时回调的函数
     *
     * @param db 数据库对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据表
        Set<String> sqlCreateTableList = SQLiteConstant.sqlCreateTableList;
        for (String str : sqlCreateTableList)
            if (!TextUtils.isEmpty(str))
                db.execSQL(str);
    }

    /**
     * 当数据库版本更新的时候回调函数
     *
     * @param db         数据库对象
     * @param oldVersion 数据库旧版本
     * @param newVersion 数据库新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// 升级数据库
        if (oldVersion < newVersion) {
            db.beginTransaction();
            try {
                // 删除数据表
                Set<String> sqlDropTableList = SQLiteConstant.sqlDropTableList;
                for (String str : sqlDropTableList)
                    if (!TextUtils.isEmpty(str))
                        db.execSQL(str);
                // 创建数据表
                Set<String> sqlCreateTableList = SQLiteConstant.sqlCreateTableList;
                for (String str : sqlCreateTableList)
                    if (!TextUtils.isEmpty(str))
                        db.execSQL(str);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
}
