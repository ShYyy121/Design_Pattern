package model.memento;

import model.Student;

public class Originator {
   private String state;
   private Student student;
   public void setState(String state,Student student){
      this.state = state;
      this.student=student;
   }

   public String getState(){
      return state;
   }
 
   public Memento saveStateToMemento(){
      return new Memento(state,student);
   }

}