lolc
====

This was a project for course [INF6302](http://www.polymtl.ca/etudes/cours/details.php?sigle=INF6302) at EPM. The original objective was to write a lolcode to PHP converter to be able to execute lolcode programs. I also wrote an interpreter to execute programs directly instead of using conversion. Both the converter and interpreter are in Java. The grammar is written using JavaCC.

Usage
=====

Dependencies:
* A Java compiler/VM
* JavaCC
* PHP CLI (optional)
* Graphviz (optional)

Compile the stuff:

```
$ make
```

Run tests:

```
$ make tests

```

There are lolcode progam examples in the tests directory. The most interesting ones (although not very interesting) are:

* tempconverter.lol: a temperature conversion program
* primechecker.lol: a prime checker program
* guess.lol: a number guessing "game"


Run the PHP converter:
```
$ java ca.polymtl.lol.grammar.VisiteurPHPTrans < tests/tempconverter.lol > tests/tempconverter.php
$ php tests/tempconverter.php 
```

Run the interpreter:
```
$ java ca.polymtl.lol.LOLexec tests/tempconverter.lol
```

Run the formatter/pretty-printer:
```
$ java ca.polymtl.lol.grammar.VisiteurAffichageCoquet < tests/tempconverter.lol 
```

Run the AST graph generator:
```
$ java ca.polymtl.lol.grammar.VisiteurDOTOutput < tests/tempconverter.lol > tests/tempconverter.ast.dot
$ dot -Tpng tests/tempconverter.ast.dot > tests/tempconverter.ast.png
```

Run the CFG/dominators/post-dominators graph generator:

```
$ java ca.polymtl.lol.cfg.VisiteurCFG tests/tempconverter.cfg.dot tests/tempconverter.dom.dot tests/tempconverter.pdom.dot < tests/tempconverter.lol
$ dot -Tpng tests/tempconverter.cfg.dot > tests/tempconverter.cfg.png
$ dot -Tpng tests/tempconverter.dom.dot > tests/tempconverter.dom.png
$ dot -Tpng tests/tempconverter.pdom.dot > tests/tempconverter.pdom.png
```
