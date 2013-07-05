import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ItemApp extends Application {

	private AnchorPane pane;
	private ImageView imgItem;
	private Label lbPreco, lbDescricao;
	private Button btAddCarrinho;
	public static Stage stage;
	public static Produto produto;
	public static int index;
	private static String[] images = {
			"http://www.sportcenterlopes.com.br/images/250_topper_campo_2009replic.jpg",
			"http://1.bp.blogspot.com/_H8uGs8K8kaY/TLZTXR8nIgI/AAAAAAAAF_0/BvpxdqGF4PE/s1600/luva_umbro.png",
			"http://bimg2.mlstatic.com/camisa-nike-active-importada-manga-longa-esportiva-vermelha_MLB-F-199843960_1391.jpg",
			"http://www.showtenis.com.br/images/_product/979/979112/chuteira-nike-mercurial-glide-3-fg-campo--199fd9.jpg",
			"http://www.katy.com.br/imagens/produtos/original/caneleira-topper-trainning-difusion-13340619502673137.jpg" };

	@Override
	public void start(Stage stage) throws Exception {
		initComponents();
		initListeners();
		initTransition();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle(produto.getProduto());
		stage.show();
		initLayout();
		ItemApp.stage = stage;
	}

	private void initTransition() {
		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(imgItem.opacityProperty(), 0.0);
		KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);
		timeline.getKeyFrames().add(kf);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.play();
	}

	private void initComponents() {
		pane = new AnchorPane();
		pane.setPrefSize(600, 400);
		pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, blue 0%, silver 100%);");
		imgItem = new ImageView(new Image(images[index]));
		imgItem.setFitWidth(300);
		imgItem.setFitHeight(200);
		imgItem.setEffect(new Reflection());
		lbPreco = new Label(
				"Preço: ".concat(Double.toString(produto.getPreco())));
		lbPreco.setStyle("-fx-text-fill: white;");
		lbDescricao = new Label("Descrição: ".concat(produto.getProduto()));
		lbDescricao.setStyle("-fx-text-fill: white;");
		btAddCarrinho = new Button("Adicionar ao Carrinho");
		InnerShadow is = new InnerShadow();
		is.setColor(Color.RED);
		btAddCarrinho.setEffect(is);
		pane.getChildren().addAll(imgItem, lbPreco, lbDescricao, btAddCarrinho);
	}

	private void initListeners() {
		btAddCarrinho.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				VitrineApp.carrinho.addProdutos(produto);
				try {
					new CarrinhoApp().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initLayout() {
		lbPreco.setLayoutX(350);
		lbPreco.setLayoutY(50);
		lbDescricao.setLayoutX(350);
		lbDescricao.setLayoutY(100);
		btAddCarrinho.setLayoutX(400);
		btAddCarrinho.setLayoutY(300);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
