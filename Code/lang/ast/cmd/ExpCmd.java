package lang.ast.cmd;

import lang.ast.NodeVisitor;
import lang.ast.expr.Expr;

public class ExpCmd extends Cmd { 

    private Expr e;
    
    public ExpCmd(Expr e){
        this.e = e;
    }
    
    public Expr getExpr(){ return e;}
    
    public void accept(NodeVisitor v){ v.visit(this);}
}
