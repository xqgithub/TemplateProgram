package example.com.templateprogram.test.bean;

import java.util.List;

/**
 * Created by beijixiong on 2019/3/29.
 */

public class RxJavaStudents {

    private String name;
    private int age;
    private List<Course> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static class Course {

        private String coursename;

        public String getCoursename() {
            return coursename;
        }

        public void setCoursename(String coursename) {
            this.coursename = coursename;
        }
    }


}
