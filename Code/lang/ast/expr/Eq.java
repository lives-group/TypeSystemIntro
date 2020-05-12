package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Eq extends BinOP {
   
   public Eq(Expr left, Expr right ){
       super(left,right);
   }
   public void accept(NodeVisitor v){ v.visit(this);}
}
