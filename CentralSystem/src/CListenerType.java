import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

/**
 * Listener used when trying to obtain the type of a variable being declared by analysing
 * the LHS of a statement/expression
 * @author emma
 *
 */

public class CListenerType extends CBaseListener {
	private String type;
	private String identifier;
	private Token typeToken;

	
	/* Obtains the first token from the expression and gets the token
	 * 
	 * (non-Javadoc)
	 * @see CBaseListener#enterTypeSpecifier(CParser.TypeSpecifierContext)
	 */
	public void enterTypeSpecifier(CParser.TypeSpecifierContext ctx) { 
		System.out.println(ctx.getText());
	  	Token token = ctx.start;
	  	typeToken = token;
	  	token.getTokenIndex();
	  	type = token.getText();
	}
	
	/**
	 * Obtains the first token from the primary expression, which will be the identifier of the variable
	 * 
	 * @param ctx
	 */
	public void enterPrimaryExpression(CParser.PrimaryExpressionContext ctx) {
		Token token = ctx.start;
		identifier = token.getText();
	}
	

	public String getIdentifier(){
		return identifier;
	}
	
	public String getType(){
		return type;
	}
	
	public Token getTypeToken(){
		return typeToken;
	}
}
