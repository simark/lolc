/* Generated By:JJTree: Do not edit this line. ASTvariableDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */

package ca.polymtl.lol.grammar;

public
class ASTvariableDeclaration extends SimpleNode {
  public ASTvariableDeclaration(int id) {
    super(id);
  }

  public ASTvariableDeclaration(LOL p, int id) {
    super(p, id);
  }

  public String name;

  /** Accept the visitor. **/
  @Override
public Object jjtAccept(LOLVisitor visitor, Object data) throws LOLVisitException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0aaceaf43481c6a2956aa4c359397f81 (do not edit this line) */
