package lang.ast.expr;

import lang.ast.NodeVisitor;

public class And extends BinOP {
   
   public And(Expr left, Expr right ){
       super(left,right);
   }
   public void accept(NodeVisitor v){ v.visit(this);}
}
