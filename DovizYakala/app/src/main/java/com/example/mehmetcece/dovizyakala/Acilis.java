package com.example.mehmetcece.dovizyakala;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Acilis extends AppCompatActivity {

    Hashtable<String, String> semboller=new Hashtable<>();
    ArrayList<Doviz> dovizList=new ArrayList<>();
    List<Bitmap> bayraklarList=new LinkedList<>();
    Bitmap bayrakBitmap;
    ArrayList keyArray=new ArrayList<String>();
    JSONObject jsonObject1;
    int d=0; // Dişli

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis);
        VeriIndir veriIndir= new VeriIndir();

        //region Semboller
        semboller.put("BTN","Nu.");
        semboller.put("XAF","C");
        semboller.put("LYD","ل.د");
        semboller.put("XOF","CFA");
        semboller.put("PGK","K");
        semboller.put("RWF","RF");
        semboller.put("WST","WS$");
        semboller.put("AMD","դր");
        semboller.put("CVE","$");
        semboller.put("ERN","Nfk");
        semboller.put("GEL","₾");
        semboller.put("HTG","G");
        semboller.put("JOD","JD");
        semboller.put("MWK","MK");
        semboller.put("MRO","UM");
        semboller.put("MZN","MTn");
        semboller.put("STD","Db");
        semboller.put("SLL","Le");
        semboller.put("SDG","£");
        semboller.put("AOA","KZ");
        semboller.put("BHD",".د.ب");
        semboller.put("BIF","FBu");
        semboller.put("KWD","د.ك");
        semboller.put("LSL","L");
        semboller.put("MMK","K");
        semboller.put("TOP","T$");
        semboller.put("VEF","Bs.");
        semboller.put("DZD","د.ج");
        semboller.put("CDF","FC");
        semboller.put("GMD","D");
        semboller.put("IQD","د.ع");
        semboller.put("MGA","Ar.");
        semboller.put("MDL","leu");
        semboller.put("MAD","د.م.");
        semboller.put("TRY","₺");
        semboller.put("AED","د.إ");
        semboller.put("VUV","Vt");
        semboller.put("BDT","৳");
        semboller.put("KMF","CF");
        semboller.put("DJF","FDj");
        semboller.put("ETB","Br");
        semboller.put("XPF","F");
        semboller.put("GHS","₵");
        semboller.put("GNF","FG");
        semboller.put("XDR","SDR");
        semboller.put("MOP","毫");
        semboller.put("MVR",".ރ");
        semboller.put("SZL","E");
        semboller.put("TJS","TJS");
        semboller.put("TND","د.ت");
        semboller.put("TMT","m");
        semboller.put("ZMW","ZK");
        semboller.put("BTC","₿");
        //endregion

        if(!internetKontrol()){
            Toast.makeText(getApplicationContext(), R.string.internetBaglantiSorunuMesaji, Toast.LENGTH_LONG).show();
        }
        else{
            String urlS="https://free.currencyconverterapi.com/api/v6/currencies";
            veriIndir.execute(urlS);
        }
    }

    ///////////////////////////////
    HttpsURLConnection connection=null;
    class resimIndir extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL urlBitmap;
            try {
                // https://github.com/ccmehmet/currency_flag/blob/master/aed.png?raw=true
                urlBitmap=new URL(strings[0]);
                connection=(HttpsURLConnection)urlBitmap.openConnection();
                connection.setDoInput(true);
                InputStream input=connection.getInputStream();
                bayrakBitmap= BitmapFactory.decodeStream(input);
                input.close();
                return bayrakBitmap;
            }catch (Exception e){
                return null;
            }finally {

                if (connection!=null){
                    try {
                        connection.getInputStream().close();
                    }catch (Exception e){
                        e.getLocalizedMessage();
                    }
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bayraklarList.add(bitmap);
            ImageView view=findViewById(R.id.denemeIMG);
            TextView textView=findViewById(R.id.txtAcilis);
            textView.setText(String.valueOf(d));
            dovizList.get(Acilis.this.d).setBayrak(bitmap);
            view.setImageBitmap(dovizList.get(Acilis.this.d).getBayrak());

            disliSistemi();// Dişlinin birinci kliki
        }
    }

    //////////// Resim indirden sonra dönen dişliler
    void disliSistemi(){
        if(keyArray.size()>d){
            d++; // Dişlinin ikinci kliki
            resimIndir resimIndir=new resimIndir();
            resimIndir.execute("https://github.com/ccmehmet/currency_flag/blob/master/"+keyArray.get(d).toString().toLowerCase()+".png?raw=true");
        }
    }

    protected boolean internetKontrol(){
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();
        if (netInfo!=null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }

//    String bitmapToString(Bitmap bitmap){
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
//        byte[]b=byteArrayOutputStream.toByteArray();
//        String dosya= Base64.encodeToString(b,Base64.DEFAULT);
//        return dosya;
//    }

    void dovizListeDoldur(ArrayList keyArray,JSONObject jsonObject){
        Drawable drawable = getApplication().getResources().getDrawable(R.drawable.temp);
        for (int i=0;i<keyArray.size();i++){
            try {
                String veriDoviz=jsonObject.getString(keyArray.get(i).toString());
                JSONObject jsonObject1=new JSONObject(veriDoviz);
                dovizList.add(new Doviz((String) keyArray.get(i),jsonObject1.getString("currencyName"),
                        sembolAL(jsonObject1,(String) keyArray.get(i)),((BitmapDrawable)drawable).getBitmap()));
            }
            catch (Exception e){
                e.getLocalizedMessage();
            }
        }
        String[] dovizlerDizi=new String[dovizList.size()];

        for (int i=0;i<dovizList.size();i++){
            dovizlerDizi[i]=dovizList.get(i).toString();
        }
    }

    String sembolAL(JSONObject json,String key){
        if (semboller.containsKey(key)){
            return semboller.get(key);
        }
        else {
            try {
                return json.getString("currencySymbol");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return key;
    }

    class VeriIndir extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String sonuc="";
            URL urlS;
            HttpsURLConnection httpsURLConnection;
            try {
                urlS=new URL(strings[0]);
                httpsURLConnection=(HttpsURLConnection) urlS.openConnection();
                InputStream inputStream=httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader =new InputStreamReader(inputStream);
                int veri =inputStreamReader.read();

                while (veri>0){
                    char chatKarakter=(char) veri;
                    sonuc+=chatKarakter;
                    veri=inputStreamReader.read();
                }
                return sonuc;
            }
            catch (Exception e){
                Toast.makeText(Acilis.this, R.string.baglantiKesildi, Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String stringJSON="";
            try {
                JSONObject jsonObject = new JSONObject(s);
                stringJSON = jsonObject.getString("results");
                jsonObject1=new JSONObject(stringJSON);

                Iterator iterator=jsonObject1.keys();
                while (iterator.hasNext()){
                    String key=(String)iterator.next();
                    JSONObject issue = jsonObject1.getJSONObject(key);
                    keyArray.add(issue.optString("id"));
                }
                resimIndir resimIndir=new resimIndir();
                resimIndir.execute("https://github.com/ccmehmet/currency_flag/blob/master/"+keyArray.get(0).toString().toLowerCase()+".png?raw=true");// KURMA KOLU...
                dovizListeDoldur(keyArray,jsonObject1);
            }
            catch (Exception e){
                System.out.println("HATA: "+e.getLocalizedMessage());

            }

            //Intent intent=new Intent(getApplicationContext(),Giris.class);
            //intent.putStringArrayListExtra("keyLER",keyArray);
            //intent.putExtra("JSON",stringJSON);
            //startActivity(intent);
        }
    }
   
}
