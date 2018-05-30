package com.koalafield.cmart.db.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.koalafield.cmart.bean.HistoryContent;
import com.koalafield.cmart.bean.User;
import com.koalafield.cmart.db.dao.Column;
import com.koalafield.cmart.db.dao.Table;
import java.lang.reflect.Field;

/**
 * @author jiangrenming
 * 数据库
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "koala_mart.db";
    private static DbHelper helper;
    private static final int DB_VERSION = 1; // 数据库版本号

    public static DbHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (DbHelper.class) {
                if (helper == null) {
                    helper = new DbHelper(context);
                }
            }
        }
        return helper;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);//创建数据库
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(getCreateTableSql(HistoryContent.class));
            onUpgrade(db, 1, DB_VERSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库升级操作兼容性
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }

    public static String getCreateTableSql(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        String tableName = table.name();
        String idColumn = "";
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column c = field.getAnnotation(Column.class);
            if (c != null && c.primaryKey()) {
                idColumn = c.name();
                break;
            }
        }
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("CREATE TABLE IF NOT EXISTS ");
        sqlBuffer.append(tableName);
        sqlBuffer.append(" ( ");
        sqlBuffer.append("\"").append(idColumn).append("\"  ").append("INTEGER PRIMARY KEY AUTOINCREMENT,");
        fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column c = field.getAnnotation(Column.class);
            if (c != null && !c.primaryKey()) {
                sqlBuffer.append("\"").append(c.name()).append("\"  ");

                sqlBuffer.append(getDBColumnType(field));

                if (c.unique()) {
                    sqlBuffer.append(" UNIQUE");
                }
                sqlBuffer.append(",");
            }
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(" )");
        return sqlBuffer.toString();
    }

    private static String getDBColumnType(Field field) {
        Class<?> javaType = field.getType();
        if (javaType == Integer.class) {
            return "INTEGER";
        } else if (javaType == Long.class) {
            return "INTEGER";
        } else if (javaType == String.class) {
            return "VARCHAR(" + field.getAnnotation(Column.class).len() + ")";
        } else {
            return "VARCHAR(" + field.getAnnotation(Column.class).len() + ")";
        }
    }

    /**
     * 判断tableName表是否有columnName字段。
     * @param db
     * @param tableName
     * @param columnName
     * @return
     */
    private boolean checkColumnExist(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            //查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 判断流水表是否有columnName字段。
     *
     * @param db
     * @param columnName
     * @return
     */
    private boolean checkWaterColumnExist(SQLiteDatabase db, String columnName) {
        Log.d("是否存在字段" , columnName + "：" + checkColumnExist(db, "t_water", columnName));
        return checkColumnExist(db, "t_water", columnName);
    }
}
