import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VitrineApp extends Application {

	private AnchorPane pane;
	private TextField txPesquisa;
	private TableView<ItensProperty> tbVitrine;
	private TableColumn<ItensProperty, String> columnProduto;
	private TableColumn<ItensProperty, Double> columnPreco;
	private static ObservableList<ItensProperty> listItens = FXCollections
			.observableArrayList();
	public static Carrinho carrinho = new Carrinho();

	@Override
	public void start(Stage stage) throws Exception {
		initComponents();
		initListeners();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Vitrine - GolFX");
		stage.show();
		initLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, blue 0%, silver 100%);");
		txPesquisa = new TextField();
		DropShadow ds = new DropShadow();
		ds.setSpread(0.5);
		ds.setColor(Color.web("#FF0000"));
		txPesquisa.setEffect(ds);
		txPesquisa.setPromptText("Digite o item para pesquisa");
		txPesquisa.setPrefWidth(200);
		txPesquisa.setFocusTraversable(false);
		tbVitrine = new TableView<ItensProperty>();
		initItens();
		tbVitrine.setItems(listItens);
		tbVitrine.setPrefSize(780, 550);
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
		tbVitrine.getColumns().addAll(columnProduto, columnPreco);
		pane.getChildren().addAll(txPesquisa, tbVitrine);
	}

	private void initListeners() {
		txPesquisa.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!txPesquisa.getText().equals("")) {
					tbVitrine.setItems(findItems());
				} else {
					tbVitrine.setItems(listItens);
				}
			}
		});
		tbVitrine.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<ItensProperty>() {
					@Override
					public void changed(
							ObservableValue<? extends ItensProperty> value,
							ItensProperty oldItem, ItensProperty newItem) {
						ItemApp.produto = new Produto(newItem.getProduto(),
								newItem.getPreco());
						ItemApp.index = tbVitrine.getSelectionModel()
								.getSelectedIndex();
						try {
							new ItemApp().start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void initLayout() {
		txPesquisa.setLayoutX(590);
		txPesquisa.setLayoutY(10);
		tbVitrine.setLayoutX(10);
		tbVitrine.setLayoutY(40);
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

	private void initItens() {
		Vitrine v = new Vitrine();
		v.addProdutos(new Produto("Bola Topper", 15.00), new Produto(
				"Luvas Umbro", 9.00), new Produto("Camisa Esportiva", 40.00),
				new Produto("Chuteira Nike Mercurial", 199.00), new Produto(
						"Caneleira Topper", 10.00));
		for (Produto p : v.getProdutos())
			listItens.add(new ItensProperty(p.getProduto(), p.getPreco()));
	}

	private ObservableList<ItensProperty> findItems() {
		ObservableList<ItensProperty> itensEncontrados = FXCollections
				.observableArrayList();
		for (ItensProperty itens : listItens) {
			if (itens.getProduto().contains(txPesquisa.getText())) {
				itensEncontrados.add(itens);
			}
		}
		return itensEncontrados;
	}

}