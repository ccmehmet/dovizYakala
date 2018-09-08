package com.example.mehmetcece.dovizyakala;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class Giris extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String deger1="";
    String deger2="";
    TextView dovizDegerTXT;
    List<Doviz> dovizList= new ArrayList<>();
    TextView smblTxt;
    AutoCompleteTextView doviz1;
    AutoCompleteTextView doviz2;
    Hashtable<String, String> semboller=new Hashtable<>();
    Handler handler;
    Runnable runnable;
    TextView[] textArray;
    boolean k;
    TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        //VeriIndir veriIndir= new VeriIndir();
        doviz1=findViewById(R.id.doviz1);
        doviz2=findViewById(R.id.doviz2);
        txtTime =findViewById(R.id.txtTime);

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

        dovizDegerTXT=findViewById(R.id.dovizDegerTXT);
        ImageView doviz_ok_1=findViewById(R.id.doviz_ok_1);
        ImageView doviz_ok_2=findViewById(R.id.doviz_ok_2);
        doviz1.setThreshold(1);
        doviz2.setThreshold(1);
        ////////////////// Diğer activy den gelen veriler \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        Bundle bundleString=getIntent().getExtras();
        assert bundleString != null;
        String json= (String) bundleString.get("JSON");
        ArrayList<String> bundleArrayKey=getIntent().getStringArrayListExtra("keyLER");
        ArrayList<Bitmap> gelenBayraklar=getIntent().getParcelableArrayListExtra("bayraklar");
        ImageView ımageView=findViewById(R.id.denemeImg);
