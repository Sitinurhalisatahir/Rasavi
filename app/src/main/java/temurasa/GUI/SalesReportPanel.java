package temurasa.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import temurasa.models.Order;
import temurasa.database.OrderDao;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SalesReportPanel {

    private VBox mainPanel;
    private TableView<Order> tableView;

    public SalesReportPanel() {
        mainPanel = new VBox(15);
        mainPanel.setStyle("-fx-padding: 20; -fx-background-color: #f5f5f5;");

        Label titleLabel = new Label("📋 Laporan Penjualan");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        tableView = new TableView<>();
        setupTable();

        ScrollPane scrollPane = new ScrollPane(tableView);
        scrollPane.setFitToWidth(true);

        mainPanel.getChildren().addAll(titleLabel, scrollPane);

        loadData();
    }

    private void setupTable() {
        TableColumn<Order, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMinWidth(80);

        TableColumn<Order, String> colNamaPelanggan = new TableColumn<>("Nama Pelanggan");
        colNamaPelanggan.setCellValueFactory(new PropertyValueFactory<>("namaPelanggan"));
        colNamaPelanggan.setMinWidth(180);

        TableColumn<Order, String> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getTanggal().toString()
                )
        );
        colTanggal.setMinWidth(180);

        TableColumn<Order, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total_pembelian"));
        colTotal.setMinWidth(120);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        colTotal.setCellFactory(col -> new javafx.scene.control.TableCell<Order, Double>() {
            @Override
            protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                if (empty || total == null) {
                    setText("");
                } else {
                    setText(currencyFormat.format(total));
                }
            }
        });

        TableColumn<Order, String> colKasir = new TableColumn<>("Kasir");
        colKasir.setCellValueFactory(new PropertyValueFactory<>("kasir"));
        colKasir.setMinWidth(120);

        tableView.getColumns().addAll(colId, colNamaPelanggan, colTanggal, colTotal, colKasir);
    }

    private void loadData() {
        List<Order> orders = new OrderDao().getAllOrders();
        ObservableList<Order> data = FXCollections.observableArrayList(orders);
        tableView.setItems(data);
    }

    public VBox getPanel() {
        return mainPanel;
    }
}