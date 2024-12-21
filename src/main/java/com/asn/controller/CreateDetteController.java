package com.asn.controller;

import java.util.ArrayList;
import java.util.List;

import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Commande;
import com.asn.data.entities.Detail;

import com.asn.data.services.ArticleService;
import com.asn.data.services.ClientService;
import com.asn.data.services.CommandeService;
import com.asn.data.services.DetailService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CreateDetteController {
    RepositoryFactory<Article> articleRepositoryFactory = new RepositoryFactoryImpl<>(Article.class);
    Repository<Article> articleRepository = articleRepositoryFactory.getRepository();
    ServiceFactory<Article> articleServiceFactory = new ServiceFactoryImpl<>(Article.class, articleRepository);
    ArticleService articleService = (ArticleService) articleServiceFactory.getService();
    RepositoryFactory<Commande> commandeRepositoryFactory = new RepositoryFactoryImpl<>(Commande.class);
    Repository<Commande> commandeRepository = commandeRepositoryFactory.getRepository();
    ServiceFactory<Commande> detteServiceFactory = new ServiceFactoryImpl<>(Commande.class, commandeRepository);
    CommandeService commandeService = (CommandeService)detteServiceFactory.getService();
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService();
    RepositoryFactory<Detail> detailRepositoryFactory = new RepositoryFactoryImpl<>(Detail.class);
    Repository<Detail> detailRepository = detailRepositoryFactory.getRepository();
    ServiceFactory<Detail> detailServiceFactory = new ServiceFactoryImpl(Detail.class, detailRepository);
    DetailService detailService = (DetailService)detailServiceFactory.getService();

    @FXML
    private TableView<Detail> detailTable;
    @FXML
    private TableColumn<Detail, String> libelleColumn;
    @FXML
    private TableColumn<Detail, String> prixColumn;
    @FXML
    private TableColumn<Detail, Double> quantiteColumn;
    @FXML
    private TableColumn<Detail, Double> montantColumn;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField libelleField;
    @FXML
    private TextField quantiteField;
    @FXML
    private Text totalField;
    @FXML
    private Button saveButton;
    @FXML
    private Button addButton;
    @FXML
    private Button searchClient;
    private List<Detail> listDetail = new ArrayList<>();
    @FXML
    private TableColumn<Detail, String> actionsColumn;

    @FXML
    public void initialize() {
        libelleColumn.setCellFactory(param -> new TableCell<Detail, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Detail detail = getTableRow().getItem();
                    setText(String.valueOf(detail.getArticle().getLibelle()));
                }
            }
        });
        prixColumn.setCellFactory(param -> new TableCell<Detail, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Detail detail = getTableRow().getItem();
                    setText(String.valueOf(detail.getArticle().getPrix()));
                }
            }
        });
        quantiteColumn.setCellFactory(param -> new TableCell<Detail, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Detail detail = getTableRow().getItem();
                    setText(String.valueOf(detail.getQuantite()));
                }
            }
        });
        montantColumn.setCellFactory(param -> new TableCell<Detail, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Detail detail = getTableRow().getItem();
                    double montant = detail.getQuantite() * detail.getArticle().getPrix();
                    setText(String.valueOf(montant));
                }
            }
        });
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button actionButton = new Button("delete");
    
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
    
                if (empty) {
                    setGraphic(null);
                } else {
                    // Définir le texte et le style du bouton
                    actionButton.setStyle("-fx-background-color: #4A76A8; -fx-text-fill: WHITE;-fx-background-radius: 5px;-fx-font-size: 10px;-fx-padding: 2px 5px;");
                    // Associer un événement au bouton
                    actionButton.setOnAction(event -> {
                        Detail detail = getTableView().getItems().get(getIndex());
                        listDetail.remove(detail);
                        loadListDetail();
                    });
    
                    // Ajouter le bouton à la cellule
                    setGraphic(actionButton);
                }
            }
        });
        detailTable.setEditable(true);

        addButton.setOnAction(event -> this.createDetailCommande());
        saveButton.setOnAction(event -> this.saveDette());
        telephoneField.setDisable(true);
        addressField.setDisable(true);
        searchClient.setOnAction(event -> this.searchClient());
    }

    

    

    @FXML
    private void createDetailCommande() {
        String libelle = libelleField.getText();
        String quantite = quantiteField.getText();
        Article article = articleService.getByLibelle(libelle);
        if (libelle.isEmpty() || quantite.isEmpty() || !quantite.matches("\\d+") || article == null || article.getQuantite() < Double.parseDouble(quantite)) {
            showAlert("Erreur", "Saisie des champs incorrecte ou l'article ou le client n'existe pas");
            return;
        }
        if (article.getQuantite() < Double.parseDouble(quantite)) {
            showAlert("Erreur", "Quantite insufficiente");
            return;
        }
        Detail detail = new Detail();
        detail.setArticle(article);
        detail.setQuantite(Double.parseDouble(quantite));
        this.listDetail.add(detail);
        libelleField.clear();
        quantiteField.clear();
        this.loadListDetail();
    }

    @FXML
    public void loadListDetail() {
        ObservableList<Detail> listDetails = FXCollections.observableArrayList(listDetail);
        detailTable.setItems(listDetails);
        updateTotal();
    }

     private void updateTotal() {
        double total = 0;
        for (Detail detail : listDetail) {
            total += detail.getArticle().getPrix() * detail.getQuantite();
        }
        totalField.setText("Total : " + total + " FCFA");
    }
    @FXML
    private void saveDette() {
        Client client = searchClient();
        if (client == null) {
            showAlert("Erreur", "Le client n'existe pas");
            return;
        }
        Commande dette = new Commande();
        dette.setClient(client);
        if (listDetail.isEmpty()) {
            showAlert("Erreur", "Aucun article dans la dette");
            return;
        }
        for (Detail detail : listDetail) {
            dette.setMontant(dette.getMontant() + (detail.getQuantite() * detail.getArticle().getPrix()));
        }
        int id = commandeService.save(dette);
        for (Detail detail : listDetail) {
            detail.setCommande(commandeService.getById(id));
            detailService.save(detail);
            Article article = articleService.getById(detail.getArticle().getId());
            articleService.update(article, article.getQuantite() - detail.getQuantite());
        }
        listDetail.clear();
        loadListDetail();
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
    private Client searchClient() {
        String surname = surnameField.getText();
        Client client = clientService.getBySurname(surname);
        if (client == null) {
            showAlert("Erreur", "Le client n'existe pas");
            return null;
        }
        telephoneField.setText(client.getPhone());
        addressField.setText(client.getAddress());
        return client;
    }
}
