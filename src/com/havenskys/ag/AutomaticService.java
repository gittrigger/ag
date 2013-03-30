package com.havenskys.ag;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

public class AutomaticService extends Service implements Runnable {

	private static String G = "Automatic"+August.title;
	private static String TAG = "Automatic"+August.title;
	private Handler mHandler;
	private NotificationManager mNM;
	private Context mCtx;
	private int pRate = 72;
	@Override
	public void onCreate() {
		super.onCreate();
		mCtx = this.getApplicationContext();
		Log.w(TAG,"onCreate() ++++++++++++++++++++++++++++++++++");
		
        

        mHandler = new Handler();

        
        mThr= new Thread(null, this, TAG + "_service_thread");
        mThr.start();

	
	}

	Thread mThr = null;
	
	public double[] getload(){
		
		try {
			Process mLoadProcess;
			InputStream mLoadStream;
			byte[] mLoadBytes;
			String[] ml = null;
			
			int mLoadReadSize;
			mLoadProcess = Runtime.getRuntime().exec("cat /proc/loadavg");
			mLoadProcess.waitFor();
			mLoadStream = mLoadProcess.getInputStream();
			mLoadBytes = new byte[100];
			mLoadReadSize = mLoadStream.read(mLoadBytes, 0, 99);
			
			if(ml == null){}else{
				ml = (mLoadBytes!=null)?new String(mLoadBytes).trim().replaceAll("\\s+", " ").split(" "):new String[]{"0","0","0","0"};
			if(ml.length >= 4){	
				Log.e(TAG,"Load Test " + ml[0]+","+ml[1]+","+ml[2]+","+ml[3]);
			}else{Log.e(TAG,"Load Test " + ml[0]+" length:"+ml.length);}
				//mLoadDouble = new Double(mLoadParts[0]);
			}
	
			
		} catch (InterruptedException e) {
			{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Load InterruptedException");bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
			e.printStackTrace();
		} catch (NumberFormatException e) {
			//Log.e(G,"Load NumberFormatException");
			//e.printStackTrace();
		} catch (IOException e) {
			{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Load IOException");bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new double[]{0,0,0};
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.w(TAG,"onBind() ++++++++++++++++++++++++++++++++++");
		return null;
	}

	public void run() {

		Log.w(TAG,"run() ++++++++++++++++++++++++++++++++++");
		{Message ml = new Message(); Bundle bl = new Bundle(); bl.putLong("ms", SystemClock.uptimeMillis()); ml.setData(bl); getlatest.sendMessageDelayed(ml,100);}

	}

	@Override
	public void onDestroy() {
		Log.w(TAG,"onDestroy() ++++++++++++++++++++++++++++++++++");
		SharedPreferences mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);
		Editor mEdt = mReg.edit();
		
		super.onDestroy();
	}


	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.w(TAG,"onStart() ++++++++++++++++++++++++++++++++++");
	}

	
	
	private SharedPreferences mReg;
	private Editor mEdt;

	long rg = 1;long gy = 1;
	String location = "";String ltitle = "";boolean ga = false;
	Handler getlatest = new Handler(){ public void handleMessage(Message msg){
		Bundle bdl = msg.getData();
		if(rg > SystemClock.uptimeMillis()){Log.w(G,"overwork relax");return;}
		rg = SystemClock.uptimeMillis() + 3000;

		if(gy > SystemClock.uptimeMillis()){Log.w(G,"serviceWork steady helm " + (gy - SystemClock.uptimeMillis()) + " ms");return;}
		long took = (SystemClock.uptimeMillis() - bdl.getLong("ms"))*100;
		if(took > 100000){took = 130000;}
		if(took < 1000){took = 500;}
Log.i(G,"getlatest "+mThr.getId()+" in " + took + " ms");
gy = SystemClock.uptimeMillis() + took;		
//{Message ml = new Message(); Bundle bl = new Bundle(bdl); ml.setData(bl); kes.sendMessageDelayed(ml,took);}
		
//	}};
		
	
	
//	Handler kes = new Handler(){ public void handleMessage(Message msg){
//		Bundle bdl = msg.getData();
		

		//
		//Log.w(TAG,"getlatest() ++++++++++++++++++++++++++++++++++ " +(took)+" ms");
		//if( took > 50 ){
			//ready(10 * 1000);
			//return;
		//}
		//mLog = new Custom(this);
		
		
		//getload();
		
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//mLog.setNotificationManager(mNM);
		mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);
		mEdt = mReg.edit();
		//mLog.setSharedPreferences(mReg,mEdt);
  	  	
		//mEdt.putInt("count_intent", mReg.getInt("count_service", 0)+1);mEdt.commit();
		
        //String mUuid = UUID.randomUUID().toString();
        //content://settings/system/notification_sound
        //for (Account account1 : accountsWithNewMail.keySet()) { if (account1.isVibrate()) vibrate = true; ringtone = account1.getRingtone(); }

        //BASEURL = "http://www.seashepherd.org/news-and-media/sea-shepherd-news/feed/rss.html";
        //BASEURL = "http://www.whitehouse.gov/blog/";
        // This will block until load is low or time limit exceeded
		
	
		Date d4 = new Date();
		d4.setSeconds(0);d4.setMinutes(0);d4.setHours(d4.getHours()+1);
		Log.w(TAG,"serviceWork Schedule ("+(d4.getHours())+") with("+d4.getTime()+") valence("+(d4.getTime()-System.currentTimeMillis())/1000/60+" minutes)");

		
		AlarmManager mAlM = (AlarmManager) mCtx.getSystemService(mCtx.ALARM_SERVICE);
		Intent resetservice = new Intent();
		resetservice.setAction(getString(R.string.cp)+".SERVICE_RECOVER3");
		PendingIntent service4 = PendingIntent.getBroadcast(mCtx, 80, resetservice, Intent.FLAG_ACTIVITY_NEW_TASK | PendingIntent.FLAG_CANCEL_CURRENT);
		mAlM.set(AlarmManager.RTC_WAKEUP, d4.getTime(), service4);

	
		
		{Message ml = new Message(); Bundle bl = new Bundle(bdl); ml.setData(bl); mGet.sendMessageDelayed(ml,1000);}
		{Message ml = new Message(); Bundle bl = new Bundle(bdl); ml.setData(bl); portgrip.sendMessageDelayed(ml,3100);}
		{Message ml = new Message(); Bundle bl = new Bundle(bdl); ml.setData(bl); serviceWork.sendMessageDelayed(ml,100);}
					  
				
				
				/*
				
				
		{Message ml = new Message(); Bundle bl = new Bundle(); ml.setData(bl); getlatest.sendMessageDelayed(ml, hy );}

		
		int fid = -1;
		int responsems = -1;
		int port = -1;
		String hostname = "";String dest = "";String sititle = "";String source = "";String ipaddress = "";String status = "";
		
		Cursor c6 = null;
		c6 = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), new String[]{"_id","title","location","sr","srview","srdate","responsems","trailsource","ipaddress","port"}, "status > 0 && sr > date('now','start of month','-1 month')", null, "srdate desc");
		if(c6 != null){if(c6.moveToFirst()){
			//int oi = -3;
for(int oi = 0; oi < c6.getCount(); oi++){	

c6.moveToPosition(oi);
fid = c6.getInt(0);
dest = c6.getString(2);
sititle = c6.getString(1);
responsems = c6.getInt(6);
source = c6.getString(7);
status = c6.getString(4);
ipaddress = c6.getString(8);
port = c6.getInt(9);					
hostname = dest.replaceFirst(".*://","").replaceAll("[/:].*","");

Log.i(G,"sr() Creating work order("+oi+") "+fid+" ("+hostname+")"+dest+"["+sititle+"] "+source+" "+status+" ["+responsems+" ms] ("+ipaddress+":"+port+")");

Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("fid",fid); bl.putString("title", sititle); bl.putString("dest", dest); bl.putString("storloc", "bucket"+fid); ml.setData(bl); 
getlist.sendMessageDelayed(ml,30);


}}c6.close();}
		
//*/		
		
		
		/*
		int interval = mReg.getInt("interval",1);
		long ls = mReg.getLong("bucket_saved",(long)0);
		
		//mLog.loadLimit(TAG + " getlatest() 107", syncLoad, 5 * 1000, 30 * 1000);
		if( interval == 0 ){
			Log.w(TAG,"Not Automatic");return;
		}else if( interval == 1 ){
			
			if( d.getHours() == 0 || d.getHours() == 5 || d.getHours() == 10 || d.getHours() == 15 || d.getHours() == 20 ){
				if( ls < System.currentTimeMillis() - 5 * 60 * 60 * 1000 ){
					getlist.sendMessage(ml);ga = true;
				}else{Log.w(TAG,"recent " + (System.currentTimeMillis() - ls)/60000 + " minutes");}
			}else{Log.w(TAG,"wrong hour " + d.getHours());}
		}else if( interval == 2 ){
			
			if( d.getHours() == 0 || ls < System.currentTimeMillis() - 24 * 60 * 60 * 1000 ){
				getlist.sendMessage(ml);ga = true;
			}else{Log.w(TAG,"recent " + (System.currentTimeMillis() - ls)/60000 + " minutes or hour("+d.getHours()+") != 0");}
		}else if( interval >= 10 ){
		
			if( d.getHours() == 0 || ls < System.currentTimeMillis() - interval * 60000 ){
				getlist.sendMessage(ml);ga = true;
			}else{Log.w(TAG,"recent interval("+interval+") > " + (System.currentTimeMillis() - ls)/60000 + " minutes or hour("+d.getHours()+") != 0");}
		}
		
		if(!ga && mReg.getLong("bucket_start",0) == 0){
			Log.w(TAG,"Running by manual request");
			getlist.sendMessage(ml);
			ga = true;
		}
		//*/
		//}
		
		
		
		//ready(10 * 60 * 1000);
	  
		
		
		/*/
	
		//*/
	}
	};
	
		
	//private void wayGo(){mCtx.finish();}
	private void ready(long mx){
	
		
		try {
		mHandler.postDelayed(this, mx);
		//Log.w(TAG,"ready("+mx+" ms)");
} catch (OutOfMemoryError e2){
		
		Log.w(G,"OOM");e2.printStackTrace();
		
	}
		
	
	
	
	}

	
	Handler portgrip = new Handler(){

	public void handleMessage(Message msg){Bundle bdl = msg.getData();
	
	
	
	
	// SQL
	
	Cursor gx = null;
	gx = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect,filter"), new String[]{"filter.hostname","retrospect.ipaddress","retrospect.port"}, "filter.updated > date('now','-1 hour') AND retrospect.updated > date('now','-1 hour') AND filterid = filter._id AND length(retrospect.ipaddress) > 3 group by filter.hostname,retrospect.ipaddress,retrospect.port ", null, "retrospect.filtered desc");

	if(gx != null){if(gx.moveToFirst()){
		for(int i2 = 0; i2 < gx.getCount(); i2++){

			gx.moveToPosition(i2);

			final String fghost = gx.getString(0);
			final int fport = gx.getInt(2);
			final String fip = gx.getString(1);


		
	// THREAD
	
		Thread se = new Thread(){
			
			public void run(){
				SharedPreferences mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);
		        Editor mEdt = mReg.edit();
				
			
		        //String gourl = destf;
				//String gourl = (port!=443?"http":"https")+"://"+ghost+(port!=80&&port!=443?":"+port:"")+"/favicon.ico";
				
		        int port = fport;
	            String ip = fip;
		        
		        boolean secure = false;
				Socket socket = null;
				SSLSocket sslsocket = null;
				BufferedReader br = null;
				BufferedWriter bw = null;
				int loopcnt = 0;
				long rxx = -2;
				try {
					
					if(port == 443){secure = true;}
					
//						Log.i(G,"serviceWork Portgrip ip("+ip+") port("+port+")");
						
						long rrr = System.currentTimeMillis();long rrs = 2;
						
						if( !secure ){
							sslsocket = null;
							Log.w(G,"serviceWork Portgrip("+ip+":"+port+")");
							socket = new Socket(ip,port);
														
							if( socket.isConnected() ){
								Log.i(G,"serviceWork Portgrip("+ip+":"+port+") On");
							}else{
								int loopcnt2 = 0;
								while( !socket.isConnected() ){
									Log.e(G,"serviceWork Portgrip("+ip+":"+port+") " + loopcnt2);
									loopcnt2++;
									if( loopcnt2 > 10 ){
										Log.e(G,"serviceWork Portgrip("+ip+":"+port+") Timeout");
										break;
									}
									SystemClock.sleep(300);
								}
							}
							
							//Log.w(G,"se() Creating Writable to hostname("+hostname+") port("+port+")");
							bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
							//Log.w(G,"se() Creating Readable to hostname("+hostname+") port("+port+")");
							br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						}else{
							socket = null;
							Log.w(G,"serviceWork Portgrip("+ip+":"+port+") secure");
							
							SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
							sslsocket = (SSLSocket) factory.createSocket(ip,443);
							SSLSession session = sslsocket.getSession();
							X509Certificate cert;
							try { cert = (X509Certificate) session.getPeerCertificates()[0]; }
							catch(SSLPeerUnverifiedException e){
								Log.e(G,"serviceWork Portgrip("+ip+":"+port+") SSL Failure " + e.getLocalizedMessage());
								//{Bundle bl = new Bundle(); bl.putString("text","SSL Unverified " + e.getLocalizedMessage()); bl.putInt("id",g7id);Message ml = new Message(); ml.setData(bl); setText.sendMessage(ml);}
								sslsocket.close();
								return;
							
							}
							
							if( sslsocket.isConnected() ){
								Log.i(G,"serviceWork Portgrip("+ip+":"+port+") secure On");
							}else{
								int loopcnt2 = 0;
								while( !sslsocket.isConnected() ){
									Log.e(G,"serviceWork Portgrip("+ip+":"+port+") secure "+loopcnt2);
									loopcnt2++;
									if( loopcnt2 > 20 ){
										Log.e(G,"serviceWork Portgrip("+ip+":"+port+") Timeout");
										break;
									}
									SystemClock.sleep(300);
								}
							}					
							
							//Log.w(G,"se() Creating Writable to hostname("+hostname+") port("+port+")");
							bw = new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream()));
							//Log.w(G,"se() Creating Readable to hostname("+hostname+") port("+port+")");
							br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
						}
						
						
						
						
						//if(gourl.matches("[hH][tT][tT][pP].*")){
						//mEdt.putString(loc+"_proto",secure?"https":"http");
							//mEdt.putLong(loc+"_connected",System.currentTimeMillis());
						//mEdt.putLong("lastfeedactive", System.currentTimeMillis());mEdt.commit();
						
						
						
						
						Log.w(G,"serviceWork Portgrip("+ip+":"+port+") Requesting");
						bw.write("GET /favicon.ico HTTP/1.0\r\n");
						bw.write("Host: " + fghost + "\r\n");
						bw.write("User-Agent: Android\r\n");
						//bw.write("Range: bytes=0-"+(1024 * 1)+"\r\n");
						//bw.write("TE: deflate\r\n");
						bw.write("\r\n");
						bw.flush();
						//http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html#sec5
						Log.i(G,"serviceWork Portgrip("+ip+":"+port+") Request delivered in http to /robots.txt");
						//}else{
							//mEdt.putString(loc+"_proto",secure?"protos":"proto");
							
						
						//Log.i(G,"Request delivered ip("+ip+":"+port+")");
						//}
						
						//mEdt.commit();
						String status = "";
						String line = "";

							
						char[] u7 = new char[102];
						int cs = br.read(u7);
						int took = (int)(System.currentTimeMillis() - rrr);
						line = Uri.encode(new String(u7));
						line = line.replaceAll("%([8-9A-F1][0-9A-F]|7F|0[1-8BCEF])","");
						line = Uri.decode(line);
						Log.w(G,"serviceWork Portgrip("+ip+":"+port+") Took " + took + "ms ready to respond with " + line.replaceAll("\n"," "));

						if(line.length() < cs){
							Log.w(G,"serviceWork Portgrip("+ip+":"+port+") Security Alert " + (cs - line.length() ));
						}

						//Log.i(G,"se() g6("+g6id+","+took+")");
//						{Bundle bl = new Bundle(); bl.putString("text",""+line.replaceAll("\n.*","")); bl.putInt("id",g7id);Message ml = new Message(); ml.setData(bl); setText.sendMessage(ml);}
//						{Bundle bl = new Bundle(); bl.putString("text",""+took); bl.putInt("id",g6id);Message ml = new Message(); ml.setData(bl); setText.sendMessage(ml);}



						{
							ContentValues w = new ContentValues();
							w.put("responsems",took);
//						    w.put("srview", ""+line.replaceAll("\n.*",""));
//						    w.put("srdate", datetime());
						    int u2 = SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"),w,"ipaddress='"+ip+"' AND port="+port+" AND updated > date('now','-1 hour')",null);
						
						}

							
							
							
							
							
						
						if( !secure ){
//							Log.i(G,"serviceWork Portgrip("+ip+":"+port+") ");
							socket.close();
						}else{
//							Log.i(G,"serviceWork Portgrip("+ip+":"+port+") ");
							sslsocket.close();
						}
				
					
					
					
					
					

				
				
				
				
				
				
				} catch (UnknownHostException e1) {
					Log.e(G,"se() unknownHostException");
					mEdt.putString("errortype","Unknown host " + e1.getLocalizedMessage());
					mEdt.commit();
					e1.printStackTrace();
					rxx = -11;
				} catch (IOException e1) {
					Log.e(G,"se() IOException");
					mEdt.putString("errortype","serious face " + e1.getLocalizedMessage());
					mEdt.commit();
					e1.printStackTrace();
					rxx = -12;
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				//if(rxx > -1){	
				//
//				Log.i(G,"se() g6("+g6id+","+g6id+")");
//				{Bundle bl = new Bundle(); bl.putString("text",""+rxx); bl.putInt("id",g6id);Message ml = new Message(); ml.setData(bl); setText.sendMessage(ml);}
				//}
			
//				ContentValues c8 = new ContentValues();
//				c8.put("responsems", rxx);	
				//pper.update
//				SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), c8, "_id = " + wi, null);

				
			
				
				
			}};
			
			
			
			se.start();	
		}
		
		
	
	
	}gx.close();}
	
	
	
	
	
	
	
	
	
	}
	};
	
	
	
	
	
	
	
	
	int bl2 = -1;File f8 = null;FileInputStream hx4 = null;byte[] bx5 = new byte[1];
	public int nxtIndex(String isplit){
	int bx8 = -2;String b7 = "";
	//if(bx5 == null || bx5.length < f8.length() ){bx5 = new byte[(int) f8.length()];}
	bx5 = new byte[102400];
	long bc6 = 3;int sb2 = bl2;int gg7 = 0;int u8 = 1;
	try{b7 = "";for(bx8 = bl2+1; bx8 <f8.length() ;bx8+=(bx5.length/2)){
	if(u8 > 10){Log.e(G,"["+u8+"] nxt("+bx8+") ("+b7.length()+") ("+isplit+") "+sb2+"-"+bx8+"");return -14;}
		Log.i(G,"["+u8+"] nxt("+bx8+") ("+b7.length()+") ("+isplit+")");
		bc6=hx4.skip(bx8);
	hx4.read(bx5,0,(int) (bc6+bx5.length<f8.length()?bx5.length:f8.length()-bc6));
	b7=new String(bx5);Log.i(G,"nxt b7("+b7.length()+")");
	u8++;if(b7.indexOf(isplit) > -1 ){bx8 += b7.indexOf(isplit);bl2 = bx8; break;} }}//mGet
    
	
	catch(IndexOutOfBoundsException e){Log.e(G,"nxt123 seek("+bx8+") over file size("+f8.length()+") " + e.getLocalizedMessage() );e.printStackTrace();return -1;}
    catch(IOException e){Log.e(G,"nxt123 seek("+bx8+") ioexception size("+f8.length()+") " + e.getLocalizedMessage() );e.printStackTrace(); return -1;}
    catch(OutOfMemoryError e){Log.e(G,"nxt123 seek("+bx8+") outofmemoryerror size("+f8.length()+") " + e.getLocalizedMessage() );e.printStackTrace(); bl2 = sb2; return -13;}
    
    return bx8;
	
	}
    
	Handler spliter = new Handler(){
		int l5 = 0;
		int sa1 = -2;int sat = -2;String isplit = "";
		public void handleMessage(Message msg){
		Bundle bdl = msg.getData();
		if(bdl.containsKey("isplit")){isplit = bdl.getString("isplit");}
		if(isplit.length() == 0){Log.e(G,"spliter isplit length empty turning off at "+sat+"/"+f8.length()+"");return;}
		
		sa1 = nxtIndex(isplit);
		sat = nxtIndex(isplit);
		
		Log.i(G,"spliter("+sa1+","+sat+","+bl2+","+isplit+")");
		
		l5++;if(l5 > 5){return;}
		spliter.sendEmptyMessageDelayed(-2,70);
	}};
	
	Handler serviceWork = new Handler(){
long gh = 1;
		public void handleMessage(Message msg){		
			if(gh > SystemClock.uptimeMillis()){Log.w(G,"serviceWork release filter load");return;}gh = SystemClock.uptimeMillis() + 10000;
			
			final Bundle bdl = msg.getData();
			Thread mt = new Thread(){
				public void run(){
			
			SharedPreferences mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);
			Editor mEdt = mReg.edit();


			int fid = -1;
			int responsems = -1;
			int port = -1;
			String hostname = ""; String dest = ""; String sititle = ""; String source = ""; String ipaddress = ""; String status = "";
			int rid = -1; int filterid = -1; String rtitle = ""; String rurl = ""; String rhostname = ""; String rdocpath = ""; int rport = -1; String rbody = ""; String rheader = "";
            String split = ""; String parse_item = ""; String parse_link = ""; String parse_title = ""; String parse_author = ""; String parse_content = ""; String parse_published = ""; String parse_build = "";
			String rcharset = ""; String rcontenttype = ""; int rcontentlength = 0; String rprotostatus = "";
            String ripaddress = "";
			
			Cursor c1 = null;
			c1 = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), new String[]{"_id","filterid","title","url","hostname","docpath","port","body","header","charset","contenttype","contentlength","protostatus","ipaddress","port"}, "status > 0 AND (filtered < created || filtered is null) AND length(body) > 0", null, "updated desc limit 1");
			if(c1 != null){if(c1.moveToFirst()){
            rid = c1.getInt(0);
			filterid = c1.getInt(1);
			rtitle = c1.getString(2);
			rurl = c1.getString(3);
			rhostname = c1.getString(4);
			rdocpath = c1.getString(5);
			rport = c1.getInt(6);
			rbody = c1.getString(7);if(rbody == null){rbody = "";}
			rheader = c1.getString(8);if(rheader == null){rheader = "";}
			rcharset = c1.getString(9);if(rcharset == null || rcharset.length()==0){rcharset = "ISO-8859-1";}
			rcontenttype = c1.getString(10);if(rcontenttype == null){rcontenttype = "";}
			rcontentlength = c1.getInt(11);if(rcontentlength < rbody.length() ){rcontentlength = rbody.length();}
			rprotostatus  = c1.getString(12);
ripaddress = c1.getString(13);
rport = c1.getInt(14);			
			}c1.close();}


			if(rid < 0 || filterid < 0 || rheader.length() < 10){
				Log.i(G,"serviceWork Filter Recognized no work");return;
			}
			
			
			
			
			
			
			
			
			
			

			
			
			
			
			Cursor c6 = null;
			c6 = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), new String[]{"_id","title","location","sr","srview","srdate","responsems","trailsource","ipaddress","port","split","parse_item","parse_link","parse_title","parse_author","parse_content","parse_published","parse_build"}, "_id = "+filterid, null, "created desc");
	if(c6 != null){if(c6.moveToFirst()){
Log.i(G,"filter lookup " + filterid);
		fid = c6.getInt(0);
	dest = c6.getString(2);
	sititle = c6.getString(1);
	responsems = c6.getInt(6);
	source = c6.getString(7);
	status = c6.getString(4);
	ipaddress = c6.getString(8);
	port = c6.getInt(9);					
    split = c6.getString(10);if(split == null){split = "";}
    parse_item = c6.getString(11);if(parse_item == null){parse_item = "";}
    parse_link = c6.getString(12);if(parse_link == null){parse_link = "";}
    parse_title = c6.getString(13);if(parse_title == null){parse_title = "";}
    parse_author = c6.getString(14);if(parse_author == null){parse_author = "";}
    parse_content = c6.getString(15);if(parse_content == null){parse_content = "";}
    parse_published = c6.getString(16);if(parse_published == null){parse_published = "";}
    parse_build = c6.getString(17);if(parse_build == null){parse_build = "";}
    
	hostname = dest.replaceFirst(".*://","").replaceAll("[/:].*","");
	}c6.close();}
	if(fid < 0){
	Log.e(G,"filter "+filterid+" lookup failure ###################### ");
    return;
	}
	
	
	{
		ContentValues v3 = new ContentValues();
		v3.put("filtered",datetime());
	
		SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), v3, "_id="+filterid, null);
	}
	{
		ContentValues v3 = new ContentValues();
		v3.put("filtered",datetime());

	SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), v3, "_id="+rid, null);
	}
	

	
	Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" ("+rhostname+") ("+ripaddress+":"+rport+") ["+rtitle+"] "+rdocpath+" body("+rbody.length()+" bytes) " + datetime());
