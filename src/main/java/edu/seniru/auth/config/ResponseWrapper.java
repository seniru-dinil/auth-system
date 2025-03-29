package edu.seniru.auth.config;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream outputStream;
    private final PrintWriter writer;

    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        outputStream = new ByteArrayOutputStream();
        writer = new PrintWriter(outputStream);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {}
        };
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    // Get the response body as a String
    public String getResponseBody() {
        return outputStream.toString();
    }

    // Flush the output stream to the original response
    public void flushResponse() throws IOException {
        writer.flush();
        outputStream.writeTo(getResponse().getOutputStream());
    }

    // Reset the response writer
    public void resetResponse() {
        writer.flush();
        outputStream.reset();
    }
}
