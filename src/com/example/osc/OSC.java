package com.example.osc;
import android.os.AsyncTask;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class OSC extends AsyncTask<Object, Integer, Long> {

	protected Long doInBackground(Object... args) {
		try {
//			OSCPortOut sender = new OSCPortOut(InetAddress.getByName("192.168.1.4"), 7770);
			OSCPortOut sender = (OSCPortOut)args[0];
			Object msgs[] = new Object[1];
			msgs[0] = args[2];
			OSCMessage msg = new OSCMessage(String.valueOf(args[1]), msgs);
			sender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}