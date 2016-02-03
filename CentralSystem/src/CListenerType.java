import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;


public class CListenerType extends CBaseListener {
	private String type;
	private String identifier;
	private Token typeToken;
	private CParser parser;
	
	public CListenerType(CParser parser){
		super(parser);
		this.parser = parser;
	}
	
	public CListenerType(){
		
	}
	
	public void enterTypeSpecifier(CParser.TypeSpecifierContext ctx) { 
		System.out.println(ctx.getText());
	  	Token token = ctx.start;
	  	typeToken = token;
	  	token.getTokenIndex();
	  	type = token.getText();
	  	System.out.println("Type: " +type);
	}
	
	public void enterPrimaryExpression(CParser.TypeSpecifierContext ctx) {
		Token token = ctx.start;
		identifier = token.getText();
		System.out.println("Identifier: " + identifier);
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
