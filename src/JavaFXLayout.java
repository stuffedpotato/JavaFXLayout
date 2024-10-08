import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;

/**
 * Program Name: JavaFXLayout;
 * Student Name: Piyusha Satija;
 * Student ID: 200001855;
 * Date: Mar 11, 2024;
 * Course: CPSC 1181-003;
 * Compiler: IntelliJ IDEA 2023.2.1 OpenJDK
 */

public class JavaFXLayout extends Application
{
    private Group rainbow, tree, smile;
    private CheckBox rainbowCB, smileCB;
    private RadioButton zeroDeg, ninetyDeg, oneEightyDeg, twoSeventyDeg;
    private Button updateText, close;
    private Text display;
    private TextField caption;

    /**
     * The main method launches args for the Application class.
     * @param args
     */

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * This is the overridden start method. It sets the panes, adds to the scene which is then added to the stage. The stage is then displayed.
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     * Applications may create other stages, if needed, but they will not be primary stages.
     * Here we have a background (grass), a tree, a smiley and a rainbow. User can choose to show/hide both the smiley and the rainbow.
     * User can also choose to rotate the tree using given rotation options.
     * Lastly, user can add text to the stage.
     * User can close the window using the red window button or by pressing the <code>close</code> button.
     */

    @Override
    public void start(Stage primaryStage)
    {
        Text background, captionText;
        BorderPane root = new BorderPane();
        Pane scenery = new Pane();
        VBox radioButtons, allControls;
        HBox checkboxes, bottom;
        Rectangle clip;

        //SCENERY

        tree = new Group();
        createBackground(tree, scenery);

        rainbow = new Group();
        createRainbow(rainbow);
        scenery.getChildren().addAll(rainbow);
        rainbow.setVisible(false);

        smile = new Group();
        createSmile(smile);
        scenery.getChildren().addAll(smile);
        smile.setVisible(false);

        //CHECKBOXES

        background = new Text("Background");
        rainbowCB = new CheckBox("Rainbow");
        smileCB = new CheckBox("Smile");
        checkboxes = new HBox(10, rainbowCB, smileCB);

        BackgroundHandler CBEvent = new BackgroundHandler();
        rainbowCB.setOnAction(CBEvent);
        smileCB.setOnAction(CBEvent);

        //RADIOBUTTONS

        zeroDeg = new RadioButton("0 Degrees");
        ninetyDeg = new RadioButton("90 Degrees");
        oneEightyDeg = new RadioButton("180 Degrees");
        twoSeventyDeg = new RadioButton("270 Degrees");

        ToggleGroup rotation = new ToggleGroup();
        zeroDeg.setToggleGroup(rotation);
        ninetyDeg.setToggleGroup(rotation);
        oneEightyDeg.setToggleGroup(rotation);
        twoSeventyDeg.setToggleGroup(rotation);
        rotation.selectToggle(zeroDeg);
        radioButtons = new VBox(10, zeroDeg, ninetyDeg, oneEightyDeg, twoSeventyDeg);
        radioButtons.setPadding(new Insets(15, 0, 15, 0));

        RotationHandler RBEvent = new RotationHandler();
        zeroDeg.setOnAction(RBEvent);
        ninetyDeg.setOnAction(RBEvent);
        oneEightyDeg.setOnAction(RBEvent);
        twoSeventyDeg.setOnAction(RBEvent);

        //TEXTBOX & CAPTION

        captionText = new Text("Enter your text below:");
        caption = new TextField();
        updateText = new Button("Update text");
        display = new Text(5, 460, "");
        Font f = Font.font("Calibri", 40);
        display.setFont(f);
        scenery.getChildren().add(display);

        updateText.setOnAction(new TextButtonHandler());

        //CLOSE

        close = new Button("Close");
        bottom = new HBox(close);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        root.setBottom(bottom);

        /**
         * This event handler class handles the events generated by pressing the <code>close</code> button.
         */

        class CloseButtonHandler implements EventHandler<ActionEvent>
        {
            /**
             * Overriding this method to handle the close button: closes the window/stage.
             * @param e the event which occurred pressing the <code>close</code> button.
             */

            public void handle(ActionEvent e)
            {
                if (e.getSource() == close)
                {
                    primaryStage.close();
                }
            }
        }

        close.setOnAction(new CloseButtonHandler());

        //CLIPPING

        scenery.setPrefWidth(500);
        scenery.setPrefHeight(470);
        clip = new Rectangle(0, 0, 500, 470);
        scenery.setClip(clip);

        //SETTING UP

        root.setCenter(scenery);
        allControls = new VBox(10, background, checkboxes, radioButtons, captionText, caption, updateText);
        VBox.setMargin(background, new Insets(0, 0, 0, 32));
        VBox.setMargin(updateText, new Insets(0, 0, 0, 32));
        allControls.setPadding(new Insets(20, 0, 0, 15));
        root.setLeft(allControls);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setResizable(false);
        primaryStage.setTitle("JavaFX Layout & Event Handling");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method creates a rainbow using ellipses.
     * @param rainbow is the group so that all the ellipses of different colors can be added to a group and manipulated easily.
     */

    public static void createRainbow(Group rainbow)
    {
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.DARKGREEN, Color.DARKBLUE, Color.INDIGO, Color.DARKVIOLET};
        int y = 340;

        for (int i = 0; i < 7; i++)
        {
            Ellipse temp = new Ellipse(235, y, 400, 310);
            temp.setStroke(colors[i]);
            temp.setStrokeWidth(7);
            temp.setFill(Color.TRANSPARENT);
            rainbow.getChildren().add(temp);
            y = y + 7;
        }
    }

