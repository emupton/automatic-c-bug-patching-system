import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class Driver {
	public static void main(String args[]) throws IOException{
		String programSpecifics = "";
		if(args.length>0){
			programSpecifics = args[0];
			String[] parsedSpecifics = programSpecifics.split(",");
			String bugDescription = parsedSpecifics[0];
			System.out.println(bugDescription);
			if(bugDescription.equals( "Result of 'malloc' is converted to a pointer of type 'long'")){
				System.out.println("Boo");
			}
			int lineNumber = (int) Integer.parseInt(parsedSpecifics[2]);
			
			String inputFilePath = args[1];
			String outputFilePath = args[2];
			
			String input = Files.readAllLines(Paths.get(inputFilePath)).get(lineNumber-1);
			
			if(bugDescription.contains("Result of 'malloc' is converted to a pointer of type")){
				System.out.println(input);
				ANTLRInputStream inputStream = new ANTLRInputStream(input);
				// create a lexer that feeds off of input 
				CLexer lexer = new CLexer(inputStream);
				// create a buffer of tokens pulled from the lexer 
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				CParser parser = new CParser(tokens);
				
				ParseTree tree = parser.declarationSpecifier();
				// Create a generic parse tree walker that can trigger callbacks
				ParseTreeWalker walker = new ParseTreeWalker();
				System.out.println(tree.toStringTree(parser));
				
				//getting the appropriate declarator type
				CListenerType listenerType = new CListenerType(parser);
				walker.walk(listenerType, tree);
				String type = listenerType.getType();
				Token typeToken = listenerType.getTypeToken();
				tree = parser.initDeclarator();
				
				//updating the value of sizeof with the previously obtained type
				CListenerUpdate listenerUpdate = new CListenerUpdate(parser, type, typeToken);
				walker.walk(listenerUpdate, tree);
				TokenStream updatedStream = listenerUpdate.getUpdatedStream();
				String text = updatedStream.getText();
				text = listenerUpdate.getUpdatedLine();
				System.out.println(text);
				String partA = text.substring(0, type.length());
				String partB = text.substring(type.length());
				text = partA + " " + partB;
				//has trouble parsing declarationSpecifier along with rest of statement...
				System.out.println(text);
			}
			
		}
		else{
			//DEBUG/TESTING
			
			//testing memory leak problem, need to get identifier p...
			String input = "realloc(p, sizeof(int));";
			ANTLRInputStream inputStream = new ANTLRInputStream(input);
			// create a lexer that feeds off of input 
			CLexer lexer = new CLexer(inputStream);
			// create a buffer of tokens pulled from the lexer 
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CParser parser = new CParser(tokens);
			
			ParseTree tree = parser.statement();
			// Create a generic parse tree walker that can trigger callbacks
			ParseTreeWalker walker = new ParseTreeWalker();
			System.out.println(tree.toStringTree(parser));
			
			//getting the identifier
			CListenerType listenerType = new CListenerType(parser);
			walker.walk(listenerType, tree);
			String identifier = listenerType.getIdentifier();
			System.out.println(identifier);
			Token typeToken = listenerType.getTypeToken();
			/*tree = parser.initDeclarator();
			
			//updating the value of sizeof with the previously obtained type
			CListenerUpdate listenerUpdate = new CListenerUpdate(parser, type, typeToken);
			walker.walk(listenerUpdate, tree);
			TokenStream updatedStream = listenerUpdate.getUpdatedStream();
			String text = updatedStream.getText();
			text = listenerUpdate.getUpdatedLine();
			System.out.println(text);
			String partA = text.substring(0, type.length());
			String partB = text.substring(type.length());
			text = partA + " " + partB;
			//has trouble parsing declarationSpecifier along with rest of statement...
			System.out.println(text);*/
		}
		
	}
}
