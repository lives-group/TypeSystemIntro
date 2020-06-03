package lang.ast.cmd;

import lang.ast.NodeVisitor;
import lang.ast.expr.Expr;

public class If extends Cmd { 

    private Cmd thn;
    private Cmd els;
    private Expr e;
    
    public If(Expr cnd, Cmd bt, Cmd be){
        this.thn = bt;
        this.els = be;
        this.e = cnd;
    }
    
    public Expr getCondition(){ return e;}
    public Cmd getThenBody(){ return thn;}
    public Cmd getElseBody(){ return els;}
    
    public void accept(NodeVisitor v){ v.visit(this);}
}
