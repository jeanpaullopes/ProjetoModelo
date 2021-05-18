package br.edu.uniritter.mobile.aplicacaomodelo.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import br.edu.uniritter.mobile.aplicacaomodelo.services.AppIntentService;


// importante ajustar o intent filter no manifesto

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("MyReceiver", intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Toast.makeText(context,"Boot Completo",Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            Toast.makeText(context,"Modo Avião",Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Toast.makeText(context,"Ligou a tela",Toast.LENGTH_LONG).show();
            Intent intent1 = new Intent(context, AppIntentService.class);
            intent1.setAction("START_SERVIÇO");
            AppIntentService.enqueueWork(context,intent1);
            //context.startService(intent1);

        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Intent intent1 = new Intent(context, AppIntentService.class);
            intent1.setAction("STOP_SERVIÇO");
            AppIntentService.enqueueWork(context,intent1);

        }
    }
}