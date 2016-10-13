package garfield.java.jersey.example.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

@Priority(Priorities.ENTITY_CODER)
@Provider
public class GZipReaderInterceptor implements ReaderInterceptor {
    
	@Override
	public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
		final InputStream outputStream = context.getInputStream();
		context.setInputStream(new GZIPInputStream(outputStream));
        return context.proceed();
	}
}
