package lang.ast.cmd;

import lang.ast.NodeVisitor;
import lang.ast.expr.Expr;

public class Attr extends Cmd { 
    private String vname;
    private Expr e;
    
    public Attr(String v, Expr e){
        vname = v;
        this.e = e;
    }
    
    public String getLeftSide(){ return vname;}
    public Expr getRightSide(){ return e;}
    
    public void accept(NodeVisitor v){ v.visit(this);}
}
