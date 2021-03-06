package com.reign.framework.netty.mvc.view;

import com.reign.framework.netty.servlet.*;
import com.reign.framework.netty.mvc.result.*;
import com.reign.framework.netty.mvc.exception.*;
import com.reign.framework.common.*;
import com.reign.framework.netty.http.*;

public class FreeMarkerView extends DefaultView
{
    private boolean compress;
    
    @Override
    public void setCompress(final boolean compress) {
        this.compress = compress;
    }
    
    @Override
    public boolean compress() {
        return this.compress;
    }
    
    @Override
    public void prepareRender(final Response response) {
        response.addHeader("Content-Type", "text/html");
    }
    
    @Override
    public void doRender(final Result<?> result, final Request request, final Response response) throws Exception {
        if (!(result instanceof FreeMarkerResult)) {
            throw new NotMatchResultException("un match result type, except ", FreeMarkerResult.class);
        }
        final FreeMarkerResult freeMarkerResult = (FreeMarkerResult)result;
        if (ServerProtocol.TCP.equals(response.getProtocol())) {
            throw new UnsupportedOperationException("freemarker view not supported tcp");
        }
        final HttpRespone httpRespone = (HttpRespone)response;
        freeMarkerResult.process(httpRespone.getOutPutStream());
    }
}
