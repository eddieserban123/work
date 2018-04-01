package com.aqr.tca.model.deserializer;

import com.aqr.tca.model.PersistToWebService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PersistToWebServiceDeserializer extends StdDeserializer<PersistToWebService> {

    public PersistToWebServiceDeserializer(Class<PersistToWebServiceDeserializer> vc) {
        super(vc);
    }

    public PersistToWebServiceDeserializer() {
        this(null);
    }

    @Override
    public PersistToWebService deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        try {
            URI location = new URI(node.get("location").asText());
            HttpMethod methodType = HttpMethod.resolve(node.get("method").asText());
            Map<String, String> headers = new HashMap<>();
            JsonNode headersNode = node.get("headers");
            if (headers != null) {
                headersNode.elements().forEachRemaining(
                        el -> {
                            headers.put(el.fieldNames().next(), el.elements().next().textValue());
                        }
                );
            }

//            TypeFactory factory = TypeFactory.defaultInstance();
//            MapType type    = factory.constructMapType(HashMap.class, String.class, String.class);
//            mapper  = new ObjectMapper();
//            = mapper.readValue(headersNode.asText(), type);
            return new PersistToWebService(location, methodType, headers);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
