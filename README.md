# ZLocalData
静态常量操作管理，LRUcache内存数据操作管理，SP数据操作管理，Assets文件数据操作管理，File文件操作管理，SQLite数据操作管理。

#### 类别：功能组件

#### 功能：本地数据管理

#### 名称：
  localdatalib（功能+lib）
  ApplicationId：com.dayi56.android.localdatalib（com.dayi56.android.名称小写）

#### 支持：
静态常量/内存/SP/Assets/SQLite/File

#### 混淆：
```

-keep class com.dayi56.android.localdatalib.dto.** { *; }
```

#### 引用
在build.gradle中添加如下代码：
```
implementation project(':localdatalib')
```

#### 用法
在Application中进行初始化即可：（两种方式）
```
// 初始化
LocalDataLib.init(Application application);
```

#### 推荐用法：
一、常用方法：

```
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
```
二、SQLite：
```
/**
 * SQL语句常量
 *
 * @author 邹峰立
 */
public class VehicleSQLiteContants extends SQLiteConstant {
    static final String SQL_CREATE_USER_TABLE = "";
    static final String SQL_DROP_USER_TABLE = "";

    static void addSQL() {
        addSqlCreateTableList(SQL_CREATE_USER_TABLE);
        addSqlDropTableList(SQL_DROP_USER_TABLE);
    }
}


/**
 * SQL语句操作接口
 *
 * @author 邹峰立
 */
public interface VehicleSQLiteDao extends SQLiteDao {
}

/**
 * 数据库访问接口实现类
 * <p>
 * Created by 邹峰立 on 2017/2/16 0016.
 */
public class VehicleSQLiteDaoImpl implements VehicleSQLiteDao {
    private SQLiteHelper dbHelper;

    public VehicleSQLiteDaoImpl(Context context) {
        VehicleSQLiteContants.addSQL();
        initSQLiteHelper(context);
    }

    @Override
    public void initSQLiteHelper(Context context) {
        dbHelper = SQLiteHelper.getSqliteHelper(context);
    }

    @Override
    public void deleteDbTable() {
        // 获取一个可写的数据库
        SQLiteDatabase db = dbHelper.openDatabase();
        // 删除数据表
        db.execSQL(VehicleSQLiteContants.SQL_DROP_USER_TABLE);
        dbHelper.closeDatabase();
    }
}
```
三、File用户：
```
FileUtil类.
```
