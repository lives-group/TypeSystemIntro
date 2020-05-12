package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Call extends Expr {
   
   private String name;
   private Expr args[];
   
   public Call(String n ,Expr[] args){
       this.name = n;
       this.args = args;
   }
   
   public String getCalledName(){return name;}
   public Expr[] getArgs(){ return args;}
   public int getNumArgs(){ return args.length;}
   public void accept(NodeVisitor v){ v.visit(this);}
}
