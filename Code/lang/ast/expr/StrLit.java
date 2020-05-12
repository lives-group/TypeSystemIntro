package lang.ast.expr;

import lang.ast.NodeVisitor;

public class StrLit extends Expr {
   
   private String val; 
   
   public StrLit(String b ){
       this.val = val;
   }
   
   public String getValue(){ return val;}
   
   public void accept(NodeVisitor v){ v.visit(this);}
}
