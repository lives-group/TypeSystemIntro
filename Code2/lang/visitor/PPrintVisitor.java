package  lang.visitor;

import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;
import lang.visitor.interpreter.value.*;
import lang.ast.*;
import java.util.*;


public class PPrintVisitor extends NodeVisitor {
  
  private Stack<String> code;
  private String ident;
  
  public PPrintVisitor(){
    code = new Stack<String>();
    ident= "";
  }
  
  public String getTop(){
      return code.peek();
  }
  
  private void binOpStr(String opname){
       String s = code.pop() ;
       s = code.pop() + opname + s;
       code.push(s);
  }
  
  public  void visit(Add e){
        e.getLeft().accept(this);
        e.getRight().accept(this);
        binOpStr("+");
  }
  
  public  void visit(Sub e){
        e.getLeft().accept(this);
        e.getRight().accept(this);
        binOpStr("-");
  }
  
  public  void visit(And e){
        e.getLeft().accept(this);
        e.getRight().accept(this);
        binOpStr("&&");
  }
  
  public  void visit(Not e){
        e.getExpr().accept(this);
        code.push("!" + code.pop());
  }
  
  public  void visit(Eq e){
        e.getLeft().accept(this);
        e.getRight().accept(this);
        binOpStr("==");
  }
  
  public  void visit(Lt e){
       e.getLeft().accept(this);
       e.getRight().accept(this);
       binOpStr("<");
  }
  
  public  void visit(Call e){
      String s = e.getCalledName() + "(";
      Expr[] es = e.getArgs();
      if (es.length > 0){
         es[0].accept(this);
         s += code.pop();
         for(int i = 1; i < es.length; i++ ){
              es[i].accept(this);
              s += "," + code.pop();
         }
      }
      s += ")";
      if(e.getTargets().length > 0){
          int i = 0;
          s += "<:" + e.getTargets()[0];
          for(i = 1; i < e.getTargets().length; i++){
              s += "," + e.getTargets()[i];
          }
          s += ":>";
      }
      code.push(s);
  }
  
  public  void visit(StrLit e){ code.push(e.getValue()); }
  public  void visit(BoolLit e){ code.push(e.getValue()+""); }
  public  void visit(IntLit e){ code.push(e.getValue()+""); }
  public  void visit(Var e){ code.push(e.getVarName()+""); }
  
  public  void visit(Attr c){
       c.getRightSide().accept(this);
       c.getLeftSide();
       code.push(ident + c.getLeftSide() + " = " + code.pop() + ";\n");
  }
  
  public  void visit(While c){
      String lastIndet = ident;
      String s = ident + "while(";
      c.getCondition().accept(this);
      s += code.pop() + "){\n";
      ident += "    ";
      c.getBody().accept(this);         
      s += code.pop();
      ident = lastIndet;
      s += ident + "}\n";
      code.push(s);
  }
  
  public  void visit(If c){
      String s = ident + "if(";
      String lasti = ident;
      c.getCondition().accept(this);
      s += code.pop() + "){\n";
      ident += "    ";
      c.getThenBody().accept(this);
      s += ident + "}else{\n";
      c.getElseBody().accept(this);
      s += code.pop()+"\n";
      ident = lasti;
      s += ident +"}\n";
  }
  
  public  void visit(Seq c){
      c.getLeft().accept(this);
      c.getRight().accept(this);
      String s= code.pop();
      s = code.pop()  + s;
      code.push(s);
  }
  
  public  void visit(ExpCmd c){
     c.getExpr().accept(this);
     code.push(ident + code.pop() + ";\n");
  }
 
  public  void visit(TyInt c){code.push("Int");}
  public  void visit(TyString c){code.push("String");}
  public  void visit(TyBool c){code.push("Bool");}
  public  void visit(TyVoid c){code.push("Void");}
  
  public  void visit(Func d){
      String last = ident;
      String s = ident + d.getFuncName()+"(";
      Pair[] pp = d.getParams();
      if(pp.length > 0 ){
          pp[0].accept(this);
          s += code.pop();
          for(int i =1; i < pp.length; i++){
             pp[i].accept(this);
             s += "," + code.pop();
          }
      }
      s += ") : ";
      Expr[] v =  d.getReturns();
      if(v.length > 0){
         v[0].accept(this);
         s += code.pop();
         for(int i = 1; i < v.length; i ++){
             v[i].accept(this);
             s += "," + code.pop();
         }
      }
      s += "{ \n";
      ident += "    ";
      d.getBody().accept(this);
      ident = last;
      s += code.pop();
      s += ident + "}\n";
      code.push(s);
  }
  
  public  void visit(Program d){
      Func[] funcs = d.getFuncList();
      String p = "";
      for(Func f : funcs){
          f.accept(this);
          p += code.pop();
      }
      code.push(p);
  }
  
  public void visit(Pair p){
      p.getFirst().accept(this);
      String s = code.pop();
      s += " " + p.getSecond();
      code.push(s);
  }

}
