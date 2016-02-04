import java.io.File;
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
		boolean debugMode = false;
		if(args.length>0 && !debugMode){
			programSpecifics = args[0];
			String[] parsedSpecifics = programSpecifics.split(",");
			String bugDescription = parsedSpecifics[0];
			System.out.println(bugDescription);
			
			String inputFilePath = args[1];
			String outputFilePath = args[2];
			
			File inputFile = new File(inputFilePath);
			File outputFile = new File(outputFilePath);
			
			if(bugDescription.contains("Result of 'malloc' is converted to a pointer of type")){
				int lineNumber = (int) Integer.parseInt(parsedSpecifics[2]);
				//in this instance, because of a comma in the bug descriptor, relevant line numbers begin at index 2 in the parsed specifics array
				String input = Files.readAllLines(Paths.get(inputFilePath)).get(lineNumber-1);
				//
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
				CListenerType listenerType = new CListenerType();
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
				
				FileHandlingUtils.writeLineAt(inputFile, outputFile, text, lineNumber);
			}
			if(bugDescription.contains("Potential memory leak")){
				int lineNumber = (int) Integer.parseInt(parsedSpecifics[parsedSpecifics.length-2]);
				//testing memory leak problem, need to get identifier p...
				String input = Files.readAllLines(Paths.get(inputFilePath)).get(lineNumber-1);
				System.out.println(input);
				ANTLRInputStream inputStream = new ANTLRInputStream(input);
				// create a lexer that feeds off of input 
				CLexer lexer = new CLexer(inputStream);
				// create a buffer of tokens pulled from the lexer 
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				CParser parser = new CParser(tokens);
				
				//ParseTree tree = parser.externalDeclaration();
				ParseTree tree = parser.expressionStatement();

				// Create a generic parse tree walker that can trigger callbacks
				ParseTreeWalker walker = new ParseTreeWalker();
				System.out.println(tree.toStringTree(parser));
				
				//getting the identifier
				CListenerAllocIdentifier listenerType = new CListenerAllocIdentifier();
				walker.walk(listenerType, tree);
				
				String identifier = listenerType.getIdentifier();
				System.out.println(identifier);
				
				String fixedLine = "	free(" + identifier + ");";
				//in future iterations determine whitespace by obtaining chars before non-whitespace char and prefixing to fixed line...
				lineNumber = (int)  Integer.parseInt(parsedSpecifics[parsedSpecifics.length-1]);
				
				FileHandlingUtils.writeLineAt(inputFile, outputFile, fixedLine, lineNumber);
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
			
			//ParseTree tree = parser.externalDeclaration();
			ParseTree tree = parser.expressionStatement();

			// Create a generic parse tree walker that can trigger callbacks
			ParseTreeWalker walker = new ParseTreeWalker();
			System.out.println(tree.toStringTree(parser));
			
			//getting the identifier
			CListenerAllocIdentifier listenerType = new CListenerAllocIdentifier();
			walker.walk(listenerType, tree);
			
			String identifier = listenerType.getIdentifier();
			System.out.println(identifier);
			
			String fixedLine = "free(" + identifier + ");";
		}
	
		
	}
}
