/* Generated By:JJTree: Do not edit this line. ASTbreakExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ca.polymtl.lol.grammar;

public
class ASTbreakExpression extends SimpleNode {
  public ASTbreakExpression(int id) {
    super(id);
  }

  public ASTbreakExpression(LOL p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  @Override
public Object jjtAccept(LOLVisitor visitor, Object data) throws LOLVisitException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=05f085e680ed198e56957c681b5c3ad1 (do not edit this line) */