//        if(gelenBayraklar.size()!=0){
//            ımageView.setImageBitmap(gelenBayraklar.get(0));
//        }

        JSONObject jsonObjectVeri= null;
        try {
            jsonObjectVeri = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //dovizListeDoldur(bundleArrayKey,jsonObjectVeri);
        deger1=degerAL(doviz1);
        deger2=degerAL(doviz2);
        dovizDegeri();
        //region TextView Tanımlamaları
        TextView textView1=findViewById(R.id.textView1);
        TextView textView2=findViewById(R.id.textView2);
        TextView textView3=findViewById(R.id.textView3);
        TextView textView4=findViewById(R.id.textView4);
        TextView textView5=findViewById(R.id.textView5);
        TextView textView6=findViewById(R.id.textView6);
        TextView textView7=findViewById(R.id.textView7);
        TextView textView8=findViewById(R.id.textView8);
        TextView textView9=findViewById(R.id.textView9);
        textArray=new TextView[]{textView1,textView2,textView3,textView4,
                textView5,textView6,textView7,textView8,textView9};
        //endregion
        doviz_ok_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dovizList.size()!=0){
                    doviz1.showDropDown();
                }
            }
        });
        doviz_ok_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dovizList.size()!=0){
                    doviz2.showDropDown();
                }
            }
        });

        doviz1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deger1=degerAlUc(parent.getItemAtPosition(position).toString());
                dovizDegeri();
            }
        });
        doviz2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deger2=degerAlUc(parent.getItemAtPosition(position).toString());
                dovizDegeri();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void oyunaBasla(View v){
        //score=0;
        k=true;
        //txtScore=(TextView) findViewById(R.id.txtScore);
        //txtScore.setText("Score: "+ score);
        for (TextView aTextArray : textArray) {
            String a=aTextArray.getText().toString()+" ";
            aTextArray.setText(a+sembolAL(degerAlUc(doviz2.getText().toString())));
        }
        GorselleriSakla();

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTime=(TextView) findViewById(R.id.txtTime);
                txtTime.setText("Time: "+ millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                txtTime= (TextView) findViewById(R.id.txtTime);
                txtTime.setText("Süreniz Doldu");
                k=false;
                handler.removeCallbacks(runnable);
                //Rekor();
            }
        }.start();
    }

    public void GorselleriSakla(){
        handler=new Handler();
        runnable= new Runnable() {
            @Override
            public void run() {
                for (TextView text:textArray){
                    text.setVisibility(View.INVISIBLE);
                }

                Random r = new Random();
                int i = r.nextInt(8-0);
                textArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);
    }

    String degerAlUc(String gDeger){
        return gDeger.substring(0,3);
    }

    String degerAL(AutoCompleteTextView doviz){
        if (!doviz.getText().toString().equals("") ){ // Text dolu olduğunda
            return doviz.getText().toString().substring(0,3);
        }
        else if(doviz.getText().toString().equals("") ){ //Text boş hint al
            return doviz.getHint().toString();
        }
        return "";
    }

    public void degistir(View view){
        String d1;
        String d2;

        if (doviz1.getText().toString().equals("") && doviz2.getText().toString().equals("")){
            d2=doviz1.getHint().toString();
            d1=doviz2.getHint().toString();
            doviz1.setHint(d1);
            doviz2.setHint(d2);

            deger1=degerAL(doviz1);
            deger2=degerAL(doviz2);
            dovizDegeri();
        }
        else if (!doviz1.getText().toString().equals("") && !doviz2.getText().toString().equals("")){
            d2=doviz1.getText().toString();
            d1=doviz2.getText().toString();
            doviz1.setText(d1);
            doviz2.setText(d2);

            deger1=degerAL(doviz1);
            deger2=degerAL(doviz2);
            dovizDegeri();
        }
        else if (!doviz1.getText().toString().equals("") && doviz2.getText().toString().equals("")){
            d2=doviz1.getHint().toString();
            d1=doviz2.getHint().toString();
            doviz1.setHint(d1);
            doviz2.setHint(d2);

            d2=doviz1.getText().toString();
            doviz1.setText("");
            doviz2.setText(d2);

            deger1=degerAL(doviz1);
            deger2=degerAL(doviz2);
            dovizDegeri();
        }
        else if (doviz1.getText().toString().equals("") && !doviz2.getText().toString().equals("")){
            d2=doviz1.getHint().toString();
            d1=doviz2.getHint().toString();
            doviz1.setHint(d1);
            doviz2.setHint(d2);

            d1=doviz2.getText().toString();
            doviz2.setText("");
            doviz1.setText(d1);

            deger1=degerAL(doviz1);
            deger2=degerAL(doviz2);
            dovizDegeri();
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
    //region onItemSelected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //endregion

//    void dovizListeDoldur(ArrayList keyArray,JSONObject jsonObject){
//        for (int i=0;i<keyArray.size();i++){
//            try {
//                String veriDoviz=jsonObject.getString(keyArray.get(i).toString());
//                JSONObject jsonObject1=new JSONObject(veriDoviz);
//                dovizList.add(new Doviz((String) keyArray.get(i),jsonObject1.getString("currencyName"),
//                        sembolAL(jsonObject1,(String) keyArray.get(i))));
//            }
//            catch (Exception e){
//                e.getLocalizedMessage();
//            }
//        }
//        String[] dovizlerDizi=new String[dovizList.size()];
//
//        for (int i=0;i<dovizList.size();i++){
//            dovizlerDizi[i]=dovizList.get(i).toString();
//        }
//        ArrayAdapter<String>adapter1=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,dovizlerDizi);
//        ArrayAdapter<String>adapter2=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,dovizlerDizi);
//        doviz1.setAdapter(adapter1);
//        doviz2.setAdapter(adapter2);
//    }

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

    void dovizDegeri(){
        doviz1.setHint("");
        doviz2.setHint("");

        VeriIndir2 veriIndir2=new VeriIndir2();

        try{
            String url="http://free.currencyconverterapi.com/api/v5/convert?q="+deger1+"_"+deger2+"&compact=y";
            veriIndir2.execute(url);
        }
        catch (Exception e){
            System.out.println("HATA: "+e.getLocalizedMessage());
        }
    }

    private class VeriIndir2 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String sonuc="";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
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
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String deger = jsonObject.getString(deger1+"_"+deger2);
                JSONObject jsonObject1=new JSONObject(deger);
                String dovizDeger=jsonObject1.getString("val");
                dovizDeger+=" "+sembolAL(degerAL(doviz2));
                dovizDegerTXT.setText(dovizDeger);
            }
            catch (Exception e){
                System.out.println("HATA: "+e.getLocalizedMessage());
            }
        }
    }

    String sembolAL(String id){
        for (int i=0;i<dovizList.size();i++){
            if (id.equals(dovizList.get(i).getId())){
                return dovizList.get(i).getSembol();
            }
        }
        return "";
    }

    public void hesapla(View v){
        Intent intent = new Intent(getApplicationContext(),Hesapla.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences =this.getSharedPreferences
                ("com.example.mehmetcece.dovizyakala_storingdata",Context.MODE_PRIVATE);
        String d1=sharedPreferences.getString("dDoviz1","");
        String d2=sharedPreferences.getString("dDoviz2","");
        if (!d1.equals(doviz1.getText().toString())){
            d1=doviz1.getText().toString();
            sharedPreferences.edit().putString("dDoviz1",d1).apply();
            doviz1.setText(d1);
        }
        if (!d2.equals(doviz2.getText().toString())){
            d2=doviz2.getText().toString();
            sharedPreferences.edit().putString("dDoviz2",d2).apply();
            doviz2.setText(d2);
        }
    }
}
