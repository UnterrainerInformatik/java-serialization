package info.unterrainer.commons.serialization.models;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ComplexUser {
    String firstName;
    String lastName;
    LocalDate dateTime;
    Double value;
}
