/* Generated By:JJTree: Do not edit this line. ASTinfiniteArityOperator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ca.polymtl.lol.grammar;

import ca.polymtl.lol.InfArityOpType;

public
class ASTinfiniteArityOperator extends SimpleNode {
  public ASTinfiniteArityOperator(int id) {
    super(id);
  }

  public ASTinfiniteArityOperator(LOL p, int id) {
    super(p, id);
  }

  InfArityOpType type;

  /** Accept the visitor. **/
  @Override
public Object jjtAccept(LOLVisitor visitor, Object data) throws LOLVisitException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=612e6b1fb2ba3b11db09909591576561 (do not edit this line) */
