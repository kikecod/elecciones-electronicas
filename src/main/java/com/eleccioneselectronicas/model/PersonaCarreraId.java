package com.eleccioneselectronicas.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonaCarreraId implements Serializable {

    private Long persona;
    private Long carrera;

    public PersonaCarreraId() {}

    public PersonaCarreraId(Long persona, Long carrera) {
        this.persona = persona;
        this.carrera = carrera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonaCarreraId)) return false;
        PersonaCarreraId that = (PersonaCarreraId) o;
        return Objects.equals(persona, that.persona) &&
               Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, carrera);
    }
}
