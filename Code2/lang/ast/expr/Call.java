package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Call extends Expr {
   
   private String name;
   private Expr args[];
   private String[] targets;
   
   public Call(String n ,Expr[] args, String[] targets){
       this.name = n;
       this.args = args;
       this.targets = targets;
   }
   
   public String getCalledName(){return name;}
   public Expr[] getArgs(){ return args;}
   public int getNumArgs(){ return args.length;}
   public String[] getTargets(){ return targets; }
   public void accept(NodeVisitor v){ v.visit(this);}
   
}
