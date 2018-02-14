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
	// TODO make that you can only click only this window
	@FXML
	private Label moveInfos;
	@FXML
	private Button yesButton;
	@FXML
	private Button noButton;
	@FXML
	static ProgressBar loadingBar;
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
		// ConfirmController.loadingBar.setProgress(0.5); //test
		// TODO make progressbar work
		Thread loadingBarThread = new Thread() {
			public void run() {

				int test = 0;
				for (int testo = 0; testo < Move.demoAmountForConfirm; testo++) {

					System.out.println(test);
					test = Move.moveCounter;
					System.out.println(test);
					//TODO MATHEMATIK immo anzahl der demos = setwert aber, Anzahl in Prozent und dann auf set also 1 ist 100
					Move.demoMover(MoveController.selectedCsgoDirectory.toString(),
							MoveController.selectedTargetDirectory.toString());
					loadingBar.setProgress(test);
					

					try {
						sleep(800);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
		};

		loadingBarThread.start();

	}

	@FXML
	void noAction(ActionEvent event) {
		System.out.println("abort mission");
		Stage stage = (Stage) noButton.getScene().getWindow();
		stage.close();
	}
}
