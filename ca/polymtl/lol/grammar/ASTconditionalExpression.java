/* Generated By:JJTree: Do not edit this line. ASTconditionalExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ca.polymtl.lol.grammar;

public
class ASTconditionalExpression extends SimpleNode {
  public ASTconditionalExpression(int id) {
    super(id);
  }

  public ASTconditionalExpression(LOL p, int id) {
    super(p, id);
  }

  public int nbElseIf = 0;
  public boolean hasElse = false;

  /** Accept the visitor. **/
  @Override
public Object jjtAccept(LOLVisitor visitor, Object data) throws LOLVisitException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=dbeca4116cfd0607a24d67ae4f3a4502 (do not edit this line) */