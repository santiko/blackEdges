package org.tiko.calllogger;

import java.sql.Date;

import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView teks = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		teks = (TextView) findViewById(R.id.teksLogger);
		StringBuffer sb = new StringBuffer();
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
		/* Query the CallLog Content Provider */
		Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
				null, null, strOrder);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		sb.append("Call Log :");
		while (managedCursor.moveToNext()) {
			String phNum = managedCursor.getString(number);
			String callTypeCode = managedCursor.getString(type);
			String strcallDate = managedCursor.getString(date);
			Date callDate = new Date(Long.valueOf(strcallDate));
			String callDuration = managedCursor.getString(duration);
			String callType = null;
			int callcode = Integer.parseInt(callTypeCode);
			switch (callcode) {
			case CallLog.Calls.OUTGOING_TYPE:
				callType = "PANGGILANG KELUAR";
				break;
			case CallLog.Calls.INCOMING_TYPE:
				callType = "PANGGILAN MASUK";
				break;
			case CallLog.Calls.MISSED_TYPE:
				callType = "PANGGILAN TIDAK TERJAWAB";
				break;
			}
			sb.append("\nNo. HP: " + phNum + " \nTipe Panggilan: "
					+ callType + " \nTanggal: " + callDate
					+ " \nDurasi : " + callDuration + " detik");
			sb.append("\n----------------------------------");
		}
		managedCursor.close();
		teks.setText(sb);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
