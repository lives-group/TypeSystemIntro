package lang.visitor.typesystem;

public class AgregateType extends VType{
    private VType[] types;
    
    public AgregateType(VType ts[]){ 
        super("AGREGATE");
        types = ts;
    }
    
    public String toString(){ 
       String s = "(";
       if(types.length > 0){
          s += types[0].toString();
          for(int i = 1; i < types.length; i++){
             s += types[i].toString();
          }
       }
       s += ")";
       return s;
    }
    
    public VType[] getTypes(){ return types;}
    
    public boolean match(VType t){ 
        if(t instanceof AgregateType){
            if(types.length != ((AgregateType)t).getTypes().length){ return false;}
            for(int i = 0; i < types.length; i++ ){
                if(! ((AgregateType)t).getTypes()[i].match(types[i])){ return false;}
            }
            return true;
        } 
        return false;
    }
    
    public boolean matchCT(VType t, CTM ct){
        if(t instanceof AgregateType){
            if(types.length != ((AgregateType)t).getTypes().length){ return false;}
            for(int i = 0; i < types.length; i++ ){
                if(! ((AgregateType)t).getTypes()[i].matchCT(types[i],ct)){ return false;}
            }
            return true;
        } 
        if(t instanceof VarType){
            ct.addConstraint(new ConstraintVar((VarType)t,this));
        }
        return true;
    }
    
}
