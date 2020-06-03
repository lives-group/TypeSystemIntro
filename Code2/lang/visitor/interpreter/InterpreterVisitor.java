package  lang.visitor.interpreter;

import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;
import lang.visitor.interpreter.value.*;
import lang.visitor.Environment;
import lang.ast.*;
import java.util.*;


public class InterpreterVisitor extends NodeVisitor {
  
  private Stack<Value> s;
  private Environment<String,Value> env;
  private Func[] funcs;
  
  public InterpreterVisitor(){
      s = new Stack<Value>();
      env = new Environment<String,Value>();
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
          fx.accept(this);
          for(String var : e.getTargets()){
              env.add(var,s.pop());
          }
      }else{
         throw new RuntimeException("Error at " + e.getLine() + ", " + e.getColumn() + ": Attempt to call non existent function \"" + e.getCalledName()+"\"");
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
      while(s.pop().asBool()){
         c.getBody().accept(this);
         c.getCondition().accept(this);
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
      c.getRight().accept(this);
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
      for(int i = d.getParams().length-1; i >= 0; i--){
          env.add(d.getParams()[i].getSecond(), s.pop());
      }
      d.getBody().accept(this);
      if(d.getReturns().length > 0 ){
         d.getReturns()[0].accept(this);
         for(int i =  d.getReturns().length-1; i > 0 ;i--){
             d.getReturns()[i].accept(this);
         }
      }
      
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
         main.getBody().accept(this);
      }else{
         System.out.println("No suitable main function found. Interpretation aborted.");
         System.exit(1);
      }
  }
  
  public void visit(Pair p){}
}
