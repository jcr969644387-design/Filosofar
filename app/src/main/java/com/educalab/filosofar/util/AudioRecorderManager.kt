package com.educalab.filosofar.util

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.IOException

/**
 * Gestiona grabación y reproducción de reflexiones de voz usando las APIs
 * reales de Android (MediaRecorder/MediaPlayer). El audio se guarda en
 * almacenamiento PRIVADO de la app (filesDir/reflexiones), nunca se sube a
 * ningún servidor y nunca se transcribe. Límite duro de 45 segundos.
 */
class AudioRecorderManager(private val context: Context) {

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var currentFile: File? = null
    private var startedAtMs: Long = 0L

    val reflectionsDir: File
        get() = File(context.filesDir, "reflexiones").apply { if (!exists()) mkdirs() }

    /** Inicia la grabación. Devuelve el archivo destino o null si falla. */
    fun startRecording(): File? {
        stopPlaybackIfAny()
        val file = File(reflectionsDir, "reflexion_${System.currentTimeMillis()}.m4a")
        return try {
            @Suppress("DEPRECATION")
            val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }
            mediaRecorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(96_000)
                setAudioSamplingRate(44_100)
                setMaxDuration(MAX_DURATION_MS.toInt())
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
            recorder = mediaRecorder
            currentFile = file
            startedAtMs = System.currentTimeMillis()
            file
        } catch (e: IOException) {
            releaseRecorder()
            null
        } catch (e: RuntimeException) {
            releaseRecorder()
            null
        }
    }

    /** Detiene la grabación. Devuelve el archivo y la duración real en ms, o null si no había grabación activa. */
    fun stopRecording(): Pair<File, Long>? {
        val file = currentFile ?: return null
        return try {
            recorder?.stop()
            val duration = (System.currentTimeMillis() - startedAtMs).coerceAtMost(MAX_DURATION_MS)
            file to duration
        } catch (e: RuntimeException) {
            file.delete()
            null
        } finally {
            releaseRecorder()
        }
    }

    fun cancelRecording() {
        try {
            recorder?.stop()
        } catch (_: RuntimeException) {
            // Grabación demasiado corta o ya detenida; se ignora al cancelar.
        }
        releaseRecorder()
        currentFile?.delete()
        currentFile = null
    }

    fun play(file: File, onCompletion: () -> Unit) {
        stopPlaybackIfAny()
        player = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            setOnCompletionListener { onCompletion() }
            prepare()
            start()
        }
    }

    fun stopPlaybackIfAny() {
        player?.apply {
            if (isPlaying) stop()
            release()
        }
        player = null
    }

    fun deleteRecording(path: String) {
        File(path).takeIf { it.exists() }?.delete()
    }

    private fun releaseRecorder() {
        recorder?.apply {
            try { reset() } catch (_: RuntimeException) { /* no-op */ }
            release()
        }
        recorder = null
    }

    companion object {
        const val MAX_DURATION_MS = 45_000L
    }
}
