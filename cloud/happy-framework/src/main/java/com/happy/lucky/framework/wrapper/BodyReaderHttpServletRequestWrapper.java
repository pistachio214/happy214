package com.happy.lucky.framework.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 自定义高级类
 *
 * @author songyangpeng
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String sessionStream = getBodyString(request);
        body = sessionStream.getBytes(StandardCharsets.UTF_8);
    }

    private String getBodyString(ServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream ins = request.getInputStream();

        try (BufferedReader isr = new BufferedReader(new InputStreamReader(ins, StandardCharsets.UTF_8));) {
            String line = "";
            while ((line = isr.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        }
        return sb.toString();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}
