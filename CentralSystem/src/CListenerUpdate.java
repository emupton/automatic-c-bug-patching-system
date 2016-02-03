import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;


public class CListenerUpdate extends CBaseListener {
	private CParser parser;
	private TokenStream updatedStream;
	private String updatedLine;
	private String type;
	private Token typeToken;
	
	public CListenerUpdate(CParser parser, String type, Token typeToken){
		super(parser);
		this.parser = parser;
		this.type = type;
		this.typeToken = typeToken;
	}
	
	public CListenerUpdate(){
		
	}
	
	public void enterTypeSpecifier(CParser.TypeSpecifierContext ctx) { 
		System.out.println(ctx.getText());
	  	Token token = ctx.start;
	  	int index = token.getTokenIndex();
	  	TokenStream ts = parser.getInputStream();
	  	TokenStreamRewriter rewriter = new TokenStreamRewriter(ts);
	  	rewriter.replace(index, type);
	  	System.out.println("Final = " + rewriter.getText());
	  	updatedLine = rewriter.getText();
	  	System.out.println("token text" + token.getText());
	  	ts.index();
	  	updatedStream = rewriter.getTokenStream();
	}
	
	public TokenStream getUpdatedStream(){
		return updatedStream;
	}
	
	public String getUpdatedLine(){
		return updatedLine;
	}
}
