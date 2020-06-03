package lang.ast.cmd;

import lang.ast.NodeVisitor;
import lang.ast.expr.Expr;

public class Seq extends Cmd { 

    private Cmd l;
    private Cmd r;
    
    public Seq(Cmd left, Cmd right){
        this.l = left;
        this.r = right;
    }
    
    public Cmd getLeft(){ return l;}
    public Cmd getRight(){ return r;}
    
    public void accept(NodeVisitor v){ v.visit(this);}
}
