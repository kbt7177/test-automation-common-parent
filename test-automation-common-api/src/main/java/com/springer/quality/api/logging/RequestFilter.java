package com.springer.quality.api.logging;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.internal.NoParameterValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ProxySpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
public class RequestFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        String url = requestSpec.getURI();
        String requestLog = printRequest(requestSpec, requestSpec.getMethod(), url);
        log.info("@Request : {}{}", System.lineSeparator(), requestLog);
        Response response = ctx.next(requestSpec, responseSpec);
        String responseLog = printResponse(response);
        log.info("@Response: {}{}", System.lineSeparator(), responseLog);

        return response;
    }

    public static String printRequest(FilterableRequestSpecification requestSpec, String returnMethod, String completeRequestUri) {
        StringBuilder builder = new StringBuilder();
        addSingle(builder, "Request method:", returnMethod);
        addSingle(builder, "Request URI:", completeRequestUri);
        addProxy(requestSpec, builder);
        addMapDetails(builder, "Request params:", requestSpec.getRequestParams());
        addMapDetails(builder, "Query params:", requestSpec.getQueryParams());
        addMapDetails(builder, "Form params:", requestSpec.getFormParams());
        addMapDetails(builder, "Path params:", requestSpec.getPathParams());
        addHeaders(requestSpec.getHeaders(), builder);
        addCookies(requestSpec.getCookies(), builder);
        addMultiParts(requestSpec, builder);
        addBody(requestSpec, builder);
        return StringUtils.removeEnd(builder.toString(), System.lineSeparator());
    }

    public static String printResponse(Response response) {
        StringBuilder builder = new StringBuilder();
        addSingle(builder, "Status line:", response.getStatusLine());
        addSingle(builder, "Time: :", response.getTime() + "ms");
        addHeaders(response.getHeaders(), builder);
        addMapDetails(builder, "Cookies:", response.getCookies());
        addSingle(builder, "Body:", System.lineSeparator() + response.asPrettyString());
        return StringUtils.removeEnd(builder.toString(), System.lineSeparator());
    }

    private static void addSingle(StringBuilder builder, String key, String value) {
        builder.append(String.format("%-20s %s", key, value)).append(System.lineSeparator());
    }

    private static void addProxy(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        ProxySpecification proxySpec = requestSpec.getProxySpecification();
        addSingle(builder, "Proxy:", proxySpec == null ? "<none>" : proxySpec.toString());
    }

    private static void addBody(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        String value = requestSpec.getBody() == null ? "<none>" : new Prettifier().getPrettifiedBodyIfPossible(requestSpec);
        addSingle(builder, "Body:", value);
    }

    private static void addCookies(Cookies cookies, StringBuilder builder) {
        if (!cookies.exist()) {
            addSingle(builder, "Cookies:", "<none>");
            return;
        }
        for (int i = 0; i < cookies.size(); i++) {
            if (i == 0) {
                addSingle(builder, "Cookies:", cookies.asList().get(i).toString());
                continue;
            }
            addSingle(builder, "", cookies.asList().get(i).toString());
        }
    }

    private static void addHeaders(Headers headers, StringBuilder builder) {
        if (!headers.exist()) {
            addSingle(builder, "Headers:", "<none>");
            return;
        }
        for (int i = 0; i < headers.size(); i++) {
            if (i == 0) {
                addSingle(builder, "Headers:", headers.asList().get(i).toString());
                continue;
            }
            addSingle(builder, "", headers.asList().get(i).toString());
        }
    }

    private static void addMultiParts(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        List<MultiPartSpecification> multiParts = requestSpec.getMultiPartParams();
        if (multiParts.isEmpty()) {
            addSingle(builder, "MultiParts:", "<none>");
            return;
        }
        addSingle(builder, "MultiParts:", "");
        for (int i = 0; i < multiParts.size(); ++i) {
            MultiPartSpecification multiPart = multiParts.get(i);

            addSingle(builder, "Content-Disposition:", requestSpec.getContentType().replace("multipart/", "") + "; name = "
                    + multiPart.getControlName()
                    + (multiPart.hasFileName() ? "; filename = " + multiPart.getFileName() : ""));
            addSingle(builder, " Content-Type:", multiPart.getMimeType());

            Map<String, String> headers = multiPart.getHeaders();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, ?> headerEntry : headers.entrySet()) {
                    addSingle(builder, headerEntry.getKey(), ": " + headerEntry.getValue());
                }
            }

            builder.append(System.lineSeparator());
            if (multiPart.getContent() instanceof InputStream) {
                addSingle(builder, "", "<inputStream>");
            } else {
                Parser parser = Parser.fromContentType(multiPart.getMimeType());
                String prettified = new Prettifier().prettify(multiPart.getContent().toString(), parser);
                String prettifiedIndented = StringUtils.replace(prettified, System.lineSeparator(), System.lineSeparator() + "\t" + "\t" + "\t" + "\t");
                addSingle(builder, "", prettifiedIndented);
            }
        }
        builder.append(System.lineSeparator());
    }

    private static void addMapDetails(StringBuilder builder, String title, Map<String, ?> map) {
        if (map.isEmpty()) {
            addSingle(builder, title, "<none>");
            return;
        }

        int i = 0;
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (!(entry.getValue() instanceof NoParameterValue)) {
                if (i++ == 0) {
                    addSingle(builder, title, entry.getKey() + "=" + entry.getValue());
                    continue;
                }
                addSingle(builder, "", entry.getKey() + "=" + entry.getValue());
            }
        }
    }

}
