package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Defuncion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio en memoria para las operaciones CRUD del ejercicio.
 */
public class DefuncionRepository {

    private final List<Defuncion> datos = new ArrayList<>();
    private long secuencia = 1;

    public Defuncion guardar(Defuncion d) {
        if (d.getId() == null) {
            d.setId(secuencia++);
        }
        datos.add(d);
        return d;
    }

    public Defuncion actualizar(Defuncion d) {
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).getId().equals(d.getId())) {
                datos.set(i, d);
                return d;
            }
        }
        throw new IllegalArgumentException("No se encontró el registro seleccionado.");
    }

    public List<Defuncion> listar() {
        return new ArrayList<>(datos);
    }

    public Optional<Defuncion> buscar(Long id) {
        return datos.stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    public void eliminar(Long id) {
        datos.removeIf(d -> d.getId().equals(id));
    }
}
