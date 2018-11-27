package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.database.DBHelper;
import example.com.templateprogram.entity.User;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.StringUtils;
import example.com.templateprogram.utils.ToastUtils;

/**
 * Created by beijixiong on 2018/11/25.
 * GreenDao数据库框架测试
 */

public class TestGreenDaoActivity extends BaseActivity {


    private Button add;
    private Button deleteall;
    private Button update;
    private Button select;
    private Button addlist;
    private Button delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        add = findViewById(R.id.add);
        deleteall = findViewById(R.id.deleteall);
        update = findViewById(R.id.update);
        select = findViewById(R.id.select);
        addlist = findViewById(R.id.addlist);
        delete = findViewById(R.id.delete);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_greendao;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                User mUser = new User();
                mUser.setId(99);
                mUser.setName("路飞");
                mUser.setSex("男");
                if (DBHelper.getInstance().insertOrReplace(mUser)) {
                    ToastUtils.showLongToast("单条数据插入成功");
                } else {
                    ToastUtils.showLongToast("单条数据插入失败");
                }

                break;
//            case R.id.addlist:
//                List<User> userList = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    User mUser2 = new User(null, i, "路飞=-=" + i, "男");
//                    userList.add(mUser2);
//                }
//                DBHelper.getInstance().getUserDao().insertInTx(userList);
//                break;
            case R.id.delete:

                break;
            case R.id.deleteall:
                //删除所有数据
                DBHelper.getInstance().deleteAll(User.class);
                break;
//            case R.id.update:
//                //修改数据
//                QueryBuilder qb3 = DBHelper.getInstance().getUserDao().queryBuilder();
//                List<User> userlist3 = qb3.where(UserDao.Properties.Name.eq("路飞")).list();
//                if (userlist3.size() > 0) {
//                    for (User user : userlist3) {
//                        user.setName("索隆");
//                        DBHelper.getInstance().getUserDao().update(user);
//                    }
//                    ToastUtils.showLongToast("修改数据成功");
//                } else {
//                    ToastUtils.showLongToast("没有要修改的数据");
//                }
//                break;
            case R.id.select:
                User user = DBHelper.getInstance().selectByPrimaryKey(User.class, 2l);
                if (!StringUtils.isBlank(user)) {
                    LogUtils.i(user.getPrimaryid());
                    LogUtils.i(user.getId());
                    LogUtils.i(user.getName());
                    LogUtils.i(user.getSex());
                }
                break;
            default:
        }
    }
}
