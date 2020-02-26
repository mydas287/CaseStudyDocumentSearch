package DocumentSearch;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import DocumentSearch.RelevancyResult;
import DocumentSearch.RelevancyResultComparator;

/**
 * This class is used to Search by String method.
 * 
 * @author  Mayank Dasila *
 */
public class SearchByStringMatch 
    implements ISearchBy
{
	
	//~ Constructors -------------------------------------------------------------------------------
	
	protected SearchByStringMatch()
	{
	}
	
	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * Read every input file by line and find if given search token is present in the file.
     * If more than one occurrence, increment count.
     * 
     * @param String searchToken the word/words to look for
     * @return List<Result> with filename and number of occurrences.
	 */
	@Override
	public List<RelevancyResult> searchBy(String singleToken) throws Exception 
	{
		if ( singleToken == null || singleToken.isEmpty())
		{
			return null;
		}
		List<RelevancyResult>  list = new LinkedList<RelevancyResult>();
		String stringMatch = singleToken.toLowerCase();
		String[] words = stringMatch.split("\\W");
		File resources = new File("sample_text");
		for (File textFile : resources.listFiles())
		{
			Scanner scnr = new Scanner(textFile);
			int count = 0;
			String line = "";
			while(scnr.hasNextLine())
			{
				line = scnr.nextLine().toLowerCase();
				//if search token is only one word
				if (words.length == 1)
				{
					for (String text : line.split("\\W"))
					{
				         if (text.equalsIgnoreCase(stringMatch)) 
				         {
				             count++;
				         }
				    }
				}
				//if search token is more than one word
				else
				{
					int index = 0;
					while ((index = line.toLowerCase().indexOf(stringMatch, index)) != -1) 
					{
						// check if next char is not alphabet
						// to avoid matching 'there' when querying for 'the'
						char nextChar = line.charAt(index + stringMatch.length());
						if( !Character.isAlphabetic(nextChar)) 
						{
							count++;
						}
						index += stringMatch.length();
					}
				}				
			}
			list.add(new RelevancyResult(textFile.getName(), count));
			scnr.close();
		}
		// return results in order of count, highest first
		Collections.sort(list, new RelevancyResultComparator());
		return list;
	}
}