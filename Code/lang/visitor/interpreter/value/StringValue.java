package lang.visitor.interpreter.value;

public class StringValue extends Value {
     
     private String s;
     
     public StringValue(String x){ v =x;}
     
     public String asString(){ return v; }
     
     public abstract Value add(Value v){throw new RuntimeException("Arithmetical operation applied to a String !");}
     public abstract Value sub(Value v){throw new RuntimeException("Arithmetical operation applied to a String !");}
     public abstract Value and(Value v){throw new RuntimeException("Logical operation applied to a String !");}
     public abstract Value not(Value v){throw new RuntimeException("Logical operation applied to a String !"); }
     public abstract Value eq(Value v) {return new BoolValue(s.equals(v.asString())); }
     public abstract Value lt(Value v) {return new BoolValue(s.compare(v.asString()) < 0); }
}
