package lang.ast.cmd;

import lang.ast.NodeVisitor;
import lang.ast.expr.Expr;

public class While extends Cmd { 

    private Cmd b;
    private Expr e;
    
    public While(Expr cnd, Cmd b){
        this.b = b;
        this.e = cnd;
    }
    
    public Expr getCondition(){ return e;}
    public Cmd getBody(){ return b;}
    
    public void accept(NodeVisitor v){ v.visit(this);}
}
