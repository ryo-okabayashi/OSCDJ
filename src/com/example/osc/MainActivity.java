package com.example.osc;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.illposed.osc.OSCPortOut;

public class MainActivity extends Activity {

	OSCPortOut sender;
	OSCPortIn receiver;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			sender = new OSCPortOut(InetAddress.getByName("192.168.11.3"), 7770);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		final ToggleButton playToggle1 = (ToggleButton)findViewById(R.id.playToggle1);
		playToggle1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int on = (playToggle1.isChecked()) ? 1 : 0;
				new OSC().execute(sender, "/play_toggle1", on);
			}
		});
		Button next1 = (Button)findViewById(R.id.next1);
		next1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new OSC().execute(sender, "/next1", 1);
			}
		});
		SeekBar volume1 = (SeekBar)findViewById(R.id.volume1);
		volume1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStartTrackingTouch(SeekBar seekBar) {
				TextView text = (TextView)findViewById(R.id.deck1);
				text.setTextColor(Color.rgb(51, 181, 229));
			}
			public void onStopTrackingTouch(SeekBar seekBar) {
				TextView text = (TextView)findViewById(R.id.deck1);
				text.setTextColor(Color.WHITE);
			}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				float out = (float) progress / 800;
				new OSC().execute(sender, "/volume1", out);
			}
		});

		final ToggleButton playToggle2 = (ToggleButton)findViewById(R.id.playToggle2);
		playToggle2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int on = (playToggle2.isChecked()) ? 1 : 0;
				new OSC().execute(sender, "/play_toggle2", on);
			}
		});
		Button next2 = (Button)findViewById(R.id.next2);
		next2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new OSC().execute(sender, "/next2", 1);
			}
		});
		SeekBar volume2 = (SeekBar)findViewById(R.id.volume2);
		volume2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStartTrackingTouch(SeekBar seekBar) {
				TextView text = (TextView)findViewById(R.id.deck2);
				text.setTextColor(Color.rgb(51, 181, 229));
			}
			public void onStopTrackingTouch(SeekBar seekBar) {
				TextView text = (TextView)findViewById(R.id.deck2);
				text.setTextColor(Color.WHITE);
			}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				float out = (float) progress / 800;
				new OSC().execute(sender, "/volume2", out);
			}
		});
		
		try {
			receiver = new OSCPortIn(9000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		receiver.addListener("/file_nums", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						TextView t = (TextView)findViewById(R.id.fileNums);
						t.setText(msgs[0].toString());
					}
				});
			}
		});
		receiver.addListener("/file_path1", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						TextView t = (TextView)findViewById(R.id.filePath1);
						t.setText(msgs[0].toString());
					}
				});
			}
		});
		receiver.addListener("/remain1", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						TextView t = (TextView)findViewById(R.id.remain1);
						t.setText(msgs[0].toString());
					}
				});
			}
		});
		receiver.addListener("/play_volume1", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						ProgressBar bar = (ProgressBar)findViewById(R.id.playVolume1);
						int i = (int) (100 * Float.valueOf(msgs[0].toString()));
						bar.setProgress(i);
					}
				});
			}
		});
		receiver.addListener("/play_toggle1", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						ToggleButton button = (ToggleButton)findViewById(R.id.playToggle1);
						button.setChecked(false);
					}
				});
			}
		});
		receiver.addListener("/volume1", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						SeekBar bar = (SeekBar)findViewById(R.id.volume1);
						bar.setProgress(0);
					}
				});
			}
		});

		// deck2
		receiver.addListener("/file_path2", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						TextView t = (TextView)findViewById(R.id.filePath2);
						t.setText(msgs[0].toString());
					}
				});
			}
		});
		receiver.addListener("/remain2", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						TextView t = (TextView)findViewById(R.id.remain2);
						t.setText(msgs[0].toString());
					}
				});
			}
		});
		receiver.addListener("/play_volume2", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						ProgressBar bar = (ProgressBar)findViewById(R.id.playVolume2);
						int i = (int) (100 * Float.valueOf(msgs[0].toString()));
						bar.setProgress(i);
					}
				});
			}
		});
		receiver.addListener("/play_toggle2", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						ToggleButton button = (ToggleButton)findViewById(R.id.playToggle2);
						button.setChecked(false);
					}
				});
			}
		});
		receiver.addListener("/volume2", new OSCListener() {
			public void acceptMessage(java.util.Date time, OSCMessage message) {
				final Object msgs[] = message.getArguments();
				handler.post(new Runnable() {
					public void run() {
						SeekBar bar = (SeekBar)findViewById(R.id.volume2);
						bar.setProgress(0);
					}
				});
			}
		});
		receiver.startListening();
	}
	
}