//serviceWork "+rid+" Filtering "+fid+" ("+hostname+")"+dest+"["+sititle+"] "+source+" "+status+" ["+responsems+" ms] ("+ripaddress+":"+rport+")");

	
	
	
	
	{
	    ContentValues v3 = new ContentValues();
		v3.put("srview","Filter");
		v3.put("srdate",datetime());
		SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), v3, "_id="+fid, null);
	}		

	
	
	//split

	if(split.length() > 0 && rbody.indexOf(split) > -1 ){
	//nochange to split
	}else if(parse_item.length() > 0 && rbody.indexOf(parse_item) > -1){
	split = parse_item;
	}else if(rbody.indexOf("<entry") > -1){split = "<entry";}else if(rbody.indexOf("<item") > -1){split = "<item";
	//}else if(rbody.indexOf("<html") > -1){split = "<html";
	//}else if(rbody.indexOf("<xml") > -1){split = "<xml";
	//}else if(rbody.indexOf("<entry") > -1){split = "<body";
	//}else if(rbody.indexOf("<body") > -1){split = "<body";
	}
	
	
	
	{
		ContentValues v3 = new ContentValues();
		v3.put("split",split);
		SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), v3, "_id="+fid, null);
		Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" split("+split+")");
	}

			
	
	
	

	
		int foundnew = 0;
		String[] ctl = null;
		if( (ctl == null || ctl.length == 1) && split.length() > 0 && rbody.indexOf(split) > -1){ctl = rbody.split(split);}
		
		if(ctl == null || split.length() == 0){
			ctl = new String[]{rbody};
		}
		
		Log.i(G,"serviceWork "+rid+" Filtering "+fid+" parts " + ctl.length + " body(" + rbody.length() + ") + head(" + rheader.length() + ") bytes" );
		
		
		//if( mReg.getLong(loc+"_start", 0) < mReg.getLong(loc+"_saved", 0) ){
			//Log.i(G,"sw() download ready("+(mReg.getLong(loc+"_saved", 0) -mReg.getLong(loc+"_start", 0) )+"ms)");
			//Date d = new Date(mReg.getLong(loc+"_saved", 0));
			//String ct = mReg.getString(loc, "");
			//String lct = ct.toLowerCase();
			
				
				
				{
				
				
				//ct = ct.replaceAll("<[eE][nN][tT][rR][yY]","<entry"); ct = ct.replaceAll("<[xX][mM][lL]","<xml");
				//ct = ct.replaceAll("<[hH][tT][mM][lL]","<html");ct=ct.replaceAll("<[bB][oO][dD][yY]","<body");ct=ct.replaceAll("<[iI][tT][eE][mM]","<item");
				
				
				//
				// CUSTOMIZED
				//
				//ct = ct.replaceAll("(%3C|<|&lt;)[sS][cC][rR][iI][pP][tT].*?(&gt;|>|%3E).*?(%3C|<|&lt;)(/|%2F)[sS][cC][rR][iI][pP][tT].*?(&gt;|>|%3E)","");
				//ct = ct.replaceAll("(%3C|<|&lt;)(!|%21)(-|%2D)(-|%2D).*?(-|%2D)(-|%2D)(&gt;|>|%3E)","");

				// EC
				
				
				
				//mEdt.putLong(loc+"_size",ct.length());
				//mEdt.commit();
				//String desttitle = rtitle;//mReg.getString(loc+"_title", "");
				
				//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("id", headerText); bl.putString("text", "Downloaded " + (ct.length()>1024?ct.length()/1024+"Kb":ct.length()+"b")+" at " + (d.getHours() > 12?d.getHours()-12:d.getHours()) + ":" + (d.getMinutes()<10?"0":"") + d.getMinutes() + "\nContaining " + ctl.length); ml.setData(bl); setText.sendMessage(ml);}
				//ct = ct.replace("\r", "\n");
				
				if( ctl.length > 0){

					
					
					//mProgressDialog = ProgressDialog.show(mCtx, "Gratuitous Notification", "Loading", true);
					//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("max",ctl.length); bl.putString("titleOFF", "Notification"); bl.putBoolean("indeter", true); bl.putString("text", "Loading"); ml.setData(bl); mProgress.sendMessage(ml);}
					//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("max",ctl.length); ml.setData(bl); mProgressMax.sendMessage(ml);}
				Cursor cx=null;
				boolean found = true;


				//ContentValues inc = new ContentValues();
				//inc.put("location", dest);
				//inc.put("prefilter", ct);
				//inc.put("status",1);
				//if(status < 10 && source!=null && source.getLastPathSegment().matches("[0-9]+")){
					//SqliteWrapper.update(mCtx,getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), inc, "_id = "+source.getLastPathSegment(), null );		
					//Log.i(TAG,"Updating " + source.toString());
				//}else{
					//Uri bsource = SqliteWrapper.insert(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), inc);
					//Log.i(TAG,"Created " + bsource.toString());
				//}
				
				Uri contentpath = Uri.parse(getString(R.string.cp)+"/moment");
				ContentValues cv = new ContentValues();
				String title = "";String published= "";String content= "";String url= "";String author = "";Uri geturi;
				//String parse_link = mReg.getString(loc+"_parse_link", August.parse_link);
				//String parse_title = mReg.getString(loc+"_parse_title", August.parse_title);
				//String parse_author = mReg.getString(loc+"_parse_author", August.parse_author);
				//String parse_author2 = mReg.getString(loc+"_parse_author2", August.parse_author2);
				//String parse_content = mReg.getString(loc+"_parse_content", August.parse_content);
				//String parse_summary = mReg.getString(loc+"_parse_summary", August.parse_summary);
				//String parse_published = mReg.getString(loc+"_parse_published", August.parse_published);
				//String parse_lastbuild = mReg.getString(loc+"_parse_lastbuild", August.parse_lastbuild);
				int hush = 0;//String plitt = mReg.getString(loc+"_split","");
				CharBuffer cbuf;CharsetDecoder decoder;CharsetEncoder encoder;ByteBuffer bbuf;
				long gm7 = 2;
				for(int b = 0; b < ctl.length; b++){if(ctl[b].length() == 0){continue;}
					ctl[b] = split + " " + ctl[b];
					
					
					
					Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" ["+b+"] " + ctl[b].length() + " bytes");
					if( (gm7+5000) < System.currentTimeMillis() ){
						gm7 = System.currentTimeMillis();
						{
						    ContentValues v3 = new ContentValues();
							v3.put("srview","Filtering");
							v3.put("srdate",datetime());
							SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), v3, "_id="+fid, null);
						}
						
					}
					// && foundnew <= mReg.getInt("August_naturalLimit", August.naturalLimit)
					
				//
				// CUSTOMIZED
				//
				
				// Link
				if(parse_link.length() > 0){
				if(parse_link.contains("\n")){
					if(parse_link.split("\n")[0].length() == 0){url = ctl[b];}
					else{url = ctl[b].replaceAll(".*?"+parse_link.split("\n")[0],"");}
					url = url.replaceAll(parse_link.split("\n")[1]+".*", "");
				}else if(ctl[b].indexOf("<"+parse_link) > -1 && ctl[b].indexOf("</"+parse_link+">") > -1){
					url = ctl[b].substring(ctl[b].indexOf('>', ctl[b].indexOf(parse_link))+1, ctl[b].indexOf("</"+parse_link+">"));
				}else{ 
					url = ctl[b].replaceAll(".* [hH][rR][eE][fF].*?=.*?(\"|')","").replaceAll("(\"|'|>).*","");
				}
				}else{
url = rurl;
				}
				if(url.indexOf('"') > 3){url = url.replaceAll("\".*", "");}

				if(url.indexOf("cdata[") > -1){url = url.substring(url.indexOf("cdata[")+6, url.lastIndexOf("]]"));}
				if(url.indexOf("CDATA[") > -1){url = url.substring(url.indexOf("CDATA[")+6, url.lastIndexOf("]]"));}

				Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" link("+parse_link.replaceAll("\n","%0a")+", "+url+")");
				
				
				
				
				// Title
				//title = momentd;

				if(parse_title.length() > 0){
					if(parse_title.contains("\n")){
						if(parse_title.split("\n")[0].length() == 0){title = ctl[b];}
						else{title = ctl[b].replaceAll(".*?"+parse_title.split("\n")[0],"");}
						title = title.replaceAll(parse_title.split("\n")[1]+".*", "");
					}else if(ctl[b].indexOf("<"+parse_title) > -1 && ctl[b].indexOf("</"+parse_title+">") > -1){
						title = ctl[b].substring(title.indexOf('>', ctl[b].indexOf(parse_title))+1, ctl[b].indexOf("</"+parse_title+">"));
					}else if(title.contains(parse_title+">")){title = title.substring(title.indexOf(parse_title+">")+parse_title.length()+1);}
				}else{
				
					if(ctl[b].regionMatches(true, 0, "<title", 0, ctl[b].length()) && ctl[b].regionMatches(true, 0, "</title>", 0, ctl[b].length())){
				title = title.replaceAll("</[tT][iI][tT][lL][eE]>.*","");title = title.replaceAll(".*<[tT][iI][tT][lL][eE].*?>","");
						
				    }
				}

