import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;


public class GenerateText {

	public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub

		TigerCorpusReader reader = new TigerCorpusReader();
		
		reader.read("../data/tiger_release_dec05.xml");
		
		List<TaggedSentence> sample = reader.getSentences(10, 12);
		
		for (TaggedSentence sentence: sample)
		{
			for (int i=0; i < sentence.size(); i++)
			{
				System.out.print(sentence.getToken(i) + "\t"+sentence.getPOS(i)+"\n");
			}
		}
		
		
	}

}
