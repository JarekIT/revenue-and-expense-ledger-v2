package pl.jarekit.rael.logs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LogUtils {

    private LogUtils() { }

    private final static String TARGET_URL = "https://log-service-jarekit.herokuapp.com/log";

    public static void saveLogStatic(String message, Level level) {

        Runnable task = () -> {

            Log log = new Log(
                    Site.RAEL ,
                    LocalDateTime.now(ZoneOffset.UTC).plusHours(2) ,
                    message,
                    level);

            try {

                URL targetUrl = new URL(TARGET_URL);

                HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
                httpConnection.setDoOutput(true);
                httpConnection.setRequestMethod("POST");
                httpConnection.setRequestProperty("Content-Type", "application/json");

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                        .create();

                String input = gson.toJson(log);

                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(input.getBytes());
                outputStream.flush();

                if (httpConnection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + httpConnection.getResponseCode());
                }

                BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                        (httpConnection.getInputStream())));

                String output;
                System.out.println("Output from Server:");
                while ((output = responseBuffer.readLine()) != null) {
                    System.out.println(output);
                }

                httpConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

}