if(title == null){title = "";}
if(title.length() == 0){title = rtitle;}
if(title.length() == 0){title = sititle;}
if(title.length() == 0){
String tm2 = "\n" + ctl[b].replaceAll("<[sS][tT][yY][lL][eE].*?>.*?</[sS][tT][yY][lL][eE]>","").replaceAll("<[sS][cC][rR][iI][pP][tT].*?>.*?</[sS][cC][rR][iI][pP][tT]>","").replaceAll("<[a-zA-Z].*?>","").replaceAll("<[/!][a-zA-Z].*?>","").replaceAll("\n+"," ").replaceAll(" +"," ");
tm2 = tm2.replaceAll("\n +","").replaceAll("\n","");						
if(tm2.length() < 1){}
if(tm2.length() > 50){tm2 = tm2.substring(0, tm2.indexOf(' ', tm2.length() > 50?40:tm2.length()-1));}
}				
if(title.length() == 0){title = rid+":"+fid;}



					// Seattle's The Stranger uses encoding for the title
					//title = title.replaceAll("&quot;", "\"");
				//}
				title = Uri.decode(title).trim();	
				title = title.replaceAll("&amp;","&").replaceAll("&nbsp;"," ");
				title = title.replaceAll("&quot;","\"").replaceAll("&apos;","'").replaceAll("&lt;","<").replaceAll("&gt;",">");
				
				try{
					
					Charset charset = Charset.forName(rcharset);
					decoder = charset.newDecoder();
					encoder = charset.newEncoder();
					bbuf = ByteBuffer.wrap(title.getBytes());
					CharBuffer c2;
					c2 = decoder.decode(bbuf);	
				
					title = c2.toString();
				
					
					if(!rcharset.matches(".*8859-1")){
						title = Uri.decode(title).trim();//tripple
						title = title.replaceAll("&amp;","&").replaceAll("&nbsp;"," ");
						title = title.replaceAll("&quot;","\"").replaceAll("&apos;","'").replaceAll("&lt;","<").replaceAll("&gt;",">");
								
						charset = Charset.forName("ISO-8859-1");
						ByteBuffer b3 = ByteBuffer.wrap(title.getBytes());
						CharBuffer c3; c3 = decoder.decode(b3);
						title = c3.toString();
					
					}
				
				
				} catch (CharacterCodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				title = Uri.decode(title).trim();//double
				title = title.replaceAll("&amp;","&").replaceAll("&nbsp;"," ");
				title = title.replaceAll("&quot;","\"").replaceAll("&apos;","'").replaceAll("&lt;","<").replaceAll("&gt;",">");//beatimg-log
				
				
				
				//title = title.replaceAll("&#039;","'");								
				//title = title.replaceAll("&#39;","'");title = title.replaceAll("&#38;","&");title = title.replaceAll("&#x27;","'");
				//title = title.replaceAll("&#34;","'");
				//title = title.replaceAll("&#124;",":");title = title.replaceAll("&#187;",":");
				//title = title.replaceAll("&#8217;","'");title = title.replaceAll("&#8216;","'");title = title.replaceAll("&#8212;","/");
				//title = title.replaceAll("&#[0-9]+;","");
				if(title.matches("&([a-zA-Z])+;")){Log.e(G,"##############)Found One In ("+Uri.encode(title)+")");}
				if(dest.indexOf("twitter.com") > -1 && title.indexOf(':') > -1 && title.length() > title.indexOf(':')+3 ){title = title.substring(title.indexOf(':')+2);}
				if(title.indexOf("CDATA[") > -1){title = title.substring(title.indexOf("CDATA[")+6, title.lastIndexOf("]]"));}
				if(title.indexOf("cdata[") > -1){title = title.substring(title.indexOf("cdata[")+6, title.lastIndexOf("]]"));}
				
				title = title.replaceAll("<[a-zA-Z].*?>", "");

				//if(title.length() > 0){
				//ContentValues b3 = new ContentValues();
				//b3.put("title",title);	
				//SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/retrospect"),b3,"_id="+rid,null);
				//SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),b3,"_id="+fid,null);
				//}

				Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" title("+parse_title.replaceAll("\n","%0a")+","+title+")");
				
				
				
				
