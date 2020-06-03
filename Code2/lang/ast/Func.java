package lang.ast;

import lang.ast.cmd.*;
import lang.ast.types.*;
import lang.ast.expr.Expr;

public class Func extends SuperNode {
   
   private Expr[] returns;
   private String fname;
   private Pair[] params;
   private Cmd body;
   
   public Func(Expr[] rt, String s, Pair[] params, Cmd b ){
       returns = rt;
       fname = s;
       this.params = params;
       body = b;
   }
   
   public Cmd getBody(){return body;}
   public Expr[] getReturns(){return returns;}
   public Pair[] getParams(){return params;}
   public String getFuncName(){return fname;}
   
   public void accept(NodeVisitor v){ v.visit(this);}
   
}

