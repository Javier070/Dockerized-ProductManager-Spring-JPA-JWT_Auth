package com.jm.tfg.wrapper;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.jm.tfg.Entidades.Category}
 */
@Value
public class CategoryDto implements Serializable {
    Long id;
    String name;
}