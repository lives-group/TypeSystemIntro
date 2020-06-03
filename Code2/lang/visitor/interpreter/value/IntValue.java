package lang.visitor.interpreter.value;

public class IntValue extends Value {
     
     private int v;
     
     public IntValue(int x){ v =x;}
     
     public int asInt(){ return v; }
     
     public Value add(Value v){return new IntValue(this.v + v.asInt() );}
     public Value sub(Value v){return new IntValue(this.v - v.asInt() );}
     public Value and(Value v){ throw new RuntimeException("Logical operation applied to an int !");}
     public Value not(){ throw new RuntimeException("Logical operation applied to an int !");}
     public Value eq(Value v){ return new BoolValue(this.v == v.asInt()); }
     public Value lt(Value v){  return new BoolValue(this.v < v.asInt()); }
     public String toString(){return ""+ v;}
    
}
