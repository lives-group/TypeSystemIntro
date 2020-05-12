package lang.visitor.typesystem;

public abstract class VType {
    
    private String typeName;
    
    public VType(String t){
        typeName = t;
    }
    
    public String getName(){ return typeName;}
    public boolean match(VType t){ return t.getName().equals(typeName);}

}
