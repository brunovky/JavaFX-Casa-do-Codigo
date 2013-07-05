import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;


public class LoginApp extends Application {

	private AnchorPane pane;
	private TextField txLogin;
	private PasswordField txSenha;
	private Button btEntrar, btSair;
	private static Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception {
		initComponents();
		initListeners();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("login.css");
		stage.setScene(scene);
		stage.setTitle("Login - GolFX");
		stage.setResizable(false);
		stage.show();
		initLayout();
		LoginApp.stage = stage;
	}

	private void initLayout() {
		txLogin.setLayoutX((pane.getWidth() - txLogin.getWidth()) / 2);
		txLogin.setLayoutY(50);
		txSenha.setLayoutX((pane.getWidth() - txSenha.getWidth()) / 2);
		txSenha.setLayoutY(100);
		btEntrar.setLayoutX((pane.getWidth() - btEntrar.getWidth()) / 2);
		btEntrar.setLayoutY(150);
		btSair.setLayoutX((pane.getWidth() - btSair.getWidth()) / 2);
		btSair.setLayoutY(200);
	}

	private void initComponents() {
		pane = new AnchorPane();
		pane.setPrefSize(400, 300);
		pane.getStyleClass().add("pane");
		txLogin = new TextField();
		txLogin.setPromptText("Digite seu login...");
		txSenha = new PasswordField();
		txSenha.setPromptText("Digite sua senha...");
		btEntrar = new Button("Entrar");
		btEntrar.getStyleClass().add("btEntrar");
		btSair = new Button("Sair");
		btSair.getStyleClass().add("btSair");
		pane.getChildren().addAll(txLogin, txSenha, btEntrar, btSair);
	}
	
	private void initListeners() {
		btSair.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		btEntrar.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				if (txLogin.getText().equals("admin") && txSenha.getText().equals("casadocodigo")) {
					try {
						new VitrineApp().start(new Stage());
						LoginApp.stage.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Login e/ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
