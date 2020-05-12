package lang.ast.types;

import lang.ast.NodeVisitor;

public class TyString extends SType {

    
    public void accept(NodeVisitor v){ v.visit(this);}
    
}
