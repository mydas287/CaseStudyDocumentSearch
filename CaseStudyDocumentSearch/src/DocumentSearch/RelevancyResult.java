package DocumentSearch;

/**
 * Relevancy result contains the file name and the word count for the input token
 */
public class RelevancyResult
{
    // ~ Instance attributes ------------------------------------------------------------------------
    private String fileName;
    private int    count;

    // ~ Constructors -------------------------------------------------------------------------------

    public RelevancyResult(String fName,
                           int cnt)
    {
        this.fileName = fName;
        this.count = cnt;
    }

    // ~ Methods ------------------------------------------------------------------------------------

    public String getFilename()
    {
        return fileName;
    }

    public void setFilename(String fName)
    {
        this.fileName = fName;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int cnt)
    {
        this.count = cnt;
    }
}
