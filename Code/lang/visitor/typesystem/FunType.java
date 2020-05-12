package lang.visitor.typesystem;


public class FunType extends VType {
    private VType o,d;
    public FunType(VType o, VType d){ 
       super("FUN");
       this.o = o;
       this.d = d;
    }
    
    public VType getO(){ return o;}
    public VType getD(){ return d;}
    
    @override
    public boolean match(VType t){
         if(t instanceof FunType){
             return ((FunType)t).getO().match(o) && ((FunType)t).getD().match(d);
         }
         return false;
    }
}
