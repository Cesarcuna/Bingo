package heme.mx.com.arrow.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import heme.mx.com.arrow.Avtivity.Mapas;
import heme.mx.com.arrow.DataBase.Controler.Sesion;
import heme.mx.com.arrow.DataBase.Tablas.UsuarioEO;
import heme.mx.com.arrow.LoginActivity;
import heme.mx.com.arrow.R;
import heme.mx.com.arrow.Variables.ArrowC;
import heme.mx.com.arrow.sync.PostMetod;


public class tipos extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatSpinner spinner;
    private int seleccion=0;
    private Sesion sesion;
    private PostMetod postMetod;
    private LoginActivity activity;

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public void setActivity(LoginActivity activity) {
        this.activity = activity;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tipos() {
        // Required empty public constructor
    }

    public static tipos newInstance(Sesion sesion,LoginActivity activity, String param2) {
        tipos fragment = new tipos();
        Bundle args = new Bundle();
        fragment.setSesion(sesion);
        fragment.setActivity(activity);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_tipos, container, false);
        // Title text
        getDialog().setTitle(Html.fromHtml("<font color='#FFFFFF'>Completar</font>"));
        getDialog().getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
        spinner =(AppCompatSpinner)view.findViewById(R.id.spinner);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabEstados);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new APs().execute();
            }
        });
        SwitchCompat switchCompat =(SwitchCompat)view.findViewById(R.id.switchButton);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ArrowC.tipo=b?"0x001":"0x002";
                spinner.setVisibility(b?View.VISIBLE:View.GONE);
            }
        });
        // Title divider
        final View titleDivider = getDialog().findViewById(getResources().getIdentifier("titleDivider", "id", "android"));
        if (titleDivider != null) titleDivider.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        llenarComoBox(sesion.rutasLista(),spinner);
        return view;
    }

    private void llenarComoBox(List<String> mSecciones, AppCompatSpinner spSecciones) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice, mSecciones);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spSecciones.setAdapter(spinnerArrayAdapter);spSecciones.setSelection(0);
        spSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (seleccion == position) {
                    return; //do nothing
                } else {
                    ArrowC.ruta=ArrowC.respuestas.get(position);
                }
                seleccion = position;
                //write your code here
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private String getMD5(String s) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            //System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));
            return String.valueOf(new BigInteger(1, m.digest()).toString(16));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public class APs extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            postMetod= new PostMetod(sesion);
        }

        @Override
        protected String doInBackground(Void... voids) {
            if(getInternetConnection()){
                String g=postMetod.JsonReader(ArrowC.URL_REGISTRO,
                        new String[]{"key","nick","password","nombre","ruta","tipo"},
                        new String[]{getMD5(ArrowC.ApiKey),ArrowC.nick,ArrowC.password,ArrowC.nombre,ArrowC.ruta,ArrowC.tipo});
                sesion.SVE(new UsuarioEO((sesion.getLastAutoId(new UsuarioEO())),ArrowC.nombre,ArrowC.password,ArrowC.nick));
                return g;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            activity.IrInicio();
            dismiss();
        }
    }

    private boolean getInternetConnection(){
        final ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if( wifi.isAvailable() && wifi.getDetailedState() == NetworkInfo.DetailedState.CONNECTED){
            /*Wifi*/
            return true;
        } else if( mobile.isAvailable() && mobile.getDetailedState() == NetworkInfo.DetailedState.CONNECTED ){
            /*Mobile 3G*/
            return true;
        } else {
            return false;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
