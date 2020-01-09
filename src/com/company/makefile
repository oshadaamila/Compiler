JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        LexicalException.java \
        Node.java \
        ParseException.java \
        Parser.java \
	Scanner.java \
	Token.java \
	winzigc.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class
