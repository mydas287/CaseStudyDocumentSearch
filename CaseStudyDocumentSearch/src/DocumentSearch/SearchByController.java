package DocumentSearch;

import DocumentSearch.ISearchBy.SearchByMethod;

/**
 * factory for creating the Searcher object.
 * Takes methodType as input and creates an instance based this value.
 *
 * @author  Mayank Dasila
 */
public class SearchByController {

	public static ISearchBy getSearchByMethod(String methodType)
	{
		if (methodType.equals(SearchByMethod.STRINGMATCH.toString())) 
		{
			return new SearchByStringMatch();
		}
		if (methodType.equals(SearchByMethod.REGEX.toString())) 
		{
			return new SearchByRegex();
		}
		else
		{
			return new SearchByIndexed();
		}
	}
}