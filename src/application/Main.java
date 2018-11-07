package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import minimax.GomokuMinimax;
import minimax.Move;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {
	private Thread thread;
	public static boolean state = true;
	public static volatile boolean computerMove = false;
	
	
	
	@Override
	public void start(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("GomokuBoard.fxml"));
			Scene sc = new Scene(root);
			//primaryStage.getStyle(StageStyle.DECORATED);
			primaryStage.setTitle("Gomoku");
			primaryStage.setScene(sc);
			primaryStage.setResizable(false);
			primaryStage.show();
			
					
			
			
			thread = new Thread(new Runnable() {
				public void run() {
					try {
						while(state) {
							if(computerMove) {
								 Move aiMove =GomokuMinimax.getAIMove();
								 GomokuMinimax.board[aiMove.row][aiMove.col] = 2;
								 String id = aiMove.row+""+aiMove.col; System.out.println(id);
								 ButtonHandler.moveTracker.put(id, "o");
								 
								 Platform.runLater(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										ImageView iv2 = new ImageView(new Image("rp2.png"));
										GameController.squares[aiMove.col][aiMove.row].setGraphic(iv2);
										GameController.squares[aiMove.col][aiMove.row].setDisable(true);
										GameController.squares[aiMove.col][aiMove.row].setStyle("-fx-opacity: 1;-fx-background-color: transparent;");
										
									
									}
								}
								 );
								 
								
								 computerMove = false;
							}
							//System.out.println("l");
						}
						
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			});
			
			thread.start();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent arg0) {
					// TODO Auto-generated method stub
					Platform.exit();
					System.exit(0);
				}
			});
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
