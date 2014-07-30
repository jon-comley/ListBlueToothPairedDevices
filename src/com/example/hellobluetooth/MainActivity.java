package com.example.hellobluetooth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.bluetooth.*;
import android.content.Intent;

public class MainActivity extends Activity {

	public int REQUEST_ENABLE_BT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onButtonClick(View v){
		
		BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();	
		String status = "";
						
		if(bluetooth != null)
		{
		    // Continue with bluetooth setup.
			if (bluetooth.isEnabled()) {
			    display_result();
			    status = "Enabled";
			}
			else
			{
			    // Disabled. Do something else.Intent enableBt
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT );
			    status = "Enabling";
			}
		}
		else {
			//no bluetooth adaptor on device
		    status = "No bluetooth device available";
		}
		Toast.makeText(this, status, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	    // Check which request we're responding to
	    if (requestCode == REQUEST_ENABLE_BT) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	//user clicked enable bluetooth
	        	display_result();
        	}else if (resultCode == RESULT_CANCELED){
        		//user clicked cancel bluetooth
				Toast.makeText(this, "Bluetooth denied", Toast.LENGTH_LONG).show();
	        }
	    }
	}
	
	public void display_result()
	{	
		Button display = (Button) findViewById(R.id.bt_button);

	    display.setText("Bluetooth enabled");

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		
		List<String> s = new ArrayList<String>();
		
		for(BluetoothDevice bt : pairedDevices)
		   s.add(bt.getName());

		ListView listView1 = (ListView) findViewById(R.id.listView1);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, s);
		
		listView1.setAdapter(adapter);
	    
	}
}
