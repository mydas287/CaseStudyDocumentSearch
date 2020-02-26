# CaseStudyDocumentSearch
A program to search an input string using 3 different search methods

Document Search

Problem Statement:

The goal of this exercise is to create a working program to search a set of documents for the given search term or phrase (single token), and return results in order of relevance.
Relevancy is defined as number of times the exact term or phrase appears in the document. 

Create three methods for searching the documents: 
•	Simple string matching 
•	Text search using regular expressions 
•	Preprocess the content and then search the index
Prompt the user to enter a search term and search method, execute the search, and return results. For instance:

Enter the search term: <user enters search term> 
Search Method: 1) String Match 2) Regular Expression 3) Indexed 
  Search results: 
  File2.txt – X matches 
  File1.txt - X matches 
  File3.txt – X matches 
  Elapsed time: 40 ms

Three files have been provided for you to read and use as sample search content.
Run a performance test that does 2M searches with random search terms, and measures execution time. Which approach is fastest? Why?
Provide some thoughts on what you would do on the software or hardware side to make this program scale to handle massive content and/or 
very large request volume (5000 requests/second or more). 

----------------------------------------------------------------------------------------------------------------------------------------
Solution:
The code was written in Java and tested using JUnit. The performance test for 2M random search tearms had the following results

Time taken for method type: STRINGMATCH 3234989 ms

Time taken for method type: REGEX 3252355 ms

Time taken for method type: INDEXED 590709 ms

To run the program, import the entire folder and run the main class DocumentSearch.java. The program takes input in the console.
Alternatively, the Test class can be run to test each search method.


