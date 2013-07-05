import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

public class CarrinhoApp extends Application {

	private AnchorPane pane;
	private TableView<ItensProperty> tbCarrinho;
	private TableColumn<ItensProperty, String> columnProduto;
	private TableColumn<ItensProperty, Double> columnPreco;
	private Button btExcluirItem, btVoltarVitrine, btConfirmarCompra;
	private static Stage stage;
	private static ObservableList<ItensProperty> listItens;
	private int count = 0;

	@Override
	public void start(Stage stage) throws Exception {
		initComponents();
		initListeners();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("GolFX - Carrinho");
		stage.show();
		initLayout();
		CarrinhoApp.stage = stage;
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		listItens = FXCollections.observableArrayList();
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, blue 0%, silver 100%);");
		tbCarrinho = new TableView<ItensProperty>();
		initItens();
		tbCarrinho.setItems(listItens);
		tbCarrinho.setPrefSize(780, 550);
		columnProduto = new TableColumn<ItensProperty, String>();
		columnProduto
				.setCellValueFactory(new PropertyValueFactory<ItensProperty, String>(
						"produto"));
		columnProduto.setText("Produto");
		columnPreco = new TableColumn<ItensProperty, Double>();
		columnPreco
				.setCellValueFactory(new PropertyValueFactory<ItensProperty, Double>(
						"preco"));
		columnPreco.setText("Preço");
		tbCarrinho.getColumns().addAll(columnProduto, columnPreco);
		btExcluirItem = new Button("Excluir Item");
		btVoltarVitrine = new Button("Voltar à Vitrine");
		btConfirmarCompra = new Button("Confirmar Compra");
		pane.getChildren().addAll(tbCarrinho, btExcluirItem, btVoltarVitrine,
				btConfirmarCompra);
	}

	private void initListeners() {
		btExcluirItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				VitrineApp.carrinho.removeProduto(new Produto(tbCarrinho
						.getSelectionModel().getSelectedItem().getProduto(),
						tbCarrinho.getSelectionModel().getSelectedItem()
								.getPreco()));
				tbCarrinho.getItems().remove(
						tbCarrinho.getSelectionModel().getSelectedItem());
			}
		});

		btVoltarVitrine.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				CarrinhoApp.stage.close();
				ItemApp.stage.close();
			}
		});

		btConfirmarCompra.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Thread thread = new Thread() {
					public void run() {
						try {
							sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null,
								"Compra realizada com sucesso!");
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								CarrinhoApp.stage.close();
								ItemApp.stage.close();
							}
						});
					};
				};
				thread.start();
			}
		});
	}

	private void initLayout() {
		tbCarrinho.setLayoutX(10);
		tbCarrinho.setLayoutY(5);
		btExcluirItem.setLayoutX(10);
		btExcluirItem.setLayoutY(570);
		btVoltarVitrine.setLayoutX((pane.getWidth() - btVoltarVitrine
				.getWidth()) / 2);
		btVoltarVitrine.setLayoutY(570);
		btConfirmarCompra.setLayoutX(675);
		btConfirmarCompra.setLayoutY(570);

	}

	private void initItens() {
		for (Produto p : VitrineApp.carrinho.getProdutos())
			listItens.add(new ItensProperty(p.getProduto(), p.getPreco()));
	}

	public static void main(String[] args) {
		launch(args);
	}

	public class ItensProperty {
		private SimpleStringProperty produto;
		private SimpleDoubleProperty preco;

		public ItensProperty(String produto, double preco) {
			this.produto = new SimpleStringProperty(produto);
			this.preco = new SimpleDoubleProperty(preco);
		}

		public String getProduto() {
			return produto.get();
		}

		public void setProduto(String produto) {
			this.produto.set(produto);
		}

		public double getPreco() {
			return preco.get();
		}

		public void setPreco(double preco) {
			this.preco.set(preco);
		}
	}

}
