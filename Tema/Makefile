JC = javac
JVM = java
SRC_DIR := src
EXEC = Interpreter
JFLAGS := -g -cp $(SRC_DIR)/

all: build

sources := $(wildcard $(SRC_DIR)/*.java)
classes := $(sources:$(SRC_DIR)/%.java=$(SRC_DIR)/%.class)

build: $(classes)
$(classes): $(SRC_DIR)/%.class: $(SRC_DIR)/%.java
	$(JC) $(JFLAGS) $<

run:
	$(JVM) -cp $(SRC_DIR) $(EXEC) $(input) $(base)

.PHONY: clean
clean :
	rm -f $(SRC_DIR)/*.class *~

