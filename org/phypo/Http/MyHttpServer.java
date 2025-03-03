package org.phypo.Http;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.script.SimpleScriptContext;

import org.phypo.PPg.PPgUtils.Log;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

//*******************************************
public class MyHttpServer {
	
	File KEYSTORE = new File("/home/phipo/Dev/CSam/Java/Eclipse/TOTO/keystore/keystore.p12");
	String PASS = "tototo";

	HttpServer cServer; 
	
    public MyHttpServer( int iPort, boolean iHttpsFlag ) throws Exception {
    	    	
    	if( iHttpsFlag ) {
    	    
    		cServer = HttpsServer.create(new InetSocketAddress(iPort), 0);
    		
    		System.out.println("https !!!");
    		//======================
    		SSLContext sslContext = SSLContext.getInstance("TLS");
    		char[] password = PASS.toCharArray();
    		KeyStore ks = KeyStore.getInstance("JKS");
    		FileInputStream fis = new FileInputStream(KEYSTORE);
    		ks.load(fis, password);
    		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    		kmf.init(ks, password);
    		
    		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    		tmf.init(ks);

    		sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    		//======================
    		System.out.println("https finish");

    	}
    	else
    		cServer = HttpServer.create(new InetSocketAddress(iPort), 0);	
    }
    //------------------------------------- +   
    public boolean createContext( String iPath, HttpHandler iHdl ) {
    	
        cServer.createContext( iPath, iHdl );
        
        cServer.setExecutor(new ThreadPoolExecutor(4, 8, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100)));      
    ////    cServer.setExecutor(null); // creates a default executor
        System.out.println( "Server starting ...");
        System.out.println( "Server starting ok");
        return true;
    }
    //------------------------------------- 
    void security() throws NoSuchAlgorithmException {
    	
        SSLContext sslContext = SSLContext.getInstance("TLS");
    }
    //------------------------------------- 
	public void start() {
		cServer.start();
	}
   //------------------------------------- 
    static class MyHandler implements HttpHandler {
    	
        @Override public void handle( HttpExchange iEchange ) throws IOException {
        	
        	System.out.println( "Cmd <<<" + iEchange.getRequestMethod().toString() + ">>>");
        	System.out.println( "  Headers <<<" + iEchange.getRequestHeaders().toString() + ">>>");
        	System.out.println( "     Body <<<" + iEchange.getRequestBody().toString() + ">>>" );

            String lResponse = "*** Use test1";
            
            iEchange.sendResponseHeaders(200, lResponse.getBytes().length );
            
            OutputStream os = iEchange.getResponseBody();
            os.write(lResponse.getBytes());
            os.close();
        }
    }
    //------------------------------------- 
    public static void main(String[] args) throws Exception {
    	
    	MyHttpServer lServer = new MyHttpServer( 8000, true);
  
    	
    	lServer.createContext( "/test/test1/", new  MyHandler() );
  	
    	lServer.createContext( "/test/test2/", new  HttpHandler() {
    		//===============================
    		 @Override public void handle(HttpExchange iEchange ) throws IOException {    	        	
    	            String response = "+++ Use test2";
    	            
    	           	System.out.println( "Cmd <<<" + iEchange.getRequestMethod().toString() + ">>>");
    	        	System.out.println( "  Headers <<<" + iEchange.getRequestHeaders().toString() + ">>>");
    	        	System.out.println( "     Body <<<" + iEchange.getRequestBody().toString() + ">>>" );
  	            
    	            String lResponse = "*** Use test2";
    	            iEchange.sendResponseHeaders(200, response.length());
    	            
    	            OutputStream os = iEchange.getResponseBody();
    	            os.write(response.getBytes());
    	            os.close();
    	        }
     		//===============================
    	});
    	lServer.start();
	
   }

}
//*******************************************
