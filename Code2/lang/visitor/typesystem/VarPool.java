package lang.visitor.typesystem;

import java.util.ArrayList;

public class VarPool{
    
    private ArrayList<VarType> vars;
    private int n; 
    
    public VarPool(){
        vars = new ArrayList<VarType>();
        n = 0;
    }
    
    public VarType newVar(){
       VarType p = new VarType(n++);
       vars.add(p);
       return p;
    }
}
