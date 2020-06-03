package  lang.visitor.typesystem;

import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;
import lang.visitor.Environment;
import lang.ast.*;
import java.util.*;


public class TypeChecker extends NodeVisitor {
  
  private Stack<VType> s;
  private CTM ct;
  private Environment<String,VType> gamma;
  private Environment<String,EnvPair> delta;
  private Environment<String,ArrayList<VType>> prelude;
  private boolean addVar; 
  
  private VarPool vs; 
  
  public TypeChecker(){
      s = new Stack<VType>();
      addVar = false;
      ct = new CTM();
      prelude = new Environment<String, ArrayList<VType>>();
      delta = new Environment<String,EnvPair>();
      
      IntType tint = new IntType();
      BoolType tbool = new BoolType();
      StringType tstring = new StringType();
      ArrayList<VType> l = new ArrayList<VType>();
      vs = new VarPool();
      
      l.add(new FunType(tint, new FunType(tint,tint)));
      prelude.add("+",l);
      prelude.add("-",l);
      l = new ArrayList<VType>();
      l.add(new FunType(tbool,new FunType(tbool,tbool)));
      prelude.add("&",l);
      
      l = new ArrayList<VType>();
      l.add(new FunType(tbool, new FunType(tbool,tbool)));
      l.add(new FunType(tint ,new FunType(tint,tbool)));
      l.add(new FunType(tstring, new FunType(tstring,tbool)));
      // Manually overloading equals (==) operator ! ;-).  
      prelude.add("==",l);
      
      l = new ArrayList<VType>();
      l.add(new FunType(tbool,tbool));
      prelude.add("!",l);
  }
  // x + y - z
  // x : A; y : B; z : C
  // + : A -> B -> D
  // - : D -> C -> E 
  //
  
  private void reportError(String s){ 
      System.out.println(s);
      System.exit(1);
  }
  
  private VType simplify(VType f,VType[] p ){
      int i = 0;
      for(i =0; i < p.length; i++){
          if(f instanceof FunType){
             if ( ((FunType)f).getO().match(p[i])){
                f = ((FunType)f).getD();
             }
             else{ return null;}
          }else{
             return null;
          }
      }
      return f;
  }
  
//   private boolean matchCT(VType x, VType y){
//         if(x instanceof VarType){
//             ct.addConstraint(new ConstraintVar((VarType)x,y) );
//             return true;
//         }else if(y instanceof VarType){
//             ct.addConstraint(new ConstraintVar((VarType)y, x));
//             return true;
//         }else{
//             return x.match(y);   
//         }
//   }
  
  
  private VType resolvOverload(ArrayList<VType> defs, VType[] p){
       VType r;
       for(VType d : defs){
           r = simplify(d,p);
           if(r != null){ return r;}
       }
       return null;
  }
  
  private boolean matchBinOp(String op, VType o, VType d){
      VType t; 
      if((o instanceof VarType) || (d instanceof VarType)){
          t = vs.newVar();
          ct.addConstraint(new ConstraintSym(op, new FunType(o,new FunType(d,t)) ));
          s.push(t);
          return true;
      }
      else {
          t = resolvOverload(prelude.get(op),new VType[]{o,d});
          if(t != null){
              s.push(t);
              return true;
          }
          return false;
      }
  }
  
  
  
//   private boolean fcall(String fname, VType args[], VType rets[]){
//       VType t; 
//       if((o instanceof VarType) || (d instanceof VarType)){
//           t = vs.newVar();
//           ct.addConstraint(new ConstraintSym(op, new FunType(o,new FunType(d,t)) ));
//           s.push(t);
//           return true;
//       }
//       else {
//           t = resolvOverload(prelude.get(op),new VType[]{o,d});
//           if(t != null){
//               s.push(t);
//               return true;
//           }
//           return false;
//       }
//   }
  
  public  void visit(Add e){
     e.getLeft().accept(this);
     e.getRight().accept(this);
     if(!matchBinOp("+",s.pop(),s.pop())){
          reportError("Error at " + e.getLine() + "," + e.getColumn()+ ". Type error");
     }
  }
  
  public  void visit(Sub e){
     e.getLeft().accept(this);
     e.getRight().accept(this);
     if(!matchBinOp("-",s.pop(),s.pop())){
          reportError("Error at " + e.getLine() + "," + e.getColumn()+ ". Type error");
     }
  }
  
  public  void visit(And e){
     e.getLeft().accept(this);
     e.getRight().accept(this);
     if(!matchBinOp("&",s.pop(),s.pop())){
          reportError("Error at " + e.getLine() + "," + e.getColumn()+ ". Type error");
     }
  }
  
  public  void visit(Not e){
       e.getExpr().accept(this);
       
  }
  
  public  void visit(Eq e){
     e.getLeft().accept(this);
     e.getRight().accept(this);
     if(!matchBinOp("==",s.pop(),s.pop())){
          reportError("Error at " + e.getLine() + "," + e.getColumn()+ ". Type error");
     }
  }
  
  public  void visit(Lt e){e.getLeft().accept(this);
     e.getLeft().accept(this);
     e.getRight().accept(this);
     if(!matchBinOp("<",s.pop(),s.pop())){
          reportError("Error at " + e.getLine() + "," + e.getColumn()+ ". Type error");
     }
  }
  
