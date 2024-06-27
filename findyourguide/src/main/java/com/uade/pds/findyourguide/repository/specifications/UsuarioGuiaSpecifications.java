package com.uade.pds.findyourguide.repository.specifications;

import com.uade.pds.findyourguide.enums.Idioma;
import com.uade.pds.findyourguide.enums.TipoServicio;
import com.uade.pds.findyourguide.model.user.UsuarioGuia;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioGuiaSpecifications {
    public static Specification<UsuarioGuia> hasNombre(String nombre) {
        return (root, query, criteriaBuilder) ->
                nombre != null ? criteriaBuilder.equal(root.get("nombre"), nombre) : null;
    }

    public static Specification<UsuarioGuia> hasApellido(String apellido) {
        return (root, query, criteriaBuilder) ->
                apellido != null ? criteriaBuilder.equal(root.get("apellido"), apellido) : null;
    }

    public static Specification<UsuarioGuia> hasCiudad(String ciudad) {
        return (root, query, criteriaBuilder) ->
                ciudad != null ? criteriaBuilder.equal(root.join("listaCiudadesActivo").get("ciudad"), ciudad) : null;
    }

    public static Specification<UsuarioGuia> hasPais(String pais) {
        return (root, query, criteriaBuilder) ->
                pais != null ? criteriaBuilder.equal(root.join("listaCiudadesActivo").get("pais"), pais) : null;
    }

    public static Specification<UsuarioGuia> hasIdiomas(List<Idioma> idiomas) {
        return (root, query, criteriaBuilder) -> {
            if (idiomas != null && !idiomas.isEmpty()) {
                Join<UsuarioGuia, Idioma> idiomasJoin = root.join("idiomas");
                CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(idiomasJoin);
                idiomas.forEach(inClause::value);
                return inClause;
            }
            return null;
        };
    }

    public static Specification<UsuarioGuia> hasTipoServicio(List<TipoServicio> servicios) {
        return (root, query, criteriaBuilder) -> {
            if (servicios != null && !servicios.isEmpty()) {
                CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(root.join("listServicios").get("tipoServicio"));
                servicios.forEach(inClause::value);
                return inClause;
            }
            return null;
        };
    }

    public static Specification<UsuarioGuia> hasPuntuacion(int puntuacion) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("puntuacion"), puntuacion);
    }

    public static Specification<UsuarioGuia> combineSpecifications(
            Specification<UsuarioGuia>... specifications) {
        Specification<UsuarioGuia> result = Specification.where(specifications[0]);
        for (int i = 1; i < specifications.length; i++) {
            result = result.and(specifications[i]);
        }
        return result;
    }
}
