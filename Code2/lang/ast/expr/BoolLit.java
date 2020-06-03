package lang.ast.expr;

import lang.ast.NodeVisitor;

public class BoolLit extends Expr {
   
   private boolean val; 
   
   public BoolLit(boolean b ){
       this.val = b;
   }
   
   public boolean getValue(){ return val;}
   
   public void accept(NodeVisitor v){ v.visit(this);}
}
