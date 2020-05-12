package  lang.visitor.typesystem;

import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;
import lang.ast.*;
import java.util.*;


public class TypeChecker extends NodeVisitor {
  
  private Stack<VType> s;
  private Environment<String,VType> gamma;
  
  // x = x + y - 3 * 4
  //       ^      
  
  //
  //
  //
  // 
  // 
  // ->  Int
  
  public TypeChecker(){
      s = new Stack<VType>();
      gamma = new Environment<String,VType>();
  }
  
  public  void visit(Add e){
     e.getLeft().accept(this);
     e.getRight().accept(this);
     VType t1 = s.pop();
     VType t2 = s.pop();
     IntType inti = new IntType();
     if(t1.match(inti) && t2.match(inti)){
         s.push(init);
     }else{
         System.out.println("Error at " + e.getLine() + "," + e.getColumn()+ ". Type error");
         System.exit(1);
     }
  }
  
  public  void visit(Sub e){
     e.getLeft().accept(this);
     e.getRight().accept(this);
     VType t1 = s.pop();
     VType t2 = s.pop();
     IntType inti = new IntType();
     if(t1.match(inti) && t2.match(inti)){
         s.push(init);
     }else{
         System.out.println("Error at " + e.getLine() + "," + e.getColumn()+ ". Type error");
         System.exit(1);
     }

  }
  
  public  void visit(And e){

  }
  
  public  void visit(Not e){

  }
  
  public  void visit(Eq e){

  }
  
  public  void visit(Lt e){

  }
  
  public  void visit(Call e){

      
  }
  
  public  void visit(StrLit e){ s.push(new StringType()); }
  public  void visit(BoolLit e){s.push(new BoolType()); }
  public  void visit(IntLit e){ s.push(new IntType()); }
  
  public  void visit(Var e){
      VType t = gamma.get(e.getVarName());
      if(t != null){
         s.push(t);
      }else{
          System.out.println("Error at " + e.getLine() + "," + e.getColumn()+ " "+e.getVarName() + "not declared. "  );
          System.exit(1);
      }
  }
  
  
  
  public  void visit(Attr c){
        String varname = c.getLeftSide();
        c.getRightSide().accept(this);
        VType t = s.pop();
        VType vt = gamma.get(varname);
        if(vt != null){
           if(! vt.match(t)){
                System.out.println("Error at " + c.getLine() + "," + c.getColumn()+ " "+varname + " type mismatch ");
                System.exit(1);
           }
        }else{
           gamma.add(varname,t);
        }
  }
  
  public  void visit(While c){
      c.getCondition().accept(this);
      if(s.pop().match(new BoolType())){
          c.getBody().accept(this);
      }else{
           System.out.println("Error at " + c.getLine() + "," + c.getColumn()+
           "while condition must have type bool ");
           System.exit(1);
      }
  }
  
  public  void visit(If c);
  public  void visit(Seq c);
  public  void visit(ExpCmd c);
 
  public  void visit(TyInt c){ s.push(new IntType()); }
  public  void visit(TyString c){s.push(new StringType());}
  public  void visit(TyBool c){s.push(new BoolType());}
  public  void visit(TyVoid c){s.push(new VoidType());}
 
 
 // t4 f t1 t2 t3 
 // >t4 t3 t2 t1
 // t1 -> (t2 -> (t3 -> t4))
  public  void visit(Func d){
       Pair[] p = d.getgetParams();
       s.clear();
       gamma.extend();
       for(Pair pp : p){
           pp.accept(this);
       }
       VType f,r;
       d.getReturnType().accept(this);
       r = s.pop();
       f = r;
       while(! s.empty()){
         f = new FunType(s.pop(),f);  
       }
//  --> Enfia (none,f) no contexto !       
       d.getBody().accept(this);
       s.pop().match(r);
  }
  
  public  void visit(Pair p){
     p.getFirst().accept(this);
     gamma.add(p.getSecond(),s.peek());
  }
  
  public  void visit(Program d);
  
}
