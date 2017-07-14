package heme.mx.com.arrow;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import heme.mx.com.arrow.Avtivity.Mapas;
import heme.mx.com.arrow.DataBase.Controler.Sesion;
import heme.mx.com.arrow.DataBase.Tablas.RutasEO;
import heme.mx.com.arrow.Fragments.tipos;
import heme.mx.com.arrow.Variables.ArrowC;
import heme.mx.com.arrow.sync.PostMetod;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Sesion sesion;
    private PostMetod postMetod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        sesion= new Sesion(this);
        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if(sesion.getUsers(mEmailView.getText().toString(), mPasswordView.getText().toString()))IrInicio();
                    else Snackbar.make(textView,"No estas registrado",Snackbar.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sesion.getUsers(mEmailView.getText().toString(), mPasswordView.getText().toString()))IrInicio();
                else Snackbar.make(view,"No estas registrado",Snackbar.LENGTH_LONG).show();
            }
        });

        Button mRegister =(Button) findViewById(R.id.register);
        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mEmailView.getText().toString().equals("") && !mPasswordView.getText().toString().equals("")){
                    ArrowC.nick=mEmailView.getText().toString();
                    ArrowC.password=mPasswordView.getText().toString();
                    ArrowC.nombre=telephonyManager.getDeviceId();
                    FragmentManager fm = getSupportFragmentManager();
                    DialogFragment editNameDialogFragment = tipos.newInstance(sesion,LoginActivity.this,"");
                    editNameDialogFragment.show(fm, "mensages");
                }
                else Snackbar.make(view,"Campos vacios necesarios",Snackbar.LENGTH_LONG).show();
            }
        });
        //sesion.getPreguntas();
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        new APs().execute();
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
                return postMetod.JsonReader(ArrowC.URL_RUTAS, new String[]{"key"},new String[]{getMD5(ArrowC.ApiKey)});
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            try {
                parseJSon(aVoid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJSon(String data) throws Exception {
        if(data==null)return;
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.optJSONArray("servicio");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.optJSONObject(i);
            Log.e("==>",jsonRoute.toString());
            postMetod.saveAll(new RutasEO().JSONValues(jsonRoute),0);
        }
    }

    private boolean getInternetConnection(){
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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


    public void IrInicio(){
        //AmbientalC.user= UsuariosDO.ADMINISTRADOR;
        Intent i = new Intent(this, Mapas.class);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        //saveAll(0,new Component[]{nuevoUsuario(0, "THMOG", "34RYUD","LUIS FERNANDO HERNANDEZ MENDEZ","",""), nuevoSeccion(0, "ZXY001"), nuevoCasilla(0, "ABC001")});
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
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }
}