    /**
     * This method sets up a background (grass) and adds a tree to the scenery.
     * @param tree is the group so that all the components of the tree can be manipulated together easily.
     * @param scenery is the pane that is passed to add the background and tree to the pane.
     */

    public static void createBackground(Group tree, Pane scenery)
    {
        Rectangle trunk, ground;
        Ellipse leaves;

        ground = new Rectangle(0, 400, 600, 100);
        ground.setFill(Color.DARKGREEN);

        trunk = new Rectangle(90, 320, 20, 100);
        trunk.setFill(Color.SADDLEBROWN);

        leaves = new Ellipse(100, 320, 40, 60);
        leaves.setFill(Color.rgb(30, 120, 80));

        tree.getChildren().addAll(trunk, leaves);

        scenery.getChildren().addAll(ground, tree);
    }

    /**
     * This method creates a smiley face.
     * @param smile is the group so that all components of the face can be manipulated together with ease.
     */

    public static void createSmile(Group smile)
    {
        smile.getChildren().add(new Circle(260, 220, 80, Color.YELLOW));
        smile.getChildren().add(new Arc(260, 220, 60, 60, 180, 180));
        smile.getChildren().add(new Ellipse(220, 180, 10, 15));
        smile.getChildren().add(new Ellipse(300, 180, 10, 15));
    }

    /**
     * This event handler class handles the events generated by checking the background checkboxes.
     */

    private class BackgroundHandler implements EventHandler<ActionEvent>
    {
        /**
         * Overriding this method to handle both checkboxes: show/hide rainbow, show/hide smile.
         * @param e the event which occurred on checking the <code>rainbowCB</code> and/or <code>smileCB</code>.
         */

        @Override
        public void handle(ActionEvent e)
        {
            if (rainbowCB.isSelected())
            {
                rainbow.setVisible(true);
            }
            else
            {
                rainbow.setVisible(false);
            }

            if (smileCB.isSelected())
            {
                smile.setVisible(true);
            }
            else
            {
                smile.setVisible(false);
            }
        }
    }

    /**
     * This event handler class handles the events generated by selecting a radiobox for rotation.
     */

    private class RotationHandler implements EventHandler<ActionEvent>
    {
        /**
         * Overriding this method to handle radioboxes: rotate 0, 90, 180 or 270 degrees (implements on the tree).
         * @param e the event which occurred upon selecting a radiobox in the toggle group <code>rotation</code>.
         */

        public void handle(ActionEvent e)
        {
            if (zeroDeg.isSelected())
            {
                tree.setRotate(0);
            }
            if (ninetyDeg.isSelected())
            {
                tree.setRotate(90);
            }
            if (oneEightyDeg.isSelected())
            {
                tree.setRotate(180);
            }
            if (twoSeventyDeg.isSelected())
            {
                tree.setRotate(270);
            }
        }
    }

    /**
     * This event handler class handles the events generated by pressing the button that updates text on the screen.
     */

    private class TextButtonHandler implements EventHandler<ActionEvent>
    {
        /**
         * Overriding this method to handle the <code>updateText</code> button: displays entered text on screen.
         * @param e the event which occurred upon pressing the <code>updateText</code> button.
         * This method also accesses the text entered by the user in the <code>caption</code> text field.
         */

        public void handle(ActionEvent e)
        {
            display.setText(caption.getText());
        }
    }
}

