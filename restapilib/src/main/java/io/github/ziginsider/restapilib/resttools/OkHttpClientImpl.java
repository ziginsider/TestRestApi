package io.github.ziginsider.restapilib.resttools;

import android.os.Environment;
import okhttp3.*;
import timber.log.Timber;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

class OkHttpClientImpl {

    // 10 Mb
    private final static int CASH_SIZE = 10 * 1000 * 1000;
    private final static String CASH_NAME = "HttpCache";

    interface StateCallListener {
        void setElapsedTime(String elapsedTime);

        void setBodyByteCount(String bodyByteCount);
    }

    private static OkHttpClientImpl mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpClientImpl(StateCallListener stateCallListener) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File cacheFile = new File(path, CASH_NAME);
        cacheFile.mkdirs();

        Cache cache = new Cache(cacheFile, CASH_SIZE);

        Timber.plant(new Timber.DebugTree());

        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .eventListener(new PrintingEventListener(stateCallListener))
                .build();
    }

    static synchronized OkHttpClientImpl getInstance(StateCallListener stateCallListener) {
        if (mInstance == null) {
            mInstance = new OkHttpClientImpl(stateCallListener);
        }
        return mInstance;
    }

    OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    private static final class PrintingEventListener extends EventListener {
        long mCallStartNanos;

        StateCallListener mStateCallListener;

        PrintingEventListener(StateCallListener stateCallListener) {
            mStateCallListener = stateCallListener;
        }

        private void printEvent(String name) {
            long nowNanos = System.nanoTime();
            if (name.equals("callStart")) {
                mCallStartNanos = nowNanos;
            }
            long elapsedNanos = nowNanos - mCallStartNanos;
            Timber.i("%.3f %s%n", elapsedNanos / 1000000000d, name);
        }

        @Override
        public void callStart(Call call) {
            printEvent("callStart");
        }

        @Override
        public void dnsStart(Call call, String domainName) {
            printEvent("dnsStart");
        }

        @Override
        public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
            printEvent("dnsEnd");
        }

        @Override
        public void connectStart(
                Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
            printEvent("connectStart");
        }

        @Override
        public void secureConnectStart(Call call) {
            printEvent("secureConnectStart");
        }

        @Override
        public void secureConnectEnd(Call call, Handshake handshake) {
            printEvent("secureConnectEnd");
        }

        @Override
        public void connectEnd(
                Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
            printEvent("connectEnd");
        }

        @Override
        public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
                                  Protocol protocol, IOException ioe) {
            printEvent("connectFailed");
        }

        @Override
        public void connectionAcquired(Call call, Connection connection) {
            printEvent("connectionAcquired");
        }

        @Override
        public void connectionReleased(Call call, Connection connection) {
            printEvent("connectionReleased");
        }

        @Override
        public void requestHeadersStart(Call call) {
            printEvent("requestHeadersStart");
        }

        @Override
        public void requestHeadersEnd(Call call, Request request) {
            printEvent("requestHeadersEnd");
        }

        @Override
        public void requestBodyStart(Call call) {
            printEvent("requestBodyStart");
        }

        @Override
        public void requestBodyEnd(Call call, long byteCount) {
            printEvent("requestBodyEnd");
        }

        @Override
        public void responseHeadersStart(Call call) {
            printEvent("responseHeadersStart");
        }

        @Override
        public void responseHeadersEnd(Call call, Response response) {
            printEvent("responseHeadersEnd");
        }

        @Override
        public void responseBodyStart(Call call) {
            printEvent("responseBodyStart");
        }

        @Override
        public void responseBodyEnd(Call call, long byteCount) {
            printEvent("responseBodyEnd");
            mStateCallListener.setBodyByteCount(String.valueOf(byteCount));
        }

        @Override
        public void callEnd(Call call) {
            printEvent("callEnd");
            long elapsedTime = System.nanoTime() - mCallStartNanos;
            mStateCallListener.setElapsedTime(String.format("%.3f", elapsedTime / 1000000000d));
        }

        @Override
        public void callFailed(Call call, IOException ioe) {
            printEvent("callFailed");
        }
    }
}
