import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class Driver {
	public static void main(String args[]) throws IOException{
		//String filename = "/Users/Emma/Desktop/problem3/2016-01-26-143220-29794-1/report-29d634.html";
		String filename = "";
		if(args.length == 0){
			System.out.println("File not found");
			filename = "/Users/emma/Desktop/problem3/2016-01-26-140713-29740-1/report-25d370.html";
		}
		else{
			System.out.println("File found");
			filename = args[0];
		}
		File input = new File(filename);
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		
		Elements matches = doc.getElementsByClass("simpletable");
		Element table = matches.first();
		
		Elements columns = table.getElementsByTag("td");
		Element bugDescriptor = columns.last();
		
		String description = bugDescriptor.ownText();
		
		System.out.print(description);
		//parsing "simpletable" in order to obtain the bug description text
		
		matches = doc.getElementsByClass("code");
		table = matches.first();
				
		/*Elements bugComments = table.getElementsByAttributeValueMatching("class", "PathIndex PathIndexEvent");
		Element bugComment = bugComments.first();
		System.out.println(bugComment.html());
		
		Elements parents = bugComment.parents();
		Elements rows = table.getElementsByTag("tr");
		Element row = rows.first();
		System.out.println(row.html());
		
		Elements siblings = bugComment.siblingElements();
		System.out.println(siblings.html());*/
		
		
		Elements rows = table.getElementsByTag("tr");
		//System.out.println(rows.html());
		Elements divs = table.getElementsByAttributeValueContaining("id","Path");
		//System.out.println(divs.html());
		for(Element div : divs){
			Node parent = div.parentNode().parentNode();
			//System.out.println(((Element) parent).html());
			Node lineColumn = parent.previousSibling().previousSibling();
			Elements contents = ((Element) lineColumn).getElementsByAttributeValue("class", "num");
			System.out.print("," + contents.text());
			//System.out.println("Printing contents");
			//System.out.println(((Element) lineColumn).text());
		}
		
		
	}
}
