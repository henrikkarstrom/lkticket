package se.lundakarnevalen.ticket.api.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONArray;
import org.json.JSONException;

import se.lundakarnevalen.ticket.db.Entity;
import se.lundakarnevalen.ticket.logging.ErrorLogger;

@Provider
public class EntityListWriter implements MessageBodyWriter<List<? extends Entity>> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return List.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(List<? extends Entity> t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(List<? extends Entity> t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		JSONArray json = new JSONArray();
		for (Entity node : t) {
			try {
				json.put(node.toJSON());
			} catch (JSONException e) {
				ErrorLogger.getInstance().put(e);
			}
		}
		entityStream.write(json.toString().getBytes());
	}
}
