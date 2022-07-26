package info.unterrainer.commons.serialization.objectmapper.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherObjectWithArray {
    Object[] objects;
}
