package com.example.hellofxtubes;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private TableView<Mahasiswa> table = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());

        stage.setTitle("Test TableView");
        stage.setWidth(350);
        stage.setHeight(350);

        final Label label = new Label("Daftar Mahasiswa");
        label.setFont(new Font("Arial", 30));

        table.setEditable(true);

        TableColumn<Mahasiswa, String> nameCol = new TableColumn<>("Nama");
        TableColumn<Mahasiswa, String> nimCol = new TableColumn<>("NIM");
        TableColumn<Mahasiswa, String> emailCol = new TableColumn<>("Email");

        table.getColumns().addAll(nameCol, nimCol, emailCol);

        final ObservableList<Mahasiswa> data = FXCollections.observableArrayList(
                new Mahasiswa("Larynt", "202110370311189", "laryntsa@gmail.com"),
                new Mahasiswa("Ahya", "202110370311187", "ayaa@gmail.com")
        );

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nimCol.setCellValueFactory(new PropertyValueFactory<>("nim"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.setItems(data);


        TextField addName = new TextField();
        addName.setPromptText("Nama");
        TextField addNim = new TextField();
        addNim.setPromptText("NIM");
        TextField addEmail = new TextField();
        addEmail.setPromptText("Email");


        Button addButton = new Button("Tambah");
        addButton.setOnAction(e -> {
            data.add(new Mahasiswa(
                    addName.getText(),
                    addNim.getText(),
                    addEmail.getText()
            ));

            addName.clear();
            addNim.clear();
            addEmail.clear();
        });


        HBox inputBox = new HBox(5, addName, addNim, addEmail, addButton);

        final VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.getChildren().addAll(label, table, inputBox);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class Mahasiswa {
        private final SimpleStringProperty name;
        private final SimpleStringProperty nim;
        private final SimpleStringProperty email;

        public Mahasiswa(String name, String nim, String email) {
            this.name = new SimpleStringProperty(name);
            this.nim = new SimpleStringProperty(nim);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getNim() {
            return nim.get();
        }

        public void setNim(String fName) {
            nim.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public SimpleStringProperty nimProperty() {
            return nim;
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }
    }
}

