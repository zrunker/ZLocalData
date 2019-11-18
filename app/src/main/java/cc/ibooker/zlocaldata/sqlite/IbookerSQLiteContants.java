package cc.ibooker.zlocaldata.sqlite;

import cc.ibooker.localdatalib.sqlite.SQLiteConstant;

/**
 * SQL语句常量
 *
 * @author 邹峰立
 */
public class IbookerSQLiteContants extends SQLiteConstant {
    static final String SQL_CREATE_USER_TABLE = "";
    static final String SQL_DROP_USER_TABLE = "";

    static void addSQL() {
        addSqlCreateTableList(SQL_CREATE_USER_TABLE);
        addSqlDropTableList(SQL_DROP_USER_TABLE);
    }
}