//				if(b == 0 && !title.contains(desttitle)){
//					mEdt.putString(loc+"_title",title);
//					mEdt.putString("bucket_title",title);
//					mEdt.putString("sourcetitle",title);mEdt.commit();
//				}
				
				if(dest.indexOf("dealextreme.com") > -1 ){
					url = url.replaceAll("99999999","89653085");
				
				}
				
				

				if(parse_published != null){
				// Published
				if(parse_published.contains("\n")){
					
						if(parse_published.split("\n")[0].length() == 0){published = ctl[b];}
					else{published = ctl[b].replaceAll(".*?"+parse_published.split("\n")[0],"");}
					published = published.replaceAll(parse_published.split("\n")[1]+".*", "");
				}else if(ctl[b].indexOf("<"+parse_published) > -1 && ctl[b].indexOf("</"+parse_published+">") > -1){	
					published = fixDate(ctl[b].substring(ctl[b].indexOf("<"+parse_published+">")+(parse_published.length()+2), ctl[b].indexOf("</"+parse_published+">")));
					//Log.e(G,"Using found published("+published+") <"+August.parse_published+">");
				}else if(ctl[b].indexOf("<"+parse_build) > -1 && ctl[b].indexOf("</"+parse_build+">") > -1){
					published = fixDate(ctl[b].substring(ctl[b].indexOf("<"+parse_build+">")+(parse_build.length()+2), ctl[b].indexOf("</"+parse_build+">")));
					//Log.e(G,"Using found published("+published+") <"+August.parse_lastbuild+">");
					//mEdt.putString("sourcetitle",title);mEdt.commit();{Message ml = new Message(); Bundle bl = new Bundle(); bl.putString("text",title); ml.setData(bl); mProgressTitle.sendMessage(ml);}
				}else if(ctl[b].indexOf("<dc:date>") > -1 && ctl[b].indexOf("</dc:date>") > -1){
					published = fixDate(ctl[b].substring(ctl[b].indexOf("<dc:date>")+(new String("<dc:date>").length()), ctl[b].indexOf("</dc:date>")));
					//Log.e(G,"Using found published("+published+") <dc:date>");
					//mEdt.putString("sourcetitle",title);mEdt.commit();{Message ml = new Message(); Bundle bl = new Bundle(); bl.putString("text",title); ml.setData(bl); mProgressTitle.sendMessage(ml);}
				}else if(ctl[b].indexOf("<published>") > -1){
					published = fixDate(ctl[b].substring(ctl[b].indexOf("<published>")+(new String("<published>").length()), ctl[b].indexOf("</published>")));
					//Log.e(G,"Using found published("+published+") <published>");
					//mEdt.putString("sourcetitle",title);mEdt.commit();{Message ml = new Message(); Bundle bl = new Bundle(); bl.putString("text",title); ml.setData(bl); mProgressTitle.sendMessage(ml);}
				}else{published = datetime();}
				}else{published = datetime();}//Log.e(TAG,"Using current date. because unable to locate <"+parse_published+"> --- second thought, skipping these");String[] pp = ctl[b].split("\n");for(int p = 0; p < pp.length; p++){Log.i(TAG,"DEBUG: " + pp[p]);} }
				//Log.i(G,title+" published(" + published+")");
				
				//2010-10-04 if(b == 0){mEdt.putString("sourcetitle",title);mEdt.commit(); {Message ml = new Message(); Bundle bl = new Bundle(); bl.putString("text",title); ml.setData(bl); mProgressTitle.sendMessage(ml);}}
				
				
				Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" pubished("+parse_published.replaceAll("\n","%0a")+","+published+")");
				
				
				
				
				// EC
					
					//DEBUG
					//Log.w(G,"thread("+this.getId()+") title("+title+") published("+published+") author(unavil) url("+url+") content("+content+")");

					found = false;
//					cx = SqliteWrapper.query(mCtx, mCtx.getContentResolver(), contentpath, new String[] {"_id"}, "title = \""+Uri.encode(title)+"\" AND url = \""+url+"\" AND strftime('%s',published) = strftime('%s','"+published+"')", null, null);
					//if(mFoo > 5280){ SystemClock.sleep(2000);hush++;} fooTest.sendEmptyMessageDelayed((int)SystemClock.uptimeMillis(),3000);
					//
					// CUSTOMIZED
					//
					try {
						String qd = "title = \""+Uri.encode(title)+"\" AND url = \""+url+"\"";
						cx = SqliteWrapper.query(mCtx, mCtx.getContentResolver(), contentpath, new String[] {"_id"}, qd, null, null);
						if( cx != null){if( cx.moveToFirst() ){ if( cx.getInt(0) > 0){Log.i(G,"serviceWork "+rid+" Duplicate "+fid+" " + cx.getInt(0) + " " + qd); found=true;}; }else{found=false;} cx.close();}
					} catch (SQLiteException e){Log.e(TAG,"SQL Error:" + e.getLocalizedMessage());}
					// EC

					if(found){

						try {
String tm2 = ""+b+"."+ctl[b].length();
title = tm2 +" ["+title+"]";
String qd = "title = \""+Uri.encode(title)+"\" AND url = \""+url+"\"";
cx = SqliteWrapper.query(mCtx, mCtx.getContentResolver(), contentpath, new String[] {"_id"}, qd, null, null);
						if( cx != null){if( cx.moveToFirst() ){ if( cx.getInt(0) > 0){Log.i(G,"serviceWork "+rid+" Duplicate "+fid+" " + cx.getInt(0) + " " + qd); found=true;}; }else{found=false;} cx.close();}
					} catch (SQLiteException e){Log.e(TAG,"SQL Error:" + e.getLocalizedMessage());}

					}

					
					
					
					if(!found){

						Log.w(G,"serviceWork "+rid+" Recording " + fid + " " + title + "("+url+")");
						
						
						// author
if(parse_author != null && parse_author.length() > 0){
						if(parse_author.contains("\n")){
							if(parse_author.split("\n")[0].length() == 0){author = ctl[b];}
							else{author = ctl[b].replaceAll(".*?"+parse_author.split("\n")[0],"");}
							author = author.replaceAll(parse_author.split("\n")[1]+".*", "");
						}else if(ctl[b].indexOf("<"+parse_author+">") > -1){
							author = ctl[b].substring(ctl[b].indexOf("<"+parse_author+">")+(parse_author.length()+2), ctl[b].indexOf("</"+parse_author+">"));
//						}else if(ctl[b].indexOf("<"+parse_author2+">") > -1){
//							author = ctl[b].substring(ctl[b].indexOf("<"+parse_author2+">")+(parse_author2.length()+2), ctl[b].indexOf("</"+parse_author2+">"));
						}else{author = "";}
						if(author.indexOf("CDATA[") > -1){author = author.substring(author.indexOf("CDATA[")+6, author.lastIndexOf("]]"));}
}else{author = "";}

Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" author("+parse_author.replaceAll("\n","%0a")+","+author+")");


						// Content
