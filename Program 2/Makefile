#Jeffrey Yeung
#jeyyeung
#CMPS 12B
#January 22, 2018
#Compiles Search.java and makes a jarfile Search

JAVASRC = Search.java
SOURCES = README Makefile $(JAVASRC)
MAINCLASS = Search
CLASSES = Search.class
JARFILE = Search

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
