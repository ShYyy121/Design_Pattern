package model.memento;

import model.Student;

public class Memento {
   private String state;
   private Student student;

   public void setState(String state) {
      this.state = state;
   }


   public Student getStudent() {


      return student;
   }




   public void setStudent(Student student) {
      this.student = student;
   }

   public Memento(String state, Student student) {
      this.state = state;
      this.student = student;
   }

   public String getState(){
      return state;
   }

}