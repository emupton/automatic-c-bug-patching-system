import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * File handling utility class, contains various static methods used for
 * manipulating the input and output .c files
 * 
 * @author emma
 *
 */
public class FileHandlingUtils {
	/**
	 * Creates a duplicated version of 'input', however, at line 'lineNumber' 'input''s
	 * line is over-written with 'lineReplacement' in the 'output' file.
	 * 
	 * @param input
	 * @param output
	 * @param lineReplacement
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 */
	public static boolean writeLineAt(File input, File output, String lineReplacement, int lineNumber) throws IOException{
		FileWriter fw = new FileWriter(output);
		int lineNo = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line="";
			while((line = br.readLine())!=null){
				String newLine;
				if(lineNo == lineNumber-1){
					newLine = lineReplacement;
				}
				else{
					newLine = line;
				}
				fw.write(newLine +"\n");
				lineNo++;
			}
		}
		fw.close();
		return false;
	}
	
	public static boolean insertLineAt(File input, File output, String lineInsert, int lineNumber) throws IOException{
		FileWriter fw = new FileWriter(output);
		int lineNo = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line="";
			while((line = br.readLine())!=null){
				if(lineNo == lineNumber){
					fw.write(line + "\n");
					fw.write(lineInsert +"\n");
				}
				else{
					fw.write(line + "\n");
				}
				lineNo++;
			}
		}
		fw.close();
		return false;
	}
}
