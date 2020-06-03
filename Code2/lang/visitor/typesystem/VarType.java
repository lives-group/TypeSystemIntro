package lang.visitor.typesystem;

public class VarType extends VType {
    
    private int id;
    private VType ty;
    
    public VarType(int id){
        super("_"+id);
        this.id = id;
        ty = null;
    }
    
    public int getID(){ return id; }
    
    public void setInstance(VType v){ ty = v;}
    public VType getInstance(){ return ty;}
    
    public boolean matchCT(VType t, CTM ct){
        ct.addConstraint(new ConstraintVar(this,t));
        return true;
    }
    
    public String toString(){ 
        if(ty != null){
            return getName() + "{"+ ty.toString() +"}";
        }
        return getName();
    }
}
