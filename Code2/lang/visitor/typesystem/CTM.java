package lang.visitor.typesystem;

import java.util.Stack;
import java.util.ArrayList;
import java.util.LinkedList;

public class CTM {
  
  private Stack<LinkedList<Constraint>> ct;
    
  
  public CTM(){
      ct = new Stack<LinkedList<Constraint>>();
      ct.push(new LinkedList<Constraint>());
  }
    
  public void tempConstraint(){ ct.push(new LinkedList<Constraint>());}
  
  public void addConstraint(Constraint c){ ct.peek().add(c);}
  
  public void discardTempConstraint(){
       if(ct.size() > 1){
           ct.pop();
       }
  }
  
  public void consolidateConstraint(){ 
       if(ct.size() > 1){
          LinkedList<Constraint> ct1 = ct.pop();
          ct.peek().addAll(ct1);
       }
  }
  
  public String toString(){
      String s = "";
      for(Constraint c : ct.peek()){
          s += c.toString() + "\n";
     }
     return s;
  }

}
