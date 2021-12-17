package controller;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Javafx combobox default käyttäytymisen muokkaamiseen
 * 
 * @author elyasa
 *
 */
public class StatusListCell extends ListCell<String> {
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (item != null) {
			if (item.equalsIgnoreCase("EN")) {
				ImageView imageView = new ImageView(new Image("file:images/united-states-of-america.png"));
				setGraphic(imageView);
				setText("ENG");
			}
			if (item.equals("FI")) {
				ImageView imageView = new ImageView(new Image("file:images/finland.png"));
				setGraphic(imageView);
				setText("FI");
			}
		}
	}
}
