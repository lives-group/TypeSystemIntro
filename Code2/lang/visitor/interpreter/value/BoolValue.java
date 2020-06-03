package lang.visitor.interpreter.value;

public class BoolValue extends Value {
     
     private boolean v;
     
     public BoolValue(boolean x){ v =x;}
     
     public boolean asBool(){ return v; }
     
     public Value add(Value v){throw new RuntimeException("Arithmetical operation applied to a boolean !");}
     public Value sub(Value v){throw new RuntimeException("Arithmetical operation applied to a boolean !");}
     public Value and(Value v){ return new BoolValue(this.v && v.asBool()); }
     public Value not(){ return new BoolValue(! this.v);             }
     public Value eq(Value v){ return new BoolValue((this.v && v.asBool()) || (!this.v && !v.asBool()) ); }
     public Value lt(Value v){ throw new RuntimeException("Less than operation applied to a boolean !");  }
     public String toString(){return ""+ v;}
}
