package DocumentSearch;

import java.util.Comparator;

import DocumentSearch.RelevancyResult;

/**
 * Custom comparator to order {@link}RelevancyResult in decreasing order of {@link}
 * RelevancyResult count. This is required to return search results in order of count
 */
public class RelevancyResultComparator 
    implements Comparator<RelevancyResult> 
{
	@Override
	public int compare(RelevancyResult o1, RelevancyResult o2) 
	{
		return o2.getCount() - o1.getCount();
	}

}