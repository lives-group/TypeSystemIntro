package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Add extends BinOP {
   
   public Add(Expr left, Expr right ){
       super(left,right);
   }
   public void accept(NodeVisitor v){ v.visit(this);}
}
