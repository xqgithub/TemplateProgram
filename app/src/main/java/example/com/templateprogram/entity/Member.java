package example.com.templateprogram.entity;

/**
 * Created by admin on 2017/12/8.
 * 测试实体类
 */
public class Member {

    /**
     * Member : {"address":"东海风车村","email":"lufei@qq.com","id":0,"level":5,"name":"路飞","password":"123456"}
     */

    private MemberBean Member;

    public MemberBean getMember() {
        return Member;
    }

    public void setMember(MemberBean Member) {
        this.Member = Member;
    }

    public static class MemberBean {
        /**
         * address : 东海风车村
         * email : lufei@qq.com
         * id : 0
         * level : 5
         * name : 路飞
         * password : 123456
         */

        private String address;
        private String email;
        private int id;
        private int level;
        private String name;
        private String password;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
