package cc.ibooker.localdatalib.sqlite;

import java.util.HashSet;
import java.util.Set;

/**
 * SQLite常量类
 * Created by 邹峰立 on 2017/2/16 0016.
 */
public class SQLiteConstant {
    static final String DB_NAME = "dayi56.db"; //数据库名称
    static final int DB_VERSION = 2; //数据库版本号

    // 创建表集合
    static Set<String> sqlCreateTableList = new HashSet<>();
    // 删除表集合
    static Set<String> sqlDropTableList = new HashSet<>();

    public static Boolean addSqlCreateTableList(String sqlCreateTable) {
        return sqlCreateTableList.add(sqlCreateTable);
    }

    public static Boolean addSqlDropTableList(String sqlDropTable) {
        return sqlDropTableList.add(sqlDropTable);
    }
}
