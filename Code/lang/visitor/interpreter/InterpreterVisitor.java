package  lang.visitor.interpreter;

import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;
import lang.ast.*;
import java.util.*;


public class InterpreterVisitor extends NodeVisitor {
  
  private Stack<Value> s;
  private Environment<String,Value> env;
  
  public IntepreterVisitor(){
      s = new Stack<Value>();
      env = new Environment<String,Value>();
  }
  
  public abstract void visit(Add e){
        try{
           e.getLeft().accpet(this);
           e.getRight().accpet(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.add(op1));
        }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ getColumn() +") : " + ex.getMessage() );
        }
  }
  
  public abstract void visit(Sub e){
        try{
           e.getLeft().accpet(this);
           e.getRight().accpet(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.sub(op1));
        }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ getColumn() +") : " + ex.getMessage() );
        }
  }
  
  public abstract void visit(And e){
        try{
           e.getLeft().accpet(this);
           e.getRight().accpet(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.and(op1));
        }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ getColumn() +") : " + ex.getMessage() );
        }
  }
  
  public abstract void visit(Not e){
      try{
         e.getExpr().accept(this);
         Value v = s.pop();
         s.push(v.not());
      }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ getColumn() +") : " + ex.getMessage() );
      }
  }
  
  public abstract void visit(Eq e){
       try{
           e.getLeft().accpet(this);
           e.getRight().accpet(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.eq(op1));
       }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ getColumn() +") : " + ex.getMessage() );
       }
  }
  
  public abstract void visit(Lt e){
       try{
           e.getLeft().accpet(this);
           e.getRight().accpet(this);
           Value op1 = s.pop();
           Value op2 = s.pop();
           s.push(op2.lt(op1));
       }catch(Exception ex){
             throw new RuntimeException(" Runtime error at (" + e.getLine()+ ","+ getColumn() +") : " + ex.getMessage() );
       }
  }
  
  public abstract void visit(Call e){
      Stack<Value> old = s;
      s = new Stack<Value>();
      
  }
  
  public abstract void visit(StrLit e){ s.push(new StringValue(e.getValue())); }
  public abstract void visit(BoolLit e){ s.push(new BoolValue(e.getValue())); }
  public abstract void visit(IntLit e){ s.push(new IntValue(e.getValue())); }
  
  public abstract void visit(Var e){
      Value v = env.get(e.getVarName());
      if(v != null){
          s.psuh(v);
      }
      else{ throw new RuntimeException("Runtime error at (" + e.getLine()+ ","+ getColumn() +") : Undefined var " + e.getVarName());}
  }
  
  
  
  public abstract void visit(Attr c);
  public abstract void visit(While c);
  public abstract void visit(If c);
  public abstract void visit(Seq c);
  public abstract void visit(ExpCmd c);
 
  public abstract void visit(TyInt c);
  public abstract void visit(TyString c);
  public abstract void visit(TyBool c);
  public abstract void visit(TyVoid c);
 
  public abstract void visit(Func d);
  public abstract void visit(Program d);
  public abstract void visit(Pair p);
}
