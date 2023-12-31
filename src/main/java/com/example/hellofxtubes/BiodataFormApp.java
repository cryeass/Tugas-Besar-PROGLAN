package com.example.hellofxtubes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BiodataFormApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Biodata Mahasiswa");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));


        TextField namaField = new TextField();
        TextField nimField = new TextField();
        TextField emailField = new TextField();
        TextField fakultasField = new TextField();
        TextField jurusanField = new TextField();
        TextField alamatField = new TextField();
        TextField kotaField = new TextField();

        Button createButton = new Button("Create");

        createButton.setOnAction(e -> {

            String nama = namaField.getText();
            String nim = nimField.getText();
            String email = emailField.getText();
            String fakultas = fakultasField.getText();
            String jurusan = jurusanField.getText();
            String alamat = alamatField.getText();
            String kota = kotaField.getText();

            if (nama.isEmpty() || nim.isEmpty() || email.isEmpty() || fakultas.isEmpty() || jurusan.isEmpty() || alamat.isEmpty() || kota.isEmpty()) {
                showAlert("Semua field harus diisi!");
                return;
            }

            if (!nim.matches("\\d+")) {
                showAlert("NIM harus berupa angka!");
                return;
            }

            if (!email.endsWith("@webmail.umm.ac.id")) {
                showAlert("Format email tidak valid! Email harus diakhiri dengan @webmail.umm.ac.id");
                return;
            }


            showResultScene(nama, nim, email, fakultas, jurusan, alamat, kota);
        });


        grid.add(new Label("Nama:"), 0, 0);
        grid.add(namaField, 1, 0);
        grid.add(new Label("NIM:"), 0, 1);
        grid.add(nimField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Fakultas:"), 0, 3);
        grid.add(fakultasField, 1, 3);
        grid.add(new Label("Jurusan:"), 0, 4);
        grid.add(jurusanField, 1, 4);
        grid.add(new Label("Alamat:"), 0, 5);
        grid.add(alamatField, 1, 5);
        grid.add(new Label("Kota:"), 0, 6);
        grid.add(kotaField, 1, 6);
        grid.add(createButton, 0, 7, 2, 1);

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showResultScene(String nama, String nim, String email, String fakultas, String jurusan, String alamat, String kota) {
        Stage resultStage = new Stage();
        resultStage.setTitle("Data Mahasiswa");

        GridPane resultGrid = new GridPane();
        resultGrid.setHgap(10);
        resultGrid.setVgap(10);
        resultGrid.setPadding(new Insets(20, 20, 20, 20));

        resultGrid.add(new Label("Nama:"), 0, 0);
        resultGrid.add(new Label(nama), 1, 0);
        resultGrid.add(new Label("NIM:"), 0, 1);
        resultGrid.add(new Label(nim), 1, 1);
        resultGrid.add(new Label("Email:"), 0, 2);
        resultGrid.add(new Label(email), 1, 2);
        resultGrid.add(new Label("Fakultas:"), 0, 3);
        resultGrid.add(new Label(fakultas), 1, 3);
        resultGrid.add(new Label("Jurusan:"), 0, 4);
        resultGrid.add(new Label(jurusan), 1, 4);
        resultGrid.add(new Label("Alamat:"), 0, 5);
        resultGrid.add(new Label(alamat), 1, 5);
        resultGrid.add(new Label("Kota:"), 0, 6);
        resultGrid.add(new Label(kota), 1, 6);

        Scene resultScene = new Scene(resultGrid, 300, 300);
        resultStage.setScene(resultScene);
        resultStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}