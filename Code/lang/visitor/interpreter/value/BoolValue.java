package lang.visitor.interpreter.value;

public class BoolValue extends Value {
     
     private boolean v;
     
     public BoolValue(boolean x){ v =x;}
     
     public boolean asBool(){ return v; }
     
     public abstract Value add(Value v){throw new RuntimeException("Arithmetical operation applied to a boolean !");}
     public abstract Value sub(Value v){throw new RuntimeException("Arithmetical operation applied to a boolean !");}
     public abstract Value and(Value v){ return new BoolValue(this.v && v.asBool()); }
     public abstract Value not(Value v){ return new BoolValue(! this.v);             }
     public abstract Value eq(Value v){ return new BoolValue((this.v && v.asBool()) || (!this.v && !v.asBool()) ); }
     public abstract Value lt(Value v){ throw new RuntimeException("Less than operation applied to a boolean !");  }
}
