package lang.ast.types;

import lang.ast.NodeVisitor;

public class TyBool extends SType {
    
    
    public void accept(NodeVisitor v){ v.visit(this);}
    
}
