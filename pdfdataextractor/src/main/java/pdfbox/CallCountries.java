package pdfbox;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class CallCountries {
	static HttpClient client = HttpClientBuilder.create().build();
	static HttpUriRequest allHttpUriRequest;
	public static boolean CallHTTPGetService(String url) throws ClientProtocolException, IOException {
	    try{
		    HttpUriRequest httpUriRequest = new HttpGet("https://restcountries.eu/rest/v2/name/" + url + "?fullText=true");
		    allHttpUriRequest = httpUriRequest;
		    HttpResponse response = client.execute(allHttpUriRequest);
		    String json_string = EntityUtils.toString(response.getEntity());
		//    System.out.println(json_string);
	        if(response.getStatusLine().getStatusCode()!=404) {
	        	return true;
	        }
		}catch(org.json.JSONException e){
			e.printStackTrace();
		}  
	    return false;
	}
}
