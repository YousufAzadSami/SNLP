import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LDA_Computation {

	int no_topics;
	
	int no_words;
	
	int[] alpha;
	
	int[] beta;
	
	List<List<String>> words;
	
	List<List<Integer>> topics;
	
	Map<Integer,Map<Integer,Integer>> n_d_k;
	
	Map<Integer,Map<String,Integer>> n_w_k;
	
	Map<Integer,Integer> n_k;

	public LDA_Computation(int no_topics, int no_words, int[] alpha, int[] beta) {
		super();
		this.no_topics = no_topics;
		this.no_words = no_words;
		this.alpha = alpha;
		this.beta = beta;
		
		words = new ArrayList<List<String>>();
		topics = new ArrayList<List<Integer>>();
		
		n_d_k= new HashMap<Integer,Map<Integer,Integer>>();
		n_w_k = new HashMap<Integer,Map<String,Integer>>();
		n_k = new HashMap<Integer,Integer>();
		
		
		
	}
	
	public void sample(int T)
	{
		initializeTopics();
		String word;
		Integer topic;
		
		for (int t =0; t < T; t++)
		{
			for (int i=0; i < words.size(); i++)
			{
				List<String> doc = words.get(i);
				
				for (int j=0; j < doc.size(); j++)
				{
					word = doc.get(j);
					topic = topics.get(i).get(j);
					
					// decrease counts
					
					for (int k=0; k < no_topics; k++)
					{
						// compute topic distribution
						// sample from topic distribution
						// recompute counts
					}
			
				}
			}
		}
	}
	
	public void addDocument(List<String> document)
	{
		words.add(document);
	}
	
	public void initializeTopics()
	{
		// leave that to you!
	}
	
	public String printTopicAssignments()
	{
		// print the assignments
		return "";
	}
	
	public String printPhi()
	{
		// print phi multinomial
		return "";
	}
	
	public String printTheta()
	{
		// for each document print the distribution theta per document
		return "";
	}
	
	
}
