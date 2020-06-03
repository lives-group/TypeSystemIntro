package  lang.visitor.interpreter;

import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;
import lang.visitor.interpreter.value.*;
import lang.visitor.Environment;
import lang.ast.*;
import java.util.*;


public class InteractiveInterpreterVisitor extends NodeVisitor {
  
  private Stack<Value> s;
  private Environment<String,Value> env;
  private Func[] funcs;
  private Stack<SuperNode> nexts; 
  
  public InteractiveInterpreterVisitor(){
      s = new Stack<Value>();
      env = new Environment<String,Value>();
      nexts = new Stack<SuperNode>();
  }
  
  public void printEnv(){
      System.out.println(env.toString());
      String so = "Stak Top > ";
      while(!s.empty()){
          so += s.pop().toString() + " |> ";
      }
      so += "|| Stack botton";
      System.out.println(so);
  }
  
  public  void visit(Add e){
        try{
           e.getLeft().accept(this);
           e.getRight().accept(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.add(op1));
        }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ e.getColumn() +") : " + ex.getMessage() );
        }
  }
  
  public  void visit(Sub e){
        try{
           e.getLeft().accept(this);
           e.getRight().accept(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.sub(op1));
        }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ e.getColumn() +") : " + ex.getMessage() );
        }
  }
  
  public  void visit(And e){
        try{
           e.getLeft().accept(this);
           e.getRight().accept(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.and(op1));
        }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ e.getColumn() +") : " + ex.getMessage() );
        }
  }
  
  public  void visit(Not e){
      try{
         e.getExpr().accept(this);
         Value v = s.pop();
         s.push(v.not());
      }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ e.getColumn() +") : " + ex.getMessage() );
      }
  }
  
  public  void visit(Eq e){
       try{
           e.getLeft().accept(this);
           e.getRight().accept(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.eq(op1));
       }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ e.getColumn() +") : " + ex.getMessage() );
       }
  }
  
  public  void visit(Lt e){
       try{
           e.getLeft().accept(this);
           e.getRight().accept(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.lt(op1));
       }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ e.getColumn() +") : " + ex.getMessage() );
       }
  }
  
  public  void visit(Call e){
      Func fx = null;
      for(Func f : funcs){
          if(f.getFuncName().equals(e.getCalledName()) && (f.getParams().length == e.getArgs().length)){
             fx = f;
             break;
          }
      }
      if(fx != null){
          for(Expr arg : e.getArgs()){
             arg.accept(this);
          }
          nexts.push(fx);
      }else{
         throw new RuntimeException("Error at " + e.getLine() + ", " + e.getColumn() + ": Attempt to call non existent function " + e.getCalledName());
      }
  }
  
  public  void visit(StrLit e){ s.push(new StringValue(e.getValue())); }
  public  void visit(BoolLit e){ s.push(new BoolValue(e.getValue())); }
  public  void visit(IntLit e){ s.push(new IntValue(e.getValue())); }
  
  public  void visit(Var e){
      Value v = env.get(e.getVarName());
      if(v != null){
          s.push(v);
      }
      else{ throw new RuntimeException("Runtime error at (" + e.getLine()+ ","+ e.getColumn() +") : Undefined var " + e.getVarName());}
  }
  
  public  void visit(Attr c){
       c.getRightSide().accept(this);
       env.add(c.getLeftSide(),s.pop());
  }
  
  public  void visit(While c){
      c.getCondition().accept(this);
      if(s.pop().asBool()){
         nexts.push(c);
         nexts.push(c.getBody());         
      }
  }
  
  public  void visit(If c){
      c.getCondition().accept(this);
      if(s.pop().asBool()){
         c.getThenBody().accept(this);
      }else{
         c.getElseBody().accept(this);
      }
  }
  
  public  void visit(Seq c){
      c.getLeft().accept(this);
      nexts.push(c.getRight());
  }
  
  public  void visit(ExpCmd c){
     c.getExpr().accept(this);
  }
 
  public  void visit(TyInt c){}
  public  void visit(TyString c){}
  public  void visit(TyBool c){}
  public  void visit(TyVoid c){}
  
  public  void visit(Func d){
      env.extend();
      for(Pair param : d.getParams()){
          env.add(param.getSecond(),s.pop());
      }
      nexts.push(d.getBody());
      env.retract();
  }
  
  public  void visit(Program d){
      funcs = d.getFuncList();
      Func main = null;
      for(Func f : funcs){
         if(f.getFuncName().equals("main") && f.getParams().length == 0){
            main = f;
         }
      }
      if(main != null){
         nexts.push(main.getBody());
         
      }else{
         System.out.println("No suitable main function found. No command to start execution. ");
      }
      System.out.println("Entering interpreter mode.");
      System.out.println(funcs.length + " definitions loaded.");
      run();
  }
  
  public void visit(Pair p){}
  
  public void list(){
      for(Func f : funcs){
         System.out.println(f.getFuncName());
      }
  }
  
  public void run(){
     String s = null;
     Scanner sc = new Scanner(System.in);
     do{
        System.out.print("::>");
        s = sc.nextLine();
        if(s.equals("list")){ 
           list();
        }else if(s.equals("st")){
           printEnv(); 
        }else if(s.equals("step")){
           
           nexts.pop().accept(this);
           printEnv(); 
        }
        
     }while(!s.equals(":q"));
  }
  
}
