package io.github.ziginsider.restapilib.resttools;

import android.os.Environment;
import android.support.annotation.NonNull;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

class OkHttpClientImpl {

    interface StateCallListener {
        void setElapsedTime(String elapsedTime);
        void setBodyByteCount(String bodyByteCount);
    }

    private static OkHttpClientImpl instance;
    private OkHttpClient okHttpClient;

    private OkHttpClientImpl(StateCallListener stateCallListener) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File cacheFile = new File(path,"HttpCache");
        cacheFile.mkdirs();

        // 10 Mb
        Cache cache = new Cache(cacheFile, 10 * 1000 * 1000);

        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor httpLoggingInterceptor = new
                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .eventListener(new PrintingEventListener(stateCallListener))
                .build();
    }

    public static synchronized OkHttpClientImpl getInstance(StateCallListener stateCallListener) {
        if (instance == null) {
            instance = new OkHttpClientImpl(stateCallListener);
        }
        return instance;
    }

    OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private static final class PrintingEventListener extends EventListener {
        long callStartNanos;

        StateCallListener stateCallListener;

        PrintingEventListener(StateCallListener stateCallListener) {
            this.stateCallListener = stateCallListener;
        }

        private void printEvent(String name) {
            long nowNanos = System.nanoTime();
            if (name.equals("callStart")) {
                callStartNanos = nowNanos;
            }
            long elapsedNanos = nowNanos - callStartNanos;
            Timber.i("%.3f %s%n", elapsedNanos / 1000000000d, name);
        }

        @Override public void callStart(Call call) {
            printEvent("callStart");
        }

        @Override public void dnsStart(Call call, String domainName) {
            printEvent("dnsStart");
        }

        @Override public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
            printEvent("dnsEnd");
        }

        @Override public void connectStart(
                Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
            printEvent("connectStart");
        }

        @Override public void secureConnectStart(Call call) {
            printEvent("secureConnectStart");
        }

        @Override public void secureConnectEnd(Call call, Handshake handshake) {
            printEvent("secureConnectEnd");
        }

        @Override public void connectEnd(
                Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
            printEvent("connectEnd");
        }

        @Override public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
                                            Protocol protocol, IOException ioe) {
            printEvent("connectFailed");
        }

        @Override public void connectionAcquired(Call call, Connection connection) {
            printEvent("connectionAcquired");
        }

        @Override public void connectionReleased(Call call, Connection connection) {
            printEvent("connectionReleased");
        }

        @Override public void requestHeadersStart(Call call) {
            printEvent("requestHeadersStart");
        }

        @Override public void requestHeadersEnd(Call call, Request request) {
            printEvent("requestHeadersEnd");
        }

        @Override public void requestBodyStart(Call call) {
            printEvent("requestBodyStart");
        }

        @Override public void requestBodyEnd(Call call, long byteCount) {
            printEvent("requestBodyEnd");
        }

        @Override public void responseHeadersStart(Call call) {
            printEvent("responseHeadersStart");
        }

        @Override public void responseHeadersEnd(Call call, Response response) {
            printEvent("responseHeadersEnd");
        }

        @Override public void responseBodyStart(Call call) {
            printEvent("responseBodyStart");
        }

        @Override public void responseBodyEnd(Call call, long byteCount) {
            printEvent("responseBodyEnd");
            stateCallListener.setBodyByteCount(String.valueOf(byteCount));

        }

        @Override public void callEnd(Call call) {
            printEvent("callEnd");
            long elapsedTime = System.nanoTime() - callStartNanos;
            stateCallListener.setElapsedTime(String.format("%.3f", elapsedTime / 1000000000d));
        }

        @Override public void callFailed(Call call, IOException ioe) {
            printEvent("callFailed");
        }
    }
}
