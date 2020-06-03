package lang;

import java.io.*;
import lang.ast.*;
import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.parser.*;
import lang.visitor.interpreter.*;
import lang.visitor.typesystem.TypeChecker;
import lang.visitor.*;

public class LangCompiler{
   // Recupera o nome base (sem extensão) de um arquivo.
   public static void main(String[] args){
       if( args.length != 2 ){
          System.out.println("Lang compiler v 0.0.1 - Maio de 2020");
          System.out.println("Use java -cp . Lang ação <Caminho para código Fonte> ");
          System.out.println("Ação (uma das seguintes possibilidades): ");
          System.out.println(" -pp: Pretty print program.");
          System.out.println(" -i : Apenas interpretar");
          System.out.println(" -tp: Verfiicar tipos e imprimir o ambiente de tipos");
          //System.out.println(" -ti: Verificar tipos e depois interpretar");
          System.out.println(" -dti: Verificar tipos, imprimir o ambiente de tipos e depois interpretar");
          //System.out.println(" -gvz: Create a dot file. (Feed it to graphviz dot tool to generate graphical representation of the AST)");
          System.out.println(" -gvz: Create a dot file. (Feed it to graphviz dot tool to generate graphical representation of the AST)");
          //System.out.println(" -ti: Verificar tipos e depois interpretar");
       }
       try{
          NodeVisitor iv;
          BufferedReader input  = new BufferedReader(new FileReader( args[1] ));
          LangScanner lex = new LangScanner(input);
          SuperNode result = (SuperNode) new LangParser().parse(lex);
          if(args[0].equals("-i") ){
              iv = new InterpreterVisitor();
              result.accept(iv);
              ((InterpreterVisitor)iv).printEnv();
          }
          else if(args[0].equals("-ii") ){
            // iv = new InteractiveInterpreterVisitor();
            // result.accept(iv);
          }
          else if(args[0].equals("-tp") ){
             iv = new TypeChecker();
             result.accept(iv);
          }
          else if(args[0].equals("-pp") ){
              iv = new PPrintVisitor();
              result.accept(iv);
              System.out.println(((PPrintVisitor)iv).getTop());
          }
      }catch(Exception e){
          e.printStackTrace();
      }
   }
}
 
