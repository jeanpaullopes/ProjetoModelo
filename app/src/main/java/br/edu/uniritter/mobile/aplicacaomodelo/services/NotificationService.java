package br.edu.uniritter.mobile.aplicacaomodelo.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import br.edu.uniritter.mobile.aplicacaomodelo.R;


public class NotificationService {

        private static Context context;
        private static NotificationChannel channel;
        public static final String CHANNEL_ID = "CHANNEL_APP_MODELO";
        public static final String CHANNEL_NOME = "Nossa App Modelo";
        public static final String CHANNEL_DESCR = "Canal de notificações de nossa app Modelo";

        public static void criarCanalNotificacao(Context contexto) {
            context = contexto;
            // cria o NotificationChannel, somente para API 26+
            // a classe NotificationChannel nçao existe em API´s anteriores
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = CHANNEL_NOME;
                String description = CHANNEL_DESCR;
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.createNotificationChannel(channel);
            }
        }

        public static void criaNotificacao(int id, String texto) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_coronavirus_24)
                    .setContentTitle("Nossa notificação")
                    .setContentText(texto+"...")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(texto+"o texto longo, com muito mais de uma linha\nsegunda linha..."+texto))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(id, builder.build());

        }






}