  public  void visit(Call e){
     EnvPair finf = delta.get(e.getCalledName());
     if(finf != null){                
        VType t;  // f(1,2)<: m,n,o :> : [ INT, A ,BOOL ] 
        VType[] targs = new VType[e.getTargets().length];
        for(int i  = 0; i < targs.length; i++){
           t = gamma.get(e.getTargets()[i]);
           if(t != null){
              targs[i] = t;
           }else{
              targs[i] = vs.newVar();
              gamma.add(e.getTargets()[i],targs[i]);
           }
        }
        if(e.getArgs().length > 0){
           if(targs.length == 0){t = new VoidType();}else{ t = new AgregateType(targs);} 
           for(int j = e.getArgs().length -1; j >= 0 ; j--){
               e.getArgs()[j].accept(this);
               t = new FunType(s.pop(),t);
           }
        }else{
           if(targs.length == 0){ t = new VoidType();}
           else{ t = new AgregateType(targs);}
        }
        if(!finf.type.matchCT(t,ct)){
           reportError("Error at "  + e.getLine() + "," + e.getColumn()+  " call of "+ e.getCalledName() +" does not match");
        }
     }else{
        reportError("Error at "  + e.getLine() + "," + e.getColumn()+  " wrong call");
     }
     
  }
  
  public  void visit(StrLit e){ s.push(new StringType()); }
  public  void visit(BoolLit e){s.push(new BoolType());   }
  public  void visit(IntLit e){ s.push(new IntType());    }
  
  public  void visit(Var e){
      VType t = gamma.get(e.getVarName());
      if(t != null){
         s.push(t);
      }else{
          if(addVar){
              s.push(vs.newVar()); 
              gamma.add(e.getVarName(),s.peek()); 
              return;
          }
          reportError("Error at " + e.getLine() + "," + e.getColumn()+ " "+e.getVarName() + " not declared. "  );
      }
  }
  
  public  void visit(Attr c){
        String varname = c.getLeftSide();
        c.getRightSide().accept(this);
        VType t = s.pop();
        VType vt = gamma.get(varname);
        if(vt != null){
           if(! vt.matchCT(t,ct)){
                System.out.println("Error at " + c.getLine() + "," + c.getColumn()+ " "+varname + " type mismatch ");
                System.exit(1);
           }
        }else{
           gamma.add(varname,t);
        }
  }
  
  public  void visit(While c){
      c.getCondition().accept(this);
      if(s.pop().match(new BoolType())){
          c.getBody().accept(this);
      }else{
           reportError("Error at " + c.getLine() + "," + c.getColumn()+" while condition must have type bool ");
      }
  }
  
  public  void visit(If c){}
  public  void visit(Seq c){
      c.getLeft().accept(this);
      c.getRight().accept(this);
  }
  
  public  void visit(ExpCmd c){
      c.getExpr().accept(this);
  }
 
  public  void visit(TyInt c){ s.push(new IntType()); }
  public  void visit(TyString c){s.push(new StringType());}
  public  void visit(TyBool c){s.push(new BoolType());}
  public  void visit(TyVoid c){s.push(new VoidType());}
 
 //    Int  -> Int -> A B   z: A;  + : A ->Int ->Int
 // f (Int x,Int y) : z +1, w*2
 //    ....
 //    z = 0;
 //    w = 1;
 //    ...
  public  void visit(Func d){
        gamma = delta.get(d.getFuncName()).env;
        if(gamma != null){
           d.getBody().accept(this);
        }else{
           System.out.println(d.getFuncName() + " not found.");
        }
  }
  
  public  void visit(Pair p){
     p.getFirst().accept(this);
     gamma.add(p.getSecond(),s.peek());
  }
  
  public  void visit(Program d){
     VType t, pars[];
     for(Func f : d.getFuncList()){
          gamma = new Environment<String,VType>();
          pars = new VType[f.getParams().length];
          for(int i = 0; i< f.getParams().length; i++){
             f.getParams()[i].getFirst().accept(this);
             gamma.add(f.getParams()[i].getSecond(),s.peek());
             pars[i] = s.pop();
          }
          VType xs[] = new VType[f.getReturns().length]; 
          addVar= true;
          for(int i = 0; i < f.getReturns().length;i++){
              f.getReturns()[i].accept(this);
              xs[i] = s.pop();
          }
          addVar= false;
          if(xs.length > 0){ t = new AgregateType(xs); }else{ t = new VoidType();}
          int start = pars.length -1;
          if(pars.length > 0 ){
              start = pars.length-1;
              if(t == null){ 
                 t = pars[pars.length -1]; 
                 start = pars.length-2;
              } 
              for(int j = start ; j >= 0; j--){
                  t = new FunType(pars[j],t);
              }
          }
          delta.add(f.getFuncName(), new EnvPair(gamma,t));
     }
     for(Func f : d.getFuncList()){
          f.accept(this);
     }
     
     System.out.println(" --- Type Environment ---");
     System.out.println(delta.toString());
     System.out.println(" --- Recorded constraints ---");
     System.out.println(ct.toString());
     System.out.println(" --- END OF DATA ---");
  }
  
}
