package lang;

import java.io.*;
import lang.ast.*;
import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.parser.*;


public class LangCompiler{
   // Recupera o nome base (sem extensão) de um arquivo.
   public static void main(String[] args){
       if( args.length != 2 ){
          System.out.println("Lang compiler v 0.0.1 - Maio de 2020");
          System.out.println("use java -cp . Lang [-i |-ti] <Caminho para código Fonte> ");
          System.out.println(" -i : Run the interpreter.");
          System.out.println(" -ti: Type check and then run the inpterter.");
       }
       try{
          BufferedReader input  = new BufferedReader(new FileReader( args[1] ));
          LangScanner lex = new LangScanner(input);
          SuperNode result = (SuperNode) new LangParser().parse(lex);
          if(args[0].equals("i") ){
              
          }
          else if(args[0].equals("ti") ){
          }
          else if(args[0].equals("i") ){
          }
          
      }catch(Exception e){
          e.printStackTrace();
      }
   }
}
 
