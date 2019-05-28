package pdfbox;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

public class CallCountries {
	static HttpClient client = HttpClientBuilder.create().build();
	static HttpUriRequest allHttpUriRequest;
	public static boolean CallHTTPGetService(String url) throws ClientProtocolException, IOException {
	    boolean resposta = true;
	    try{
		    HttpUriRequest httpUriRequest = new HttpGet("https://restcountries.eu/rest/v2/name/" + url + "?fullText=true");
		    allHttpUriRequest = httpUriRequest;
		    HttpResponse response = client.execute(allHttpUriRequest);
		    String json_string = EntityUtils.toString(response.getEntity());
	        JSONArray  temp1 = new JSONArray("[" + json_string + "]");
	        System.out.println(temp1);
		}catch(org.json.JSONException e){
			resposta = false;
			e.printStackTrace();
		}  
	    return resposta;
	}
	public static String getFullName() throws ClientProtocolException, Exception {
	    HttpResponse response = client.execute(allHttpUriRequest);
	    String json_string = EntityUtils.toString(response.getEntity());
        JSONArray  temp1 = new JSONArray(json_string);
		return null;
		
	}
}
