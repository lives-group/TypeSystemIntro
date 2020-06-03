
all: compile

lex: LangScanner

parser:  lang/parser/LangScanner.java  lang/parser/LangParser.java

compile: lang/parser/LangScanner.java lang/parser/LangParser.java
	javac -cp .:beaver-rt-0.9.11.jar lang/LangCompiler.java
	
lang/parser/LangParser.java: 
	java -jar lang/parser/beaver-cc-0.9.11.jar -T lang/parser/Lang.grammar

lang/parser/LangScanner.java: 
	java -jar lang/parser/JFlex.jar -nobak lang/parser/Lang.flex

	
compileAll: 
	find . -name '*.java' > slist.txt
	javac -cp .:beaver-rt-0.9.11.jar @slist.txt
	rm -f slist.txt


cleanclass: 
	find -name "*.class" -delete
	
cleanParser: 
	rm -f  lang/parser/LangScanner.java
	rm -f  lang/parser/LangParser.java
	rm -f  lang/parser/Terminals.java

clean: cleanclass cleanParser


dist: compile
	jar cfm Lang.jar Manifest.txt .
	
