package View;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Controller.Controller;
import javafx.scene.text.Text;
import javafx.xml.soap.Text;
import javafx.geometry.Pos;

import Game.RangeBoard;
public class AppView extends Application implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 13; i++)	{
			for(int j = 0; j < 13;j++)	{
				if(event.getSource() == hands[i][j])	{
					toggleHand(i,j);
					return;
				}
			}
		}
		for(int i = 0; i < 14; i++)	{
			if(event.getSource() == dataText.get(i)) {
				toggleData(i);
				
			}
		}
			
	}
	DecimalFormat format = new DecimalFormat("0.00");
	Button hands[][];
	Button deadCards[][];
	ArrayList<Button> dataText = new ArrayList<Button>();
	Controller controller = new Controller();
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception	{
		primaryStage.setTitle("FLOPATRON");
		hands = new Button[13][13];
		deadCards = new Button[4][13];
		GridPane rangeBoardLayout = new GridPane();
		VBox dataLayout = new VBox();
		GridPane deadCardsLayout = new GridPane();
		dataLayout.setMinWidth(245);
		//dataLayout.setStyle("-fx-background-color: #444444;");
		setUpRangeBoard(hands,rangeBoardLayout);
		setUpData(dataLayout);
		setUpDeadCards(deadCards,deadCardsLayout);
		HBox AppLayout = new HBox();
		AppLayout.setStyle("-fx-background-color: #444444;");
		AppLayout.getChildren().add(dataLayout);
		AppLayout.getChildren().add(rangeBoardLayout);
		AppLayout.getChildren().add(deadCardsLayout);
		Scene scene = new Scene(AppLayout,1000,500);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void setUpRangeBoard(Button hands[][],GridPane layout)	{
		for(int i = 0; i < 13; i++)	{
			for(int j = 0; j < 13;j++)	{
				
				hands[i][j] = new Button();
				Button currentHand = hands[i][j];
				currentHand.setMinWidth(40);
				currentHand.setMinHeight(35);
				currentHand.setOnAction(this);
				
				if(i > j) {
					//unsuited hands
					currentHand.setText((getCardNameFromNumber(i) + getCardNameFromNumber(j) + "s"));
					currentHand.setStyle("-fx-background-color: #ff9999;-fx-font: 13px Consolas ");
				}else if(i == j)	{
					//pocket pairs
					currentHand.setText(getCardNameFromNumber(j) + getCardNameFromNumber(i));
					currentHand.setStyle("-fx-background-color: #aaaaaa;-fx-font: 13px Consolas");
				}else {
					//suited hands
					currentHand.setText((getCardNameFromNumber(j) + getCardNameFromNumber(i) + "u"));
					currentHand.setStyle("-fx-background-color: #66dd66;-fx-font: 13px Consolas");
				}
				
				layout.getChildren().add(currentHand);	
				
				GridPane.setRowIndex(currentHand, 12 - i);
				GridPane.setColumnIndex(currentHand, 12 - j);
			}
		}
		layout.setMinWidth(700);
		layout.setAlignment(Pos.CENTER);
		layout.setHgap(3);
		layout.setVgap(3);
		
	}
	
	public void setUpData(VBox data) {
		ArrayList<String> percentageData = controller.getData();
		
		for(int i = 0; i < percentageData.size() + 1; i++) {
			if(i != percentageData.size()) {
				dataText.add(new Button(percentageData.get(i)));
				dataText.get(i).setOnAction(this);
				dataText.get(i).setStyle("-fx-font: 15px Consolas; -fx-background-color: #11aa11; ");
				dataText.get(i).setMinHeight(25);
				dataText.get(i).setMinWidth(270);
			}else {
				dataText.add(new Button("Hit Percentage: "));
				dataText.get(i).setMinWidth(270);
				dataText.get(i).setStyle("-fx-font: 18px Consolas; -fx-background-color: #444444");
			}
			
			
			
			dataText.get(i).setTextFill(Color.web("#ffffff", 0.8));
			data.setSpacing(3);
			
			data.getChildren().add(dataText.get(i));
		}
			
		
		data.setAlignment(Pos.CENTER);
		
	}
	public String getCardNameFromNumber(int number)	{
		if(number < 8) {
			return Integer.toString(number + 2);
		}
		switch(number)	{
		case 8:
			return "T";
		case 9:
			return "J";
		case 10:
			return "Q";
		case 11:
			return "K";
		case 12:
			return "A";
		}
		return "ERROR";
	}
	
	public void toggleData(int i)	{
		System.out.println("in toggleData");
		if(controller.getFilter().doesItHit[i] == true)	{
			dataText.get(i).setStyle("-fx-font: 15px Consolas; -fx-background-color: #aa1111");
			controller.getFilter().doesItHit[i]  = false;
		}else {
			dataText.get(i).setStyle("-fx-font: 15px Consolas; -fx-background-color: #11aa11");
			controller.getFilter().doesItHit[i]  = true;
		}
		updateHitPercentage();
	}
	
	public void toggleHand(int x,int y)	{
		if(controller.rangeBoard.getMatrixVal(x, y) == 0)	{
			
			controller.rangeBoard.addValueToMatrix(x + 2, y + 2, 100);
			if(x < y) {
				hands[x][y].setStyle("-fx-background-color: #11aa11;  -fx-text-fill:#ffffff;-fx-font: 13px Consolas ");
			}else if(x == y) {
				hands[x][y].setStyle("-fx-background-color: #333333;  -fx-text-fill:#ffffff;-fx-font: 13px Consolas ");
			}else {
				hands[x][y].setStyle("-fx-background-color: #aa1111;  -fx-text-fill:#ffffff;-fx-font: 13px Consolas ");
			}
			
		}else {
			if(x < y) {
				hands[x][y].setStyle("-fx-background-color: #99ff99;  -fx-text-fill:#000000;-fx-font: 13px Consolas ");
			}else if(x == y) {
				hands[x][y].setStyle("-fx-background-color: #aaaaaa;  -fx-text-fill:#000000;-fx-font: 13px Consolas ");
			}else {
				hands[x][y].setStyle("-fx-background-color: #ff9999;  -fx-text-fill:#000000;-fx-font: 13px Consolas ");
			}
			controller.rangeBoard.addValueToMatrix(x + 2, y + 2, 0);
		}
		
		updateData();
		
	}
	
	public void updateData() {
		
		ArrayList<String> percentageData = controller.getData();
		
		for(int i = 0; i < percentageData.size(); i++) {
			
			dataText.get(i).setText(percentageData.get(i));
			
			
		}
			updateHitPercentage();
	}
	
	public void setUpDeadCards(Button[][] deadCards,GridPane deadCardsLayout)	{
		
		for(int i = 0; i < 4; i++)	{
			for(int j = 0; j < 13;j++)	{
				
				deadCards[i][j] = new Button();
				Button currentCard = deadCards[i][j];
				currentCard.setMinWidth(40);
				currentCard.setMinHeight(35);
				currentCard.setOnAction(this);
				
				
					
					currentCard.setText((getCardNameFromNumber(j) + getSuitFromNumber(i)));
					currentCard.setStyle("-fx-background-color: #777777;-fx-font: 15px Consolas");
				
				
				deadCardsLayout.getChildren().add(currentCard);	
				
				GridPane.setRowIndex(currentCard,12 - j);
				GridPane.setColumnIndex(currentCard, i);
				deadCardsLayout.setAlignment(Pos.CENTER_RIGHT);
			}
		}
		deadCardsLayout.setHgap(3);
		deadCardsLayout.setVgap(3);
		
	}
	
	private void updateHitPercentage()	{
		dataText.get(dataText.size() - 1).setText("Hit Percentage: "+format.format(controller.getHitPercentage()) +"%");
	}
	
	private char getSuitFromNumber(int number)	{
		switch(number) {
		case 0:
			return 'H';
		case 1:
			return 'D';
		case 2:
			return 'C';
		case 3:
			return 'S';
		}
		return ' ';
	}
}
