package com.springboot.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Registers the base endpoints for rest api
 *
 */
@Component
@ApplicationPath("/shops")
public class JerseyConfig extends ResourceConfig {
 
  @Autowired
  public JerseyConfig(ObjectMapper objectMapper) {
    // register endpoints
    packages("com.springboot.rest");
    // register jackson for json 
    register(new ObjectMapperContextResolver(objectMapper));
  }
 
  @Provider
  public static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
 
    private final ObjectMapper mapper;
 
    public ObjectMapperContextResolver(ObjectMapper mapper) {
      this.mapper = mapper;
    }
 
    public ObjectMapper getContext(Class<?> type) {
      return mapper;
    }
  }
}