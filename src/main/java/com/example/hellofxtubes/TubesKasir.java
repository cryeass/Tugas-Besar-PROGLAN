package com.example.hellofxtubes;

import javafx.collections.ObservableList;
import javafx.beans.property.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class TubesKasir extends Application {


    private final String PEMBELI_FILE = "pembeli.txt";
    private ObservableList<Pembeli> daftarPembeli;
    private TableView<Pembeli> tableView;
    private TableColumn<Pembeli, String> namaColumn;
    private TableColumn<Pembeli, String> barangColumn;
    private TableColumn<Pembeli, Integer> jumlahColumn;
    private TableColumn<Pembeli, Double> hargaColumn;
    private TableColumn<Pembeli, Double> totalColumn;
    private TableColumn<Pembeli, LocalDate> tanggalColumn;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        daftarPembeli = loadPembeliFromFile();
        tableView = new TableView<>();
        namaColumn = new TableColumn<>("Nama");
        barangColumn = new TableColumn<>("Barang");
        jumlahColumn = new TableColumn<>("Jumlah");
        hargaColumn = new TableColumn<>("Harga");
        totalColumn = new TableColumn<>("Total");
        tanggalColumn = new TableColumn<>("Tanggal");

        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        barangColumn.setCellValueFactory(cellData -> cellData.getValue().barangProperty());
        jumlahColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty().asObject());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        tanggalColumn.setCellValueFactory(CellData -> CellData.getValue().tanggalProperty());

        tableView.setItems(FXCollections.observableArrayList(daftarPembeli));
        tableView.getColumns().addAll(namaColumn, barangColumn, jumlahColumn, hargaColumn, totalColumn,tanggalColumn);

        Button tambahButton = new Button("Tambah Pembeli");
        tambahButton.setOnAction(event -> handleTambahPembeli());

        Button simpanButton = new Button("Simpan");
        simpanButton.setOnAction(event -> handleSimpan());

        Button hapusButton = new Button("Hapus Pembeli");
        hapusButton.setOnAction(event -> handleHapusPembeli());

        HBox buttonBox = new HBox(10, tambahButton, hapusButton, simpanButton);
        buttonBox.setPadding(new Insets(10));

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setHgap(10);
        root.add(tableView, 0, 0);
        root.add(buttonBox, 0, 1);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Daftar Pembeli");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleTambahPembeli() {
        DatePicker datePicker = new DatePicker();
        TextField namaField = new TextField();
        TextField barangField = new TextField();
        Spinner<Integer> jumlahSpinner = new Spinner<>(1, 100, 1);
        TextField hargaField = new TextField();

        GridPane grid = new GridPane();
        grid.add(new Label("Nama:"), 0, 0);
        grid.add(namaField, 1, 0);
        grid.add(new Label("Barang:"), 0, 1);
        grid.add(barangField, 1, 1);
        grid.add(new Label("Jumlah:"), 0, 2);
        grid.add(jumlahSpinner, 1, 2);
        grid.add(new Label("Harga:"), 0, 3);
        grid.add(hargaField, 1, 3);
        grid.add(new Label("Tanggal:"), 0, 4);
        grid.add(datePicker, 1, 4);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tambah Pembeli");
        dialog.setHeaderText("Masukkan detail pembeli:");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                try {
                    // Ambil nilai dari input nama, barang, jumlah, harga, dan tanggal
                    String nama = namaField.getText();
                    String barang = barangField.getText();
                    int jumlah = jumlahSpinner.getValue();
                    double harga = Double.parseDouble(hargaField.getText());
                    LocalDate tanggal = datePicker.getValue();

                    // Create a new Pembeli object with the provided values and add it to the list
                    Pembeli pembeli = new Pembeli(nama, barang, jumlah, harga, tanggal);
                    daftarPembeli.add(pembeli);
                    tableView.getItems().add(pembeli);
                } catch (NumberFormatException e) {
                    showErrorDialog("Gagal menambahkan pembeli. Pastikan input harga berupa angka.");
                }
            }
        });
    }

    private void handleHapusPembeli() {
        Pembeli selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            daftarPembeli.remove(selectedItem);
            tableView.getItems().remove(selectedItem);

            // Hapus data dari file
            updateFileFromList();
        } else {
            showErrorDialog("Pilih pembeli yang ingin dihapus.");
        }
    }

    // Metode untuk menyimpan daftar pembeli ke file setelah menghapus
    private void updateFileFromList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PEMBELI_FILE, false))) {
            for (Pembeli pembeli : daftarPembeli) {
                writer.write(pembeli.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            showErrorDialog("Gagal menyimpan data ke file.");
        }
    }


    private void handleSimpan() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PEMBELI_FILE, false))) {
            for (Pembeli pembeli : daftarPembeli) {
                writer.write(pembeli.toFileString());
                writer.newLine();
            }
            showInformationDialog("Data berhasil disimpan.", "Informasi");
        } catch (IOException e) {
            showErrorDialog("Gagal menyimpan data ke file.");
        }
    }

    private ObservableList<Pembeli> loadPembeliFromFile() {
        ObservableList<Pembeli> daftarPembeli = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(PEMBELI_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String nama = parts[0];
                String barang = parts[1];
                int jumlah = Integer.parseInt(parts[2]);
                double harga = Double.parseDouble(parts[3]);
                daftarPembeli.add(new Pembeli(nama, barang, jumlah, harga, LocalDate.now()));
            }
        } catch (IOException e) {
            // Handle exception
        }
        return daftarPembeli;
    }


    private void showInformationDialog(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public class Pembeli {

        private final ObjectProperty<LocalDate> tanggal;
        private final StringProperty nama;
        private final StringProperty barang;
        private final IntegerProperty jumlah;
        private final DoubleProperty harga;
        private final DoubleProperty total;


        public Pembeli(String nama, String barang, int jumlah, double harga,LocalDate tanggal) {
            this.tanggal = new SimpleObjectProperty<>(tanggal);
            this.nama = new SimpleStringProperty(nama);
            this.barang = new SimpleStringProperty(barang);
            this.jumlah = new SimpleIntegerProperty(jumlah);
            this.harga = new SimpleDoubleProperty(harga);
            this.total = new SimpleDoubleProperty(jumlah * harga);
        }
        public ObjectProperty<LocalDate> tanggalProperty(){
            return tanggal;
        }

        // Getters
        public String getNama() {
            return nama.get();
        }

        public String getBarang() {
            return barang.get();
        }

        public int getJumlah() {
            return jumlah.get();
        }

        public double getHarga() {
            return harga.get();
        }

        public double getTotal() {
            return total.get();
        }

        // Property Getters
        public StringProperty namaProperty() {
            return nama;
        }

        public StringProperty barangProperty() {
            return barang;
        }

        public IntegerProperty jumlahProperty() {
            return jumlah;
        }

        public DoubleProperty hargaProperty() {
            return harga;
        }

        public DoubleProperty totalProperty() {
            return total;
        }

        // Additional methods if needed

        // Example method to convert to a formatted string for writing to a file
        public String toFileString() {
            return String.format("%s,%s,%d,%.2f,%s", getNama(), getBarang(), getJumlah(), getHarga(), tanggalToString());
        }

        private String tanggalToString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return tanggal.get().format(formatter);
        }
}   }