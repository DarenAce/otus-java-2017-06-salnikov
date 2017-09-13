package ru.otus.servlet;

import ru.otus.cache.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class CacheParametersServlet extends HttpServlet {
    private CacheInfo cache;

    public CacheParametersServlet(CacheInfo cache) {
        this.cache = cache;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, Object> pageVariables = new HashMap<>();
        long hits = cache.getCacheHitCount();
        long misses = cache.getCacheMissCount();
        double ratio = ((double) hits / (hits + misses)) * 100.0;

        pageVariables.put("hitCount", hits);
        pageVariables.put("missCount", misses);
        pageVariables.put("hitRatio", ratio);

        response.getWriter().println(TemplateProcessor.instance().getPage("cacheParameters.json", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
