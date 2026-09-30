package pe.edu.upeu.sysventas.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pe.edu.upeu.sysventas.model.Defuncion;
import pe.edu.upeu.sysventas.repository.DefuncionRepository;
import pe.edu.upeu.sysventas.service.DefuncionService;

import java.time.LocalDate;

public class DefuncionController {

    private final DefuncionService service = new DefuncionService(new DefuncionRepository());

    @FXML
    private TextField txtNombre, txtLugar, txtCausa, txtFamiliar, txtParentesco, txtBuscar;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TableView<Defuncion> tabla;
    @FXML
    private TableColumn<Defuncion, Number> colId;
    @FXML
    private TableColumn<Defuncion, String> colNombre, colLugar, colCausa, colFamiliar, colParentesco;
    @FXML
    private TableColumn<Defuncion, LocalDate> colFecha;
    @FXML
    private Label lblEstado;

    private Long idSeleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreFallecido()));
        colFecha.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaDefuncion()));
        colLugar.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLugarDefuncion()));
        colCausa.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCausaMuerte()));
        colFamiliar.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreFamiliar()));
        colParentesco.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getParentesco()));

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, d) -> {
            if (d != null) {
                cargarFormulario(d);
            }
        });
        listar();
    }

    @FXML
    public void guardar() {
        try {
            Defuncion d = leerFormulario();
            service.guardar(d);
            listar();
            limpiar();
            estado("Registro guardado correctamente.");
        } catch (Exception e) {
            alerta(e.getMessage());
        }
    }

    @FXML
    public void actualizar() {
        if (idSeleccionado == null) {
            alerta("Selecciona un registro de la tabla para modificarlo.");
            return;
        }
        try {
            Defuncion d = leerFormulario();
            d.setId(idSeleccionado);
            service.actualizar(d);
            listar();
            limpiar();
            estado("Registro actualizado correctamente.");
        } catch (Exception e) {
            alerta(e.getMessage());
        }
    }

    @FXML
    public void eliminar() {
        Defuncion d = tabla.getSelectionModel().getSelectedItem();
        if (d == null) {
            alerta("Selecciona un registro para eliminar.");
            return;
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar el registro seleccionado?",
                ButtonType.YES, ButtonType.NO);
        a.setHeaderText("Confirmar eliminación");
        if (a.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            service.eliminar(d.getId());
            listar();
            limpiar();
            estado("Registro eliminado.");
        }
    }

    @FXML
    public void buscar() {
        String q = txtBuscar.getText() == null ? "" : txtBuscar.getText().trim().toLowerCase();
        tabla.setItems(FXCollections.observableArrayList(
                service.listar().stream()
                        .filter(d -> d.getNombreFallecido().toLowerCase().contains(q)
                                || d.getLugarDefuncion().toLowerCase().contains(q)
                                || String.valueOf(d.getId()).equals(q))
                        .toList()));
    }

    @FXML
    public void listar() {
        tabla.setItems(FXCollections.observableArrayList(service.listar()));
    }

    @FXML
    public void limpiar() {
        idSeleccionado = null;
        txtNombre.clear();
        txtLugar.clear();
        txtCausa.clear();
        txtFamiliar.clear();
        txtParentesco.clear();
        dpFecha.setValue(null);
        tabla.getSelectionModel().clearSelection();
        estado("Formulario listo para un nuevo registro.");
    }

    private Defuncion leerFormulario() {
        Defuncion d = new Defuncion();
        d.setNombreFallecido(txtNombre.getText());
        d.setFechaDefuncion(dpFecha.getValue());
        d.setLugarDefuncion(txtLugar.getText());
        d.setCausaMuerte(txtCausa.getText());
        d.setNombreFamiliar(txtFamiliar.getText());
        d.setParentesco(txtParentesco.getText());
        return d;
    }

    private void cargarFormulario(Defuncion d) {
        idSeleccionado = d.getId();
        txtNombre.setText(d.getNombreFallecido());
        dpFecha.setValue(d.getFechaDefuncion());
        txtLugar.setText(d.getLugarDefuncion());
        txtCausa.setText(d.getCausaMuerte());
        txtFamiliar.setText(d.getNombreFamiliar());
        txtParentesco.setText(d.getParentesco());
        estado("Editando registro N.º " + d.getId());
    }

    private void alerta(String mensaje) {
        new Alert(Alert.AlertType.WARNING, "No se pudo completar la operación.\n" + mensaje, ButtonType.OK)
                .showAndWait();
    }

    private void estado(String mensaje) {
        lblEstado.setText(mensaje);
    }
}
