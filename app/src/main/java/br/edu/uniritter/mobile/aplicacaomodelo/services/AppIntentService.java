package br.edu.uniritter.mobile.aplicacaomodelo.services;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import java.util.Calendar;


// Declarar o serviço no Manifesto

public class AppIntentService extends JobIntentService {

    private static ThreadAppService minhaThread;


    /**
            * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, AppIntentService.class, JOB_ID, work);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d("AppIntentService", intent.getAction());

        if(intent.getAction().equals("START_SERVIÇO")) {
            Log.d("AppIntentService", "start serviço ");
            minhaThread = new ThreadAppService(this.getApplicationContext());
            minhaThread.start();
        }
        if(intent.getAction().equals("STOP_SERVIÇO")) {
            try{
                Log.d("AppIntentService", "stop serviço ");
                minhaThread.interrupt();
            } catch (NullPointerException ex) {}
        }

    }
    private class ThreadAppService extends Thread {
        private int id = 0;
        private boolean running;
        private Context context;

        public ThreadAppService(Context context) {
            this.context = context;
            this.running = false;
        }
        @Override
        public synchronized void start() {
            this.running = true;
            super.start();
        }

        @Override
        public void run() {
            super.run();
            while (!this.isInterrupted() && this.running)  {
                try {
                    sleep(60000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    this.running = false;
                }

                Log.d("MyService", "aqui "+ Calendar.getInstance().getTime());
                NotificationService.criaNotificacao(++id,"hora atual:\n"+
                        Calendar.getInstance().getTime().toString());
            }
        }

        @Override
        public void interrupt() {
            super.interrupt();
            this.running = false;
        }
    }
}
