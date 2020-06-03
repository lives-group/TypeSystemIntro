package lang.ast.expr;

import lang.ast.NodeVisitor;

public class IntLit extends Expr {
   
   private int val; 
   
   public IntLit(int b ){
       this.val = b;
   }
   
   public int getValue(){ return val;}
   
   public void accept(NodeVisitor v){ v.visit(this);}
}
