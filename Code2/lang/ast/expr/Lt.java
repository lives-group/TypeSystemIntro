package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Lt extends BinOP {
   
   public Lt(Expr left, Expr right ){
       super(left,right);
   }
   public void accept(NodeVisitor v){ v.visit(this);}
}
