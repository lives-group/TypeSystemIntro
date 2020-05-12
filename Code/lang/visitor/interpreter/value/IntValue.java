package lang.visitor.interpreter.value;

public class IntValue extends Value {
     
     private int v;
     
     public IntValue(int x){ v =x;}
     
     public int asInt(){ return v; }
     
     public abstract Value add(Value v){return new Value(this.v + v.asInt() );}
     public abstract Value sub(Value v){return new Value(this.v - v.asInt() );}
     public abstract Value and(Value v){ throw new RuntimeException("Logical operation applied to an int !");}
     public abstract Value not(Value v){ throw new RuntimeException("Logical operation applied to an int !");}
     public abstract Value eq(Value v){ return new BoolValue(this.v == v.asInt()); }
     public abstract Value lt(Value v){  return new BoolValue(this.v < v.asInt()); }
    
}
