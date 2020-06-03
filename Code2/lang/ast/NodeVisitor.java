package lang.ast;

import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;

public abstract class NodeVisitor {
  
  // Aritmética, lógica e comparações.
  public abstract void visit(Add e);
  public abstract void visit(Sub e);
  public abstract void visit(And e);
  public abstract void visit(Not e);
  public abstract void visit(Eq e);
  public abstract void visit(Lt e);
  public abstract void visit(Call e);
  public abstract void visit(StrLit e);
  public abstract void visit(BoolLit e);
  public abstract void visit(IntLit e);
  public abstract void visit(Var e);
  
  public abstract void visit(Attr c);
  public abstract void visit(While c);
  public abstract void visit(If c);
  public abstract void visit(Seq c);
  public abstract void visit(ExpCmd c);
 
  public abstract void visit(TyInt c);
  public abstract void visit(TyString c);
  public abstract void visit(TyBool c);
  public abstract void visit(TyVoid c);
 
  public abstract void visit(Func d);
  public abstract void visit(Program d);
  public abstract void visit(Pair p);
}
