package MLabAccess;

/**
 * Created by dturi on 2016-11-01.
 */


/*Interface implemented by activites that get results from the RequestTask async task
(recipeDetails, SearchResults, and IngredientsSearch)
*/
public interface AsyncResponse {
    /*the method where the magic happens:
     output from RequestTask is padded to the implementing classes through here*/
    void processFinish(String output);
}