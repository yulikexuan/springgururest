//: guru.springfamework.api.v1.model.Modes.java


package guru.springfamework.api.v1.model;


import lombok.Data;

import java.util.Collection;
import java.util.HashMap;


@Data
public class ModesDTO extends HashMap<String, InfoDTO> {

    public Collection<InfoDTO> getAllInfos() {
        return this.values();
    }

}///:~