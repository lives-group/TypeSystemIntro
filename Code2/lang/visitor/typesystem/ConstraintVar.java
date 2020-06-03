package lang.visitor.typesystem;

public class ConstraintVar extends Constraint{
    
    private VarType v;
    private VType t;
    
    public ConstraintVar(VarType v, VType t){
        this.v = v;
        this.t = t;
    }
    
    public String toString(){
        return v.toString() + " = " + t.toString();
    }
}
