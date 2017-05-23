package com.example.lenovo.tsm;


import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.ListFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZatrzymanieWysylekSms#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ZatrzymanieWysylekSms extends ListFragment {


    AppConfig appConfig = AppConfig.getInstance();
    ProgressDialog prgDialog;
    TextView errorMsg;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList = new ArrayList<String>();
    public String text;
    final Holder holder = new Holder();

    public class Holder {
        Button buttonPlus;
        CheckedTextView checkedTextView;
    }


    public ZatrzymanieWysylekSms() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ZatrzymanieWysylekSms newInstance(String param1, String param2) {
        ZatrzymanieWysylekSms fragment = new ZatrzymanieWysylekSms();

        return fragment;
    }

    String  [] listtemp = {"Item1", "Item2"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Zatrzymanie wysyłek Sms");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zatrzymanie_sms, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {

        getStatus(this.getView());

        list =  getListView();

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.cheacked_list, arrayList);
        list.setAdapter(adapter);

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                text= (String)parent.getItemAtPosition(position);

                //Toast.makeText(getActivity().getApplicationContext(), "Wybrałeś "+ text     , Toast.LENGTH_LONG).show();
            }

        });

        Button buttonWznow = (Button) getActivity().findViewById(R.id.SmsWznowienie);
        buttonWznow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(text!= null){

                    //((CheckedTextView)(getActivity().findViewById(R.id.checkedList))).setText(activeToDeactive(text));
                    String temp = getID(text);
                    startSMS(view,temp);
                    //Toast.makeText(getActivity().getApplicationContext(), "Wznowiłeś", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Proszę coś wybrać", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button buttonZatrzymaj = (Button) getActivity().findViewById(R.id.SmsZatrzymanie);
        buttonZatrzymaj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(text!= null){

                    //((CheckedTextView)(getActivity().findViewById(R.id.checkedList))).setText(activeToDeactive(text));
                    String temp = getID(text);
                    stopSMS(view, temp);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Proszę coś wybrać", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public String getID(String text){
        String ID= null;
        String [] temp_tab = text.split(" ");
        for(int i = 0; i<temp_tab.length ; i++){
            if(temp_tab[i].equals("ID:")){
               ID  = temp_tab[i+1];
            }
        }
        return ID;
    }

    public String activeToDeactive(String text){
        String temp = null;
        String [] temp_tab = text.split(" ");
        for(int i = 0; i<temp_tab.length ; i++){
            if(temp_tab[i] == "Aktywny"){
                temp_tab[i] = "Nieaktywny";
            }
        }
        temp = Arrays.toString(temp_tab);
        return temp;
    }

    public String deactiveToActive(String text){
        String temp = null;
        String [] temp_tab = text.split(" ");
        for(int i = 0; i<temp_tab.length ; i++){
            if(temp_tab[i] == "Nieaktywny"){
                temp_tab[i] = "Aktywny";
            }
        }
        temp = Arrays.toString(temp_tab);
        return temp;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String  item = ((String)getListAdapter().getItem(position));

        //Toast.makeText(getActivity().getApplicationContext(), "Wybrałeś "+ item     , Toast.LENGTH_LONG).show();
    }



    public void stopSMS(View view,String id){
        // Get Email Edit View Value

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();

        // When Email entered is Valid

        // Put Http parameter username with value of Email Edit View control
        params.put("SMS_ID", id);
        params.put("API", appConfig.getAPI_String());

        // Put Http parameter password with value of Password Edit Value control
        // Invoke RESTful Web Service with Http parameters
        invokeWSstopSMS(params);
    }

    public void startSMS(View view,String id){
        // Get Email Edit View Value

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();

        // When Email entered is Valid

        // Put Http parameter username with value of Email Edit View control
        params.put("SMS_ID", id);
        params.put("API", appConfig.getAPI_String());

        // Put Http parameter password with value of Password Edit Value control
        // Invoke RESTful Web Service with Http parameters
        invokeWSstartSMS(params);

        // When Email is invalid


    }

    public void getStatus(View view){
        // Get Email Edit View Value

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();

        // When Email entered is Valid

        // Put Http parameter username with value of Email Edit View control

        params.put("API", appConfig.getAPI_String());
        // Put Http parameter password with value of Password Edit Value control
        // Invoke RESTful Web Service with Http parameters
        invokeWS(params);

        // When Email is invalid


    }

    public void invokeWSstopSMS(RequestParams params) {
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post("http://auto.techdra.pl/API/StopSMS.php", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // Hide Progress Dialog
//                prgDialog.hide();

                try {
                    // JSON Object
                    String responseString = new String(response);
                    JSONObject obj = new JSONObject(responseString);
                    // When the JSON response has status boolean value assigned with true

                    if (obj.getString("STATUS").equals("TRUE")) {

                        Toast.makeText(getActivity().getApplicationContext(), "Zatrzymano wysyłkę", Toast.LENGTH_SHORT).show();
                    }
                    // Else display error message
                    else{
                        //errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getActivity().getApplicationContext(), "Ta wysyłka jest już zatrzymana", Toast.LENGTH_SHORT).show();
                    }
                    arrayList.clear();
                    adapter.notifyDataSetChanged();
                    getStatus(getView());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // Hide Progress Dialog
                //prgDialog.hide();
                // When Http response code is '404'


                    if (statusCode == 404) {
                        Toast.makeText(getActivity().getApplicationContext(), "Niepoprawne źródlo zapytania", Toast.LENGTH_SHORT).show();
                    }
                    // When Http response code is '500'
                    else if (statusCode == 500) {
                        Toast.makeText(getActivity().getApplicationContext(), "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                    }
                    // When Http response code other than 404, 500
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_SHORT).show();
                    }


            }
        });



    }

    public void invokeWSstartSMS(RequestParams params) {
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post(appConfig.getURL_StartSms(), params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // Hide Progress Dialog
//                prgDialog.hide();

                try {
                    // JSON Object
                    String responseString = new String(response);
                    JSONObject obj = new JSONObject(responseString);
                    // When the JSON response has status boolean value assigned with true


                    if (obj.getString("STATUS").equals("TRUE")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Wznowiono wysyłkę", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "Ta wysyłka jest aktywna", Toast.LENGTH_LONG).show();
                    }
                    arrayList.clear();
                    adapter.notifyDataSetChanged();
                    getStatus(getView());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // Hide Progress Dialog
                //prgDialog.hide();
                // When Http response code is '404'


                if (statusCode == 404) {
                    Toast.makeText(getActivity().getApplicationContext(), "Niepoprawne źródlo zapytania", Toast.LENGTH_SHORT).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getActivity().getApplicationContext(), "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void invokeWS(RequestParams params) {
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post(appConfig.getURL_UsrData(), params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // Hide Progress Dialog
//                prgDialog.hide();

                try {
                    // JSON Object

                    String responseString = new String(response);
                    JSONObject obj = new JSONObject(responseString);
                    // When the JSON response has status boolean value assigned with true




                    if (obj.getString("EMAIL") != "null") {

                        //textStatus.setText(obj.get("NAME")+ "\n" +obj.get("EMAIL").toString()+ "\n" +obj.get("SCHEDULED_SMS").toString());
                        JSONArray sms_obj = obj.getJSONArray("SCHEDULED_SMS");
                        //sms_obj.put(obj.get("SCHEDULED_SMS"));



                        for (int i = 0; i < sms_obj.length(); i++) {
                            JSONObject temp_obj = sms_obj.getJSONObject(i);
                            String temp = temp_obj.getString("ACTIVE");
                            if (temp_obj.getString("GROUP_NAME") != "null") {
                                if (temp == "1") {
                                    String temp2 =
                                            "Grupa: " + temp_obj.getString("GROUP_NAME") + "\n" +
                                                    "Następna wysyłka: " + temp_obj.getString("DATE_NEXT") + "\n" +
                                                    "Powtarzalność: " + temp_obj.getString("FREQUENCY") + "\n" +
                                                    " ID: "+ temp_obj.getString("ID")+" Status: Aktywny ";
                                    arrayList.add(temp2);
                                } else {
                                    String temp2 =
                                            "Grupa: " + temp_obj.getString("GROUP_NAME") + "\n" +
                                                    "Następna wysyłka: " + temp_obj.getString("DATE_NEXT") + "\n" +
                                                    "Powtarzalność: " + temp_obj.getString("FREQUENCY") + "\n" +
                                                    " ID: "+ temp_obj.getString("ID")+" Status: Nieaktywny ";
                                    arrayList.add(temp2);
                                }

                            } else {
                                if (temp == "1") {
                                    String temp2 =
                                            temp_obj.getString("CLIENT_NAME") + " " + temp_obj.getString("CLIENT_SURNAME") + "\n" +
                                                    "Następna wysyłka: " + temp_obj.getString("DATE_NEXT") + "\n" +
                                                    "Powtarzalność: " + temp_obj.getString("FREQUENCY") + "\n" +
                                                    " ID: "+ temp_obj.getString("ID")+" Status: Aktywny ";
                                    arrayList.add(temp2);
                                } else {
                                    String temp2 =
                                            temp_obj.getString("CLIENT_NAME") + " " + temp_obj.getString("CLIENT_SURNAME") + "\n" +
                                                    "Następna wysyłka: " + temp_obj.getString("DATE_NEXT") + "\n" +
                                                    "Powtarzalność: " + temp_obj.getString("FREQUENCY") + "\n" +
                                                    " ID: "+ temp_obj.getString("ID")+" Status: Nieaktywny ";
                                    arrayList.add(temp2);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                    // Else display error message
                    else if (obj.getString("EMAIL") == "null") {
                        errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getActivity().getApplicationContext(), "Niepoprawne dane logowania!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // Hide Progress Dialog
                //prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getActivity().getApplicationContext(), "Niepoprawne źródlo zapytania", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getActivity().getApplicationContext(), "Coś poszło nie tak", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
