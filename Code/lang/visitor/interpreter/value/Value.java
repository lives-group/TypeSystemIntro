package lang.visitor.interpreter.value;

public abstract class Value {
     
     public int asInt(){ throw new RuntimeException("Invalid operation, the value is not an int."); }
     public boolean asBool(){ throw new RuntimeException("Invalid operation, the value is not a bool."); }
     public String asString(){ throw new RuntimeException("Invalid operation, the value is not a string."); }
     
     public abstract Value add(Value v);
     public abstract Value sub(Value v);
     public abstract Value and(Value v);
     public abstract Value not(Value v);
     public abstract Value eq(Value v);
     public abstract Value lt(Value v);
    
}