if(parse_content != null && parse_content.length() > 0){
//Log.w(G,"Parse content("+parse_content+")");
						if(parse_content.contains("\n")){
							if(parse_content.split("\n")[0].length() == 0){content = ctl[b];}
						else{content = ctl[b].replaceAll(".*?"+parse_content.split("\n")[0],"");}	
							content = content.replaceAll(parse_content.split("\n")[1]+".*", "");
						}else if(parse_content.length() > 0 && ctl[b].indexOf("<"+parse_content+"") > 0 && ctl[b].indexOf("</"+parse_content+"") > 0){content = ctl[b].substring(ctl[b].indexOf(">",ctl[b].indexOf("<"+parse_content))+1, ctl[b].indexOf("</"+parse_content+">"));
						//}else if(ctl[b].indexOf("<"+parse_summary+">") > 0 && ctl[b].indexOf("</"+parse_summary+">") > 0){content = ctl[b].substring(ctl[b].indexOf("<"+parse_summary+">")+(parse_summary.length()+2), ctl[b].indexOf("</"+parse_summary+">")); 
						}else if(ctl[b].indexOf("<subtitle>") > 0 && ctl[b].indexOf("</subtitle>") > 0){content = ctl[b].substring(ctl[b].indexOf("<subtitle>")+(new String("subtitle").length()+2), ctl[b].indexOf("</subtitle>"));
						}else if(ctl[b].indexOf("<summary>") > 0 && ctl[b].indexOf("</summary>") > 0){content = ctl[b].substring(ctl[b].indexOf("<summary>")+(new String("summary").length()+2), ctl[b].indexOf("</summary>"));
						}else if(ctl[b].indexOf("<content") > 0 && ctl[b].indexOf("</content") > 0){content = ctl[b].substring(ctl[b].indexOf('>', ctl[b].indexOf("<content"))+1, ctl[b].indexOf("</content>"));
						}else if(ctl[b].indexOf("<subtitle") > 0 && ctl[b].indexOf("</subtitle") > 0){content = ctl[b].substring(ctl[b].indexOf('>', ctl[b].indexOf("<subtitle"))+1, ctl[b].indexOf("</subtitle>"));
						}else if(ctl[b].indexOf("<summary") > 0 && ctl[b].indexOf("</summary") > 0){content = ctl[b].substring(ctl[b].indexOf('>', ctl[b].indexOf("<summary"))+1, ctl[b].indexOf("</summary>"));
						}else{content=ctl[b];}
						//content = content.replaceAll("&#8230;","");// CC Insider
}else{content=ctl[b];}

Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" content("+parse_content.replaceAll("\n","%0a")+","+content.length()+" bytes)");


						//if(August.dest.indexOf("dealextreme.com") > -1 ){content = content.replaceAll("99999999","89653085");}
						//if(August.dest.indexOf("flickr.com") > -1 && content.indexOf("posted a photo:") > -1 ){content = content.replaceFirst(".*?posted a photo:", "");}
						
						

						/*
						// Content
						if(ctl[b].indexOf("<"+August.parse_content+"") > 0){content = ctl[b].substring(ctl[b].indexOf(">",ctl[b].indexOf("<"+August.parse_content))+1, ctl[b].indexOf("</"+August.parse_content+">"));
						}else if(ctl[b].indexOf("<"+August.parse_summary+">") > 0){content = ctl[b].substring(ctl[b].indexOf("<"+August.parse_summary+">")+(August.parse_summary.length()+2), ctl[b].indexOf("</"+August.parse_summary+">")); 
						}else if(ctl[b].indexOf("<summary>") > 0){content = ctl[b].substring(ctl[b].indexOf("<summary>")+(new String("summary").length()+2), ctl[b].indexOf("</summary>"));
						}else if(ctl[b].indexOf("<content") > 0){content = ctl[b].substring(ctl[b].indexOf('>', ctl[b].indexOf("<content"))+1, ctl[b].indexOf("</content>"));
						}else if(ctl[b].indexOf("<subtitle") > 0){content = ctl[b].substring(ctl[b].indexOf('>', ctl[b].indexOf("<subtitle"))+1, ctl[b].indexOf("</subtitle>"));
						}else if(ctl[b].indexOf("<summary") > 0){content = ctl[b].substring(ctl[b].indexOf('>', ctl[b].indexOf("<summary"))+1, ctl[b].indexOf("</summary>"));
						}else{content="unavail";}
						
						// Published
						if(ctl[b].indexOf("<"+August.parse_published+">") > -1){	
							published = fixDate(ctl[b].substring(ctl[b].indexOf("<"+August.parse_published+">")+(August.parse_published.length()+2), ctl[b].indexOf("</"+August.parse_published+">")));
						}else if(ctl[b].indexOf("<"+August.parse_lastbuild+">") > -1){
							published = fixDate(ctl[b].substring(ctl[b].indexOf("<"+August.parse_lastbuild+">")+(August.parse_lastbuild.length()+2), ctl[b].indexOf("</"+August.parse_lastbuild+">")));
						}else if(ctl[b].indexOf("<dc:date>") > -1){
							published = fixDate(ctl[b].substring(ctl[b].indexOf("<dc:date>")+(new String("<dc:date>").length()), ctl[b].indexOf("</dc:date>")));
						}else if(ctl[b].indexOf("<published>") > -1){
							published = fixDate(ctl[b].substring(ctl[b].indexOf("<published>")+(new String("<published>").length()), ctl[b].indexOf("</published>")));
						}else{published = datetime();Log.e(TAG,"Using current date. because unable to locate <"+August.parse_published+"> --- second thought, skipping these"); }
//						if(b == 0){mEdt.putString("sourcetitle",title);mEdt.commit();}
						//*/
						


						
						
						
						try {
						    // Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
						    // The new ByteBuffer is ready to be read.
						    //ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("a string"));
							
							Charset charset = null; 
							charset =Charset.forName("ISO-8859-1");
							decoder = charset.newDecoder();
							encoder = charset.newEncoder();    
							bbuf = ByteBuffer.wrap(content.getBytes());
						    // Convert ISO-LATIN-1 bytes in a ByteBuffer to a character ByteBuffer and then to a string.
						    // The new ByteBuffer is ready to be read.
						    cbuf = decoder.decode(bbuf);

						    if(cbuf.length() > 0){

						    	content = cbuf.toString();
						    	Log.i(G,"serviceWork "+rid+" Filtering "+filterid+" decoded("+parse_content.replaceAll("\n","%0a")+","+content.length()+" bytes)");

						    }
						} catch (OutOfMemoryError e2){
						    Log.e(G,"serviceWork "+rid+" OutOfMemory ");

						    ContentValues v3 = new ContentValues();
							v3.put("srview","Filtered");
							v3.put("srdate",datetime());
							SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), v3, "_id="+fid, null);

						    
						    return;
						} catch (CharacterCodingException e) {
						Log.e(G,"CCE MAN " + e.getLocalizedMessage());
						}
						
						
						//content = content.replaceAll("&#[0-9]+;","");
						//content = content.replaceAll("&amp;","&").replaceAll("&lt;","<").replaceAll("&gt;",">");
						//content = content.replaceAll("&quot;","\"").replaceAll("&apos;","'");
						//content = content.replaceAll("&([a-z]|[a-z])+;", "");
						

						foundnew ++;
						cv.put("title", Uri.encode(title));
						cv.put("url", url);
						cv.put("published", published);
						cv.put("filterid", fid);
						cv.put("retrospectid", rid);
						cv.put("content", content);
						cv.put("status", 1);
						cv.put("part",b);

						//cv.put("prefilter", momentdf);
						//cv.put("source",source.toString());
						//cv.put("author", author);
						geturi = SqliteWrapper.insert(mCtx, mCtx.getContentResolver(), contentpath, cv);

						Log.i(G,"serviceWork "+rid+" Moment "+filterid+" "+geturi.toString() + " ("+content.length()+" bytes) ");
						
						
						//Log.w(TAG,"Inserted("+mFoo+") New " + geturi.toString() + " " + title.replaceAll("\n"," ") + " ("+content.replaceAll("\n"," ")+")");
						
						//if(mFoo > 5380){ SystemClock.sleep(2000);hush++;} fooTest.sendEmptyMessageDelayed((int)SystemClock.uptimeMillis(),3000);
						
						//
						// CUSOMIZED
						//
						
						// EC
						if( (foundnew == 1 && b != 0) || b==1){setEntryNotification(TAG + " serviceWork", geturi, Uri.decode(title), "Wave");}
					
					}else{continue;}//geturi = Uri.withAppendedPath(contentpath, ""+found);}
					
					//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("progress",b+1); ml.setData(bl); mProgressPlus.sendMessage(ml);}
				}
				//mProgressOut.sendEmptyMessage(2);
				//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("id", headerText); bl.putString("text", "Downloaded " + (ct.length()>1024?ct.length()/1024+"Kb":ct.length()+"b")+" at " + (d.getHours() > 12?d.getHours()-12:d.getHours()) + ":" + (d.getMinutes()<10?"0":"") + d.getMinutes() + "\nContaining " + ctl.length + ", " + foundnew + " New"); ml.setData(bl); setText.sendMessage(ml);}
				
				Log.w(TAG,"Hushed " + hush + " times");
				
				}
			}
			
			
			//mEdt.putLong(loc+"_new",foundnew);
			//mEdt.putLong(loc+"_done",System.currentTimeMillis());
			//mEdt.commit();
	
			
			{
				ContentValues v3 = new ContentValues();
				v3.put("srview","Filtered");
				v3.put("srdate",datetime());
if(rtitle != null && rtitle.length() > 0){v3.put("title",rtitle);}
				v3.put("status",1);
				
				SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), v3, "_id="+fid, null);
			}
			Log.i(G,"serviceWork "+rid+" Filtered "+filterid+"                                                " + rid);
//			{Message ml = new Message(); Bundle bl = new Bundle(); ml.setData(bl); serviceWork.sendMessageDelayed(ml,5000);}
//ready(5000);
			
				
			
			
    		//{Message ml = new Message(); Bundle bl = new Bundle(bdl); ml.setData(bl); serviceWork.sendMessageDelayed(ml,5000);}
    		//{Message ml = new Message(); Bundle bl = new Bundle(); ml.setData(bl); mGet.sendMessage(ml);}
			
			
			
			
			
			
			
			
			
			}};mt.start();
			
	}
	};
	
	long mFoo = -2;
	
	Handler fooTest = new Handler(){public void handleMessage(Message msg){mFoo = SystemClock.uptimeMillis() - (long)msg.what;}};
	

	Uri source = null;int sourceid = -1;int status = -1;String[] workorder;int workat = -10;
	

public void setEntryNotification(String who, Uri geturi, String title, String summary){
		
	
	//summary = summary.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&");
	if(summary.length() > 20 && summary.indexOf("CDATA[") == 3){summary = summary.substring(9, summary.length() - 3);}
		
		summary = summary.replaceAll("<.*?>", "").trim();
		
		
		//
		// CUSOMIZED
		//
		Notification notif = new Notification(August.notifyimage, title + " -- " + summary, System.currentTimeMillis()); // This text scrolls across the top.
		Intent intentJump2 = new Intent(mCtx,com.havenskys.ag.Lookup.class);
		// EC
		intentJump2.putExtra("uri", geturi.toString());
		intentJump2.putExtra("title", title);
		intentJump2.putExtra("moment", Integer.parseInt(geturi.getLastPathSegment()));
		PendingIntent pi2 = PendingIntent.getActivity(this, 0, intentJump2, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY);
        //PendingIntent pi2 = PendingIntent.getActivity(mContext, 0, intentJump2, Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_MULTIPLE_TASK );
        
        //if( syncvib != 3 ){ // NOT OFF
        	//notif.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
        //}else{
        notif.defaults = Notification.DEFAULT_LIGHTS;
        //}
        notif.ledARGB = Color.argb(255, 250, 10, 250);
		notif.setLatestEventInfo(mCtx, title, summary, pi2); // This Text appears after the slide is open
		
		//Date da = new Date();if(da.getHours() < 10){syncvib = 3;}
		
		SharedPreferences mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);
		Editor mEdt = mReg.edit();mEdt.putBoolean("notifier",true);mEdt.commit();
		mNM.notify(1, notif);
	}



















    public void getPage(){
		
		
		
		
		
		
		
		
		
		

		int fid = -1;
		int responsems = -1;
		int port = -1;
		String hostname = ""; String dest = ""; String sititle = ""; String source = ""; String ipaddress = ""; String status = "";
		String split = ""; String parse_item = ""; String parse_link = ""; String parse_title = ""; String parse_author = ""; String parse_content = ""; String parse_published = ""; String parse_build = "";
		String cookies = "";
		
		Cursor c6 = null;
		c6 = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), new String[]{"_id","title","location","sr","srview","srdate","responsems","trailsource","ipaddress","port","split","parse_item","parse_link","parse_title","parse_author","parse_content","parse_published","parse_build","cookies"}, "status > 0 AND sr > date('now','start of month','-1 month')", null, "srdate desc limit 1");
if(c6 != null){if(c6.moveToFirst()){
fid = c6.getInt(0);
dest = c6.getString(2);
sititle = c6.getString(1);
responsems = c6.getInt(6);
source = c6.getString(7);
status = c6.getString(4);
ipaddress = c6.getString(8);
port = c6.getInt(9);					
split = c6.getString(10);
parse_item = c6.getString(11);
parse_link = c6.getString(12);
parse_title = c6.getString(13);
parse_author = c6.getString(14);
parse_content = c6.getString(15);
parse_published = c6.getString(16);
parse_build = c6.getString(17);
cookies = c6.getString(18);

hostname = dest.replaceFirst(".*://","").replaceAll("[/:].*","");

Log.i(G,"serviceWork Getting "+fid+" ("+hostname+")"+dest+"["+sititle+"] "+source+" "+status+" ["+responsems+" ms] ("+ipaddress+":"+port+")");

}c6.close();}




