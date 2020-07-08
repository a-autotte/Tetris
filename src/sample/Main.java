package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {


    private final static int arrayWidth = 500;
    private final static int arrayHeigth = 500;
    private static int snakeSpeed = 5;
    private static ArrayList<Tail> tailOfTheSnake = new ArrayList<>();
    private static SnakeDirection snakeDir = SnakeDirection.Left;
    private static boolean IsGameOver = false;

    public enum SnakeDirection
    {
        Top, Right, Bottom, Left
    }

    public static class Tail
    {
        int x;
        int y;

        public Tail(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }




    public void start(Stage primaryStage) {
        try {
            VBox gameArray = new VBox();
            Canvas c = new Canvas(arrayWidth, arrayHeigth);
            GraphicsContext gc = c.getGraphicsContext2D();
            gameArray.getChildren().add(c);


            new AnimationTimer() {

                long lastSnaketick = 0;

                public void handle(long runningtick)
                {
                    if (lastSnaketick == 0)
                    {
                        lastSnaketick = runningtick;
                        tick(gc);
                        return;
                    }

                    if (runningtick - lastSnaketick > 1000000000 / snakeSpeed)
                    {
                        lastSnaketick = runningtick;
                        tick(gc);

                    }
                }

            }.start();


            Scene gameScene = new Scene(gameArray, arrayWidth, arrayHeigth);

            gameScene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.RIGHT)
                {
                    snakeDir = SnakeDirection.Right;
                }

                if (key.getCode() == KeyCode.LEFT)
                {
                    snakeDir = SnakeDirection.Left;
                }

                if (key.getCode() == KeyCode.UP)
                {
                    snakeDir = SnakeDirection.Top;
                }

                if (key.getCode() == KeyCode.DOWN)
                {
                    snakeDir = SnakeDirection.Bottom;
                }
            });

            tailOfTheSnake.add(new Tail(10, 10));
            tailOfTheSnake.add(new Tail(10, 10));
            tailOfTheSnake.add(new Tail(10, 10));
            gameScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(gameScene);
            primaryStage.setTitle("My Snake Game");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tick(GraphicsContext gc)
    {

        for (int i = tailOfTheSnake.size() - 1; i >= 1; i--)
        {
            tailOfTheSnake.get(i).x = tailOfTheSnake.get(i - 1).x;
            tailOfTheSnake.get(i).y = tailOfTheSnake.get(i - 1).y;
        }


        switch(snakeDir)
        {
            case Right:
                    tailOfTheSnake.get(0).x++;

                    if (tailOfTheSnake.get(0).x >= arrayWidth)
                    {
                        IsGameOver = true;
                    }
                break;

            case Left:
                tailOfTheSnake.get(0).x--;

                if (tailOfTheSnake.get(0).x <= 0)
                {
                    IsGameOver = true;
                }
                break;

            case Top:
                tailOfTheSnake.get(0).y++;

                if (tailOfTheSnake.get(0).y >= arrayHeigth)
                {
                    IsGameOver = true;
                }
                break;

            case Bottom:
                tailOfTheSnake.get(0).y--;

                if (tailOfTheSnake.get(0).y <= 0)
                {
                    IsGameOver = true;
                }
                break;
        }

        gc.setFill(Color.MAGENTA);
        gc.fillRect(0, 0, arrayWidth, arrayHeigth);

        for (Tail tail : tailOfTheSnake)
        {
            gc.setFill(Color.BLUE);
            gc.fillRect(25, 25, 25 - 1, 25 - 1);
            gc.setFill(Color.RED);
            gc.fillRect(25, 25, 25 - 2, 25 - 2);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
