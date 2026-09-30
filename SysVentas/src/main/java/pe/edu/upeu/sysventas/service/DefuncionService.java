package pe.edu.upeu.sysventas.service;

import pe.edu.upeu.sysventas.model.Defuncion;
import pe.edu.upeu.sysventas.repository.DefuncionRepository;

import java.time.LocalDate;
import java.util.List;

public class DefuncionService {

    private final DefuncionRepository repository;

    public DefuncionService(DefuncionRepository repository) {
        this.repository = repository;
    }

    public Defuncion guardar(Defuncion d) {
        validar(d);
        return repository.guardar(d);
    }

    public Defuncion actualizar(Defuncion d) {
        validar(d);
        return repository.actualizar(d);
    }

    public List<Defuncion> listar() {
        return repository.listar();
    }

    public void eliminar(Long id) {
        repository.eliminar(id);
    }

    private void validar(Defuncion d) {
        if (vacio(d.getNombreFallecido())
                || d.getFechaDefuncion() == null
                || vacio(d.getLugarDefuncion())
                || vacio(d.getCausaMuerte())
                || vacio(d.getNombreFamiliar())
                || vacio(d.getParentesco())) {
            throw new IllegalArgumentException("Completa todos los campos antes de guardar.");
        }
        if (d.getFechaDefuncion().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de defunción no puede ser futura.");
        }
    }

    private boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
