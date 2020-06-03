package lang.visitor.typesystem;

public class VoidType extends VType {
    public VoidType(){ super("VOID");}
    
    public String toString(){ return getName();}
    
    public boolean matchCT(VType t, CTM ct){
        if(t instanceof VarType){ 
           ct.addConstraint(new ConstraintVar((VarType)t,this));
           return true;
        }
        return match(t);
    }
}
