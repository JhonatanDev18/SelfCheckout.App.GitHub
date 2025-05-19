package com.crystal.selfcheckoutapp.Modelo.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private LogFile() {}

    // Método principal para registrar un objeto genérico como JSON
    public static <T> void adjuntarLog(String titulo, T objeto) {
        if(SPM.getBoolean(Constantes.IS_ACTIVE_LOG)){
            if (!verificarPermisos()) return;

            // Convertir el objeto a formato JSON
            String json = convertirObjetoAJson(objeto);

            // Generar el mensaje a registrar
            String logMessage = generarMensaje(json);

            // Obtener archivo de log y escribir
            File logFile = obtenerArchivoLog();
            escribirEnArchivo(logFile, titulo, logMessage);
        }
    }

    // Método para convertir un objeto genérico a JSON
    private static <T> String convertirObjetoAJson(T objeto) {
        try {
            return gson.toJson(objeto);
        } catch (Exception e) {
            android.util.Log.e("LogFile", "Error al convertir objeto a JSON: " + e.getMessage());
            return "Error al convertir objeto a JSON: " + objeto.toString();
        }
    }


    // Método principal para adjuntar logs
    public static void adjuntarLog(String titulo, String text) {
        if(SPM.getBoolean(Constantes.IS_ACTIVE_LOG)){
            if (!verificarPermisos()) return;

            String logMessage = generarMensaje(text);
            File logFile = obtenerArchivoLog();
            escribirEnArchivo(logFile, titulo, logMessage);
        }
    }

    public static void adjuntarLogTitulo(String titulo) {
        if(SPM.getBoolean(Constantes.IS_ACTIVE_LOG)){
            if (!verificarPermisos()) return;

            File logFile = obtenerArchivoLog();
            escribirEnArchivoTitulo(logFile, titulo);
        }
    }

    // Método sobrecargado para logs con excepciones
    public static void adjuntarLog(String lugarError, Throwable exception) {
        if(SPM.getBoolean(Constantes.IS_ACTIVE_LOG)){
            if (!verificarPermisos()) return;

            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement element : exception.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }

            String logMessage = generarMensaje(exception.getMessage() + "\n" + stackTrace);
            File logFile = obtenerArchivoLog();
            escribirEnArchivo(logFile, lugarError, logMessage);
        }
    }

    // Verifica permisos para escritura en almacenamiento
    private static boolean verificarPermisos() {
        Context context = MyApp.getContext();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constantes.PERMISO_WRITE_EXTERNAL_STORAGE
            );
            return false;
        }
        return true;
    }

    // Método para generar el mensaje con título y contenido
    @SuppressLint("SimpleDateFormat")
    private static String generarMensaje(String contenido) {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date todayDate = new Date();
        String fechahora = currentDate.format(todayDate);

        return fechahora + ": " + contenido;
    }

    // Obtiene o crea el archivo de log
    private static File obtenerArchivoLog() {
        Context context = MyApp.getContext();
        File directorioLogs = new File(context.getExternalFilesDir(null), Constantes.NOMBRE_CARPETA);
        if (!directorioLogs.exists() && !directorioLogs.mkdirs()) {
            android.util.Log.e("LogFile", "No se pudo crear el directorio para los logs.");
        }

        @SuppressLint("SimpleDateFormat")
        String nombreArchivo = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "log.txt";
        return new File(directorioLogs, nombreArchivo);
    }

    // Escribe el mensaje en el archivo de log
    private static void escribirEnArchivo(File archivo, String titulo, String mensaje) {
        try (BufferedWriter buf = new BufferedWriter(new FileWriter(archivo, true))) {
            buf.append(titulo.toUpperCase());
            buf.newLine();
            buf.append(mensaje);
            buf.newLine();
            buf.append("======================================================================================");
            buf.newLine();
        } catch (IOException e) {
            android.util.Log.e("LogFile", "No se pudo escribir en el log: " + e.getMessage());
        }
    }

    private static void escribirEnArchivoTitulo(File archivo, String titulo) {
        try (BufferedWriter buf = new BufferedWriter(new FileWriter (archivo, true))) {
            buf.append("=========================================================")
                    .append(titulo.toUpperCase())
                    .append("=========================================================");
            buf.newLine();
        } catch (IOException e) {
            android.util.Log.e("LogFile", "No se pudo escribir en el log: " + e.getMessage());
        }
    }
}