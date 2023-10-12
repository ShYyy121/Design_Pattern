package model.Command;

import com.alibaba.fastjson2.JSON;
import model.Dao;
import model.Student;

public class Reduce implements Order{
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Reduce(Student student) {
        this.student = student;
    }

    @Override
    public boolean execute() {
        Dao dao=Dao.getInstance();
        return dao.write(JSON.toJSONString(student),false);
    }
}