if(fid < 0){
Log.i(G,"serviceWork Get Recognized no work");return;
}



		
		{
		    ContentValues v3 = new ContentValues();
			v3.put("srview","Get");
			v3.put("srdate",datetime());
			v3.put("sr","0");//0000-00-00 00:00:00");
			SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/filter"), v3, "_id="+fid, null);
		}		

		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		{
		
		//w(TAG,"getPage() get ConnectivityManager");
    	ConnectivityManager cnnm = (ConnectivityManager) mCtx.getSystemService(mCtx.CONNECTIVITY_SERVICE);
    	//w(TAG,"getPage() get NetworkInfo");
    	NetworkInfo ninfo = cnnm.getActiveNetworkInfo();
    	if(cnnm == null || ninfo == null){w(TAG,"network off");return;}
    	w(TAG,"getPage() got NetworkInfo state("+ninfo.getState().ordinal()+") name("+ninfo.getState().name()+")");
    	//android.os.Process.getElapsedCpuTime()
    	
    	
		}
    	
    	
    	
    	
    	
    	
    	
		
    	
    	DefaultHttpClient mHC = null;
		mHC = new DefaultHttpClient();
    	
    	
    	
    	if(cookies != null && cookies.length() > 0){
    	CookieStore cs = (mHC != null) ? mHC.getCookieStore(): new DefaultHttpClient().getCookieStore();
		//SharedPreferences mReg = mCtx.getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);Editor mEdt = mReg.edit();
		String[] clist = cookies.split("\n");
		ContentValues cg = new ContentValues();
		for(int h=0; h < clist.length; h++){
			String[] c = clist[h].split(" ",2);
			if(c.length == 2 && c[0].length() > 3){if(cg.containsKey(c[0]) == false){
				//cg.put(c[0], c[1]);
				Cookie logonCookie = new BasicClientCookie(c[0], c[1].replaceAll("; expires=null", ""));
				//Log.w(G,"Carry Cookie mGet2 " + c[0] + ":"+c[1] + " expires("+logonCookie.getExpiryDate()+")" + " path("+logonCookie.getPath()+") domain("+logonCookie.getDomain()+")");
				cs.addCookie(logonCookie);//TODO
			}}
		}
		mHC.setCookieStore(cs);
    	}
    	
    	
		
		
		
		
		
		
		
		
		
		
    	
    	
    	
    	String rurl = dest;
		String httpPage = "";
		//String gourl = baseurl;
		Socket socket = null;
		SSLSocket sslsocket = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		int loopcnt = -1;
		try {
			while(rurl.length() > 0 ){
				
				//mPreferencesEditor.putLong("lastfeedactive", System.currentTimeMillis()).commit();
				loopcnt ++;
				if( loopcnt > 8 ){
					e(TAG,"getPage() Looped 8 times, really?! this many forwards?");
					break;
				}
				boolean secure = rurl.contains("https:") || rurl.contains(":443/") ? true : false;
				String rhostname = rurl.replaceFirst(".*://", "").replaceFirst("/.*", "");
				int rport = secure ? 443 : 80;
				int rid = -1;
				if( rhostname.contains(":") ){
					String[] p = rhostname.split(":");
					rhostname = p[0];
					rport = Integer.parseInt(p[1]);
				}
				
				String rdocpath = rurl.replaceFirst(".*://", "").replaceFirst(".*?/", "/");

				
				
				
				
				
				
{
ContentValues ins = new ContentValues();
        ins.put("title",sititle);
        ins.put("hostname",rhostname);
        ins.put("docpath",rdocpath);
        ins.put("url",rurl);
        ins.put("port",rport);
ins.put("filterid",fid);
        Uri ruri = SqliteWrapper.insert(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), ins);
rid = ruri.getLastPathSegment().matches("[0-9]+")?Integer.parseInt(ruri.getLastPathSegment()):-1;
Log.i(TAG,"Created "+rid+" " + ruri.toString());
String sourceheader = ""; String sourcebody = "";
}
final int frid = rid;














mHC.setRedirectHandler(new RedirectHandler(){
	public URI getLocationURI(HttpResponse arg0, HttpContext arg1) throws ProtocolException {
		
		if( arg0.containsHeader("Location")){

			String url = arg0.getFirstHeader("Location").getValue();
			URI uri = URI.create(url);
			
			ContentValues v3 = new ContentValues();
			v3.put("url",url);
			SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), v3, "_id="+frid, null);

			return uri;
		}else{
			return null;
		}
	}

	public boolean isRedirectRequested(HttpResponse arg0,HttpContext arg1) {
		if( arg0.containsHeader("Location") ){
			String url = arg0.getFirstHeader("Location").getValue();

			{
			    ContentValues v3 = new ContentValues();
				v3.put("url",url);
				SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), v3, "_id="+frid, null);
			}
			
			return true;
		}
		return false;
	}
	
});





				w(TAG,"getPage() "+rid+" hostname("+rhostname+") path("+rdocpath+") gourl("+rurl+")");// file("+f.exists()+","+f.getAbsolutePath()+")");
				/*
				if(f.exists()){
					if(f.lastModified() +30*60*1000 >System.currentTimeMillis()){
						mEdt.putLong(loc+"_saved",System.currentTimeMillis());mEdt.putLong(loc+"_connect",System.currentTimeMillis());mEdt.putLong(loc+"_connected",System.currentTimeMillis());mEdt.putLong(loc+"_responded",System.currentTimeMillis());
						mEdt.putString(loc,f.getAbsolutePath());mEdt.commit();
						return f.getAbsolutePath();
					}
				}
				//*/
				
//				mEdt.putString(loc+"_hostname",hostname);
				//mEdt.putInt(loc+"_forward",loopcnt);
				//mEdt.commit();
				
				//String ipaddr = "";
				long rrr = System.currentTimeMillis();long rrs = 2;
				if( !secure ){
					sslsocket = null;
					w(TAG,"getPage() Connecting to hostname("+rhostname+") port("+rport+")");
					socket = new Socket(rhostname,rport);
					
					//socket = new SecureSocket();
					//SecureSocket s = null;
					
					if( socket.isConnected() ){

						InetAddress iix = socket.getInetAddress();
						i(TAG,"getPage() Connecting to hostname("+hostname+") CONNECTED");
						if(rrs == 2){rrs = System.currentTimeMillis();Log.w(G,"Took " + (rrs - rrr) + "ms ready to connect.");//mEdt.putLong(loc+"_respondedms",(rrs-rrr));mEdt.putLong(loc+"_connect",System.currentTimeMillis());mEdt.commit();

						ContentValues v9 = new ContentValues();
						v9.put("srview", "Link Established");	
                        v9.put("srdate", datetime());
						SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v9,"_id="+fid,null);

						{
						ContentValues v8 = new ContentValues();
						v8.put("responsems", (rrs - rrr));	
						if(iix != null){v8.put("ipaddress", iix.getHostAddress());}
						v8.put("port",socket.getPort());	
						SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/retrospect"),v8,"_id="+rid,null);
						}

						{
						ContentValues v8 = new ContentValues();
						if(iix != null){v8.put("ipaddress", iix.getHostAddress());}
						v8.put("port",socket.getPort());	
						SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v8,"_id="+fid,null);
						}
						
						
					    }
					
					}else{
						int loopcnt2 = 0;
						while( !socket.isConnected() ){
							e(TAG,"getPage() Not connected to hostname("+hostname+")");
							loopcnt2++;
							if( loopcnt2 > 10 ){

								ContentValues v9 = new ContentValues();
								v9.put("srview", "Timeout");	
	                            v9.put("srdate", datetime());
								SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v9,"_id="+fid,null);

								
								e(TAG,"getPage() Not connected to hostname("+hostname+") TIMEOUT REACHED");
								break;
							}
							SystemClock.sleep(300);
						}
					}
					
					//w(TAG,"getPage() Creating Writable to hostname("+hostname+") port("+port+")");
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					//w(TAG,"getPage() Creating Readable to hostname("+hostname+") port("+port+")");
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				}else{
					socket = null;
					w(TAG,"getPage() Connecting Securely to hostname("+hostname+") port("+port+")");
					
					SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
					sslsocket = (SSLSocket) factory.createSocket(hostname,443);
					SSLSession session = sslsocket.getSession();
					X509Certificate cert;
					try { cert = (X509Certificate) session.getPeerCertificates()[0]; }
					catch(SSLPeerUnverifiedException e){
						e(TAG,"getPage() Connecting to hostname("+hostname+") port(443) failed CERTIFICATE UNVERIFIED");
						break;
					}
					
					if( sslsocket.isConnected() ){
						i(TAG,"getPage() Connecting to hostname("+hostname+") CONNECTED");


						InetAddress iix = sslsocket.getInetAddress();
						
						if(rrs == 2){rrs = System.currentTimeMillis();Log.w(G,"Took " + (rrs - rrr) + "ms ready to connect.");//mEdt.putLong(loc+"_respondedms",(rrs-rrr));mEdt.putLong(loc+"_connect",System.currentTimeMillis());mEdt.commit();

							ContentValues v9 = new ContentValues();
							v9.put("srview", "Link Secure");	
                            v9.put("srdate", datetime());
							SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v9,"_id="+fid,null);
						
							{
							
							ContentValues v8 = new ContentValues();
							if(iix != null){v8.put("ipaddress", iix.getHostAddress());}
							v8.put("port", sslsocket.getPort());	
							v8.put("responsems", (rrs - rrr));	
							SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/retrospect"),v8,"_id="+rid,null);
						
						    }
						
							{
								
								ContentValues v8 = new ContentValues();
								if(iix != null){v8.put("ipaddress", iix.getHostAddress());}
								v8.put("port", sslsocket.getPort());	
								SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v8,"_id="+fid,null);
							
							}
							
						
						}
						
//						if(iix != null){mEdt.putString(loc+"_ipaddress",iix.getHostAddress());}
//						mEdt.putInt(loc+"_ipport",sslsocket.getPort());mEdt.commit();

						
					}else{
						int loopcnt2 = 0;
						while( !sslsocket.isConnected() ){
							e(TAG,"getPage() Not connected to hostname("+hostname+")");
							loopcnt2++;
							if( loopcnt2 > 20 ){

								ContentValues v9 = new ContentValues();
								v9.put("srview", "Timeout");	
	                            v9.put("srdate", datetime());
								SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v9,"_id="+fid,null);
								
								e(TAG,"getPage() Not connected to hostname("+hostname+") TIMEOUT REACHED");
								break;
							}
							SystemClock.sleep(300);
						}
					}
											
					//w(TAG,"getPage() Creating Writable to hostname("+hostname+") port("+port+")");
					bw = new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream()));
					//w(TAG,"getPage() Creating Readable to hostname("+hostname+") port("+port+")");
					br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
				}
				
				rrs = 2;//
				//mEdt.putLong(loc+"_connected",System.currentTimeMillis());
				//mEdt.putLong("lastfeedactive", System.currentTimeMillis());mEdt.commit();
				w(TAG,"getPage() Requesting document("+rdocpath+") hostname("+rhostname+") port("+rport+") limit("+DOWNLOAD_LIMIT+")");
				bw.write("GET " + rdocpath + " HTTP/1.0\r\n");
				bw.write("Host: " + rhostname + "\r\n");
				bw.write("User-Agent: Android Doc Chomps Soft\r\n");
				bw.write("Range: bytes=0-"+(1024 * DOWNLOAD_LIMIT)+"\r\n");
				//bw.write("TE: deflate\r\n");
				bw.write("\r\n");
				bw.flush();
				//http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html#sec5

				{
				
					ContentValues v9 = new ContentValues();
					v9.put("srview", secure?"Request securely delivered.":"Request delivered.");	
                    v9.put("srdate", datetime());
					SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v9,"_id="+fid,null);

				}
					
				
				
				
				
				
				//{
				//Log.i(G,"Request delivered Looking up next job while remote processing");
				//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putLong("ms", SystemClock.uptimeMillis()); ml.setData(bl); getlatest.sendMessage(ml);}
				
