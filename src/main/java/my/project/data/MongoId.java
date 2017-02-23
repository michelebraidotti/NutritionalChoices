package my.project.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Created by michele on 2/17/17.
 */
public class MongoId {
    private String $oid;

    public MongoId(){
    }

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }

    @JsonCreator
    public static String fromJSON(String val) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        MongoId a = mapper.readValue(val,MongoId.class);
        return a.get$oid();
    }

}

