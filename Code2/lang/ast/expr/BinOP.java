package lang.ast.expr;

public abstract class BinOP extends Expr {
   
   private Expr l,r;
   public BinOP(Expr left, Expr right ){
       l = left;
       r = right;
   }
   
   public Expr getLeft(){ return l;}
   public Expr getRight(){ return r;}

}
