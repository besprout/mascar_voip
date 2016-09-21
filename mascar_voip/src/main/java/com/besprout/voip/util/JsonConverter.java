package com.besprout.voip.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

public class JsonConverter extends MappingJacksonHttpMessageConverter {

	public JsonConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		StdSerializerProvider sp = new StdSerializerProvider();
		sp.setNullValueSerializer(new NullSerializer());
		objectMapper.setSerializerProvider(sp);
		this.setObjectMapper(objectMapper);
	}

}
