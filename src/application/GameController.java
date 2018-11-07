package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import minimax.GomokuMinimax;
import minimax.Move;

public class GameController {
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private GridPane gridPane;

	public static Button[][] squares;

	/**
	 * anchorpane bc= style="-fx-background-image: url('bc2.jpg');" gridpane bc =
	 * style="-fx-background-image: url('bc1.jpg');"
	 */
	int size = 10;
	public int[][] board = new int[size][size];
	public static Thread thread;
	public static int threadValue = 1;
	Thread t;

	@FXML
	void initialize() {
		assert gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'GomokuBoard.fxml'.";
		squares = new Button[size][size];
		initializeBoard();
		ButtonHandler.board = board;
		anchorPane.setStyle("-fx-background-image: url('bc1.jpg');");
		ButtonHandler buttonhandler = new ButtonHandler();
		ButtonHandler.img1 = new Image("rp1.png");
		ButtonHandler.img2 = new Image("rp2.png");
		ImageView iv2 = new ImageView(new Image("rp2.png"));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// System.out.println(i+j);
				squares[i][j] = new Button();
				squares[i][j].setPrefSize(70, 70);
				squares[i][j].setStyle("-fx-background-color: transparent;");
				squares[i][j].setId(j + "" + i + "");
				gridPane.add(squares[i][j], i, j);

				squares[i][j].setOnAction(buttonhandler);

			}

		}

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (Main.state) {
					// System.out.println("dd");
					if (Main.computerMove) {
						gridPane.setDisable(true);
					} else {
						gridPane.setDisable(false);
					}
				}

			}
		});
		t.start();
	}

	private void initializeBoard() {

		for (int i = 0; i < size; i++) {
			board[i] = new int[size];
			for (int j = 0; j < size; j++) {
				board[i][j] = 0;
			}
		}

	}
}
