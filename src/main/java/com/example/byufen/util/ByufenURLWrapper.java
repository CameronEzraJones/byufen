package com.example.byufen.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cejon on 7/10/2017.
 */
public class ByufenURLWrapper {
    private URL url;

    public ByufenURLWrapper(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public InputStream openStream() throws IOException {
        return this.url.openStream();
    }
}
