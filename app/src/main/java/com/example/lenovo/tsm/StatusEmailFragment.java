package com.example.lenovo.tsm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatusEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusEmailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    AppConfig appConfig = AppConfig.getInstance();
    ProgressDialog prgDialog;
    TextView errorMsg;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    public StatusEmailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StatusEmailFragment newInstance(String param1, String param2) {
        StatusEmailFragment fragment = new StatusEmailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Status wysyłek emaili");
        getStatus(this.getView());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_email, container, false);
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

    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post(appConfig.getURL_UsrData(),params ,new AsyncHttpResponseHandler() {
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

                    if(obj.getString("EMAIL")!= "null"){

                        //textStatus.setText(obj.get("NAME")+ "\n" +obj.get("EMAIL").toString()+ "\n" +obj.get("SCHEDULED_SMS").toString());
                        JSONArray sms_obj = obj.getJSONArray("SCHEDULED_EMAIL");
                        //sms_obj.put(obj.get("SCHEDULED_SMS"));

                        list = (ListView) getActivity().findViewById(R.id.EmailStatusList);
                        arrayList = new ArrayList<String>();

                        adapter = new ArrayAdapter<String>(getContext().getApplicationContext(), R.layout.listview_layout, arrayList);
                        list.setAdapter(adapter);

                        for(int i=0 ; i< sms_obj.length(); i++) {
                            JSONObject temp_obj = sms_obj.getJSONObject(i);
                            String temp = temp_obj.getString("ACTIVE");
                            if(temp_obj.getString("GROUP_NAME")!="null") {
                                if (temp == "1") {
                                    String temp2 =
                                            "Grupa: "+ temp_obj.getString("GROUP_NAME") + "\n" +
                                                    "Następna wysyłka: "+ temp_obj.getString("DATE_NEXT") + "\n" +
                                                    "Powtarzalność: "  +  temp_obj.getString("FREQUENCY") + "\n" +
                                                    "Status: Aktywny " ;
                                    arrayList.add(temp2);
                                }
                                else{
                                    String temp2 =
                                            "Grupa: "+ temp_obj.getString("GROUP_NAME") + "\n" +
                                                    "Następna wysyłka: "+ temp_obj.getString("DATE_NEXT") + "\n" +
                                                    "Powtarzalność: "  +  temp_obj.getString("FREQUENCY") + "\n" +
                                                    "Status: Nieaktywny " ;
                                    arrayList.add(temp2);
                                }

                            }
                            else {
                                if (temp == "1") {
                                    String temp2 =
                                            temp_obj.getString("CLIENT_NAME") + " " + temp_obj.getString("CLIENT_SURNAME")+ "\n" +
                                                    "Następna wysyłka: "+ temp_obj.getString("DATE_NEXT") +  "\n" +
                                                    "Powtarzalność: "  +  temp_obj.getString("FREQUENCY") + "\n" +
                                                    "Status: Aktywny " ;
                                    arrayList.add(temp2);
                                }
                                else {
                                    String temp2 =
                                            temp_obj.getString("CLIENT_NAME") + " " + temp_obj.getString("CLIENT_SURNAME")+ "\n" +
                                                    "Następna wysyłka: " + temp_obj.getString("DATE_NEXT")+ "\n" +
                                                    "Powtarzalność: " + temp_obj.getString("FREQUENCY")+ "\n" +
                                                    "Status: Nieaktywny ";
                                    arrayList.add(temp2);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                    // Else display error message
                    else if(obj.getString("EMAIL")== "null"){
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
                if(statusCode == 404){
                    Toast.makeText(getActivity().getApplicationContext(), "Niepoprawne źródlo zapytania", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getActivity().getApplicationContext(), "Coś poszło nie tak", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



