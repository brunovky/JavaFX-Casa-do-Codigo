import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class TesteApp extends Application {

	private AnchorPane pane;

	@Override
	public void start(Stage stage) throws Exception {
		pane = new AnchorPane();
		ProgressIndicator progress = new ProgressIndicator(0.0);
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				final int max = 10;
				for (int i = 1; i <= max; i++) {
					updateProgress(i, max);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "Progresso concluído!");
				return null;
			}
		};
		progress.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		pane.getChildren().add(progress);
		pane.setPrefSize(400, 200);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Tela com Progress Indicator");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
