package example.com.templateprogram.database;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by beijixiong on 2018/11/25.
 * greenDao
 * 接口
 */

public interface IDBHelper {
    /**
     * 00001-1
     * 插入单条数据
     */
    boolean insert(Object object);


    /**
     * 00001-2
     * 插入或者修改
     */
    boolean insertOrReplace(Object object);

    /**
     * 00002-1
     * 删除单条数据
     */
    boolean delete(Object object);

    /**
     * 00002-2
     * 删除单条数据，根据主键的值
     */
    boolean deleteByKey(Object object, long key);

    /**
     * 00002-5
     * 删除全局数据，清空表
     */
    boolean deleteAll(Class<? extends Object> classType);

    /**
     * 00003-1
     * 修改单条数据
     */
    boolean update(Object object);

    /**
     * 00004-1
     * 查询单条数据，根据主键的值
     */
    <T> T selectByPrimaryKey(Class<T> classType, long key);

    /**
     * 00004-2
     * 查询整表数据
     */
    <T> List<T> loadAll(Class<T> classType);

    /**
     * 00004-3
     * 自定义查询
     */
    <T> List<T> getQueryRaw(Class<T> classType, String where, String... selectionArg);

    /**
     * 00004-4
     * QueryBuilder 查询
     */
    <T> QueryBuilder<T> getQueryBuilder(Class<T> classType);
//
//
//    /**
//     * 00005
//     * 清理缓存
//     */
//    void clearDaoSession();
//
//
//    /**
//     * 00006
//     * Delete all tables and content from our database
//     */
//    boolean dropDatabase();
//
//
//    /**
//     * 00007
//     * 事务
//     */
//    void runInTx(Runnable runnable);
//
//    boolean refresh(M m);


}
