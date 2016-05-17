package com.saigmn;

import com.collabera.labs.sai.IMyRemoteService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExternalRemoteServiceClient extends Activity {
	private IMyRemoteService remoteService;
	private boolean started = false;
	private RemoteServiceConnection conn = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button start = (Button)findViewById(R.id.startButton);
        Button stop = (Button)findViewById(R.id.stopButton);
        Button bind = (Button)findViewById(R.id.bindButton);
        Button release = (Button)findViewById(R.id.releaseButton);
        Button invoke = (Button)findViewById(R.id.invokeButton);
        
        start.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		startService();
        	}
        });
        
        stop.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		stopService();
        	}
        });       
        
        bind.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		bindService();
        	}
        });  
        
        release.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		releaseService();
        	}
        });          
        
        invoke.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		invokeService();
        	}
        });          

   
    }
    
    private void startService(){
       		if (started) {
       			Toast.makeText(ExternalRemoteServiceClient.this, "Service already started", Toast.LENGTH_SHORT).show();
       		} else {
       			Intent i = new Intent();
       			i.setClassName("com.collabera.labs.sai", "com.collabera.labs.sai.RemoteService");
       			startService(i);
       			started = true;
       			updateServiceStatus();
       			Log.d( getClass().getSimpleName(), "startService()" );
       		}
       		
    }
       
    private void stopService() {
      		if (!started) {
       			Toast.makeText(ExternalRemoteServiceClient.this, "Service not yet started", Toast.LENGTH_SHORT).show();
      		} else {
       			Intent i = new Intent();
       			i.setClassName("com.collabera.labs.sai", "com.collabera.labs.sai.RemoteService");
       			stopService(i);
       			started = false;
       			updateServiceStatus();
       			Log.d( getClass().getSimpleName(), "stopService()" );
      		}
    }
      
    private void bindService() {
        		if(conn == null) {
        			conn = new RemoteServiceConnection();
        			Intent i = new Intent();
        			i.setClassName("com.collabera.labs.sai", "com.collabera.labs.sai.RemoteService");
        			bindService(i, conn, Context.BIND_AUTO_CREATE);
        			updateServiceStatus();
        			Log.d( getClass().getSimpleName(), "bindService()" );
        		} else {
        	        Toast.makeText(ExternalRemoteServiceClient.this, "Cannot bind - service already bound", Toast.LENGTH_SHORT).show();
        		}
    }
        
    private void releaseService() {
        		if(conn != null) {
        			unbindService(conn);
        			conn = null;
        			updateServiceStatus();
        			Log.d( getClass().getSimpleName(), "releaseService()" );
        		} else {
        			Toast.makeText(ExternalRemoteServiceClient.this, "Cannot unbind - service not bound", Toast.LENGTH_SHORT).show();
        		}
    }
        
    private void invokeService() {
        		if(conn == null) {
        			Toast.makeText(ExternalRemoteServiceClient.this, "Cannot invoke - service not bound", Toast.LENGTH_SHORT).show();
        		} else {
        			try {
        				int counter = remoteService.getCounter();
        				  TextView t = (TextView)findViewById(R.id.notApplicable);
        				  t.setText( "Counter value: "+Integer.toString( counter ) );
        				  Log.d( getClass().getSimpleName(), "invokeService()" );
        			} catch (RemoteException re) {
        				Log.e( getClass().getSimpleName(), "RemoteException" );
        			}
        		}
       	}	        	

      
      class RemoteServiceConnection implements ServiceConnection {
          public void onServiceConnected(ComponentName className, 
  			IBinder boundService ) {
            remoteService = IMyRemoteService.Stub.asInterface((IBinder)boundService);
            Log.d( getClass().getSimpleName(), "onServiceConnected()" );
          }

          public void onServiceDisconnected(ComponentName className) {
            remoteService = null;
  		   updateServiceStatus();
  		   Log.d( getClass().getSimpleName(), "onServiceDisconnected" );
          }
      };
      
      private void updateServiceStatus() {
    	  String bindStatus = conn == null ? "unbound" : "bound";
    	  String startStatus = started ? "started" : "not started";
    	  String statusText = "Service status: "+
    							bindStatus+
    							","+
    							startStatus;
    	  TextView t = (TextView)findViewById( R.id.serviceStatus);
    	  t.setText( statusText );	  
    	}
      
      protected void onDestroy() {
    	  super.onDestroy();
    	  releaseService();
    	  Log.d( getClass().getSimpleName(), "onDestroy()" );
      }
 }