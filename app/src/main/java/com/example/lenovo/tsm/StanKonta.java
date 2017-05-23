package com.example.lenovo.tsm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StanKonta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StanKonta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppConfig appConfig = AppConfig.getInstance();
    ProgressDialog prgDialog;
    TextView errorMsg;

    public StanKonta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StanKonta newInstance(String param1, String param2) {
        StanKonta fragment = new StanKonta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Stan konta");
        getStan(this.getView());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stan, container, false);
    }

    public void getStan(View view){
        // Get Email Edit View Value

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();

        // When Email entered is Valid

        // Put Http parameter username with value of Email Edit View control
        params.put("API", appConfig.getAPI_String());
        // Put Http parameter password with value of Password Edit Value control
        // Invoke RESTful Web Service with Http parameters
        invokeWS(params);




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
               // prgDialog.hide();

                try {
                    // JSON Object

                    String responseString = new String(response);
                    JSONObject obj = new JSONObject(responseString);
                    // When the JSON response has status boolean value assigned with true

                    if(obj.getString("EMAIL")!= "null"){

                        TextView textStan1 = (TextView) getView().findViewById(R.id.textStanKontaNazwa);
                        textStan1.setText("Witaj, " + obj.get("NAME").toString() );

                        TextView textStan = (TextView) getView().findViewById(R.id.textStanKonta);
                        textStan.setText(
                                "Stan Konta: " + obj.get("POINTS").toString()+ "\n" +
                                "Twój e-mail: " + obj.get("EMAIL").toString()+ "\n" +
                                "Koszt SMSa: " + obj.get("SMS_BASIC_PRICE").toString()+ "\n"+
                                "Koszt szybkiego SMSa: "+ obj.get("SMS_FAST_PRICE").toString()+ "\n"+
                                "Koszt Emaila: " + obj.get("EMAIL_PRICE").toString()+ "\n"
                                );

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
                // prgDialog.hide();
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
