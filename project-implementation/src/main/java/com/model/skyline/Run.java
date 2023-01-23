package com.model.skyline;

import com.entity.Instance;
import com.entity.Item;
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

/**
 * @Description：The main class from which the program runs
 */
public class Run extends javafx.application.Application {

    private int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

       // Data address
        String path = "src/main/java/com/data/c/c51.txt";
        //Get the instance object according to the txt file
        Instance instance = new ReadDataUtil().getInstance(path);
       // Record the algorithm start time
        long startTime = System.currentTimeMillis();

//        //1：improved skyline
//        // Instantiate the skyline object
//        Item[] items = instance.getItemList().toArray(new Item[0]);
//        // Sort in descending order by area
//        Arrays.sort(items, (o1, o2) -> {
//            // Since it is descending, add a negative sign
//            return -Double.compare(o1.getW() * o1.getH(), o2.getW() * o2.getH());
//        });
//        SkyLinePacking skyLinePacking = new SkyLinePacking(instance.isRotateEnable(), instance.getW(), instance.getH(), items);


        //2：skyline
        //Instantiate the skyline object
        SkyLinePacking skyLinePacking = new SkyLinePacking(instance.isRotateEnable(), instance.getW(), instance.getH(), instance.getItemList().toArray(new Item[0]));
//        //Call the skyline heuristic to solve
        Solution solution = skyLinePacking.packing();


        //Output relevant information
        System.out.println("solved time:" + (System.currentTimeMillis() - startTime) / 1000.0 + "s");
        System.out.println("Rectangle items co-placed：" + solution.getPlaceItemList().size() + "pcs");
        System.out.println("The optimal space utilization is :" + solution.getRate());
        // Output paint data
        String[] strings1 = new String[solution.getPlaceItemList().size()];
        String[] strings2 = new String[solution.getPlaceItemList().size()];
        for (int i = 0; i < solution.getPlaceItemList().size(); i++) {
            PlaceItem placeItem = solution.getPlaceItemList().get(i);
            strings1[i] = "{x:" + placeItem.getX() + ",y:" + placeItem.getY() + ",l:" + placeItem.getH() + ",w:" + placeItem.getW() + "}";
            strings2[i] = placeItem.isRotate() ? "1" : "0";
        }
//        System.out.println("data:" + Arrays.toString(strings1) + ",");
//        System.out.println("isRotate:" + Arrays.toString(strings2) + ",");

       // --------------------------------- Paint related code ---------------------------------------------
        AnchorPane pane = new AnchorPane();
        Canvas canvas = new Canvas(instance.getW(), instance.getH());
        pane.getChildren().add(canvas);
        canvas.relocate(100, 100);

        canvas = draw(canvas, 0, 0, instance.getW(), instance.getH(), true);

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
                    alert.setContentText("There are no more rectangle items to place！");
                    alert.showAndWait();
                }
            }
        });

        //
        pane.getChildren().add(nextButton);
        primaryStage.setTitle("2D rectangular packing problem visualization");
        primaryStage.setScene(new Scene(pane, 1000, 1000, Color.BLUE));
        primaryStage.show();
    }

    private Canvas draw(Canvas canvas, double x, double y, double l, double w, boolean isBound) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, l, w);

        if (!isBound) {
            gc.setFill(new Color(new Random().nextDouble(), new Random().nextDouble(), new Random().nextDouble(), new Random().nextDouble()));
        }else{
            gc.setFill(new Color(1,1,1,1));
        }
        gc.fillRect(x, y, l, w);
        return canvas;
    }

    public static void main(String[] args) {
        launch(args);
    }

}


