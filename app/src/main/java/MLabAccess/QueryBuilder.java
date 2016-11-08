package MLabAccess;
import jokeModel.Joke;
 /**
 * Created by dturi on 2016-11-01.
 */

public class QueryBuilder {


    /*The URL of the mongoDb database collection to access */
    public String buildJokesSaveURL() {
        return "https://api.mlab.com/api/1/databases/smartjoker/collections/jokes?apiKey=Ce20LdOG8wMtypYyzXTk6wkkQUo-TVUf";
    }

     public String getAllJokesURL(){
         return "https://api.mlab.com/api/1/databases/smartjoker/collections/jokes?s={\"rating\": -1}&apiKey=Ce20LdOG8wMtypYyzXTk6wkkQUo-TVUf";
     }

    //Creates a new entry in the database
    public String createJokeEntry(Joke joke) {
        String jsonString;
        if(joke.id != null){
            jsonString = String.format("{\"_id\": \"%s\", \"jokeText\": \"%s\", "
                                    + "\"creator\": \"%s\", \"rating\" : %s}",
                            joke.id, joke.jokeText, joke.creator,  joke.rating);
        } else{
            jsonString = String.format("{\"jokeText\": \"%s\", "
                            + "\"creator\": \"%s\"}, \"rating\" : \"%s\"}",
                    joke.jokeText, joke.creator, joke.rating);
        }
        return jsonString;
    }
}
