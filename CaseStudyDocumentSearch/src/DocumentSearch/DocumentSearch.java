package DocumentSearch;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import DocumentSearch.RelevancyResult;
import DocumentSearch.ISearchBy;
import DocumentSearch.ISearchBy.SearchByMethod;
import DocumentSearch.SearchByController;

/**
 * Main Class to run the Search Program. Prompt the user to enter a search term and search method, execute the search, and
 * return results.
 * 
 * @author Mayank Dasila
 */
public class DocumentSearch
{

    public static void main(String args[])
    {
        System.out.println("Enter the search term:");
        // open up standard input
        Scanner scanner = new Scanner(System.in);

        String singleToken = null;
        int option = 0;

        // read the token string from the command-line;
        try
        {
            singleToken = scanner.nextLine();
            while (singleToken == null || singleToken.isEmpty())
            {
                System.out.println("Input string cannot be empty. Please enter valid string:\n");
                singleToken = scanner.nextLine();
            }
            String userOption = null;
            System.out.println("Search Method: 1) String Match 2) Regular Expression 3) Indexed");
            userOption = scanner.nextLine();
            while (userOption == null || userOption.isEmpty())
            {
                System.out.println("Search option cannot be empty. Please select valid option.");
                userOption = scanner.nextLine();
            }
            option = Integer.parseInt(userOption);
            ISearchBy searchBy = null;
            String methodType = null;
            switch (option)
            {
                case 1:
                {
                    methodType = SearchByMethod.STRINGMATCH.toString();
                    break;
                }
                case 2:
                {
                    methodType = SearchByMethod.REGEX.toString();
                    break;
                }
                case 3:
                {
                    methodType = SearchByMethod.INDEXED.toString();
                    break;
                }
            }
            searchBy = SearchByController.getSearchByMethod(methodType);
            long startTime = System.nanoTime();
            List<RelevancyResult> relevancyResultList = searchBy.searchBy(singleToken);
            System.out.println("Search results: ");
            for (RelevancyResult relevancyResult : relevancyResultList)
            {
                System.out.println(relevancyResult.getFilename() + " - " + relevancyResult.getCount() + " matches ");
            }
            long endTime = System.nanoTime();
            long estimatedTime = endTime - startTime;
            long convert = TimeUnit.MILLISECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
            System.out.println("Elapsed time: " + convert + " ms");

        }
        catch (Exception e)
        {
            System.out.println("Error while searching:" + e);
        }
        finally
        {
            scanner.close();
        }

    }

}
