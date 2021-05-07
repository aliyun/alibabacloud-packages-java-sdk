package com.alibaba.aone.artlab.api.util;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtil {
    private UrlUtil() {
        // Util class
    }

    public static boolean isValidHttpUrl(String url) {
        try {
            URI uri = new URL(url).toURI();
            return StringUtils.equalsIgnoreCase(uri.getScheme(), "http")
                    || StringUtils.equalsIgnoreCase(uri.getScheme(), "https");
        } catch (MalformedURLException | URISyntaxException ignore) {
            return false;
        }
    }
}
