package DocumentSearch;

import java.util.List;
import DocumentSearch.RelevancyResult;

/**
 * Interface for the search implementations.
 * 
 * Used for invoking search methods from main maethod
 * 
 * @author  Mayank Dasila
 */
public interface ISearchBy 
{

	public enum SearchByMethod 
	{
		STRINGMATCH, REGEX, INDEXED
	}

	public List<RelevancyResult> searchBy(String searchToken) throws Exception;
}