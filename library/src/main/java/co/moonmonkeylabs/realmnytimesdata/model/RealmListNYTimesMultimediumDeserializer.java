package co.moonmonkeylabs.realmnytimesdata.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.List;

import io.realm.RealmList;

public class RealmListNYTimesMultimediumDeserializer extends
        JsonDeserializer<List<NYTimesMultimedium>> {

    ObjectMapper objectMapper;

    public RealmListNYTimesMultimediumDeserializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<NYTimesMultimedium> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        RealmList<NYTimesMultimedium> list = new RealmList<>();

        TreeNode treeNode = jp.getCodec().readTree(jp);
        if (!(treeNode instanceof ArrayNode)) {
            return list;
        }

        ArrayNode arrayNode = (ArrayNode) treeNode;
        for (JsonNode node : arrayNode) {
            NYTimesMultimedium nyTimesMultimedium =
                    objectMapper.treeToValue(node, NYTimesMultimedium.class);
            list.add(nyTimesMultimedium);
        }
        return list;
    }
}