//				{Message ml = new Message(); Bundle bl = new Bundle(); ml.setData(bl); mGet.sendMessageDelayed(ml,5000);}

				//ready(1000);
				//}
				
				
				
				
				
				
				
				
				//				String status = "";
				String line = "";
				try {
					/*
					if( !secure ){
						if( br.ready() ){
							w(TAG,"getPage() Ready to be read");
						}else{
							int loopcnt2 = 0;long sv = SystemClock.uptimeMillis();
							while( !br.ready() ){
								e(TAG,"getPage() NOT Ready to be read");
								loopcnt2++;
								if( SystemClock.uptimeMillis() - sv > 30000 ){
									e(TAG,"getPage() NOT Ready to be read TIMEOUT REACHED WAITING");
									//line = br.readLine();
									//e(TAG,"getPage() NOT Ready to be read TIMEOUT REACHED WAITING line("+line+")");
									break;
								}
								SystemClock.sleep(300);
							}
						}
					}else{
						// br.ready() doesn't work from the sslsocket source
					}//*/
					Log.i(G,"####################Ready for Reply#############");
					int linecnt = 0;
String sourceheader = "";
				    ContentValues h3 = new ContentValues();
					for(line = br.readLine(); line != null; line = br.readLine()){
						if( line.length() == 0 ){if(rrs == 2){rrs = System.currentTimeMillis();Log.w(G,"Took " + (rrs - rrr) + "ms ready to respond.");//mEdt.putLong(loc+"_respondedms",(rrs-rrr));mEdt.putLong(loc+"_responded",System.currentTimeMillis());mEdt.commit();
						h3.put("protostatus", line);
						
						}
							w(TAG,"getPage() End of header Reached");
							break;
						}
						linecnt++;
						//i(TAG,"getPage() "+fid+","+rid+" received("+line+")");
sourceheader += line;
						if( line.regionMatches(true, 0, "Location:", 0, 9) ){
							String url = line.replaceFirst(".*?:", "").trim();

h3.put("url",url);
							
							i(TAG,"getPage() ###############>>>>>>>>>>>>>> FOUND FORWARD URL("+url+") ");
						}else
						if(line.regionMatches(true,0,"Content-Length:", 0, 15) && line.replaceAll(".*?:","").trim().matches("[0-9]+")){
							int css = Integer.parseInt(line.replaceAll(".*?:","").trim());
							//mEdt.putLong(loc+"_length",css);

h3.put("contentlength",css);
														
							
							Log.i(G,"getPage() contentlength("+css+")");
						}else
						if(line.regionMatches(true,0,"Content-Type:", 0, 13) ){
							String ct = line.replaceAll(".*?:","").replaceAll(";.*","").trim();

h3.put("contenttype",ct);
							
							//mEdt.putString(loc+"_type",ct);
							Log.i(G,"getPage() contenttype("+ct+")");
							if(line.toLowerCase().matches(".*;.*?charset.*?=.*")){
								String ch = line.replaceAll(".*;.*?[cC][hH][aA][rR][sS][eE][tT].*?=","").trim();

h3.put("charset",ch.toUpperCase());								
								
								//mEdt.putString(loc+"_charset",ch.toUpperCase());
								Log.i(G,"getPage() charset("+ch+")");
							}
						}
					
					}

					
					
					
					
					
					
					
					
					
					{
						h3.put("header",sourceheader);
						SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), h3, "_id="+rid, null);
					}
					
					
					
					
					{
					String nr = "";
						Cursor b; b = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), new String[]{"url"}, "_id="+rid, null, "created desc limit 1");
						if(b != null){if(b.moveToFirst()){
							nr = b.getString(0);
						}b.close();}
					    if(nr.length() > 0 && !nr.contentEquals(rurl) ){rurl = nr;}else{rurl = "";}
						
						
					}
					
					
					
					
					if( rurl.length() > 0 ){ Log.w(TAG,"getPage() redirect url: "+rurl);continue; }
					if( line == null ){
						w(TAG,"getPage() End of read");
					}
