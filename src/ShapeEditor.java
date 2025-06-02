import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ShapeEditor extends Application {

	private static final double APP_WIDTH = 1200;
	private static final double APP_HEIGHT = 1000;
	private static final double CANVAS_WIDTH = 1200;
	private static final double CANVAS_HEIGHT = 1000;

	private BorderPane    mainPane;
	private ShapeCanvas   canvas;
	private HBox          controlPanel;
	private Button        bnClear;		  
	private CheckBox      cbFilled;
	private RadioButton   rbLine, rbOval, rbRect;
	private RadioButton   rbDelete, rbMove, rbCopy, rbGroup;
	private Button        btnUndo, btnRedo;
	private LineHandler   lineHandler;
	private OvalHandler   ovalHandler;
	private RectHandler   rectHandler;
	private DeleteHandler deleteHandler;
	private MoveHandler   moveHandler;
	private CopyHandler   copyHandler;
	private GroupHandler  groupHandler;

	private ColorPicker   color;
	private FileChooser   fcOpenSave;
	private MenuBar       menuBar;
	private Menu     	  menuFile, menuAbout;
	private MenuItem	  miLoad, miSave;
	private MenuItem      miSaveB, miLoadB;

	private Color currentColor = Color.BLACK;

	/**
	 * Start method to initialize and show the JavaFX application
	 */
	@Override
	public void start(Stage stage) {

		mainPane = new BorderPane();

		setupCanvas();
		setupControls();
		setupMenu();

		HBox topPane = new HBox();
		topPane.getChildren().addAll(menuBar, controlPanel);

		mainPane.setTop(topPane);

		Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("Shape Editor");
		stage.show();
	}

	/**
     * Method to set up the menu bar
     */
	private void setupMenu() {

		menuBar = new MenuBar();

		menuFile = new Menu("File");
		menuAbout = new Menu("About");

		miLoad = new MenuItem("Load");
		miSave = new MenuItem("Save");
		miLoadB = new MenuItem("Load Binary");
		miSaveB = new MenuItem("Save Binary");

		fcOpenSave = new FileChooser();

		miSave.setOnAction(e->{

			fcOpenSave.setTitle("Save Drawing");

			File newFile = fcOpenSave.showSaveDialog(null);

			if (newFile != null) {

				canvas.toTextFile(newFile);
			}
		});
		
		miLoad.setOnAction(e->{

			fcOpenSave.setTitle("Load Drawing");

			File newFile = fcOpenSave.showOpenDialog(null);

			if (newFile != null) {

				canvas.fromTextFile(newFile);
			}
		});


		miSaveB.setOnAction(e ->{

			fcOpenSave.setTitle("Save Drawing");
			File newFile = fcOpenSave.showSaveDialog(null);

			if (newFile != null) {

				canvas.toBinaryFile(newFile);
			}
		});

		miLoadB.setOnAction(e->{

			fcOpenSave.setTitle("Load Drawing");
			File newFile = fcOpenSave.showOpenDialog(null);

			if (newFile != null) {

				canvas.fromBinaryFile(newFile);
			}
		});

		menuBar.getMenus().addAll(menuFile, menuAbout);

		menuFile.getItems().addAll(miLoad, miSave, miSaveB, miLoadB);

	}


	/**
	 * Setup method for initializing the canvas
	 */
	public void setupCanvas() {

		canvas = new ShapeCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);

		mainPane.setCenter(canvas);
	}

	/**
	 * Setup method for initializing the control panel with buttons and checkbox
	 */
	public void setupControls() {

		controlPanel = new HBox(15);

		bnClear = new Button("Clear");
		cbFilled = new CheckBox("Filled");
		btnUndo = new Button ("Undo");
		btnRedo = new Button("Redo");
		

		ToggleGroup shapeGroup = new ToggleGroup();
		rbLine = new RadioButton("Line");
		rbOval = new RadioButton("Oval");
		rbRect = new RadioButton("Rectangle");
		rbDelete = new RadioButton("Delete");
		rbMove = new RadioButton("Move");
		rbCopy = new RadioButton("Copy");
		rbGroup = new RadioButton("Group");

		rbLine.setToggleGroup(shapeGroup);
		rbOval.setToggleGroup(shapeGroup);
		rbRect.setToggleGroup(shapeGroup);
		rbDelete.setToggleGroup(shapeGroup);
		rbMove.setToggleGroup(shapeGroup);
		rbCopy.setToggleGroup(shapeGroup);
		rbGroup.setToggleGroup(shapeGroup);

		color = new ColorPicker(currentColor);

		color.setOnAction(e->{

			currentColor = color.getValue();
			canvas.setcurrColor(currentColor);
		});

		controlPanel.getChildren().addAll(rbLine, rbOval, rbRect, cbFilled, color, bnClear, rbDelete, rbMove, rbCopy, rbGroup, btnUndo, btnRedo);


		lineHandler = new LineHandler(canvas);
		ovalHandler = new OvalHandler(canvas);
		rectHandler = new RectHandler(canvas);
		deleteHandler = new DeleteHandler(canvas);
		moveHandler = new MoveHandler(canvas);
		copyHandler = new CopyHandler(canvas);
		groupHandler = new GroupHandler(canvas);
		

		rbLine.setSelected(true);
		canvas.replaceMouseHandler(lineHandler);
		
		btnRedo.setOnAction(e->{
			
			canvas.redo();
		});
		
		btnUndo.setOnAction(e->{
			
			canvas.undo();
		});
		
		rbGroup.setOnAction(e->{
			
			canvas.replaceMouseHandler(groupHandler);
		});
		rbCopy.setOnAction(e->{
			
			canvas.replaceMouseHandler(copyHandler);
		});
		rbMove.setOnAction(e->{
			
			canvas.replaceMouseHandler(moveHandler);
		});

		rbDelete.setOnAction( e->{
			
			canvas.replaceMouseHandler(deleteHandler);
		});
		rbLine.setOnAction(e->{

			canvas.replaceMouseHandler(lineHandler);
		});

		rbOval.setOnAction(e->{

			canvas.replaceMouseHandler(ovalHandler);
		});

		rbRect.setOnAction(e->{

			canvas.replaceMouseHandler(rectHandler);
		});


		cbFilled.setOnAction(e->{

			if (cbFilled.isSelected()) {

				canvas.setcurrrFilled(true);
			}
			else {

				canvas.setcurrrFilled(false);
			}

			canvas.paint();
		});

		cbFilled.setSelected(false);

		bnClear.setOnAction( e-> {

			canvas.clear();
		});

	}
	

	/**
	 * The main method to launch the JavaFX application
	 *
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {

		launch(args);
	}
}
