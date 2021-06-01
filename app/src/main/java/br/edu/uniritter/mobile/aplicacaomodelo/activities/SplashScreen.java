package br.edu.uniritter.mobile.aplicacaomodelo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Layout;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


import br.edu.uniritter.mobile.aplicacaomodelo.R;
import br.edu.uniritter.mobile.aplicacaomodelo.receivers.MyReceiver;
import br.edu.uniritter.mobile.aplicacaomodelo.services.FirebaseServices;
import br.edu.uniritter.mobile.aplicacaomodelo.services.NotificationService;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity implements SensorEventListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private static final int RC_SIGN_IN = 123;



    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private View mControlsView;
    private boolean mVisible;
    private int background = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());


        setContentView(R.layout.activity_splash_screen);

        mVisible = true;

        // registrando BoradcastReceiver e seus IntentFilters
        BroadcastReceiver receiver = new MyReceiver();
        IntentFilter itf = new IntentFilter(Intent.ACTION_SCREEN_ON);
        itf.addAction(Intent.ACTION_SCREEN_OFF);
        //itf.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        itf.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(receiver,itf);
        //cria canal de notificação
        NotificationService.criarCanalNotificacao(this);






    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensor1 = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor1, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.unregisterListener(this);
    }

    public void onClick(View v) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    //new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
                    //new AuthUI.IdpConfig.FacebookBuilder().build(),
                    //new AuthUI.IdpConfig.TwitterBuilder().build()
            );

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        } else {
            Intent intent = new Intent(this, PrincipalActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }
    }

    public void clickNotificacao(View v) {
        NotificationService.criaNotificacao(999, "notificação vinda da splash (botão)");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseServices.setFirebaseUser( FirebaseServices.getFirebaseAuthInstance().getCurrentUser() );
//                Toast.makeText(this, "Deu certo, "+FirebaseServices.getFirebaseUser().getDisplayName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, PrincipalActivity.class);
                startActivity(intent);

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d("Sensor",sensorEvent.sensor.getName());

        //Log.d("Sensor",sensorEvent.values[0]+"");
        //Log.d("Sensor",sensorEvent.values[0]+", "+sensorEvent.values[1]+", "+sensorEvent.values[2]);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (sensorEvent.values[0] > 1 || sensorEvent.values[0] < -1) {
                Log.d("Sensor", sensorEvent.values[0] + ", " + sensorEvent.values[1] + ", " + sensorEvent.values[2]);
                trocaGradiante(sensorEvent.values[0]);
            }
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                Log.d("Sensor", sensorEvent.values[0] + ", " );

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void trocaGradiante(float direcao) {
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layoutSplashScreen);
        if (direcao > 0) {
            layout.setBackgroundResource(R.drawable.gradiante2);
            background = 1;
        } else {
            layout.setBackgroundResource(R.drawable.gradiante1);
            background = 0;
        }
    }
}