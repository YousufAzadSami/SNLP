

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HMM_Tagger implements POS_Tagger {

	// 
	// Remember
	// Here XY, NN, KOUS are POS 
	// Here hl, bab, tdt, uwi are tokens
	// 
	
	// XY={NN=6, KOUS=2, XY=32, ART=6, ADJD=2, $(=312, ADJA=2, VVINF=2, FM=1, $,=15, VVPP=4, $.=10, KON=13, CARD=39, VVFIN=4, APPR=4, PTKNEG=1, ADV=1, APPRART=1, NE=98, VAFIN=7, PDS=1}
	HashMap<String,HashMap<String,Integer>> state_transition_counts;
	// XY={�hl=8, bab=2, tdt=17, uwi=1, &amp;tel;=4, A.=1, sch=32, Kart=1, kth=2, RAr=1, ehe=5, rat=6, 10e=1, stg=8, *=6, +=3, am=1, B.=1, an=1, ips=3, ap=61, RA=1, rb=37, jbk=1, zba=4, AZ=2, rm=1, ==3, @=1, rr=1, A=2, B=15, C=2, D=5, E=3, mic=15, F=1, F-681=1, H=1, I=3, bk=3, K=3, M=1, N=1, sa=1, br=1, R=1, S=7, U=3, W=8, X=3, CWM=1, u.=1, ari=3, sp=3, _=5, BvR=1, a=2, st=2, b=5, c=1, sw=1, lhe=2, tb=3, n=1, p=3, .web=1, q=2, rtr=58, tf=1, s=3, abs=7, clau=1, http://astro.estec.esa.nl/Hipparcos/hipparcos.html=1, .store=1, to=1, tp=1, rds=10, TW=1, .com=1, ber=3, dr=4, UE=1, paa=1, ug=3, rei=4, V.=1, ukn=1, ith=2, prc=1, eh=2, em=1, F.=1, opl=1, ulf=2, es=1, chs=1, adt=2, VI=1, prw=1, A6+=1, Ul=1, hbr=2, vo=3, .net=1, fa=2, vs=7, ff=3, wa=2, wb=46, fr=8, FR=5, habe=1, sie=1, gam=4, rgg=1, sim=3, gra=4, ww=1, wz=1, wal=14, gro=9, H.=1, bho=1, http://www.vrp.de=1, http:/www.tui.com/events=1, &quot;&gt;=2, afp=45, w�p=7, ens=2, rhe=6, NAC=1, k�=1, ptz=18, xp=1, hb=1, he=2, stg.=2, heb=2, hn=1, tma=9, faf=2, hs=1, heg=1, HS=1, ski=22, ost=1, http://www.almaden.ibm.com/journal/sj/mit/sectione/zimmerman.html=1, rin=1, epd=6, yr=2, tms=1, J.=1, im=1, .arts=1, ze=1, ZF=1, zi=1, wtr=5, ZR=1, AZR=4, doe=21, jg=3, jk=27, K.=1, jm=2, jp=1, geg=2, jr=1, geh=1, guz=1, jz=1, tom=1, dpa=64, kna=3, hhb=3, ko=1, kp=1, .info=1, http://www.microsoft.com/ie-intl/de/security/update.htm=1, cob=1, dah=1, pid=5, akt=13, son=1, md=1, me=4, mh=2, mr=1, uth=2, spi=1, HKO=1, tro=6, &amp;bullet;=14, .rec=1, pid-nie=1, whp=6, joe=1, c/o=1, fwt=6, .nom=1, tst=2, .firm=1, cas=1, og=2, b�=4, cri=22, r�g=1, P.=3, kat=1, asdfghj=1, .org=1, @@@@@=1, ing=1, krp=6, hll=6, a.=1, StR=1, A330=1, PC=1, pe=2, http://www.Ibonline.de/dialogquarter/DDV.html=1, pl=1, �=6, ojw=1, PN=2, pn=1}
	HashMap<String,HashMap<String,Integer>> state_emmission_counts;
	
	
	// XY=563
	HashMap<String,Integer> state_transition_total;
	// XY=1009
	HashMap<String,Integer> state_emmission_total;
	
	
	// XY={NN=0.010657193605683837, KOUS=0.003552397868561279, XY=0.056838365896980464, ART=0.010657193605683837, ADJD=0.003552397868561279, $(=0.5541740674955595, ADJA=0.003552397868561279, VVINF=0.003552397868561279, FM=0.0017761989342806395, $,=0.02664298401420959, VVPP=0.007104795737122558, $.=0.017761989342806393, KON=0.023090586145648313, CARD=0.06927175843694494, VVFIN=0.007104795737122558, APPR=0.007104795737122558, PTKNEG=0.0017761989342806395, ADV=0.0017761989342806395, APPRART=0.0017761989342806395, NE=0.17406749555950266, VAFIN=0.012433392539964476, PDS=0.0017761989342806395}
	HashMap<String,HashMap<String,Double>> state_transitions;
	// XY={�hl=1.0034115994380895E-4, bab=3.3447053314602983E-5, tdt=2.006823198876179E-4, uwi=2.2298035543068657E-5, &amp;tel;=5.574508885767164E-5, A.=2.2298035543068657E-5, sch=3.679175864606328E-4, Kart=2.2298035543068657E-5, kth=3.3447053314602983E-5, RAr=2.2298035543068657E-5, ehe=6.689410662920597E-5, rat=7.804312440074029E-5, 10e=2.2298035543068657E-5, stg=1.0034115994380895E-4, *=7.804312440074029E-5, +=4.4596071086137314E-5, am=2.2298035543068657E-5, B.=2.2298035543068657E-5, an=2.2298035543068657E-5, ips=4.4596071086137314E-5, ap=6.912391018351283E-4, RA=2.2298035543068657E-5, rb=4.236626753183045E-4, jbk=2.2298035543068657E-5, zba=5.574508885767164E-5, AZ=3.3447053314602983E-5, rm=2.2298035543068657E-5, ==4.4596071086137314E-5, @=2.2298035543068657E-5, rr=2.2298035543068657E-5, A=3.3447053314602983E-5, B=1.7838428434454925E-4, C=3.3447053314602983E-5, D=6.689410662920597E-5, E=4.4596071086137314E-5, mic=1.7838428434454925E-4, F=2.2298035543068657E-5, F-681=2.2298035543068657E-5, H=2.2298035543068657E-5, I=4.4596071086137314E-5, bk=4.4596071086137314E-5, K=4.4596071086137314E-5, M=2.2298035543068657E-5, N=2.2298035543068657E-5, sa=2.2298035543068657E-5, br=2.2298035543068657E-5, R=2.2298035543068657E-5, S=8.919214217227463E-5, U=4.4596071086137314E-5, W=1.0034115994380895E-4, X=4.4596071086137314E-5, CWM=2.2298035543068657E-5, u.=2.2298035543068657E-5, ari=4.4596071086137314E-5, sp=4.4596071086137314E-5, _=6.689410662920597E-5, BvR=2.2298035543068657E-5, a=3.3447053314602983E-5, st=3.3447053314602983E-5, b=6.689410662920597E-5, c=2.2298035543068657E-5, sw=2.2298035543068657E-5, lhe=3.3447053314602983E-5, tb=4.4596071086137314E-5, n=2.2298035543068657E-5, p=4.4596071086137314E-5, .web=2.2298035543068657E-5, q=3.3447053314602983E-5, rtr=6.577920485205253E-4, tf=2.2298035543068657E-5, s=4.4596071086137314E-5, abs=8.919214217227463E-5, clau=2.2298035543068657E-5, http://astro.estec.esa.nl/Hipparcos/hipparcos.html=2.2298035543068657E-5, .store=2.2298035543068657E-5, to=2.2298035543068657E-5, tp=2.2298035543068657E-5, rds=1.226391954868776E-4, TW=2.2298035543068657E-5, .com=2.2298035543068657E-5, ber=4.4596071086137314E-5, dr=5.574508885767164E-5, UE=2.2298035543068657E-5, paa=2.2298035543068657E-5, ug=4.4596071086137314E-5, rei=5.574508885767164E-5, V.=2.2298035543068657E-5, ukn=2.2298035543068657E-5, ith=3.3447053314602983E-5, prc=2.2298035543068657E-5, eh=3.3447053314602983E-5, em=2.2298035543068657E-5, F.=2.2298035543068657E-5, opl=2.2298035543068657E-5, ulf=3.3447053314602983E-5, es=2.2298035543068657E-5, chs=2.2298035543068657E-5, adt=3.3447053314602983E-5, VI=2.2298035543068657E-5, prw=2.2298035543068657E-5, A6+=2.2298035543068657E-5, Ul=2.2298035543068657E-5, hbr=3.3447053314602983E-5, vo=4.4596071086137314E-5, .net=2.2298035543068657E-5, fa=3.3447053314602983E-5, vs=8.919214217227463E-5, ff=4.4596071086137314E-5, wa=3.3447053314602983E-5, wb=5.240038352621134E-4, fr=1.0034115994380895E-4, FR=6.689410662920597E-5, habe=2.2298035543068657E-5, sie=2.2298035543068657E-5, gam=5.574508885767164E-5, rgg=2.2298035543068657E-5, sim=4.4596071086137314E-5, gra=5.574508885767164E-5, ww=2.2298035543068657E-5, wz=2.2298035543068657E-5, wal=1.6723526657301492E-4, gro=1.1149017771534327E-4, H.=2.2298035543068657E-5, bho=2.2298035543068657E-5, http://www.vrp.de=2.2298035543068657E-5, http:/www.tui.com/events=2.2298035543068657E-5, &quot;&gt;=3.3447053314602983E-5, afp=5.128548174905791E-4, w�p=8.919214217227463E-5, ens=3.3447053314602983E-5, rhe=7.804312440074029E-5, NAC=2.2298035543068657E-5, k�=2.2298035543068657E-5, ptz=2.1183133765915224E-4, xp=2.2298035543068657E-5, hb=2.2298035543068657E-5, he=3.3447053314602983E-5, stg.=3.3447053314602983E-5, heb=3.3447053314602983E-5, hn=2.2298035543068657E-5, tma=1.1149017771534327E-4, faf=3.3447053314602983E-5, hs=2.2298035543068657E-5, heg=2.2298035543068657E-5, HS=2.2298035543068657E-5, ski=2.5642740874528956E-4, ost=2.2298035543068657E-5, http://www.almaden.ibm.com/journal/sj/mit/sectione/zimmerman.html=2.2298035543068657E-5, rin=2.2298035543068657E-5, epd=7.804312440074029E-5, yr=3.3447053314602983E-5, tms=2.2298035543068657E-5, J.=2.2298035543068657E-5, im=2.2298035543068657E-5, .arts=2.2298035543068657E-5, ze=2.2298035543068657E-5, ZF=2.2298035543068657E-5, zi=2.2298035543068657E-5, wtr=6.689410662920597E-5, ZR=2.2298035543068657E-5, AZR=5.574508885767164E-5, doe=2.452783909737552E-4, jg=4.4596071086137314E-5, jk=3.1217249760296116E-4, K.=2.2298035543068657E-5, jm=3.3447053314602983E-5, jp=2.2298035543068657E-5, geg=3.3447053314602983E-5, jr=2.2298035543068657E-5, geh=2.2298035543068657E-5, guz=2.2298035543068657E-5, jz=2.2298035543068657E-5, tom=2.2298035543068657E-5, dpa=7.246861551497313E-4, kna=4.4596071086137314E-5, hhb=4.4596071086137314E-5, ko=2.2298035543068657E-5, kp=2.2298035543068657E-5, .info=2.2298035543068657E-5, http://www.microsoft.com/ie-intl/de/security/update.htm=2.2298035543068657E-5, cob=2.2298035543068657E-5, dah=2.2298035543068657E-5, pid=6.689410662920597E-5, akt=1.5608624880148058E-4, son=2.2298035543068657E-5, md=2.2298035543068657E-5, me=5.574508885767164E-5, mh=3.3447053314602983E-5, mr=2.2298035543068657E-5, uth=3.3447053314602983E-5, spi=2.2298035543068657E-5, HKO=2.2298035543068657E-5, tro=7.804312440074029E-5, &amp;bullet;=1.6723526657301492E-4, .rec=2.2298035543068657E-5, pid-nie=2.2298035543068657E-5, whp=7.804312440074029E-5, joe=2.2298035543068657E-5, c/o=2.2298035543068657E-5, fwt=7.804312440074029E-5, .nom=2.2298035543068657E-5, tst=3.3447053314602983E-5, .firm=2.2298035543068657E-5, cas=2.2298035543068657E-5, og=3.3447053314602983E-5, b�=5.574508885767164E-5, cri=2.5642740874528956E-4, r�g=2.2298035543068657E-5, P.=4.4596071086137314E-5, kat=2.2298035543068657E-5, asdfghj=2.2298035543068657E-5, .org=2.2298035543068657E-5, @@@@@=2.2298035543068657E-5, ing=2.2298035543068657E-5, krp=7.804312440074029E-5, hll=7.804312440074029E-5, a.=2.2298035543068657E-5, unkwn=1.1149017771534328E-5, StR=2.2298035543068657E-5, A330=2.2298035543068657E-5, PC=2.2298035543068657E-5, pe=3.3447053314602983E-5, http://www.Ibonline.de/dialogquarter/DDV.html=2.2298035543068657E-5, pl=2.2298035543068657E-5, �=7.804312440074029E-5, ojw=2.2298035543068657E-5, PN=3.3447053314602983E-5, pn=2.2298035543068657E-5}
	HashMap<String,HashMap<String,Double>> state_emmissions;
	

	// {XY=0, PDAT=1, ADJD=2, $(=3, ADJA=4, NNE=5, PRF=6, PPOSAT=7, $,=8, PPER=9, VVIMP=10, $.=11, VMFIN=12, PROAV=13, PTKNEG=14, CARD=15, ADV=16, KOUS=17, PWS=18, APPRART=19, NE=20, APZR=21, VAFIN=22, PRELAT=23, NN=24, KOKOM=25, ART=26, VAPP=27, PTKVZ=28, FM=29, PRELS=30, PIAT=31, TRUNC=32, VMINF=33, PTKZU=34, VVFIN=35, PWAV=36, KOUI=37, PDS=38, PWAT=39, VMPP=40, VVIZU=41, PPOSS=42, VVINF=43, PTKANT=44, start=45, VVPP=46, KON=47, VAINF=48, APPR=49, APPO=50, PIS=51, VAIMP=52, ITJ=53, PTKA=54}
	HashMap<String,Integer> pos_index;
	// {1=XY, 2=PDAT, 3=ADJD, 4=$(, 5=ADJA, 6=NNE, 7=PRF, 8=PPOSAT, 9=$,, 10=PPER, 11=VVIMP, 12=$., 13=VMFIN, 14=PROAV, 15=PTKNEG, 16=CARD, 17=ADV, 18=KOUS, 19=PWS, 20=APPRART, 21=NE, 22=APZR, 23=VAFIN, 24=PRELAT, 25=NN, 26=KOKOM, 27=ART, 28=VAPP, 29=PTKVZ, 30=FM, 31=PRELS, 32=PIAT, 33=TRUNC, 34=VMINF, 35=PTKZU, 36=VVFIN, 37=PWAV, 38=KOUI, 39=PDS, 40=PWAT, 41=VMPP, 42=VVIZU, 43=PPOSS, 44=VVINF, 45=PTKANT, 46=start, 47=VVPP, 48=KON, 49=VAINF, 50=APPR, 51=APPO, 52=PIS, 53=VAIMP, 54=ITJ, 55=PTKA}
	HashMap<Integer,String> inv_pos_index;
	
	HashMap<String,Integer> token_freq;
	
		
	public void train(List<TaggedSentence> tagged_sentences) {
		
		
		state_transition_counts = new HashMap<String,HashMap<String,Integer>>();
		state_emmission_counts = new HashMap<String,HashMap<String,Integer>>();
		
		state_transition_total = new HashMap<String,Integer>();
		state_emmission_total = new HashMap<String,Integer>();
		
		state_transitions = new HashMap<String,HashMap<String,Double>>();
		state_emmissions = new HashMap<String,HashMap<String,Double>>();
		
		token_freq = new HashMap<String, Integer>();
		
		pos_index = new HashMap<String,Integer>();
		
		inv_pos_index = new HashMap<Integer,String>();
		
		TaggedSentence tagged_sentence;
		
		int no_sentences=0;
		
		for (int i = 0; i < tagged_sentences.size(); i++)
		{	
			no_sentences++;
			tagged_sentence = tagged_sentences.get(i);
			// System.out.println(tagged_sentence);
			update(tagged_sentence);
		}
		
		computeModel();
		
		System.out.println("Trained on "+no_sentences+"sentences\n");
		
		
	
	}

	private void computeModel() {

		Integer count;
		Integer total;
		Double value;
		HashMap<String,Integer> counts;		
		HashMap<String,Double> map;
		
		for (String prevTag: state_transition_counts.keySet())
		{
						
			counts = state_transition_counts.get(prevTag);
			total = state_transition_total.get(prevTag);
			
			map = new HashMap<String,Double>();
			
			for (String tag: counts.keySet())
			{
				count = counts.get(tag);
				
				value = new Double( (double) count / (double) total);
				
				map.put(tag, value);
			}
			
			state_transitions.put(prevTag, map);
		}
		
		for (String tag: state_transitions.keySet())
		{
			if (!pos_index.containsKey(tag))
			{
				pos_index.put(tag,new Integer(pos_index.keySet().size()));
				
				inv_pos_index.put(new Integer(pos_index.keySet().size()),tag);
			}
		}
		
		for (String tag: state_emmission_counts.keySet())
		{
			
			counts = state_emmission_counts.get(tag);
			total = state_emmission_total.get(tag);
			
			map = new HashMap<String,Double>();
			
			for (String token: counts.keySet())
			{
				count = counts.get(token);
				
				value = new Double( (double) (count + 1) / ((double) total + (double) (token_freq.keySet().size()+1)));
				
				map.put(token, value);
			}
			
			// smoothing
			
			value = new Double( (double) 1 / ((double) total + (double) (token_freq.keySet().size()+1)));
			
			map.put("unkwn", value);
			
			state_emmissions.put(tag, map);
			
			
		}
		

	}

	private void update(TaggedSentence tagged_sentence) {
		
		String prevTag;
		String token;
		String tag;
		
		for (int i=0; i < tagged_sentence.size(); i++)
		{
			token = tagged_sentence.getToken(i);
			
			if (i > 0) prevTag = tagged_sentence.getPOS(i-1);
			else prevTag = "start";
			
			tag = tagged_sentence.getPOS(i);
			
			updateStateTransitions(prevTag,tag);
			updateEmmissions(tag,token); 
			updateTokenFreq(token);
			
		}
	}

	private void updateTokenFreq(String token) {
		
		Integer freq;
		
		if (token_freq.containsKey(token))
		{
			freq = token_freq.get(token);
			token_freq.put(token, new Integer(freq+1));
			
		}
		else
		{
			token_freq.put(token, new Integer(1));
		}
		
		
	}

	private void updateEmmissions(String tag, String token) {
		HashMap<String,Integer> map;
		Integer intValue;
		
		if (state_emmission_counts.containsKey(tag))
		{
			
			map = state_emmission_counts.get(tag);
			
			if (map.containsKey(token))
			{
				intValue = map.get(token);
				
				map.put(token, ++intValue);
				
			}
			else
			{
				map.put(token, new Integer(1));
			}
			
			if (state_emmission_total.containsKey(tag))
			{
			
				intValue = state_emmission_total.get(tag);
				{
					state_emmission_total.put(tag, ++intValue);
				}
			}
			else
			{
				state_emmission_total.put(tag, new Integer(1));
			}
			
		}
		else
		{
			map = new HashMap<String,Integer>();
			map.put(token,new Integer(1));
			
			state_emmission_counts.put(tag, map);
			
			state_emmission_total.put(tag, new Integer(1));
			
		}
		
	}

	private void updateStateTransitions(String prevTag, String tag) {
		
		HashMap<String,Integer> map;
		Integer intValue;
		
		if (state_transition_counts.containsKey(prevTag))
		{
			
			map = state_transition_counts.get(prevTag);
			
			if (map.containsKey(tag))
			{
				intValue = map.get(tag);
				
				map.put(tag, ++intValue);
				
			}
			else
			{
				map.put(tag, new Integer(1));
			}
			
			if (state_transition_total.containsKey(prevTag))
			{
			
				intValue = state_transition_total.get(prevTag);
				{
					state_transition_total.put(prevTag, ++intValue);
				}
			}
			else
			{
				state_transition_total.put(prevTag, new Integer(1));
			}
			
		}
		else
		{
			map = new HashMap<String,Integer>();
			map.put(tag,new Integer(1));
			
			state_transition_counts.put(prevTag, map);
			
			state_transition_total.put(prevTag, new Integer(1));
			
		}
		
		
	}

	public TaggedSentence predict(Sentence sentence) {
	
		return viterbi(sentence);
	}

	private TaggedSentence viterbi(Sentence sentence) {
				
	    int k = state_transitions.keySet().size();
	    int posSize = k;
		
		double delta[][] = new double[sentence.size()][posSize];
			
		int gamma[][] =	new int[sentence.size()][posSize];
		
		TaggedSentence tagged_sentence = new TaggedSentence(sentence);
		
		
		// Implement Viterbi
		
		// iterate over the tokens of a sentence
		for(int tokenIndex = 0; tokenIndex < sentence.size(); tokenIndex++) {
			
			String currentToken = sentence.getToken(tokenIndex);
			
			// for inv_pos_index the index starts from 1
			for(Map.Entry<Integer, String> firstPass : inv_pos_index.entrySet()) {
				
				if(tokenIndex == 0) {
					// for starting case 
					// value = emission * transition(start)
					double tempProb = emission(firstPass.getValue(), currentToken)
										* transition("start", firstPass.getValue());
					delta[tokenIndex][firstPass.getKey() - 1] = tempProb;
					gamma[tokenIndex][firstPass.getKey() - 1] = 0;	
				}
				else {
					
					double maxValue = 0;
					int maxArg = 0;
					
					for(Map.Entry<Integer, String> secondPass : inv_pos_index.entrySet()) {
						
						// someValue = 
						// emission probability for firstPass pos 
						// * transition probability for firstPass pos wrt secondPass pos 
						// * previous cell 
						double currentValue = 
								emission(firstPass.getValue(), currentToken) * 
								transition(secondPass.getValue(), firstPass.getValue()) * 
								delta[tokenIndex - 1][secondPass.getKey() - 1];
						
						if(currentValue > maxValue) {
							maxValue = currentValue;
							maxArg = secondPass.getKey() - 1;
						}
					}
					// set value to delta
					delta[tokenIndex][firstPass.getKey() - 1] = maxValue;
					gamma[tokenIndex][firstPass.getKey() - 1] = maxArg;
					
				}
				
			}
			
		}
		
		
//		System.out.println("Print Delta: ");
//		for(int i = 0; i < sentence.size(); i++) {
//			for(int j = 0; j < posSize; j++) {
//				System.out.print(delta[i][j] + "\t");
//			}
//			System.out.println();
//		}
//		System.out.println("\n\n");
//		System.out.println("Print Gamma: ");
//		for(int i = 0; i < sentence.size(); i++) {
//			for(int j = 0; j < posSize; j++) {
//				System.out.print(gamma[i][j] + "\t");
//			}
//			System.out.println();
//		}
//		System.out.println("\n\n");
		
		int indexMaxGamma = -1;
		double maxValue = -1; 
		// get the pos index with maximum probability from the last row
		for(int i = 0; i < posSize; i++) {
			if(delta[sentence.size() - 1][i] > maxValue) {
				maxValue = delta[sentence.size() - 1][i]; 
				indexMaxGamma = i;
			}
		}
		
		String lastPos = inv_pos_index.get(indexMaxGamma + 1);
		tagged_sentence.setTag(sentence.size() - 1, lastPos);
		
		
		int indexOfPos = -1;
		for(int tokenInedex = sentence.size() - 1; tokenInedex >= 1; tokenInedex--) {
			
//			System.out.println("\nWord: " + tagged_sentence.getToken(tokenInedex) + " || POS: " + tagged_sentence.getPOS(tokenInedex));
				
			indexOfPos = gamma[tokenInedex][indexMaxGamma];
			String pos = inv_pos_index.get(indexOfPos + 1);
			tagged_sentence.setTag(tokenInedex -1, pos);
			indexMaxGamma = indexOfPos;
			
			
//			System.out.println("Word: " + tagged_sentence.getToken(tokenInedex) + " || POS: " + tagged_sentence.getPOS(tokenInedex));
		}
		
			
		return tagged_sentence;
		
	}
	
	private void printArray(double[][] delta, int i) {
		

			System.out.print(i+": ");
			for (int k = 0; k < delta.length; k++)
			{
				if (delta[i][k] > Double.NEGATIVE_INFINITY)
				{
					System.out.print(this.inv_pos_index.get(new Integer(k))+":"+delta[i][k]+" ");
				}
			}
		
		System.out.print("\n");
		
		
	}

	private double delta(String i, int j, HashMap<Integer, HashMap<String, Double>> delta) {
		
		HashMap<String,Double> map;
		
		if (delta.containsKey(new Integer(j)))
		{
			map = delta.get(j);
			
			if (map.containsKey(i))
			
				return ((Double) map.get(i)).doubleValue();
			
		}
		
		return 0.0;
	}

	// emission probability 
	private double emission(String tag, String token) {
		
		// implement b method
		// int i = state_emmission_counts.get(tag).get(token) / state_emmission_total.get(tag);
		if(state_emmissions.containsKey(tag))
		{
			if(state_emmissions.get(tag).containsKey(token)) {
				return state_emmissions.get(tag).get(token);
			}
			else {
				return state_emmissions.get(tag).get("unkwn") / 100;
			}
		}
		
		return 0;
		
	}

	// transition probablity 
	private double transition(String tag, String nextTag) {
		
		// implement a method
		// HashMap<String,HashMap<String,Double>> state_transitions;
		// let's say the first string represents the nextTag
		if(state_transitions.containsKey(tag)) {
			if(state_transitions.get(tag).containsKey(nextTag)) {
				return state_transitions.get(tag).get(nextTag);
			} 
		}
		
		return 0;
	}

	public String toString()
	{
		String string = "";
		
		String prevTag, tag, token;
		
		HashMap<String,Double> map;
		
		string += "===================================================\n";
		
		string += "State transition probabilities for "+state_transitions.keySet().size()+" tags:\n";
		
		for (Iterator it = state_transitions.keySet().iterator(); it.hasNext();)
		{
			
			prevTag = (String) it.next();
			
			string += prevTag+": ";
			
			map = state_transitions.get(prevTag);
			
			
			for (Iterator it2 = map.keySet().iterator(); it2.hasNext();)
			{
				tag = (String) it2.next();
				
				string += tag + "("+ map.get(tag)+")";
			}
			
			string += "\n";
			
		}
		
		string += "===================================================";
		
		string += "State emmission probabilities for "+state_emmissions.keySet().size()+" tokens:\n";
		
		for (Iterator it = state_emmissions.keySet().iterator(); it.hasNext();)
		{
			
			tag = (String) it.next();
			
			string += tag+": ";
			
			map = state_emmissions.get(tag);
			
			
			for (Iterator it2 = map.keySet().iterator(); it2.hasNext();)
			{
				token = (String) it2.next();
				
				string += token + "("+ map.get(token)+")";
			}
			
			string += "\n";
			
		}
		
		return string;
	}

}
