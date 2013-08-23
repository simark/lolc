JAVACC?=javacc
JAVAC?=javac
JJTREE?=jjtree
GRAMMAR_DIR=ca/polymtl/lol/grammar
OTHER_DIR_TO_BUILD=ca/polymtl/lol ca/polymtl/lol/exceptions ca/polymtl/lol/expressions ca/polymtl/lol/types ca/polymtl/lol/cfg

TOCLEAN=*.class lol.jj LOL.java LOLConstants.java LOLTokenManager.java LOLTreeConstants.java JJTLOLState.java SimpleCharStream.java SimpleNode.java Token.java TokenMgrError.java

all:
	$(JJTREE) -OUTPUT_DIRECTORY=$(GRAMMAR_DIR) $(GRAMMAR_DIR)/lol.jjt
	$(JAVACC) -OUTPUT_DIRECTORY=$(GRAMMAR_DIR) $(GRAMMAR_DIR)/lol.jj
	$(JAVAC) $(GRAMMAR_DIR)/*.java $(addsuffix /*.java, $(OTHER_DIR_TO_BUILD))

.PHONY: clean tests

tests:
	@bash test.sh

clean:
	rm -f $(addprefix $(GRAMMAR_DIR)/, $(TOCLEAN))
	rm -f $(addsuffix  /*.class, $(OTHER_DIR_TO_BUILD))
	rm -rf png coquet coquetpng
