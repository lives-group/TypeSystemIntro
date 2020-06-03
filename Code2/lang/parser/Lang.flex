package lang.parser;

import beaver.Symbol;
import beaver.Scanner;
import lang.ast.*;
import lang.ast.expr.*;
import lang.ast.cmd.*;
import lang.ast.types.*;
%%

%public
%class LangScanner
%extends Scanner
%function nextToken
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
         return newToken(Terminals.EOF, "end-of-file");
%eofval}
%unicode
%line
%column
%{       
        private String str;  
        private int cCount = 0;
	private Symbol newToken(short id)
	{
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength());
	}

	private Symbol newToken(short id, Object value)
	{
		return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
	}
%}

LineTerminator = \r|\n|\r\n
LineComment    = "--" [^\n\r]*
WhiteSpace     = {LineTerminator} | {LineComment} | [ \t\f]
LowerLetter = [a-z]
UpperLetter = [A-Z]
Number    = [:digit:] [:digit:]*
Id        = {LowerLetter} ({LowerLetter} | {UpperLetter} | [:digit:])*

%state STRING

%%    
<YYINITIAL> {
        ";"           { return newToken(Terminals.SEMI, yytext());   } 
        ":"           { return newToken(Terminals.COLON, yytext());  }
        ","           { return newToken(Terminals.COMMA, yytext());  }
        "{"           { return newToken(Terminals.LBRACE, yytext()); }
        "}"           { return newToken(Terminals.RBRACE, yytext()); }
        "("           { return newToken(Terminals.LPAREN, yytext()); }
        ")"           { return newToken(Terminals.RPAREN, yytext()); }
        "+"           { return newToken(Terminals.PLUS, yytext());   }
        "-"           { return newToken(Terminals.MINUS, yytext());  }
        "&"           { return newToken(Terminals.AND, yytext());    }        
        "!"           { return newToken(Terminals.NOT, yytext());    }    
        "<"           { return newToken(Terminals.LT, yytext());     }
        "<:"          { return newToken(Terminals.RTL, yytext());    }
        ":>"          { return newToken(Terminals.RTR, yytext());    }
        "=="          { return newToken(Terminals.EQ, yytext());     }
        "="           { return newToken(Terminals.ATTR, yytext());   }
        "if"          { return newToken(Terminals.IF, yytext());     }        
        "else"        { return newToken(Terminals.ELSE, yytext());   }  
        "while"       { return newToken(Terminals.WHILE, yytext());  }
        "Bool"        { return newToken(Terminals.BOOL, yytext());   }
        "Int"         { return newToken(Terminals.INT, yytext());    }
        "String"      { return newToken(Terminals.STR, yytext());    }
        "Void"        { return newToken(Terminals.VOID, yytext());   }
        "T"           { return newToken(Terminals.TRUE, yytext());   }
        "F"           { return newToken(Terminals.FALSE, yytext());  }
        \"            { str = new String(""); yybegin(STRING);       }
        {Number}      { return newToken(Terminals.NUMBER, new Integer(yytext())); }
        {Id}          { return newToken(Terminals.ID, yytext());     }
        
        {WhiteSpace}  {/* Ignorar  */ }
 }


<STRING> {
    \"               { yybegin(YYINITIAL); return newToken(Terminals.STRING, str); } 
   [^\n\t\r\"\\]*    { str = str + yytext();                                       }
   \\                { str = str + "\\";                                           }
   \\n               { str = str + "\n";                                           }
   \\t               { str = str + "\t";                                           }
   \\r               { str = str + "\r";                                           }
 }

 
[^]            { throw new Scanner.Exception("carácter inesperado na entrada: '" + yytext() + "'"); }
