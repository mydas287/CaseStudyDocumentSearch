package DocumentSearchTest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import DocumentSearch.ISearchBy;
import DocumentSearch.ISearchBy.SearchByMethod;
import DocumentSearch.SearchByController;

/**
 * performance test that does 2M searches with random search terms, and measures execution time
 * 
 * @author Mayank Dasila
 * @testedClasses SearchByInvertedIndex.java, SearchByRegex.java, SearchByStringMatch.java
 */
public class PerformanceTest
{

    // ~ Static attributes/initializers -------------------------------------------------------------

    private final static String TESTSTRING_1 = "galaxy";
    private final static String TESTSTRING_2 = "in the";
    private final static String TESTSTRING_3 = "does not";
    private final static String TESTSTRING_4 = "against";
    private final static String TESTSTRING_5 = "on the";
    private final static String TESTSTRING_6 = "some of the";
    private static String[]     arrayString  =
                                             {
        TESTSTRING_1,
        TESTSTRING_2,
        TESTSTRING_3,
        TESTSTRING_4,
        TESTSTRING_5,
        TESTSTRING_6
                                             };
    private final static long   number       = 2000000;

    // ~ Methods ------------------------------------------------------------------------

    public static void measureTime(String methodType)
    {
        long sum = new Long(0);
        for (int i = 0; i < number; i++)
        {
            Random r = new Random();
            String inputString = arrayString[Math.abs(r.nextInt() % 5)];
            long startTime = System.nanoTime();
            ISearchBy searcher = SearchByController.getSearchByMethod(methodType);
            try
            {
                searcher.searchBy(inputString);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // total execution time for the search
            double estimatedTime = System.nanoTime() - startTime;
            sum += estimatedTime;
        }
        // convert to milli-seconds
        long totalTime = TimeUnit.MILLISECONDS.convert(sum, TimeUnit.NANOSECONDS);
        System.out.println("Time taken for method type: " + methodType + " " + totalTime + " ms");
    }

    public static void main(String args[])
    {
        measureTime(SearchByMethod.STRINGMATCH.toString());
        measureTime(SearchByMethod.REGEX.toString());
        measureTime(SearchByMethod.INDEXED.toString());
    }

}
