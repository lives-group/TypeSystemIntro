package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Var extends Expr {
   
   private String val; 
   
   public Var(String n ){
       this.val = n;
   }
   
   public String getVarName(){ return val;}
   
   public void accept(NodeVisitor v){ v.visit(this);}
}
