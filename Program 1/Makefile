#Jeffrey Yeung
#jeyyeung
#CMPS 12B
#January 17, 2018
#Makefile for Recursion.java

JAVASRC = Recursion.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = Recursion
CLASSES = Recursion.class
JARFILE = Recursion

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-Class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	chmod +x $(JARFILE)

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)
clean:
	rm $(CLASSES) $(JARFILE)
