package com.exam;

import com.exam.util.Util;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public Rq(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;

        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
    }

    public String getParam(String paramName, String defaultValue) {
        String value = req.getParameter(paramName);

        if (value == null || value.trim().length() == 0) {
            return defaultValue;
        }
        return value;
    }

    public int getIntParam(String paramName, int defaultValue) {
        String value = req.getParameter(paramName);

        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public long getLongParam(String paramName, long defaultValue) {
        String value = req.getParameter(paramName);

        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public void writeln(String body) {
        write(body + "\n");
    }

    public void write(String body) {
        try {
            resp.getWriter().append(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAttr(String name, Object value) {
        req.setAttribute(name, value);
    }

    public void view(String path) {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/jsp/" + path + ".jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        return req.getRequestURI();
    }

    public String getActionPath() {
        String[] bits = req.getRequestURI().split("/");

        return "/%s/%s/%s".formatted(bits[1], bits[2], bits[3]);
    }

    public String getRouteMethod() {
        String method = getParam("_method", "");
        if (method.length() > 0 ) {
            return method.toUpperCase();
        }

        return req.getMethod();
    }

    public long getLongPathValueByIndex(int index, long defaultValue) {
        String value = getPathValueByIndex(index, null);

        if (value == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getPathValueByIndex(int index, String defaultValue) {
        String[] bits = req.getRequestURI().split("/");

        try {
            return bits[4 + index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultValue;
        }
    }

    public void historyBack(String msg) {
        if (msg != null && msg.trim().length() > 0) {
            writeln("""
                    <script>
                    alert("%s");
                    </script>
                    """.formatted(msg));
        }

        writeln("""
                <script>
                history.back();
                </script>
                """);
    }

    public void replace(String uri, String msg) {

        if (msg != null && msg.trim().length() > 0) {
            writeln("""
                    <script>
                    alert("%s");
                    </script>
                    """.formatted(msg));
        }

        writeln("""
                <script>
                location.replace("%s");
                </script>
                """.formatted(uri));
    }

    public void json(Object resultData) {
        resp.setContentType("application/json; charset=utf-8");
        String jsonStr = Util.json.toJsonStr(resultData, "");
        writeln(jsonStr);
    }
    public void json(Object data, String resultCode, String msg) {
        json(new ResultData(resultCode, msg, data));
    }

    public void successJson(Object data) {
        json(data, "S-1", "성공");
    }

    public void failJson(Object data) {
        json(data, "F-1", "실패");
    }
}
