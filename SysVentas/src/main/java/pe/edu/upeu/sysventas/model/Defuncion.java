package pe.edu.upeu.sysventas.model;

import java.time.LocalDate;

/**
 * Registro civil de una defunción.
 */
public class Defuncion {

    private Long id;
    private String nombreFallecido;
    private LocalDate fechaDefuncion;
    private String lugarDefuncion;
    private String causaMuerte;
    private String nombreFamiliar;
    private String parentesco;

    public Defuncion() {
    }

    public Defuncion(Long id, String nombreFallecido, LocalDate fechaDefuncion, String lugarDefuncion,
                     String causaMuerte, String nombreFamiliar, String parentesco) {
        this.id = id;
        this.nombreFallecido = nombreFallecido;
        this.fechaDefuncion = fechaDefuncion;
        this.lugarDefuncion = lugarDefuncion;
        this.causaMuerte = causaMuerte;
        this.nombreFamiliar = nombreFamiliar;
        this.parentesco = parentesco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFallecido() {
        return nombreFallecido;
    }

    public void setNombreFallecido(String nombreFallecido) {
        this.nombreFallecido = nombreFallecido;
    }

    public LocalDate getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(LocalDate fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public String getLugarDefuncion() {
        return lugarDefuncion;
    }

    public void setLugarDefuncion(String lugarDefuncion) {
        this.lugarDefuncion = lugarDefuncion;
    }

    public String getCausaMuerte() {
        return causaMuerte;
    }

    public void setCausaMuerte(String causaMuerte) {
        this.causaMuerte = causaMuerte;
    }

    public String getNombreFamiliar() {
        return nombreFamiliar;
    }

    public void setNombreFamiliar(String nombreFamiliar) {
        this.nombreFamiliar = nombreFamiliar;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
