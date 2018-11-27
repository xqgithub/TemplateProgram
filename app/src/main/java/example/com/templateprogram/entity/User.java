package example.com.templateprogram.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by beijixiong on 2018/11/25.
 * 实体类
 */

@Entity//表明该类是持久化的类【持久化含义，存入数据库文件中，作本地化处理】
public class User {
    @Id
    private Long primaryid;
    //    @Transient//指定GreenDao忽略此变量
    private Integer id;
    @Property(nameInDb = "USERNAME")//Property 列名 nameInDb制定具体列名
    private String name;
    @Property(nameInDb = "SEX")
    private String sex;

    @Generated(hash = 99915328)
    public User(Long primaryid, Integer id, String name, String sex) {
        this.primaryid = primaryid;
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(Long primaryid) {
        this.primaryid = primaryid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
