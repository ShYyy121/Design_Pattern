package model.Command;

import model.Dao;
import model.Student;

public class Alter implements Order{
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Alter(Student student) {
        this.student = student;
    }

    @Override
    public boolean execute() {
        Dao dao=Dao.getInstance();
        return dao.reduce(student.getNum(),student.getName(),false);
    }
}
