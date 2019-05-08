package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private List<Point> fillCells = new ArrayList<>();

    @FXML
    public Canvas gridCanvas;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField widthTxt;

    @FXML
    private TextField heightTxt;

    @FXML
    public Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll(
                "30",
                "60",
                "90",
                "120"
        );
    }

    protected void drawGrid() {
        GraphicsContext g = gridCanvas.getGraphicsContext2D();
        g.clearRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
        g.setFill(Color.rgb(102, 0, 51));

        for (Point fillCell : fillCells) {
            int cellX = 10 + (fillCell.x * 10);
            int cellY = 10 + (fillCell.y * 10);
            g.fillRect(cellX, cellY, 10, 10);
        }

        g.strokeRect(10, 10, gridCanvas.getWidth() - 20, gridCanvas.getHeight() - 20);

        for (int i = 20; i <= gridCanvas.getWidth() - 20; i += 10) {
            g.strokeLine(i, 10, i, 490);
        }

        for (int i = 20; i <= gridCanvas.getHeight() - 20; i += 10) {
            g.strokeLine(10, i, 790, i);
        }
    }

    public void fillCellFunc(List<Point> filledCells) {
        if (filledCells == null)
            filledCells.add(new Point(0, 0));
        else
            fillCells = filledCells;
    }

    public boolean[] r30() {
        boolean[] r = {false, false, false, true, true, true, true, false};
        return r;
    }

    public static boolean[] r60() {
        boolean[] r = {false, false, true, true, true, true, false, false};
        return r;
    }

    public boolean[] r90() {
        boolean[] r = {false, true, false, true, true, false, true, false};
        return r;
    }

    public boolean[] r120() {
        boolean[] r = {false, true, true, true, true, false, false, false};
        return r;
    }

    public void clickFunction(javafx.event.ActionEvent actionEvent) {
        if (((Integer.parseInt(widthTxt.getText()) * 10) > gridCanvas.getHeight()) || ((Integer.parseInt(heightTxt.getText()) * 10) > gridCanvas.getWidth())) {
            GraphicsContext g = gridCanvas.getGraphicsContext2D();
            g.clearRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
            label.setText("Błędne dane!");
        } else {
            label.setText("");
            CA1D c = new CA1D(Integer.parseInt(widthTxt.getText()), Integer.parseInt(heightTxt.getText()));
            if(comboBox.getValue()=="30")
                c.setRule(r30());
            if(comboBox.getValue()=="60")
                c.setRule(r60());
            if(comboBox.getValue()=="90")
                c.setRule(r90());
            if(comboBox.getValue()=="120")
                c.setRule(r120());
            fillCellFunc(c.compute());
            GraphicsContext g = gridCanvas.getGraphicsContext2D();
            g.clearRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());
            drawGrid();
        }
    }
}
