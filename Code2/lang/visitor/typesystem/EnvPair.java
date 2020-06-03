package  lang.visitor.typesystem;
import lang.visitor.Environment;

public class EnvPair {
    public Environment<String,VType> env;
    public VType type;
    
    public EnvPair(Environment<String,VType> env, VType type){
       this.env = env;
       this.type = type;
    }
    
    public String toString(){
        String s = "\n";
        s += " ----| " + type.toString() + " |---- \n";
        s += env.toStringf("     ");
        return s;
    }
}
