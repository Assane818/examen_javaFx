package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.services.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateUserController {
    RepositoryFactory<User> userRepositoryFactory = new RepositoryFactoryImpl<>(User.class);
    Repository<User> userRepository = userRepositoryFactory.getRepository();
    ServiceFactory<User> userServiceFactory = new ServiceFactoryImpl<>(User.class, userRepository);
    UserService userService = (UserService)userServiceFactory.getService();

    @FXML
    private Button listeUserButton;

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> userTypeSelect;
    @FXML
    private Button saveButton;
    @FXML
    private Button annulerButton;
    @FXML
    private Button listeArticleButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    public void initialize() {
        userTypeSelect.getItems().addAll("ADMIN", "BOUTIQUIER");
        userTypeSelect.setValue("ADMIN");
        // Ajouter un événement au bouton de connexion
        listeUserButton.setOnAction(event -> this.loadAdminView());
        saveButton.setOnAction(event -> this.saveUser());
        annulerButton.setOnAction(event -> this.loadAdminView());
        //affiche menu article
        listeArticleButton.setOnAction(event -> this.loadListeArticle());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    private void saveUser() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        String role = userTypeSelect.getValue();
        if (nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || password.isEmpty() || userService.getByLogin(login) != null) {
            showAlert("Erreur", "Saisie des champs incorrecte");
            return;
        }
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(Role.getValue(role));
        userService.save(user);
        this.loadAdminView();
    }


    private void showAlert(String title, String message) {
        // Créer une nouvelle alerte de type INFORMATION
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        // Supprimer le texte d'en-tête de l'alerte
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void loadListeArticle() {
        try {
            App.setRoot("listeArticle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadAdminView() {
        try {
            App.setRoot("ListeUserAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
