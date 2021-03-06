package demomover;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.Console;

import com.sun.org.apache.xerces.internal.dom.AbortException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.print.resources.serviceui;

public class ConfirmController {
	@FXML
	private Label moveInfos;
	@FXML
	private Button yesButton;
	@FXML
	private Button noButton;
	@FXML
	private ProgressBar loadingBar;
	@FXML
	private Label wantToContinue;

	public void initialize() {

		moveInfos.setText(Move.infoForConfirm);
		System.out.println("demonumber" + Move.demoAmountForConfirm);
		if (Move.demoAmountForConfirm == 0) { // check if demos are found
			yesButton.setDisable(true);
		} else {
			yesButton.setDisable(false);
		}
	}

	@FXML
	void letsGo(ActionEvent event) {
		System.out.println("lETSSEEE GOO");
		yesButton.setDisable(true);

		Thread moverThread = new Thread() {
			public void run() {
				Move.demoMover(MoveController.selectedCsgoDirectory.toString(),
						MoveController.selectedTargetDirectory.toString());
				loadingBar.setProgress(1);
				try {
					sleep(800);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		};

		moverThread.start();

		Thread progressThread = new Thread() {
			public void run() {
				while (true) {
					loadingBar.setProgress(Move.moveProgressCounter);
				}

			}
		};
		progressThread.start();

		Thread closeThread = new Thread() {
			public void run() {
				Stage stage = (Stage) yesButton.getScene().getWindow();

				stage.setOnCloseRequest((WindowEvent event1) -> {
					moverThread.stop();
					progressThread.stop();
					System.out.println("closethread killed");
				});

				try {
					sleep(800);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		};
		closeThread.start();

	}

	@FXML
	void noAction(ActionEvent event) {
		System.out.println("abort mission");
		Stage stage = (Stage) noButton.getScene().getWindow();
		stage.close();
	}
}
