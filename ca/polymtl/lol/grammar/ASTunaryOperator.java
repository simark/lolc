/* Generated By:JJTree: Do not edit this line. ASTunaryOperator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ca.polymtl.lol.grammar;

public
class ASTunaryOperator extends SimpleNode {
  public ASTunaryOperator(int id) {
    super(id);
  }

  public ASTunaryOperator(LOL p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  @Override
public Object jjtAccept(LOLVisitor visitor, Object data) throws LOLVisitException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1d16859a5ceb66317235feb05589c0b6 (do not edit this line) */