//					if( linecnt > 0 ){
//						mEdt.putLong("lastfeedactive", System.currentTimeMillis());
//						mEdt.putLong("lowmemory", 0);
//					}
//					mEdt.commit();
					
					String sourcebody = "";
					int httpPageSize = 0;
					if( line != null ){
						//int zerocnt = 0;
						char[] u7 = new char[1024 * 3];
						
						long pr = -1;
						for(int cs = br.read(u7); cs > -1; cs = br.read(u7)){
							pr = SystemClock.uptimeMillis();
							
							
							//Security Alert
							line = Uri.encode(new String(u7));
							line = line.replaceAll("%([8-9A-F1][0-9A-F]|7F|0[1-8BCEF])","");
							line = Uri.decode(line);

							//line = line.replaceAll("\t+"," ").replaceAll("\n+","\n").replaceAll(" +"," ").replaceAll(" +<","<").replaceAll("> +",">");
							//line = line.replaceAll("\n+<","<").replaceAll(">\n+",">");
							
							
							
							//line = line.replaceAll(">",">\n").replaceAll("<","\n<");
							//line = line.replaceAll("\n+</","</");
							//linecnt++;

							
							//i(TAG,"getPage() host("+rhostname+") line("+line+")");

							//fs.write(line.getBytes());
							//line = line.trim();
							
							if(line.length() > 0){//fs != null && 
							//fs.write(line.getBytes());}
							httpPageSize += cs;
sourcebody += line;

							i(G,"getPage() being sent " + cs + " bytes of "+sourcebody.length()+" bytes secure("+secure+") encoded("+line.length()+") " + (SystemClock.uptimeMillis() - pr) + "ms");
							
							//httpPage += Uri.decode(line);
							if( httpPageSize > 1024 * DOWNLOAD_LIMIT ){
								w(TAG,"getPage() downloaded "+DOWNLOAD_LIMIT+"K from the site, moving on.");
								break;
							}

							}
					
						}
						
						i(TAG,"getPage() download concluded");
						
						//fs.flush();
		                //fs.close();
					
		                //FileInputStream h = new FileInputStream(f);
		                //byte[] bx = new byte[(int)f.length()];
		                //h.read(bx);
		                //httpPage = new String(bx);
		                
		                //Log.i(G,"############## httpPage download " + httpPageSize );

		                
		                
		                
		                
{
ContentValues v4 = new ContentValues();
v4.put("srview","Saved " + httpPageSize + " bytes");
v4.put("srdate",datetime());
SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),v4,"_id="+fid,null);
}	
{
	ContentValues v4 = new ContentValues();
v4.put("body",sourcebody);
	SqliteWrapper.update(mCtx, getContentResolver(), Uri.parse(getString(R.string.cp)+"/retrospect"), v4, "_id="+rid, null);
	Log.i(G,"serviceWork "+rid+" Got "+fid+" body("+sourcebody.length()+" bytes)");
	
}



					}
					//w(TAG,"getPage() Downloaded("+httpPageSize+" bytes)");

					
					
					
					
				} catch (OutOfMemoryError e2) {	
					e(TAG,"getPage() OutOfMemoryError e2");
					e2.printStackTrace();
					
				} catch (IOException e1) {
					String msg = null;
					msg = e1.getLocalizedMessage() != null ? e1.getLocalizedMessage() : e1.getMessage();
					if( msg == null ){
						msg = e1.getCause().getLocalizedMessage();
						if( msg == null ){ msg = ""; }
					}
					e(TAG,"getPage() IOException while reading from web server " + msg);
					e1.printStackTrace();
				}//*/
				
				if( !secure ){
					socket.close();
				}else{
					sslsocket.close();
				}

				
				
				
				{
					CookieStore cs2 = mHC.getCookieStore();
			      	List<Cookie> cl2 = cs2.getCookies();
			      	
			      	Bundle co = new Bundle();String cshort2 = "";
			      	for(int i = cl2.size()-1; i >= 0; i--){
			      		Cookie c3 = cl2.get(i);
			      		if(co.containsKey(c3.getName())){continue;}
			      		co.putInt(c3.getName(), 1);
			      		cshort2 += c3.getName() +" " + c3.getValue()+(c3.getExpiryDate()!=null?"; expires="+c3.getExpiryDate():"")+(c3.getPath()!=null?"; path="+c3.getPath():"")+(c3.getDomain()!=null?"; domain="+c3.getDomain():"")+"\n";
			      	}
			      	if(cshort2.length() > 0 ){
			      		ContentValues c = new ContentValues();
			      		c.put("cookies",cshort2);
			      		SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/retrospect"),c,"_id="+rid,null);
			      		SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),c,"_id="+fid,null);
			      	}
				}
				
				
				}//while	

		} catch (ConnectException e2){
			ContentValues c = new ContentValues();
			c.put("srview","" + e2.getLocalizedMessage());
			c.put("srdate",datetime());
			SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),c,"_id="+fid,null);
	      	
			e(TAG,"getPage() connectException");
			e2.printStackTrace();
			
		} catch (UnknownHostException e1) {
			ContentValues c = new ContentValues();
			c.put("srview","Unknown Host");
			c.put("srdate",datetime());
			SqliteWrapper.update(mCtx,getContentResolver(),Uri.parse(getString(R.string.cp)+"/filter"),c,"_id="+fid,null);
	      	
			e(TAG,"getPage() unknownHostException " + e1.getLocalizedMessage());
			//e1.printStackTrace();


		} catch (IOException e1) {
			e(TAG,"getPage() IOException");
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		return;
	}



	//private DefaultHttpClient mHC;
	public Handler mGet = new Handler(){
		long gh = 1;
		public void handleMessage(Message msg){		
			if(gh > SystemClock.uptimeMillis()){Log.w(G,"serviceWork release get load");return;}gh = SystemClock.uptimeMillis() + 10000;

			Bundle bx = msg.getData();mget2(bx);}
		private void mget2(final Bundle bx){//if(mHC == null){mHC = new DefaultHttpClient();}
//		final long bt = SystemClock.uptimeMillis();
		Thread mt = new Thread(){
			public void run(){//SharedPreferences mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);Editor mEdt = mReg.edit();
			
				try{
			getPage();
				}

				catch(OutOfMemoryError e1){
				
					e1.printStackTrace();
					
				}
				
				//			long hy = 10000 - (SystemClock.uptimeMillis() - bt);if(hy < 5000){hy = 5000;}
//Log.w(G,"serviceWork Regetlatest " + hy);
			
			
			}};mt.start();
//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putLong("ms", SystemClock.uptimeMillis()); ml.setData(bl); getlatest.sendMessage(ml);}
			
			
		}
	};
	
	
	File getfilepath(){
    	File file = null;
    	
		if(Environment.getExternalStorageState().contentEquals(Environment.MEDIA_MOUNTED)){
			file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "wave");
		}else{
			file = new File(getCacheDir().getAbsolutePath(), "wave");//Environment.getDownloadCacheDirectory().getAbsolutePath()
		}
		//Log.w(TAG,"filepath " + file.getAbsolutePath());
		return file;
    }
	
	
	
	
	
	
	
	
	
	
	
	int DOWNLOAD_LIMIT = 400 * 1024;
	public void e(String t, String x){Log.e(t,x);}
	public void i(String t, String x){Log.i(t,x);}
	public void w(String t, String x){Log.w(t,x);}
	private Handler logoly = new Handler(){public void handleMessage(Message msg){Bundle bx = msg.getData();int l = bx.getInt("l");String text = bx.getString("text");switch(l){case 2:Log.e(TAG,":"+text);break;case 3:Log.w(TAG,":"+text);break;default:Log.i(TAG,":"+text);break;}}};
	
	public String datetime(){
		String g = "";
		Date d = new Date();
		g = (d.getYear()+1900)+"-"+((d.getMonth() < 9)?"0":"")+((d.getMonth()+1))+"-"+((d.getDate() < 10)?"0":"")+d.getDate()+"T"+((d.getHours() < 10)?"0":"")+d.getHours()+":"+((d.getMinutes() < 10)?"0":"")+d.getMinutes()+":00";
		{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","generated date "+g);bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
		return g;
	}
	
	private String fixDate(String updated) {
		//day, month dd, yyyy hh:mm tt
		//m/d/year hh:mm tt
		//2010-07-15T19:07:30+05:00
		if(updated.indexOf("CDATA[") > -1){updated = updated.substring(updated.indexOf("CDATA[")+6, updated.lastIndexOf("]]"));}
		String[] dateparts = updated.split(" ");
		if(dateparts.length == 1){dateparts = updated.replaceAll("T", " ").split(" ");}
		//Log.i(TAG,"fixDate ("+updated+") parts("+dateparts.length+") length("+updated.length()+")");
		if(updated.length() > 35){return datetime();}
		//if( dateparts[0].contains(",") ){ dateparts = updated.replaceFirst("T", " ").replaceFirst("..., ", "").split(" "); }
		
		
		if(dateparts[0].contains("/") && dateparts[0].contains(":")){
			
			int year = Integer.parseInt(dateparts[0].substring(dateparts[0].lastIndexOf("/")+1, dateparts[0].lastIndexOf("/")+5));
			int mon = Integer.parseInt(dateparts[0].substring(0, dateparts[0].indexOf("/")));
			int day = Integer.parseInt(dateparts[0].substring(dateparts[0].indexOf("/")+1, dateparts[0].lastIndexOf("/")));
			if( mon < 10 ){
				updated = year + "-0" + mon + "-";
			}else{
				updated = year + "-" + mon + "-";
			}
			if( day < 10 ){updated += "0"+ day + " ";}else{updated += day + " ";}
			int h = 0;int m = 0;
			h = Integer.parseInt(dateparts[0].substring(dateparts[0].indexOf(":")-2, dateparts[0].lastIndexOf(":")));
			m = Integer.parseInt(dateparts[0].substring(dateparts[0].indexOf(":")+1));
			if(dateparts[1].toLowerCase().contains("pm") && h < 12){
				h+=12;
			}if(dateparts[1].toLowerCase().contains("am") && h == 12){
				h-=12;
			}
			if( h < 10 ){updated += "0"+ h + ":";}else{updated += h + ":";}
			if( m < 10 ){updated += "0"+ m;}else{updated += m;}
			
			{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Updated date to SQLite Format("+updated+") #3");bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
			
		}
		//2010-07-15T19:07:30+05:00
		if(dateparts[0].contains("-")
				&& dateparts[1].contains(":")){
			String[] dp = dateparts[0].replaceAll("-0", "-").split("-");
			int year = Integer.parseInt(dp[0]);
			int mon = Integer.parseInt(dp[1]);
			int day = Integer.parseInt(dp[2]);
			if( mon < 10 ){
				updated = year + "-0" + mon + "-";
			}else{
				updated = year + "-" + mon + "-";
			}
			if( day < 10 ){updated += "0"+ day + " ";}else{updated += day + " ";}
			int h = 0;int m = 0;
			String[] t = dateparts[1].replaceAll(":0", ":").split(":");
			h = Integer.parseInt(t[0]);
			m = Integer.parseInt(t[1]);
			/*if(dateparts[2].toLowerCase().contains("pm") && h < 12){
				h+=12;
			}if(dateparts[2].toLowerCase().contains("am") && h == 12){
				h-=12;
			}//*/
			if( h < 10 ){updated += "0"+ h + ":";}else{updated += h + ":";}
			if( m < 10 ){updated += "0"+ m;}else{updated += m;}
			
			{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Updated date to SQLite Format("+updated+") #2");bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
		}
		if(dateparts[0].contains("/")
				&& dateparts[1].contains(":")){
			String[] dp = dateparts[0].split("/");
			int year = Integer.parseInt(dp[2]);
			int mon = Integer.parseInt(dp[0]);
			int day = Integer.parseInt(dp[1]);
			if( mon < 10 ){
				updated = year + "-0" + mon + "-";
			}else{
				updated = year + "-" + mon + "-";
			}
			if( day < 10 ){updated += "0"+ day + " ";}else{updated += day + " ";}
			int h = 0;int m = 0;
			String[] t = dateparts[1].split(":");
			h = Integer.parseInt(t[0]);
			m = Integer.parseInt(t[1]);
			if(dateparts[2].toLowerCase().contains("pm") && h < 12){
				h+=12;
			}if(dateparts[2].toLowerCase().contains("am") && h == 12){
				h-=12;
			}
			if( h < 10 ){updated += "0"+ h + ":";}else{updated += h + ":";}
			if( m < 10 ){updated += "0"+ m;}else{updated += m;}
			
			{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Updated date to SQLite Format("+updated+") #2");bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
		}
		if( dateparts.length > 5 || (dateparts.length == 5 && dateparts[3].contains(":")) ){
			// Month
			String[] month = new String("xxx Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec xxx").split(" ");
			int mon = 0;
			for(;mon < month.length; mon++){
				if( month[mon].equalsIgnoreCase(dateparts[2]) ){ break; } 
				if(dateparts[1].startsWith(month[mon])){
					break;
				}
			}
			if( mon == 13 ){
				{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Unable to determine month in fixDate("+updated+")");bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
				return updated;
			}
			
			// Year
			Date d = new Date();
			int year = d.getYear()+1900;
			if(dateparts[2].length() == 4){
				year = Integer.parseInt(dateparts[2]);
			}else if(dateparts[3].length() == 4){
				year = Integer.parseInt(dateparts[3]);
			}else if(dateparts[4].length() == 4){
				year = Integer.parseInt(dateparts[4]);
			}else{
				{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Unable to determine year in fixDate("+updated+") 2("+dateparts[2]+") 3("+dateparts[3]+")");bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
			}
			
			// Day
			int day = -1;
			
			if(dateparts[2].length() == 4 && !dateparts[0].contains(",") && dateparts[0].matches("[0-9]+")){
				day = Integer.parseInt(dateparts[0]);
			}else if(dateparts[3].length() == 4){// && dateparts[1].length() > 0){// && dateparts[2].contains(",")){
				//dateparts[1] = dateparts[1].replaceAll(",", "");
				day = Integer.parseInt(dateparts[1]);
			}
			
			// Date == updated
			updated = year + "-";
			updated += (mon < 10?"0"+mon:mon) + "-";
			//if( mon < 10 ){
				//updated = year + "-0" + mon + "-";
			//}else{
				//updated = year + "-" + mon + "-";
			//}
			updated += (day < 10?"0"+day:day) + " ";
			//if( day < 10 ){updated += "0"+ day + " ";}else{updated += day + " ";}
			
			// Hour Minute
			
			int h = 0;int m = 0;
			
			if(dateparts[3].contains(":")){
				String[] t = dateparts[3].split(":");
				h = Integer.parseInt(t[0]);
				m = Integer.parseInt(t[1]);
			}else if(dateparts[4].contains(":")){
				String[] t = dateparts[4].split(":");
				h = Integer.parseInt(t[0]);
				m = Integer.parseInt(t[1]);
				if(dateparts[5].toLowerCase().contains("pm") && h < 12){
					h+=12;
				}if(dateparts[5].toLowerCase().contains("am") && h == 12){
					h-=12;
				}
			}
			
			
			
			//Time
			updated += (h < 10?"0"+h:h)+":";
			updated += (m < 10?"0"+m:m);
			//if( h < 10 ){updated += "0"+ h + ":";}else{updated += h + ":";}
			//if( m < 10 ){updated += "0"+ m;}else{updated += m;}
			
			//{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Updated date to SQLite Format("+updated+")");bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
		}
		
		return updated;
	}
	
	
	
	
	//private long storeTime = 0;
	Handler storePage = new Handler(){
		public void handleMessage(Message msg){
			//if(storeTime > System.currentTimeMillis()){ easyStatus("Refresh occured within 8 seconds, try later.");  return; }//Bundle bl = msg.getData();Message ml = new Message(); ml.setData(bl);storePage.sendMessageDelayed(ml,1750);return;} 
			//storeTime = System.currentTimeMillis()+7750;
			Log.i(TAG,"storePage");
			final Bundle bdl = msg.getData();
	//		s01(bdl);
	
		
	//public void s01(final Bundle bdl){
		
		Thread tx = new Thread(){
				public void run(){
					String mhpb = bdl.getString("mhpb");
					String loc = bdl.getString("storloc");
					Log.i(TAG,"storePage thread " + mhpb.length() + " bytes to " + loc + " ");
					//byte[] mx = new byte[mhpb.length+1 * 2];
					//String mHP = "";
					//for(int i = 0; i+6 < mhpb.length; i++){mHP += mhpb[i]+mhpb[++i]+mhpb[++i]+mhpb[++i]+mhpb[++i]+mhpb[++i];}
					/*
					int errorcnt = 0;
					for(errorcnt = 0; errorcnt < 5; errorcnt++){
					try {
					mHP = new String(mhpb);
					break;
					} catch (OutOfMemoryError e){
						
						Log.e(G,"OutOfMemory Error Received while Storing Page");
						//{Message ml = new Message(); Bundle bl = new Bundle(bdl); ml.setData(bl); storePage.sendMessageDelayed(ml, 3500);}return;
					}
					}	
					if( errorcnt == 5 || mHP == null || mHP.length() == 0){
						easyStatus("Wild errors " + errorcnt);
						return;
					}//*/
						
					
					SharedPreferences mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);Editor mEdt = mReg.edit();
					//mEdt.putString(loc+"url", murl);
					
					Log.w(TAG,"storePage 86 storing content");
					//mx = null;
					mEdt.putString(loc, mhpb);
					mEdt.putLong("bucket_saved", System.currentTimeMillis());
					mEdt.putLong(loc+"_saved", System.currentTimeMillis());
					mEdt.commit();
					Log.w(TAG,"safeHttpGet() 368 saved");
				
					String titlr = bdl.getString("title");
			        String murl = bdl.getString("murl");	
					long sh = bdl.getLong("startdl");
			        String dest = bdl.getString("dest");
					String statusline = bdl.getString("statusline");
			        String pageconnectknow = bdl.getString("pageconnectknow");
					
					//final String murl = mUrl;final String mhp = mHP;
			        	//Thread eb = new Thread(){public void run(){
			        	if( pageconnectknow == null || mhpb.contains(pageconnectknow) ){
			        	
			        		//easyStatus("Acquired "+titlr+" in "+(SystemClock.uptimeMillis()-sh)/1000+" secs.\n"+dest);
			        	//{Message mxm = new Message(); Bundle bxb = new Bundle(); bxb.putString("subtitle","Acquired "+titlr+" in "+(SystemClock.uptimeMillis()-sh)/1000+" secs.\n"+dest ); mxm.setData(bxb);easyViewerHandler.sendMessageDelayed(mxm,10);}
						//}};eb.start();
			      		//mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
			      		//mEdt.putString(loc, mHP);mEdt.commit();
			        	//{Message mx = new Message();Bundle bxb = new Bundle();// bxb.putString("string", loc+",");bxb.putString(loc, mHP);
			      		//bxb.putString("long", "lasthttp");bxb.putLong("lasthttp", System.currentTimeMillis());
			      		// mx.setData(bxb);setrefHandler.sendMessageDelayed(mx,50);}//*/
			        	}else if(pageconnectknow != null){//easyStatus("Invalid Download");
			        	{Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("l",2);bl.putString("text",titlr+" downloaded didn't pass acknowledgement("+bdl.getString("pageconnectknow")+"). Lov'n cookies and the rest.");ml.setData(bl);logoly.sendMessageDelayed(ml,75); }
			        	String ct = "";
			        	{Message ml = new Message(); Bundle bxb = new Bundle(); bxb.putString("remove", "connect");  ml.setData(bxb);setrefHandler.sendMessageDelayed(ml, 50);}
						//String[] h = mHP.split("\n");for(int b = 0; b < h.length; b++){{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","pageknow("+bdl.getString("pageconnectknow")+") "+h[b]);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
						//if(h[b].contains("content1=") ){ct += h[b].substring(h[b].indexOf("content1=")+9, h[b].indexOf(";HS;", h[b].indexOf("content1=")));}}
						//ct = ct.replaceAll("%0a", "\n").trim();
						Log.e(TAG,"know("+bdl.getString("pageconnectknow")+")");//Log.w(G,"know("+bdl.getString("pageconnectknow")+")"+h[b]);
			        	}
			        {Message ml = new Message(); Bundle bx = new Bundle();bx.putString("text","Downloaded status(" + statusline + ") loc("+loc+") mUrl("+murl+") " + mhpb.length() + " bytes.");ml.setData(bx);logoly.sendMessageDelayed(ml,pRate);}
			    
					
				}
			};
			tx.start();
		//}	
		}
	};
	
	
	private Handler setrefHandler = new Handler(){
		Bundle bx;public void handleMessage(Message mx){
		final Bundle bx = mx.getData();
			Thread tx = new Thread(){public void run(){
		
	SharedPreferences mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);
			Editor mEdt = mReg.edit();
	String[] ht = new String[]{"int","long","float","remove","string"};
	for(int b = 0; b < ht.length; b++){
		String t = ht[b];
		if(!bx.containsKey(t)){continue;}
	String[] nm = (bx.getString(t)+",0").split(",");
	for(int h = 0; h < nm.length; h++){
		String k = nm[h];if(k==null){continue;}if(k.length()<=1){continue;}else if(k.contentEquals("null")){continue;}
		if(!bx.containsKey(k) && !t.contentEquals("remove") ){
			{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","error reading incoming preference (doesn't exist in bundle) "+k);bx.putInt("l", 2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
			continue;}String len = "";
		if(t.contentEquals("float")){mEdt.putFloat(k, bx.getFloat(k));len += bx.getFloat(k)+"";}
		else if(t.contentEquals("int")){mEdt.putInt(k, bx.getInt(k));len += bx.getInt(k)+"";}
		else if(t.contentEquals("long")){mEdt.putLong(k, bx.getLong(k));len += bx.getLong(k)+"";}
		else if(t.contentEquals("string")){mEdt.putString(k, bx.getString(k));len += bx.getString(k)+"";}
		else if(t.contentEquals("remove")){mEdt.remove(k);}
		//{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","setpref "+k+" "+t + " "+(len.length() <100?len:len.length()+"b"));mx.setData(bx);logoly.sendMessageDelayed(mx,100);}
	}mEdt.commit();	
	}
	
	}};tx.start();
	   	}
	};
	
}
