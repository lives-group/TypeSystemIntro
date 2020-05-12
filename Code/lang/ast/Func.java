package lang.ast;

import lang.ast.cmd.*;
import lang.ast.types.*;

public class Func extends SuperNode {
   
   private SType returns;
   private String fname;
   private Pair[] params;
   private Cmd body;
   
   public Func(SType rt, String s, Pair[] params, Cmd b ){
      returns = rt;
      fname = s;
      this.params = params;
      body = b;
   }
   
   public Cmd getBody(){return body;}
   public SType getReturnType(){return returns;}
   public Pair[] getParams(){return params;}
   public String getFuncName(){return fname;}
   
   public void accept(NodeVisitor v){ v.visit(this);}
   
}

