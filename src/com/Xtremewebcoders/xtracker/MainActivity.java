package com.Xtremewebcoders.xtracker;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
 
import android.widget.Button;
import android.widget.Toast;
 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.TelephonyManager;
 
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
 
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
 
 


public class MainActivity extends Activity {
	
   WebView webview;
   WebView webviewpost;
   String DeviceName;
   String DeviceModel;
   String phoneNumber;
   String DeviceID;
   LocationListener mlocListener;
   LocationManager mlocManager;
   Button start;
   
   private NotificationManager MyNotify;
   private int SIMPLE_NOTFICATION_ID;
   
   
   
   
   
   
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
       if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
           webview.goBack();
           return true;
       }
       return super.onKeyDown(keyCode, event);
   }
   
   private class MyWebViewClient extends WebViewClient {
 
		  @Override
		    public void onReceivedError(WebView view, int errorCode, String 
		        		description, String failingUrl) { 
		        		      
		        		        	 view.loadUrl("file:///android_asset/noservice.html"); 
		        		        	// view.loadData("Your internet connection  seems to be down. Please restart your app.", "text/html", "UTF-8"); 


		        		         } 
		 ///// //error
		   
 
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		    	if(url.contains("http://xtremewebcoders.com")){ // Could be cleverer and use a regex
		            return super.shouldOverrideUrlLoading(view, url); // Leave webview and use browser
		        } else {
		        view.loadUrl(url);
		        return true;
		    }
		   }
		   
		} // end class
   
   //now start notifications
  
   //end notifications
   

    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webView1);
        DeviceName =  android.os.Build.MODEL;
        DeviceModel =  android.os.Build.DEVICE;
        TelephonyManager phoneManager = (TelephonyManager) 
        getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber = phoneManager.getLine1Number();
        DeviceID = DeviceName + phoneNumber;
        
        webviewpost = (WebView) findViewById(R.id.WebViewPost);
        webviewpost.setWebViewClient(new MyWebViewClient());
        
        start = (Button) findViewById(R.id.start);
        start.getBackground().setAlpha(255);
	    webview.setWebViewClient(new MyWebViewClient()); 
	    webview.loadUrl("http://ns.xtremewebcoders.com/x-tracker/map.php?deviceName="+DeviceID);
	    webview.clearCache(true);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        
        
        
        
           //String url = "http://192.168.0.3/X-Tools/x-tracker/get.php";
       String url = "http://ns.xtremewebcoders.com/x-tracker/get.php";
       // String postData = "location=theplace";
         
       
       start.setOnClickListener(new OnClickListener() {
       	 @Override
     			   public void onClick(View v) {
     				   start.setVisibility(View.GONE);
     				  

     			      
     			    Toast.makeText( getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();
     		        
     		    	mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
     			    mlocListener = new MyLocationListener();
     		        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 10000, 1, mlocListener); 
     			        
     			     }
         }); 
       
       
       
       //start notifications
       MyNotify = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       final Notification notifyDetails = new Notification(R.drawable.ic_launcher,
               "X-Tracker", System.currentTimeMillis());
       notifyDetails.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
       Context context = getApplicationContext();
       CharSequence contentTitle = "X-Tracker";
       CharSequence contentText = "Running";
      
       Intent notifyIntent = new Intent(Intent.ACTION_MAIN);
       notifyIntent.setClass(getApplicationContext(), MainActivity.class);
       PendingIntent intent = PendingIntent.getActivity(
             MainActivity.this, 0, notifyIntent,
               android.content.Intent.FLAG_ACTIVITY_NEW_TASK);

       notifyDetails.setLatestEventInfo(context, contentTitle,
               contentText, intent);
       MyNotify.notify(SIMPLE_NOTFICATION_ID,
               notifyDetails);
       //end notifications
       

      // webview.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));   // Post the data!!!!
        
        
         
       /* 
        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

       LocationListener mlocListener = new MyLocationListener();
       mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener); 
       */
       
       
       
       
       /*
       ////Exit button
       exitButton.setOnClickListener(new OnClickListener(){

    		@Override
    		public void onClick(View arg0) {
    			// TODO Auto-generated method stub
    			 finish();
    	         System.exit(0);
    			
    		}
    		 
    	});  
       /// end exit button
       */
       
   
        
   
       
       
       
     } // end onCreate saved state
    
 
    

    
    
    
    
   /*
    //Start the toggle
    public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	        // Enable transmission
	    	Toast.makeText( getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();
	        
	    	mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		    mlocListener = new MyLocationListener();
	        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 10000, 1, mlocListener); 
	       
	    	    	
	    	
	    	
	    } else {
	    	
	    }
	} //end the toggle
    
 */
    
    //start button
    



	/* Class My Location Listener */
     public class MyLocationListener implements LocationListener
     {

       @Override
       public void onLocationChanged(final Location loc)
       {

         loc.getLatitude();
         loc.getLongitude();


         try{

             String Text = "My current location is: " +
             " Lat = " + loc.getLatitude() +
             " Long = " + loc.getLongitude();
        	 Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();

        	   ///////////////////////
             //String url = "http://192.168.0.3/X-Tools/x-tracker/get.php";
        	 String url = "http://ns.xtremewebcoders.com/x-tracker/get.php";
              String postLoc = "lat=" + loc.getLatitude() + "&long=" + loc.getLongitude() + "&devicename=" + DeviceID;
          //    String postLong = "long=" + loc.getLongitude();
            webviewpost.postUrl(url, EncodingUtils.getBytes(postLoc, "BASE64"));   // Post the data!!!!
           // webview.postUrl(url, EncodingUtils.getBytes(postLong, "BASE64"));   // Post the data!!!!
             ///////////////////////
           // Thread.sleep(5000);
            
         }
         catch (Exception e) {
             // TODO: handle exception
         }
         finally{
            //also call the same runnable 
            //handler.postDelayed(this, 2000); 
         }
         
          
          
        
         
        

     
         /*
         
         
         
        final Handler handler = new Handler(); 
       
         //Runnable runable = new Runnable() {
         handler.postDelayed(new Runnable(){
        

            // @Override 
             public void run() { 
                 try{

                     String Text = "My current location is: " +
                     " Lat = " + loc.getLatitude() +
                     " Long = " + loc.getLongitude();
                	 Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();

                	   ///////////////////////
                     //String url = "http://192.168.0.3/X-Tools/x-tracker/get.php";
                	 String url = "http://ns.xtremewebcoders.com/x-tracker/get.php";
                      String postLoc = "lat=" + loc.getLatitude() + "&long=" + loc.getLongitude() + "&devicename=" + DeviceName;
                  //    String postLong = "long=" + loc.getLongitude();
                    webview.postUrl(url, EncodingUtils.getBytes(postLoc, "BASE64"));   // Post the data!!!!
                   // webview.postUrl(url, EncodingUtils.getBytes(postLong, "BASE64"));   // Post the data!!!!
                     ///////////////////////
                   // Thread.sleep(5000);
                    
                 }
                 catch (Exception e) {
                     // TODO: handle exception
                 }
                 finally{
                    //also call the same runnable 
                    //handler.postDelayed(this, 2000); 
                 }
             } 
         }, 0); 
       // handler.postDelayed(runable, 500);  */
      
       }   //OnLocationChanged
       
       
       
     

       
       
       
       
       
       
       
       

       @Override
       public void onProviderDisabled(String provider)
       {
         Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
       }

       @Override
       public void onProviderEnabled(String provider)
       {
         Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onStatusChanged(String provider, int status, Bundle extras)
       {

       }
       
      
       
       
       
       
 

       
       
       
       
     } // end location listener class
     

 
    
    
     
     
     ///////////////////////////////
     
     
      
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.action_menu, menu);
         return true;
     }
    
    

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
      // TODO Auto-generated method stub
      switch(item.getItemId()){
      case (R.id.action_map):
     	 WebMap();
       //break;
     	 return true;
      case (R.id.action_admin):
     	 WebAdmin();
       //break;
     	 return true;
      case (R.id.action_exit):
     	 AppExit();
       //break;
     	 return true;  
      case (R.id.action_refresh):
      	 WebRefresh();
        //break;
      	 return true; 
      case (R.id.action_info):
       	 WebInfo();
         //break;
       	 return true;
      }
      return true;
     }
     
     
     /////////////////////// /Menu Actions
     private void WebMap() {
 		// TODO Auto-generated method stub
 		webview.loadUrl("http://ns.xtremewebcoders.com/x-tracker/map.php?deviceName="+DeviceID);
 	}
     private void WebAdmin(){
     	webview.loadUrl("http://ns.xtremewebcoders.com/x-tracker/admin.php");
     	//Browser.loadUrl("http://ns.xtremewebcoders.com/tc-dance/appinfo.php");
         
     	
     }
     private void WebInfo(){
      	webview.loadUrl("http://ns.xtremewebcoders.com/x-tracker/info.php");
      	//Browser.loadUrl("http://ns.xtremewebcoders.com/tc-dance/appinfo.php");
          
      	
      }
     private void WebRefresh(){
    	 webview.reload();
     }
     
     
     private void AppExit(){
     	
		 finish();
         System.exit(0);
     	
         }
     
    
    
    
    
    

    
}  
