package lang.visitor.typesystem;

// + : Int -> Int -> A  (x + y); x : Int,  y : A 

public class FunType extends VType {
    private VType o,d;
    public FunType(VType o, VType d){ 
       super("FUN");
       this.o = o;
       this.d = d;
    }
    
    public VType getO(){ return o;}
    public VType getD(){ return d;}
    
    @Override
    public boolean match(VType t){
         if(t instanceof FunType){
             return ((FunType)t).getO().match(o) && ((FunType)t).getD().match(d);
         }
         return false;
    }
    
    public boolean matchCT(VType t, CTM ct){
         if(t instanceof FunType){
             return (o.matchCT( ((FunType)t).getO(),ct ) && d.matchCT( ((FunType)t).getD(),ct ));
         }
         return false;
    }
    
    public String toString(){
       String s = "";
       if(o instanceof FunType){s += "(" + o.toString() + ")";
       }else { s += o.toString();}
       s += "->" + d.toString();
       return s;
    }
}
