package DocumentSearch;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import DocumentSearch.RelevancyResult;
import DocumentSearch.RelevancyResultComparator;

/**
 * Builds an in-memory map of word, filename, positions.
 * A typical map should look like below:
 * 
 * map: <where: <file2.txt: 15,38,87>
 *              <file3.txt: 28, 56>>
 *      <what: <file1.txt: 64,134>
 *             <file2.txt: 61>>
 *             
 * @author  Mayank Dasila *
 */
public class SearchByIndexed 
    implements ISearchBy 
{
	//~ Instance attributes ------------------------------------------------------------------------
	
	private static Map<String, HashMap<String, Queue<Integer>>> mainMap = null;

	//~ Constructors -------------------------------------------------------------------------------
	
	protected SearchByIndexed() 
	{
	}
	
	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * Use hashmap to look for search token. 
	 * For single word, count = size of positions queue for given filename.
	 * For two or more words, pick only common files containing all words in token.
	 * Look if the words are in order for the files (using positions Queue).
	 * Increment count when found a match.
	 * @param String searchToken the word/words to look for
	 * @return List<Result> with filename and number of occurrences.
	 */
	@Override
	public List<RelevancyResult> searchBy(String searchToken) throws Exception 
	{
		if (searchToken == null || searchToken.isEmpty())
		{
			return null;
		}
		//start creating map
		buildMap();
		List<RelevancyResult> list = new LinkedList<RelevancyResult>();
	    String[] searchTokens = searchToken.toLowerCase().split("\\W+");
	    
	    // initialize to files of 1st string
		Set<String> matchingFiles = mainMap.get(searchTokens[0]).keySet();
		
		// populate matching files
		for (String token : searchTokens) 
		{
			// retain all to get the common files
			matchingFiles.retainAll(mainMap.get(token).keySet());
		}
		File[] inputFiles = new File("sample_text").listFiles();
		for (File file : inputFiles) 
		{
			if (!matchingFiles.contains(file.getName())) 
			{
				list.add(new RelevancyResult(file.getName(), 0));
			}
		}
		//Matching files
		String[] fileArray = matchingFiles.toArray((new String[matchingFiles.size()]));
		int count = 0;
		for (int k = 0; k < fileArray.length; k++) 
		{
			String token = null;
			String file = fileArray[k];
			boolean morePositionsFound = true;
			boolean matched = false;
			// if a single word
			if (searchTokens.length==1)
			{
				list.add(new RelevancyResult(file, mainMap.get(searchTokens[0]).get(file).size()));
				continue;
			}
			for (int i = 0; i < searchTokens.length - 1; i++) 
			{
				token = searchTokens[i];
				Integer pos = 0;
				if (mainMap.get(token).get(file).size() != 0) 
				{
					pos = mainMap.get(token).get(file).poll();
				} 
				else 
				{
					// All positions for token is visited, no more positions left to explore.
					morePositionsFound = false;
					break;
				}
					
				String nextToken = searchTokens[i + 1];
				if (mainMap.get(nextToken).get(file).contains(pos + 1)) 
				{
					matched = true;
				} 
				else 
				{
					matched = false;
					// continue with current word
					i = i - 1;
				}
			} 
			if (matched)
			{
				//If found a match then increment count");
				count++;
			}
			// check if there are more entries for token in the same file
			// there might be multiple matches
			if (morePositionsFound) 
			{
				// continue with current file
				k = k - 1;
			} 
			else 
			{
				list.add(new RelevancyResult(file, count));
				// reset count
				count = 0;
			}
		}
		// return results in order of count, highest first
		Collections.sort(list, new RelevancyResultComparator());
		return list;
	}
	
	/**
	 * Build hashmap.
	 */
	private void buildMap() 
	{
		if (mainMap == null) 
		{
			mainMap = createInvertedIndex();
		} 
		else
		{
			//Do nothing
		}
	}

	/**
	 * Create a Word map.
	 * reads each file line by line and build file map.
	 * Add positions for every word in a given file to the file map
	 * Add the file map to the word map with the word as key.
	 */
	private synchronized Map<String, HashMap<String, Queue<Integer>>> createInvertedIndex() 
	{
		Map<String,HashMap<String,Queue<Integer>>> wordMap = new HashMap<String, HashMap<String, Queue<Integer>>>();
		File resources = new File("sample_text");
		Scanner scnr = null;
		for (File textFile : resources.listFiles()) 
		{
			String filename = textFile.getName();
			int wordPos = 0;
			try 
			{
				scnr = new Scanner(textFile);
				String line = "";
				while (scnr.hasNextLine()) 
				{					
					line = scnr.nextLine();
					// get only words, remove all special characters
					String words[] = line.toLowerCase().split("\\W+");
					for (String word : words) 
					{
						HashMap<String, Queue<Integer>> fileMap;
						Queue<Integer> positions;
						wordPos++;
						// check if word exists
						if (wordMap.containsKey(word)) 
						{
							// get childmap
							fileMap = wordMap.get(word);
							// check if entry for file exists
							if (fileMap.containsKey(filename)) 
							{
								// add position for the word
								positions = fileMap.get(filename);
							}
							else
							{
								positions = new LinkedList<Integer>();
							}
						} 
						else 
						{
							fileMap = new HashMap<String, Queue<Integer>>();
							positions = new LinkedList<Integer>();
						}
						positions.add(wordPos);
						fileMap.put(filename, positions);
						wordMap.put(word, fileMap);
					}
				}
			} 
			catch (Exception e) 
			{
				System.out.println("error:" + e);
			} 
			finally 
			{
				scnr.close();
			}
		}
		return wordMap;
	}
}