package com.model.Others.sagh;

import com.entity.Instance;
import com.entity.PlaceItem;
import com.entity.Solution;
import com.util.ReadDataUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

public class Run extends javafx.application.Application {

    private int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Data address
        String path = "src/main/java/com/data/c/c41.txt";
        //Get the instance object according to the txt file
        Instance instance = new ReadDataUtil().getInstance(path);
        //Record the algorithm start time
        long startTime = System.currentTimeMillis();
        //Instantiate the adaptive genetic algorithm object
        SAGH SAGH = new SAGH(500, 200, 1, 10, new double[]{0.2, 1.0}, new double[]{0.92, 0.98}, instance, null);
       // The adaptive genetic algorithm object is called to solve
        Solution solution = SAGH.solve();



        // Output relevant information
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("times:" + (System.currentTimeMillis() - startTime) / 1000.0 + " s");
        System.out.println("Rectangular co-placed：" + solution.getPlaceItemList().size() + "pcs");
        System.out.println("The optimal utilization is :" + solution.getRate());
        // Output paint data
        String[] strings1 = new String[solution.getPlaceItemList().size()];
        String[] strings2 = new String[solution.getPlaceItemList().size()];
        for (int i = 0; i < solution.getPlaceItemList().size(); i++) {
            PlaceItem placeItem = solution.getPlaceItemList().get(i);
            strings1[i] = "{x:" + placeItem.getX() + ",y:" + placeItem.getY() + ",h:" + placeItem.getH() + ",w:" + placeItem.getW() + "}";
            strings2[i] = placeItem.isRotate() ? "1" : "0";
        }
      System.out.println("data:" + Arrays.toString(strings1) + ",");
//        System.out.println("isRotate:" + Arrays.toString(strings2) + ",");

        // --------------------------------- Paint related code---------------------------------------------
        AnchorPane pane = new AnchorPane();
        Canvas canvas = new Canvas(instance.getW(), instance.getH());
        pane.getChildren().add(canvas);
        canvas.relocate(100, 100);
        // Draw the outermost rectangle
        canvas = draw(canvas, 0, 0, instance.getW(), instance.getH(), true);
        // Add a button
        Button nextButton = new Button("Next +1");
        Canvas finalCanvas = canvas;
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    PlaceItem placeItem = solution.getPlaceItemList().get(counter);
                    draw(finalCanvas, placeItem.getX(), placeItem.getY(), placeItem.getW(), placeItem.getH(), false);
                    counter++;
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("There are no more rectangular items to place!");
                    alert.showAndWait();
                }
            }
        });
        //
        pane.getChildren().add(nextButton);
        primaryStage.setTitle("2D rectangular packing problem visualization");
        primaryStage.setScene(new Scene(pane, 400, 400, Color.AQUA));
        primaryStage.show();
    }

    private Canvas draw(Canvas canvas, double x, double y, double l, double w, boolean isBound) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // frame
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, l, w);
        // padding
        if (!isBound) {
            gc.setFill(new Color(new Random().nextDouble(), new Random().nextDouble(), new Random().nextDouble(), new Random().nextDouble()));
        } else {
            gc.setFill(new Color(1, 1, 1, 1));
        }
        gc.fillRect(x, y, l, w);
        return canvas;
    }

    public static void main(String[] args) {
        launch(args);
    }

}

