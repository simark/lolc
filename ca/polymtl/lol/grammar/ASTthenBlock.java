/* Generated By:JJTree: Do not edit this line. ASTthenBlock.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package ca.polymtl.lol.grammar;

public
class ASTthenBlock extends SimpleNode {
  public ASTthenBlock(int id) {
    super(id);
  }

  public ASTthenBlock(LOL p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  @Override
public Object jjtAccept(LOLVisitor visitor, Object data) throws LOLVisitException {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=587909afd8210229420adc9dc572122a (do not edit this line) */
