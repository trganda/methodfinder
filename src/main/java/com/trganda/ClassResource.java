package com.trganda;

import java.io.IOException;
import java.io.InputStream;

public interface ClassResource {
    public InputStream getInputStream() throws IOException;

    public String getName();
}