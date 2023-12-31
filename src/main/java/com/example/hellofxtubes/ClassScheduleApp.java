package com.example.hellofxtubes;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClassScheduleApp extends Application {

    private TableView<ScheduleEntry> tableView;
    private TextField namaDosenField, mataKuliahField, gkbField, waktuField, ruanganField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Class Schedule App");

        // Table
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setupTable();

        // Form
        HBox formBox = createFormBox();

        // Layout
        BorderPane layout = new BorderPane();
        layout.setTop(formBox);
        layout.setCenter(tableView);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTable() {
        TableColumn<ScheduleEntry, String> namaDosenCol = new TableColumn<>("Nama Dosen");
        namaDosenCol.setCellValueFactory(new PropertyValueFactory<>("namaDosen"));

        TableColumn<ScheduleEntry, String> mataKuliahCol = new TableColumn<>("Mata Kuliah");
        mataKuliahCol.setCellValueFactory(new PropertyValueFactory<>("mataKuliah"));

        TableColumn<ScheduleEntry, String> gkbCol = new TableColumn<>("GKB");
        gkbCol.setCellValueFactory(new PropertyValueFactory<>("gkb"));

        TableColumn<ScheduleEntry, String> waktuCol = new TableColumn<>("Waktu");
        waktuCol.setCellValueFactory(new PropertyValueFactory<>("waktu"));

        TableColumn<ScheduleEntry, String> ruanganCol = new TableColumn<>("Ruangan");
        ruanganCol.setCellValueFactory(new PropertyValueFactory<>("ruangan"));

        tableView.getColumns().addAll(namaDosenCol, mataKuliahCol, gkbCol, waktuCol, ruanganCol);
        tableView.setItems(getDummyData()); // You can replace this with your data source
    }

    private HBox createFormBox() {
        HBox formBox = new HBox(10);
        formBox.setPadding(new Insets(10));

        namaDosenField = new TextField();
        mataKuliahField = new TextField();
        gkbField = new TextField();
        waktuField = new TextField();
        ruanganField = new TextField();

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        addButton.setOnAction(e -> addSchedule());
        updateButton.setOnAction(e -> updateSchedule());
        deleteButton.setOnAction(e -> deleteSchedule());

        formBox.getChildren().addAll(
                new Label("Nama Dosen:"), namaDosenField,
                new Label("Mata Kuliah:"), mataKuliahField,
                new Label("GKB:"), gkbField,
                new Label("Waktu:"), waktuField,
                new Label("Ruangan:"), ruanganField,
                addButton, updateButton, deleteButton
        );

        return formBox;
    }

    private ObservableList<ScheduleEntry> getDummyData() {
        ObservableList<ScheduleEntry> data = FXCollections.observableArrayList();
        return data;
    }

    private void addSchedule() {
        if (validateInput()) {
            ScheduleEntry newEntry = new ScheduleEntry(
                    namaDosenField.getText(),
                    mataKuliahField.getText(),
                    gkbField.getText(),
                    waktuField.getText(),
                    ruanganField.getText()
            );
            tableView.getItems().add(newEntry);
            clearFields();
        }
    }

    private void updateSchedule()  {
        ScheduleEntry selectedEntry = tableView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null && validateInput()) {
            selectedEntry.setNamaDosen(namaDosenField.getText());
            selectedEntry.setMataKuliah(mataKuliahField.getText());
            selectedEntry.setGkb(gkbField.getText());
            selectedEntry.setWaktu(waktuField.getText());
            selectedEntry.setRuangan(ruanganField.getText());
            clearFields();
        }
    }

    private void deleteSchedule() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableView.getItems().remove(selectedIndex);
            clearFields();
        }
    }

    private boolean validateInput() {
        String namaDosen = namaDosenField.getText();
        String mataKuliah = mataKuliahField.getText();
        String gkb = gkbField.getText();
        String waktu = waktuField.getText();
        String ruangan = ruanganField.getText();

        if (namaDosen.isEmpty() || mataKuliah.isEmpty() || gkb.isEmpty() || waktu.isEmpty() || ruangan.isEmpty()) {
            showAlert("Semua field harus diisi!");
            return false;
        }

        return true;
    }

    private void clearFields() {
        namaDosenField.clear();
        mataKuliahField.clear();
        gkbField.clear();
        waktuField.clear();
        ruanganField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class ScheduleEntry {
        private String namaDosen;
        private String mataKuliah;
        private String gkb;
        private String waktu;
        private String ruangan;

        public ScheduleEntry(String namaDosen, String mataKuliah, String gkb, String waktu, String ruangan) {
            this.namaDosen = namaDosen;
            this.mataKuliah = mataKuliah;
            this.gkb = gkb;
            this.waktu = waktu;
            this.ruangan = ruangan;
        }

        public String getNamaDosen() {
            return namaDosen;
        }

        public void setNamaDosen(String namaDosen) {
            this.namaDosen = namaDosen;
        }

        public String getMataKuliah() {
            return mataKuliah;
        }

        public void setMataKuliah(String mataKuliah) {
            this.mataKuliah = mataKuliah;
        }

        public String getGkb() {
            return gkb;
        }

        public void setGkb(String gkb) {
            this.gkb = gkb;
        }

        public String getWaktu() {
            return waktu;
        }

        public void setWaktu(String waktu) {
            this.waktu = waktu;
        }

        public String getRuangan() {
            return ruangan;
        }

        public void setRuangan(String ruangan) {
            this.ruangan = ruangan;
        }
    }
}