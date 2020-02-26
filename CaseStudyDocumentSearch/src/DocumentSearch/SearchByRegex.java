package DocumentSearch;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Search for token string using Java REGEX (Regular Exprepression Matching) .
 * 
 * @author  Mayank Dasila
 */
public class SearchByRegex 
    implements ISearchBy 
{

	//~ Constructors -------------------------------------------------------------------------------
	
	protected SearchByRegex()
	{
	}
	
	//~ Methods ------------------------------------------------------------------------------------
	
	/**
	 * Search for string using Regular Expression logic.
	 * 
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
		String searchString = searchToken.toLowerCase();
		
		//using word boundary so that "there" does not match for "the"
		Pattern pattern =  Pattern.compile("\\b" + searchString +"\\b");
		List<RelevancyResult>  list = new LinkedList<RelevancyResult>();
		File resources = new File("sample_text");
		for (File textFile : resources.listFiles())
		{
			Scanner scnr = new Scanner(textFile);
			Matcher matcher = null;
			int count = 0;
			String line = "";
			while(scnr.hasNextLine())
			{
				line = scnr.nextLine().toLowerCase();
				matcher = pattern.matcher(line);
				while(matcher.find())
				{
					count++;
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