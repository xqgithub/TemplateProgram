package example.com.templateprogram.database;

import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import example.com.templateprogram.DaoMaster;
import example.com.templateprogram.DaoSession;
import example.com.templateprogram.base.MyApp;
import example.com.templateprogram.utils.LogUtils;

/**
 * Created by beijixiong on 2018/11/25.
 * 数据库辅助类,简单的写法
 */

public class DBHelper implements IDBHelper {
    private static volatile DBHelper mInstance;
    private String password = "AskSky_TanPeiQi_1195211669_JMSQJ";
    //数据库的名称
    private static final String DBName = "TP";
    private DaoMaster.DevOpenHelper mOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (mInstance == null) {
            synchronized (DBHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 判断数据库是否存在，不存在就创建，也是初始化
     */
    public DaoMaster getmDaoMaster() {
        if (mDaoMaster == null) {
            // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
            // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
            // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
            // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
            mOpenHelper = new DaoMaster.DevOpenHelper(MyApp.getApplication(), DBName, null);
            SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            mDaoMaster = new DaoMaster(db);
        }
        return mDaoMaster;
    }

    /**
     * 获得DaoSession
     */
    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getmDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    /**
     * 输出日志
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }


    public void close() {
        closeHelper();
        closeSession();
    }

    /**
     * 关闭Helper
     */
    public void closeHelper() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
            mOpenHelper = null;
        }
    }

    /**
     * 关闭session
     */
    public void closeSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public <T> T getDao(Class<? extends Object> entityClass) {
        return (T) mDaoSession.getDao(entityClass);
    }


    @Override
    public boolean insert(Object object) {

        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insertInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insert(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(Object object) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insertOrReplaceInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).insertOrReplace(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Object object) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).deleteInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).delete(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(Object object, long key) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, Long>) getDao(cls)).deleteByKeyInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, Long>) getDao(cls)).deleteByKey(key);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll(Class<? extends Object> classType) {
        try {
            ((AbstractDao<Object, String>) getDao(classType)).deleteAll();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Object object) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls)).updateInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls)).update(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public <T> T selectByPrimaryKey(Class<T> classType, long key) {
        return ((AbstractDao<T, Object>) getDao(classType)).load(key);
    }

    @Override
    public <T> List<T> loadAll(Class<T> classType) {
        return ((AbstractDao<T, Object>) getDao(classType)).loadAll();
    }

    @Override
    public <T> List<T> getQueryRaw(Class<T> classType, String where, String... selectionArg) {
        return ((AbstractDao<T, Object>) getDao(classType)).queryRaw(where, selectionArg);
    }

    @Override
    public <T> QueryBuilder<T> getQueryBuilder(Class<T> classType) {
        return ((AbstractDao<T, Object>) getDao(classType)).queryBuilder();
    }
}
