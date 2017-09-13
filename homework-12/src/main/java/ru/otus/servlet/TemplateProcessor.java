package ru.otus.servlet;

import freemarker.template.*;

import java.io.*;
import java.util.*;

class TemplateProcessor {
    private static final String HTML_DIR = "tml";
    private static TemplateProcessor instance = new TemplateProcessor();

    private final Configuration configuration;

    private TemplateProcessor() {
        configuration = new Configuration();
    }

    static TemplateProcessor instance() {
        return instance;
    }

    String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
