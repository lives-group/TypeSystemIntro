package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Sub extends BinOP {
   
   public Sub(Expr left, Expr right ){
       super(left,right);
   }
   public void accept(NodeVisitor v){ v.visit(this);}
}
