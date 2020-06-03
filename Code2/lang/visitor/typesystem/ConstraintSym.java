package lang.visitor.typesystem;

public class ConstraintSym extends Constraint {
    
    private String v;
    private VType t;
    
    public ConstraintSym(String v, VType t){
        this.v = v;
        this.t = t;
    }
    
    public String toString(){
        return v.toString() + " = " + t.toString();
    }
}
