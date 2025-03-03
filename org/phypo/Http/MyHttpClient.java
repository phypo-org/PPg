package org.phypo.Http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

import javax.net.ssl.HttpsURLConnection;

import org.phypo.PPg.PPgUtils.Log;

//*********************************
public class MyHttpClient {


	//--------------------------
	public static void Run() {

		try {
		//	String lHttpsURL = "https://127.0.0.1/test/test1/";
			String lHttpsURL = "https://localhost:8000/test/test2/";
				
			URL lMyUrl = new URL(lHttpsURL);
			
			System.out.println("Client starting : " + lHttpsURL );
			
			HttpsURLConnection lConnect = (HttpsURLConnection) lMyUrl.openConnection();

			InputStream       lIs         = lConnect.getInputStream();
			InputStreamReader lIsReader   = new InputStreamReader(lIs);
			BufferedReader    lBuffReader = new BufferedReader(lIsReader);

			String lStr;

			while( (lStr = lBuffReader.readLine()) != null) {
				System.out.println(lStr);
			}
			lBuffReader.close();
		}catch( Exception lEx ) {
			System.out.println( "Exception in Run ");
			lEx.printStackTrace(System.out);
			lEx.getStackTrace();
			System.out.println( lEx.toString());
		}
	}
	//--------------------------
	public static void main(String[] args) throws Exception {
		MyHttpClient.Run();
	}
}
//*********************************

/*
public class MyHttpClient {

	HttpClient cClient;

	//------------------------------------- 
	MyHttpClient( int iPort ){
		cClient = HttpClient.newHttpClient();
	}

	//------------------------------------- 
	void sendRequest( String iRequest ){
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://test/test1/"))
				.build();

		CompletableFuture<HttpResponse<String>> lFutur = cClient.sendAsync(request, BodyHandlers.ofString());
		lFutur.then

		lFutur.thenApply<String>(HttpResponse::body);

		.thenAccept(System.out::println)
		.join(); 	
	}	
	//------------------------------------- 

    public static void main(String[] args) throws Exception {
    	MyHttpClient lClient = new 
    }
}
 */
