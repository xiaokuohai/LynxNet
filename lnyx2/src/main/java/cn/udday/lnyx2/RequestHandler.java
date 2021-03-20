package cn.udday.lnyx2;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class RequestHandler implements IRequestHandler {
    @Override
    public Response handlerRequest(HttpCall call) throws IOException {

        HttpURLConnection connection = mangeConfig(call);

        if (!call.request.headers.isEmpty()) addHeaders(connection, call.request);

        if (call.request.parameterHandlers != null) writeContent(connection,call.request.parameterHandlers);

        if (!connection.getDoOutput()) connection.connect();

        //解析返回内容
        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 400) {
            byte[] bytes = new byte[1024];
            int len;
            InputStream ins = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = ins.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            Response response = new Response
                    .Builder()
                    .code(responseCode)
                    .message(connection.getResponseMessage())
                    .body(new ResponseBody(baos.toByteArray()))
                    .build();
            try {
                ins.close();
                connection.disconnect();
            } finally {
                if (ins != null) ins.close();
                if (connection != null) connection.disconnect();
            }
            return response;
        }
        throw new IOException(String.valueOf(connection.getResponseCode()));
    }

    private void writeContent(HttpURLConnection connection,Map<String,String> map) throws IOException {
        OutputStream ous = connection.getOutputStream();
        try {
            ous.write(transToString(map).getBytes("UTF-8"));
            ous.flush();
        } finally {
            if (ous != null) {
                ous.close();
            }
        }
    }
    private String transToString(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(key, "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(map.get(key), "UTF-8"));
        }
        return sb.toString();
    }
    private HttpURLConnection mangeConfig(HttpCall call) throws IOException {
        URL url = new URL(call.request.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(call.config.connTimeout);
        connection.setReadTimeout(call.config.readTimeout);

        connection.setDoInput(true);
        if (call.request.parameterHandlers != null && call.request.hasBody) {
            connection.setDoOutput(true);
        }
        return connection;
    }

    private void addHeaders(HttpURLConnection connection, Request request) {
        Set<String> keys = request.headers.keySet();
        for (String key : keys) {
            connection.addRequestProperty(key, request.headers.get(key));
        }
    }
}
