package lang.visitor.interpreter.value;

public class StringValue extends Value {
     
     private String v;
     
     public StringValue(String x){ v =x;}
     
     public String asString(){ return v; }
     
     public Value add(Value v){throw new RuntimeException("Arithmetical operation applied to a String !");}
     public Value sub(Value v){throw new RuntimeException("Arithmetical operation applied to a String !");}
     public Value and(Value v){throw new RuntimeException("Logical operation applied to a String !");}
     public Value not(){throw new RuntimeException("Logical operation applied to a String !"); }
     public Value eq(Value v) {return new BoolValue(this.v.equals(v.asString())); }
     public Value lt(Value v) {return new BoolValue(this.v.compareTo(v.asString()) < 0); }
     public String toString(){return v.toString();}
}
