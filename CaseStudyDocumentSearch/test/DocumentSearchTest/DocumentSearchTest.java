package DocumentSearchTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import DocumentSearch.RelevancyResult;
import DocumentSearch.ISearchBy;
import DocumentSearch.SearchByController;
import DocumentSearch.ISearchBy.SearchByMethod;

/**
 *
 * This test class is used to test the search following search methods Search Method: 1) String Match 2) Regular Expression
 * 3) Indexed
 * 
 * @author Mayank Dasila
 * @testedClasses SearchByStringMatch.java,SearchByRegex.java,SearchByIndexed.java
 */
public class DocumentSearchTest
{
    // ~ Instance attributes ------------------------------------------------------------------------

    private static Map<String, HashMap<String, Integer>>  allStringsCountMap   = null;
    private ISearchBy                                     searchBy             = null;

    // ~ Static attributes/initializers -------------------------------------------------------------

    private final static String                          TESTSTRING_1          = "france";
    private final static String                          TESTSTRING_2          = "in the";
    private final static String                          TESTSTRING_3          = "some of the";
    private final static String                          TESTSTRING_NULL       = null;
    private final static String                          FILE1                 = "french_armed_forces.txt";
    private final static String                          FILE2                 = "hitchhikers.txt";
    private final static String                          FILE3                 = "warp_drive.txt";

    // ~ Test Methods ------------------------------------------------------------------------

    /**
     * Initial Setup for the tests
     */
    @BeforeClass
    public static void setUpBeforeClass()
    {
        allStringsCountMap = new HashMap<String, HashMap<String, Integer>>();
        addValuesToMap(TESTSTRING_1, 18, 0, 0);
        addValuesToMap(TESTSTRING_2, 15, 2, 1);
        addValuesToMap(TESTSTRING_3, 0, 1, 1);
    }

    /**
     * Helper method for test setup
     */
    private static void addValuesToMap(String testString,
                                       int c1,
                                       int c2,
                                       int c3)
    {
        HashMap<String, Integer> stringCountMap = new HashMap<String, Integer>();
        stringCountMap.put(FILE1, c1);
        stringCountMap.put(FILE2, c2);
        stringCountMap.put(FILE3, c3);
        allStringsCountMap.put(testString, stringCountMap);
    }

    /**
     * Test for SearchByStringMatch
     */
    @Test
    public void testSearchByStringMatch()
    {
        searchBy = SearchByController.getSearchByMethod(SearchByMethod.STRINGMATCH.toString());
        try
        {
            runAllSearches();
        }
        catch (Exception e)
        {
            fail("testSearchByStringMatch failed");
        }
    }

    /**
     * Test for SearchByRegex
     */
    @Test
    public void testSearchByRegex()
    {
        searchBy = SearchByController.getSearchByMethod(SearchByMethod.REGEX.toString());
        try
        {
            runAllSearches();
        }
        catch (Exception e)
        {
            fail("testSearchByRegex failed");
        }
    }

    /**
     * Test for SearchByIndexed
     */
    @Test
    public void testSearchByIndexed()
    {
        searchBy = SearchByController.getSearchByMethod(SearchByMethod.INDEXED.toString());
        try
        {
            runAllSearches();
        }
        catch (Exception e)
        {
            fail("testSearchByIndexed failed");
        }
    }

    /**
     * Helper Method to run all searches for all the strings
     */
    private void runAllSearches() throws Exception
    {
        List<RelevancyResult> resultList = searchBy.searchBy(TESTSTRING_1);
        assertNotNull(resultList);
        verifyResult(resultList, TESTSTRING_1);

        resultList = searchBy.searchBy(TESTSTRING_2);
        assertNotNull(resultList);
        verifyResult(resultList, TESTSTRING_2);

        resultList = searchBy.searchBy(TESTSTRING_3);
        assertNotNull(resultList);
        verifyResult(resultList, TESTSTRING_3);

        assertNull(searchBy.searchBy(TESTSTRING_NULL));

    }

    /**
     * Helper Method to validate if results match
     */
    private void verifyResult(List<RelevancyResult> relevancyResultList,
                              String testString)
    {
        for (RelevancyResult relevancyResult : relevancyResultList)
        {
            assertEquals((int) allStringsCountMap.get(testString).get(relevancyResult.getFilename()),
                         relevancyResult.getCount());
        }
    }

}
