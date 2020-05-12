package lang.ast;

import lang.ast.cmd.*;
import lang.ast.types.*;

public class Program extends SuperNode {
   
   private Func[] funcs;
   
   public Program(Func[] f){funcs = f;}
   
   Func[] getFuncList(){ return funcs;}
   public void accept(NodeVisitor v){ v.visit(this);}
   
}

