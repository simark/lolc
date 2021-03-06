/* Generated By:JJTree: Do not edit this line. ASTprimaryExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ca.polymtl.lol.grammar;

public
class ASTprimaryExpression extends SimpleNode {
  public ASTprimaryExpression(int id) {
    super(id);
  }

  public ASTprimaryExpression(LOL p, int id) {
    super(p, id);
  }

  public enum PrimaryExpressionType { VAR_OR_FUNC, LITERAL, OPERATOR, IT, CAST }

  public PrimaryExpressionType type;

  /** Accept the visitor. **/
  @Override
public Object jjtAccept(LOLVisitor visitor, Object data) throws LOLVisitException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9837ced5b5384bb900bf73759395a6b0 (do not edit this line) */
