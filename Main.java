import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Main Function
public class Main extends Application {
	/* MAIN */
	Button button;
	static Stage window;
	static Scene firstscene, login_register, loginScene, registerScene;

	public static void main(String[] args) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FirstLaunchCheck();
		window = primaryStage;
		firstMenu(window);
	}

	private static void firstMenu(Stage window) {
		window.setTitle("Covid-19 Tracking System");
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(12);

		VBox buttonBox = new VBox();
		buttonBox.setSpacing(10.0);

		// Type of User
		Label typeuserLabel = new Label("Type of User:");
		typeuserLabel.setMaxWidth(Double.MAX_VALUE);
		typeuserLabel.setAlignment(Pos.CENTER);
		grid.add(typeuserLabel, 0, 0);

		// Customer Button
		Button customerButton = new Button("Customer");
		customerButton.setOnAction(e -> {
			try {
				menuscreen("customer");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		// Admin Button
		Button adminButton = new Button("Admin");
		adminButton.setOnAction(e -> admin_loginscreen());

		// Shop Button
		Button shopButton = new Button("Shop");
		shopButton.setOnAction(e -> {
			try {
				menuscreen("shop");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(customerButton, adminButton, shopButton);

		grid.setAlignment(Pos.CENTER);
		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 3, 1, 1);
		firstscene = new Scene(grid, 400, 600);
		window.setScene(firstscene);
		window.show();
	}

	// Function for checking if the user first time launching the program
	private static void FirstLaunchCheck() throws IOException {
		File customerinfo = new File("CustomerInfo.csv");
		File custvisithistory = new File("CustomerVisitHistory.csv");
		File shopinfo = new File("shop.csv");

		// Checking if the user running this program first time or not
		if (!customerinfo.exists() && !custvisithistory.exists() && !shopinfo.exists()) {
			popupBox("Warning", "First time Lauch: \n Proceeding to create relevant CSV files.");
		}

		// Checking if all of the relevant files exists or not
		if (customerinfo.exists() && custvisithistory.exists() && shopinfo.exists()) {
			popupBox("All clear", "All relevant CSV files exists in current directory: \n Proceeding the program.");
		}

		// Creating CustomerInfo.csv if the file does not exists and preloading 5
		// customers into the CustomerInfo.csv
		if (customerinfo.createNewFile()) {
			popupBox("Warning", "CustomerInfo.csv is missing");
			ArrayList<Customer> preload_cust = new ArrayList<Customer>();
			BufferedWriter writer = new BufferedWriter(new FileWriter("CustomerInfo.csv", true));
			preload_cust.add(new Customer("ali011", "012345", "Ali bin Abu", "0175551298", "normal"));
			preload_cust.add(new Customer("sitimay012", "012345", "Siti Maysarah", "0125551346", "normal"));
			preload_cust.add(new Customer("azrul013", "012345", "Azrul Mutu", "0155555105", "normal"));
			preload_cust.add(new Customer("shuhail014", "012345", "Shuhail Azri", "0145353574", "normal"));
			preload_cust.add(new Customer("arif015", "012345", "Arif Bakar", "0145575671", "normal"));
			for (int i = 0; i < preload_cust.size(); i++) {
				writer.write(preload_cust.get(i).toCSVString());
				writer.newLine();
			}
			writer.close();
			popupBox("Clear", "CustomerInfo.csv succesfully created.");
		}

		// Creating CustomerVisitHistory.csv if the file does not exists
		if (custvisithistory.createNewFile()) {

			popupBox("Warning", "CustomerVisitHistory.csv is missing");
			popupBox("Clear", "CustomerVisitHistory.csv succesfully created.");
		}

		// Creating shop.csv if the file does not exists and preloading 4 shops into the
		// shop.csv
		if (shopinfo.createNewFile()) {
			ArrayList<Shop> preload_shop = new ArrayList<Shop>();
			BufferedWriter writer = new BufferedWriter(new FileWriter("shop.csv", true));
			preload_shop.add(new Shop("Aeon", "0331015568", "Imran", "normal"));
			preload_shop.add(new Shop("Tesco", "0392820771", "Shanthur", "normal"));
			preload_shop.add(new Shop("Jusco", "0332902193", "YapzhengYew", "normal"));
			preload_shop.add(new Shop("1Utama", "0333235399", "Kuberan", "normal"));
			for (int i = 0; i < preload_shop.size(); i++) {
				writer.write(preload_shop.get(i).toCSVString());
				writer.newLine();
			}
			writer.close();
			popupBox("Warning", "shop.csv is missing");
			popupBox("Clear", "shop.csv succesfully created.");
		}

	}

	// Function to fetch the correct menuscreen according to the user type that is
	// logged in.
	private static void menuscreen(String Valid_UserType) throws IOException {
		// Menu Screen for admins
		if (Valid_UserType.equals("admin")) {
			admin_menuscreen();
		}
		// Menu Screen for customers
		else if (Valid_UserType.equals("customer")) {
			customer_LogRegScreen();
		}
		// Menu for shops
		else if (Valid_UserType.equals("shop")) {
			shop_menuscren();
		}
	}

	/*
	 * ===================================== ADMIN
	 * =====================================
	 */

	// Admin Login Screen
	private static void admin_loginscreen() {
		window.setTitle("Admin Login");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setHgap(12);

		HBox buttonBox = new HBox();
		buttonBox.setSpacing(10.0);

		Label title = new Label("Please enter the masterpassword");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		PasswordField masterPassword = new PasswordField();
		masterPassword.setPromptText("Master Password");
		grid.add(masterPassword, 0, 1);
		masterPassword.setOnAction(c -> {
			if (MasterPassword_validating(masterPassword.getText())) {
				admin_menuscreen();
			} else {
				popupBox("Admin Login Error!", "Invalid Masterpassword\nPlease try again");
			}
		});

		Button proceedButton = new Button("Proceed");
		proceedButton.setOnAction(c -> {
			if (MasterPassword_validating(masterPassword.getText())) {
				admin_menuscreen();
			} else {
				popupBox("Admin Login Error!", "Invalid Masterpassword\nPlease try again");
			}
		});

		Button backButton = new Button("Back");
		backButton.setOnAction(c -> firstMenu(window));

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(proceedButton, backButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 3, 1, 1);
		loginScene = new Scene(grid, 400, 600);
		window.setScene(loginScene);
		window.show();
	}

	// Admin MenuScreen
	private static void admin_menuscreen() {

		window.setTitle("Admin MenuScreen");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		VBox buttonBox = new VBox();
		buttonBox.setSpacing(10.0);

		grid.setVgap(10);
		grid.setHgap(12);

		Label title = new Label("Please select the option below");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		Button custdetailsButton = new Button("View all customer's detials");
		custdetailsButton.setOnAction(c -> {
			try {
				admin_viewcustdetials();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button shopdetailsButton = new Button("View all shop's details");
		shopdetailsButton.setOnAction(c -> {
			try {
				admin_viewshopdetials();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button masterhistoryButton = new Button("View the master visit history");
		masterhistoryButton.setOnAction(c -> {
			try {
				admin_viewmastervisit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button flagcustButton = new Button("Flag a customer");
		flagcustButton.setOnAction(c -> {
			try {
				admin_flagcus();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button generate30visitsButton = new Button("Generata random 30 visit history");
		generate30visitsButton.setOnAction(c -> {
			try {
				generating30randomvisit();
				popupBox("Random Visit Generated!", "30 random visit succesfully generated");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button backButton = new Button("back");
		backButton.setOnAction(c -> firstMenu(window));

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(custdetailsButton, shopdetailsButton, masterhistoryButton, flagcustButton,
				generate30visitsButton, backButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 3, 1, 1);
		Scene adminmenuScene = new Scene(grid, 400, 600);
		window.setScene(adminmenuScene);
		window.show();
	}

	// Admin MenuScreenAction
	// Admin view all customer details action
	@SuppressWarnings("unchecked")
	private static void admin_viewcustdetials() throws IOException {
		window.setTitle("Customer Details");
		ArrayList<Customer> CustInfo = readCustomerInfoFromFile();
		ObservableList<Customer> ListCustInfo = FXCollections.observableArrayList();

		for (int i = 0; i < CustInfo.size(); i++) {
			ListCustInfo.add(new Customer(CustInfo.get(i).getCust_username(), CustInfo.get(i).getCust_password(),
					CustInfo.get(i).getCust_fullname(), CustInfo.get(i).getCust_phonenum(),
					CustInfo.get(i).getCust_status()));
		}

		// Ful Name
		TableColumn<Customer, String> fullnameColumn = new TableColumn<>("Name");
		fullnameColumn.setMinWidth(200);
		fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("Cust_fullname"));

		// Phone Number
		TableColumn<Customer, String> phonenumColumn = new TableColumn<>("Phone");
		phonenumColumn.setMinWidth(200);
		phonenumColumn.setCellValueFactory(new PropertyValueFactory<>("Cust_phonenum"));

		// Status
		TableColumn<Customer, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setMinWidth(200);
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("Cust_status"));

		TableView<Customer> custdetails_Table = new TableView<>();
		custdetails_Table.setItems(ListCustInfo);
		// No
		TableColumn<Customer, Number> indexColumn = new TableColumn<Customer, Number>("No");
		indexColumn.setSortable(false);
		indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>(
				custdetails_Table.getItems().indexOf(column.getValue()) + 1));

		custdetails_Table.getColumns().addAll(indexColumn, fullnameColumn, phonenumColumn, statusColumn);

		Button backButton = new Button("Back");
		backButton.setOnAction(c -> admin_menuscreen());

		VBox box = new VBox();

		GridPane tableGrid = new GridPane();
		GridPane buttonGrid = new GridPane();
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(backButton);
		buttonGrid.add(buttonBox, 0, 0);

		box.getChildren().addAll(custdetails_Table);
		tableGrid.add(box, 0, 0);
		tableGrid.add(buttonBox, 0, 3, 3, 3);
		Scene custdetailsScene = new Scene(tableGrid);
		window.setScene(custdetailsScene);
		window.show();
	}

	// Admin view all customer details action
	@SuppressWarnings("unchecked")
	private static void admin_viewshopdetials() throws IOException {
		window.setTitle("Shop Details");
		ArrayList<Shop> shops = readShopsFromFile();
		ObservableList<Shop> shopList = FXCollections.observableArrayList();

		for (int i = 0; i < shops.size(); i++) {
			shopList.add(new Shop(shops.get(i).getShop_name(), shops.get(i).getShop_phonenumber(),
					shops.get(i).getShop_managername(), shops.get(i).getShop_status()));
		}

		// Shop Name
		TableColumn<Shop, String> shopnameColumn = new TableColumn<>("Name");
		shopnameColumn.setMinWidth(200);
		shopnameColumn.setCellValueFactory(new PropertyValueFactory<>("shop_name"));

		// Phone Number
		TableColumn<Shop, String> phonenumColumn = new TableColumn<>("Phone");
		phonenumColumn.setMinWidth(200);
		phonenumColumn.setCellValueFactory(new PropertyValueFactory<>("shop_phonenumber"));

		// Manager name
		TableColumn<Shop, String> managernameColumn = new TableColumn<>("Manager");
		managernameColumn.setMinWidth(200);
		managernameColumn.setCellValueFactory(new PropertyValueFactory<>("shop_managername"));

		// Status
		TableColumn<Shop, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setMinWidth(200);
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("shop_status"));

		TableView<Shop> shopList_Table = new TableView<>();
		shopList_Table.setItems(shopList);
		// No
		TableColumn<Shop, Number> indexColumn = new TableColumn<Shop, Number>("No");
		indexColumn.setSortable(false);
		indexColumn.setCellValueFactory(
				column -> new ReadOnlyObjectWrapper<Number>(shopList_Table.getItems().indexOf(column.getValue()) + 1));

		shopList_Table.getColumns().addAll(indexColumn, shopnameColumn, phonenumColumn, managernameColumn,
				statusColumn);

		Button backButton = new Button("Back");
		backButton.setOnAction(c -> admin_menuscreen());

		VBox box = new VBox();

		GridPane tableGrid = new GridPane();
		GridPane buttonGrid = new GridPane();
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(backButton);
		buttonGrid.add(buttonBox, 0, 0);

		box.getChildren().addAll(shopList_Table);
		tableGrid.add(box, 0, 0);
		tableGrid.add(buttonBox, 0, 3, 3, 3);
		Scene shopdetailsScene = new Scene(tableGrid);
		window.setScene(shopdetailsScene);
		window.show();
	}

	// Admin view the master visit history
	@SuppressWarnings("unchecked")
	private static void admin_viewmastervisit() throws IOException {
		window.setTitle("Master Visit History");
		ArrayList<CustomerVisitHistory> custvisithistory = readCustomerVisitHistoryFromFile();
		ObservableList<CustomerVisitHistory> custvisithistoryList = FXCollections.observableArrayList();

		for (int i = 0; i < custvisithistory.size(); i++) {
			custvisithistoryList.add(new CustomerVisitHistory(custvisithistory.get(i).getCustomer_name(),
					custvisithistory.get(i).getShop(), custvisithistory.get(i).getvisitdatetime()));
		}

		// Date
		TableColumn<CustomerVisitHistory, String> DateColumn = new TableColumn<>("Date");
		DateColumn.setMinWidth(200);
		DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

		// Time
		TableColumn<CustomerVisitHistory, String> TimeColumn = new TableColumn<>("Time");
		TimeColumn.setMinWidth(200);
		TimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

		// Customer name
		TableColumn<CustomerVisitHistory, String> custnameColumn = new TableColumn<>("Customer");
		custnameColumn.setMinWidth(200);
		custnameColumn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));

		// Shop
		TableColumn<CustomerVisitHistory, String> ShopColumn = new TableColumn<>("Shop");
		ShopColumn.setMinWidth(200);
		ShopColumn.setCellValueFactory(new PropertyValueFactory<>("shop"));

		TableView<CustomerVisitHistory> visithistory_Table = new TableView<>();
		visithistory_Table.setItems(custvisithistoryList);

		// No
		TableColumn<CustomerVisitHistory, Number> indexColumn = new TableColumn<CustomerVisitHistory, Number>("No");
		indexColumn.setSortable(false);
		indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>(
				visithistory_Table.getItems().indexOf(column.getValue()) + 1));

		visithistory_Table.getColumns().addAll(indexColumn, DateColumn, TimeColumn, custnameColumn,ShopColumn);

		Button backButton = new Button("Back");
		backButton.setOnAction(c -> admin_menuscreen());

		VBox box = new VBox();

		GridPane tableGrid = new GridPane();
		GridPane buttonGrid = new GridPane();
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(backButton);
		buttonGrid.add(buttonBox, 0, 0);

		box.getChildren().addAll(visithistory_Table);
		tableGrid.add(box, 0, 0);
		tableGrid.add(buttonBox, 0, 3, 3, 3);
		Scene matervisitScene = new Scene(tableGrid);
		window.setScene(matervisitScene);
		window.show();
	}

	//Admin Flag Customer Screen
	private static void admin_flagcus() throws IOException {
		window.setTitle("Customer Flagging");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		VBox box = new VBox(10);
		box.setSpacing(10.0);

		Label title = new Label("Please select the option below");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		ChoiceBox<String> customerchoiceBox = new ChoiceBox<>();
		ChoiceBox<String> statutschoiceBox = new ChoiceBox<>();

		ArrayList<Customer> custinfo = readCustomerInfoFromFile();
		for (int i = 0; i < custinfo.size(); i++) {
			customerchoiceBox.getItems().add(custinfo.get(i).getCust_fullname());
		}

		statutschoiceBox.getItems().addAll("normal", "case");

		Button proceedButton = new Button("Proceed");
		proceedButton.setOnAction(c -> {
			try {
				StatusChanging(statutschoiceBox.getValue(), customerchoiceBox.getValue());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		Button backButton = new Button("back");
		backButton.setOnAction(c -> admin_menuscreen());

		box.setAlignment(Pos.BOTTOM_CENTER);
		box.getChildren().addAll(customerchoiceBox, statutschoiceBox, proceedButton, backButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(box, 0, 0);
		grid.add(innergrid, 0, 3, 3, 3);
		Scene checkinScene = new Scene(grid, 400, 600);
		window.setScene(checkinScene);
		window.show();

	}

	// Changing status
	private static void StatusChanging(String status, String fullname) throws IOException {
		ArrayList<Customer> CustInfo = readCustomerInfoFromFile();
		ArrayList<CustomerVisitHistory> CustVisitHistory = readCustomerVisitHistoryFromFile();
		ArrayList<Shop> shops = readShopsFromFile();
		int selectedcust_index = -1;

		for (int l = 0; l < CustInfo.size(); l++) {
			if (CustInfo.get(l).getCust_fullname().equals(fullname)) {
				selectedcust_index = l;
				break;
			}
		}

		ArrayList<Integer> flaggedcust_visithistory = new ArrayList<>(); // flagged customer's visit history index

		// Writers for CustomerInfo.csv and shop.csv
		BufferedWriter CustInfo_writer = new BufferedWriter(new FileWriter("CustomerInfo.csv"));
		BufferedWriter ShopInfo_writer = new BufferedWriter(new FileWriter("shop.csv"));

		if (status.equals("case")) {
			// Searching for the visit history of the flagged customer for further changes
			// of status
			CustInfo.get(selectedcust_index).set_Customerstatus(status);
			for (int i = 0; i < CustVisitHistory.size(); i++) {
				if (CustVisitHistory.get(i).getCustomer_name().equals(fullname)) {
					flaggedcust_visithistory.add(i);
				}
			}

			// Searching for the shop that the flagged customer visited and set the shop
			// status
			for (int i = 0; i < shops.size(); i++) {
				for (int j = 0; j < flaggedcust_visithistory.size(); j++) {
					String shopname = shops.get(i).getShop_name();
					String shopnamefromhistory = CustVisitHistory.get(flaggedcust_visithistory.get(j)).getShop();
					if (shopname.equals(shopnamefromhistory)) {
						shops.get(i).setshop_status(status);
					}
				}
			}

			// Searching for the close contact customer and change their status to close if
			// they are in the one hour range before and after the flagged customer's visit
			// time
			for (int i = 0; i < CustVisitHistory.size(); i++) {
				for (int j = 0; j < flaggedcust_visithistory.size(); j++) {
					String shopnamefromhistory = CustVisitHistory.get(flaggedcust_visithistory.get(j)).getShop();
					if (!CustVisitHistory.get(i).getCustomer_name().equals(fullname) && CustVisitHistory.get(i).getShop().equals(shopnamefromhistory)) {
						LocalDateTime flaggedcust_visit = CustVisitHistory.get(flaggedcust_visithistory.get(j)).getvisitdatetime().withSecond(0);
						LocalDateTime possibleclosecust_visit = CustVisitHistory.get(i).getvisitdatetime().withSecond(0);

						int compareValue = possibleclosecust_visit.compareTo(flaggedcust_visit);

						
						if(compareValue > 0) 
						{
							Duration duration = Duration.between(flaggedcust_visit, possibleclosecust_visit);
							if (duration.toMinutes() <= 60 && duration.toMinutes() >= 0 ) {
								for (int k = 0; k < CustInfo.size(); k++) {
									if (CustInfo.get(k).getCust_fullname()
											.equals(CustVisitHistory.get(i).getCustomer_name())) {
										CustInfo.get(k).set_Customerstatus("close");
									}
								}
							}
						} 
						else if (compareValue < 0) 
						{
							Duration duration = Duration.between(possibleclosecust_visit, flaggedcust_visit);
							if (duration.toMinutes() <= 60 && duration.toMinutes() >= 0) {
								for (int k = 0; k < CustInfo.size(); k++) {
									if (CustInfo.get(k).getCust_fullname()
											.equals(CustVisitHistory.get(i).getCustomer_name())) {
										CustInfo.get(k).set_Customerstatus("close");
									}
								}
							}
						} 
					}
				}
			}
		
		} else if (status.equals("normal")) {
			// Searching for the visit history of the flagged customer for further changes
			// of status
			CustInfo.get(selectedcust_index).set_Customerstatus(status);
			for (int i = 0; i < CustVisitHistory.size(); i++) {
				if (CustVisitHistory.get(i).getCustomer_name().equals(fullname)) {
					flaggedcust_visithistory.add(i);
				}
			}

			// Searching for the shop that the flagged customer visited and set the shop
			// status
			for (int i = 0; i < shops.size(); i++) {
				for (int j = 0; j < flaggedcust_visithistory.size(); j++) {
					String shopname = shops.get(i).getShop_name();
					String shopnamefromhistory = CustVisitHistory.get(flaggedcust_visithistory.get(j)).getShop();
					if (shopname.equals(shopnamefromhistory)) {
						shops.get(i).setshop_status(status);
					}
				}
			}

		}

		// Rewriting the customer's data into CustomerInfo.csv
		for (int i = 0; i < CustInfo.size(); i++) {
			CustInfo_writer.write(CustInfo.get(i).toCSVString());
			CustInfo_writer.newLine();
		}

		// Rewriting the shop's data into shop.csv
		for (int i = 0; i < shops.size(); i++) {
			ShopInfo_writer.write(shops.get(i).toCSVString());
			ShopInfo_writer.newLine();
		}

		// Closing the two writers after rewriting the csv files.
		CustInfo_writer.close();
		ShopInfo_writer.close();

		popupBox("Flagged Succcess!", "The customer [" + fullname + "] " +  "has been succesfully flagged " + status +".");
	}

	// Randomly generate a time in between two intervals "StarInclusive" and
	// "endExclusive" with the zoneid = "UTC+0800" , lastly return the generated
	// time as a LocalDateTime object
	private static LocalDateTime between(LocalDateTime startInclusive, LocalDateTime endExclusive) {
		ZoneId zoneid = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(8));

		long epoch_startSeconds = startInclusive.atZone(zoneid).toEpochSecond();
		long epoch_endSeconds = endExclusive.atZone(zoneid).toEpochSecond();
		long random = ThreadLocalRandom.current().nextLong(epoch_startSeconds, epoch_endSeconds);

		return LocalDateTime.ofEpochSecond(random, 0, ZoneOffset.ofHours(8));
	}

	// Generating 30 random visit history
	private static void generating30randomvisit() throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter("CustomerVisitHistory.csv", true));

		ArrayList<CustomerVisitHistory> CustomerVisitHistory = new ArrayList<>();

		// Fetching the list of customer's name
		ArrayList<Customer> CustInfo = readCustomerInfoFromFile();
		String[] custnamelist = new String[CustInfo.size()];
		for (int i = 0; i < CustInfo.size(); i++)
			custnamelist[i] = CustInfo.get(i).getCust_fullname();

		// Fetching the list of shop's name
		ArrayList<Shop> shops = readShopsFromFile();
		String[] shoplist = new String[shops.size()];
		for (int i = 0; i < shops.size(); i++)
			shoplist[i] = shops.get(i).getShop_name();

		// LocalDateTime checkintime = LocalDateTime.now();

		// Generating random 30 visit history
		for (int i = 0; i < 30; i++) {
			Random r = new Random();
			// picking random index from the custermer namelist
			int custnamerandom_idx = r.nextInt(custnamelist.length);
			// picking random index from the shoplist
			int shoprandom_idx = r.nextInt(shoplist.length);
			// randomdatetime generator
			LocalDateTime onedayago = LocalDateTime.now().minus(Duration.ofDays(1)); // time of one day ago depends on
																						// the local system time
			LocalDateTime randomDateTime = between(onedayago, LocalDateTime.now()); // randomly generate time between
																					// yesterday of current system time
																					// and current system time

			CustomerVisitHistory.add(new CustomerVisitHistory(custnamelist[custnamerandom_idx],
					shoplist[shoprandom_idx], randomDateTime));

			writer.write(CustomerVisitHistory.get(i).toCSVString());
			writer.newLine();
		}
		writer.close();

	}

	// CUSTOMER
	// Customer Login/Register Screen
	private static void customer_LogRegScreen() throws IOException {

		window.setTitle("Customer Login Register Screen");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		VBox buttonBox = new VBox();
		buttonBox.setSpacing(10.0);

		grid.setVgap(10);
		grid.setHgap(12);

		Label title = new Label("Please select the option below");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		Button loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			try {
				customerLogin();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		Button registerButton = new Button("Register");
		registerButton.setOnAction(e -> {
			try {
				customerRegister();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		Button exitButton = new Button("Back");
		exitButton.setOnAction(e -> firstMenu(window));

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(loginButton, registerButton, exitButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 3, 1, 1);
		loginScene = new Scene(grid, 400, 600);
		window.setScene(loginScene);
		window.show();

	}

	// Customer Login Screen
	private static void customerLogin() throws IOException {
		window.setTitle("Login");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setHgap(12);

		HBox buttonBox = new HBox();
		buttonBox.setSpacing(10.0);

		Label title = new Label("Login");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		TextField loginUserName = new TextField();
		loginUserName.setPromptText("Username");
		grid.add(loginUserName, 0, 1);

		PasswordField loginPassword = new PasswordField();
		loginPassword.setPromptText("Password");
		grid.add(loginPassword, 0, 2);
		loginUserName.setOnAction(c -> {
			try {
				LoginAction(loginUserName, loginPassword);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		loginPassword.setOnAction(c -> {
			try {
				LoginAction(loginUserName, loginPassword);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button proceedButton = new Button("Proceed");
		proceedButton.setOnAction(c -> {
			try {
				LoginAction(loginUserName, loginPassword);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button backButton = new Button("Back");
		backButton.setOnAction(c -> {
			try {
				customer_LogRegScreen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(proceedButton, backButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 3, 1, 1);
		loginScene = new Scene(grid, 400, 600);
		window.setScene(loginScene);
		window.show();
	}

	// Customer Login Action
	private static void LoginAction(TextField loginUserName, PasswordField loginPassword) throws IOException {
		ArrayList<Customer> CustInfo = readCustomerInfoFromFile();
		String Luser = loginUserName.getText().trim().toLowerCase();
		String Lpassword = loginPassword.getText().trim().toLowerCase();
		Boolean usernamevalidation = true;
		Boolean passwordvalidation = true;
		for (int i = 0; i < CustInfo.size(); i++) {
			if (!Luser.equals(CustInfo.get(i).getCust_username())) {
				usernamevalidation = false;
			} else {
				usernamevalidation = true;
				if (!Lpassword.equals(CustInfo.get(i).getCust_password())) {
					passwordvalidation = false;
					break;
				} else {
					passwordvalidation = true;
					break;
				}
			}
		}

		if (usernamevalidation == false) {
			popupBox("Login Error", "Invalid username");
			loginPassword.clear();
			loginUserName.clear();
		} else if (passwordvalidation == false) {
			popupBox("Login Error", "Invalid password");
			loginPassword.clear();
			loginUserName.clear();
		}

		if (usernamevalidation == true && passwordvalidation == true) {
			popupBox("Login Successful!", "Logged in successfully!");
			customer_Menuscreen(Luser);
		}
	}

	// popupBox for any messages
	private static void popupBox(String title, String message) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle(title);

		Label label = new Label(message);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(c -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().setAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 300, 200);
		window.setScene(scene);
		window.showAndWait();

	}

	private static void customerRegister() throws IOException {
		window.setTitle("Register");
		ArrayList<Customer> CustInfo = readCustomerInfoFromFile();

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(12);

		HBox buttonBox = new HBox();
		buttonBox.setSpacing(10.0);

		Label title = new Label("Register");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		TextField regUserName = new TextField();
		regUserName.setPromptText("Your Username");
		grid.add(regUserName, 0, 1);

		TextField regPassword = new TextField();
		regPassword.setPromptText("Your Password");
		grid.add(regPassword, 0, 2);

		TextField regFullname = new TextField();
		regFullname.setPromptText("Your Fullname");
		grid.add(regFullname, 0, 3);

		TextField regPhonenumber = new TextField();
		regPhonenumber.setPromptText("Your Phone Number");
		grid.add(regPhonenumber, 0, 4);

		Button proceedButton = new Button("Proceed");
		proceedButton.setOnAction(c -> {
			Boolean RuserValid = true;
			Boolean RpasswordValid = true;
			Boolean RfullnameValid = true;
			Boolean RphoneValid = true;
			String Ruser = regUserName.getText();
			String Rpassword = regPassword.getText();
			String Rfullname = regFullname.getText();
			ArrayList<String> registrationinfo = new ArrayList<String>();

			for (int i = 0; i < CustInfo.size(); i++) {
				if (Ruser.equals(CustInfo.get(i).getCust_username())) {
					RuserValid = false;
					regUserName.clear();
					popupBox("Register Error", "Username exists");
				} else {
					RuserValid = true;
				}
			}

			if (Ruser.length() == 0) {
				RuserValid = false;
				popupBox("Register Error", "Username is Empty");
			}
			if (Rpassword.length() == 0) {
				RpasswordValid = false;
				popupBox("Register Error", "Password is Empty");
			}
			if (Rfullname.length() == 0) {
				RfullnameValid = false;
				popupBox("Register Error", "Fullname is Empty");
			}

			try {
				double validphonenumber = Double.parseDouble(regPhonenumber.getText());
				RphoneValid = true;
			} catch (NumberFormatException ex) {
				RphoneValid = false;
				regPhonenumber.clear();
				popupBox("Register Error", "Invalid Phone Number Try again");
			}

			if (RuserValid == true && RpasswordValid == true && RfullnameValid == true && RphoneValid == true) {
				registrationinfo.add(Ruser);
				registrationinfo.add(regPassword.getText());
				registrationinfo.add(regFullname.getText());
				registrationinfo.add(regPhonenumber.getText());
				registrationinfo.add("normal");

				StringBuilder sb = new StringBuilder();
				for (String element : registrationinfo) {
					sb.append(element);
					sb.append(",");
				}
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter("CustomerInfo.csv", true));
					writer.write(sb.toString());
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				popupBox("Registration Successful!", "Your account has been succesfully created.");
				try {
					customer_LogRegScreen();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Button backButton = new Button("Back");
		backButton.setOnAction(c -> {
			try {
				customer_LogRegScreen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(proceedButton, backButton);

		grid.setAlignment(Pos.CENTER);
		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 6, 1, 1);
		registerScene = new Scene(grid, 400, 600);
		window.setScene(registerScene);
		window.show();

	}

	// Customer MenuScreen
	private static void customer_Menuscreen(String username) throws IOException {
		ArrayList<Customer> CustInfo = readCustomerInfoFromFile();
		int fullname_index = 0;

		for (int i = 0; i < CustInfo.size(); i++) {
			if (CustInfo.get(i).getCust_username().equals(username)) {
				fullname_index = i;
			}
		}

		String Customer_fullname = CustInfo.get(fullname_index).getCust_fullname();
		String Customer_status = CustInfo.get(fullname_index).getCust_status();

		window.setTitle("Welcome, " + Customer_fullname);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		VBox buttonBox = new VBox();
		buttonBox.setSpacing(10.0);

		grid.setVgap(10);
		grid.setHgap(12);

		Label title = new Label("Please select the option below");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		Button checkinButton = new Button("Check in Shop");
		checkinButton.setOnAction(e -> {
			try {
				customer_CheckinScreen(username, Customer_fullname);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});

		Button visithistoryButton = new Button("View my history of shop visited");
		visithistoryButton.setOnAction(e -> {
			try {
				visthistoryAction(Customer_fullname, username);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});

		Button viewstatusButton = new Button("View my status");
		viewstatusButton.setOnAction(e -> popupBox("Your Status", "Your status is " + Customer_status));

		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			try {
				customer_LogRegScreen();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> window.close());

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(checkinButton, visithistoryButton, viewstatusButton, backButton, exitButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 3, 2, 1);
		Scene Customer_menuScreen = new Scene(grid, 400, 600);
		window.setScene(Customer_menuScreen);
		window.show();
	}

	// Customer Check In Screen
	private static void customer_CheckinScreen(String username, String Customer_fullname) throws IOException {

		window.setTitle("Check In");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		VBox box = new VBox(10);
		box.setSpacing(10.0);

		Label title = new Label("Please select the option below");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		ArrayList<Shop> shoplist = readShopsFromFile();
		for (int i = 0; i < shoplist.size(); i++) {
			choiceBox.getItems().add(shoplist.get(i).getShop_name());
		}

		Button checkinButton = new Button("Check in");
		checkinButton.setOnAction(c -> {
			try {
				checkInAction(choiceBox.getValue(), Customer_fullname);
				customer_Menuscreen(username);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		Button backButton = new Button("back");
		backButton.setOnAction(c -> {
			try {
				customer_Menuscreen(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		box.setAlignment(Pos.BOTTOM_CENTER);
		box.getChildren().addAll(choiceBox, checkinButton, backButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(box, 0, 0);
		grid.add(innergrid, 0, 3, 3, 3);
		Scene checkinScene = new Scene(grid, 400, 600);
		window.setScene(checkinScene);
		window.show();

	}

	// Customer Check In Action
	private static void checkInAction(String choice, String Customer_fullname) throws IOException {
		LocalDateTime datetime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		BufferedWriter writer = new BufferedWriter(new FileWriter("CustomerVisitHistory.csv", true));

		writer.write(Customer_fullname + "," + choice + "," + datetime.format(formatter));
		writer.newLine();
		writer.close();

		popupBox("Checked In Succesfully!", "Checked in at: " + choice + " \n" + datetime.format(formatter));

	}

	// Customer Visit History Action
	@SuppressWarnings("unchecked")
	private static void visthistoryAction(String Customer_fullname, String username) throws IOException {

		ArrayList<CustomerVisitHistory> full_custvisithitory = readCustomerVisitHistoryFromFile();
		ObservableList<CustomerVisitHistory> selected_custvisithistory = FXCollections.observableArrayList();
		for (int i = 0; i < full_custvisithitory.size(); i++) {
			if (full_custvisithitory.get(i).getCustomer_name().equals(Customer_fullname))
				selected_custvisithistory.add(new CustomerVisitHistory(full_custvisithitory.get(i).getCustomer_name(),
						full_custvisithitory.get(i).getShop(), full_custvisithitory.get(i).getvisitdatetime()));
		}

		// Date
		TableColumn<CustomerVisitHistory, String> DateColumn = new TableColumn<>("Date");
		DateColumn.setMinWidth(200);
		DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

		// Time
		TableColumn<CustomerVisitHistory, String> TimeColumn = new TableColumn<>("Time");
		TimeColumn.setMinWidth(200);
		TimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

		// Shop
		TableColumn<CustomerVisitHistory, String> ShopColumn = new TableColumn<>("Shop");
		ShopColumn.setMinWidth(200);
		ShopColumn.setCellValueFactory(new PropertyValueFactory<>("Shop"));

		TableView<CustomerVisitHistory> visithistory_Table = new TableView<>();
		visithistory_Table.setItems(selected_custvisithistory);
		// No
		TableColumn<CustomerVisitHistory, Number> indexColumn = new TableColumn<CustomerVisitHistory, Number>("No");
		indexColumn.setSortable(false);
		indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>(
				visithistory_Table.getItems().indexOf(column.getValue()) + 1));

		visithistory_Table.getColumns().addAll(indexColumn, DateColumn, TimeColumn, ShopColumn);

		Button backButton = new Button("Back");
		backButton.setOnAction(c -> {
			try {
				customer_Menuscreen(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		VBox box = new VBox();

		GridPane tableGrid = new GridPane();
		GridPane buttonGrid = new GridPane();
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(backButton);
		buttonGrid.add(buttonBox, 0, 0);

		box.getChildren().addAll(visithistory_Table);
		tableGrid.add(box, 0, 0);
		tableGrid.add(buttonBox, 0, 3, 3, 3);
		Scene visitHistoryScene = new Scene(tableGrid);
		window.setScene(visitHistoryScene);
		window.show();
	}

	// Shop
	// Shop MenuScreen
	private static void shop_menuscren(){
		window.setTitle("Shop MenuScreen");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		VBox buttonBox = new VBox();
		buttonBox.setSpacing(10.0);

		grid.setVgap(10);
		grid.setHgap(12);

		Label title = new Label("Please select the option below");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		Button checkshopstatusButtion = new Button("View shop status");
		checkshopstatusButtion.setOnAction(c -> {
			try {
				shop_checkstatus();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button backButton = new Button("back");
		backButton.setOnAction(c -> firstMenu(window));

		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.getChildren().addAll(checkshopstatusButtion,backButton);
	
		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(buttonBox, 0, 0);
		grid.add(innergrid, 0, 3, 1, 1);
		Scene shopmenuScene = new Scene(grid, 400, 600);
		window.setScene(shopmenuScene);
		window.show();
	}

	private static void shop_checkstatus() throws IOException {
		window.setTitle("Check Shop Status");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		VBox box = new VBox(10);
		box.setSpacing(10.0);

		Label title = new Label("Please select the option below");
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		grid.add(title, 0, 0);

		ChoiceBox<String> shopchoiceBox = new ChoiceBox<>();
		ArrayList<Shop> shoplist = readShopsFromFile();
		for (int i = 0; i < shoplist.size(); i++) {
			shopchoiceBox.getItems().add(shoplist.get(i).getShop_name());
		}

		Button proceedButton = new Button("Proceed");
		proceedButton.setOnAction(c -> {
			int selectedshop_index = -1;
			for (int l=0;l<shoplist.size();l++){
				if (shoplist.get(l).getShop_name().equals(shopchoiceBox.getValue())){
					selectedshop_index = l;
					break;
				}
			}

			String status = shoplist.get(selectedshop_index).getShop_status();
			popupBox("Status " , "The status of " + shopchoiceBox.getValue() + " is " + status);
		});

		Button backButton = new Button("back");
		backButton.setOnAction(c -> shop_menuscren());

		box.setAlignment(Pos.BOTTOM_CENTER);
		box.getChildren().addAll(shopchoiceBox, proceedButton, backButton);

		GridPane innergrid = new GridPane();
		innergrid.setAlignment(Pos.CENTER);
		innergrid.add(box, 0, 0);
		grid.add(innergrid, 0, 3, 3, 3);
		Scene checkinScene = new Scene(grid, 400, 600);
		window.setScene(checkinScene);
		window.show();

	}

//===================================== VALIDATION =====================================//
	/*Validating the userselection -- only able to input integer, string inputs will cause InputMismatchException
	and finally return the userselection back into the main if checking is done.*/
	// private static int userselection_validation(Scanner input){
	// 	boolean InvalidUserSelection = true;
	// 	int userselection = 0;

	// 	do{
	// 		try{
	// 			System.out.print("Please select your choice above:");
	// 			userselection = input.nextInt();

	// 			InvalidUserSelection = false;
	// 		}

	// 		catch (InputMismatchException ex){
	// 			System.out.println("Error: Invalid User Selection, Please try again");
	// 			input.nextLine();
	// 		}

	// 	}while(InvalidUserSelection);

	// 	return userselection;
	// }

	/*Validating the usertype -- and detect whether the input usertype is within this list
	if the input is not within this list, the InvalidUserTypeException will be thrown.*/
	// private void usertype_validating(String Valid_Usertype) {
	// 	String [] user =  {"admin","customer","shop","exit"};

	// 	try{
	// 		if (!Arrays.asList(user).contains(Valid_Usertype))
	// 			throw new InvalidUserTypeException(Valid_Usertype);
	// 	}	
	// 	catch(InvalidUserTypeException ex){
	// 		System.out.println(ex);
	// 	}

	// }

	/*Function that return a boolean after validating the password input by the user
	match the masterpassword*/
	private static boolean MasterPassword_validating(String password){
		String masterpassword = "admin";

		if (password.equals(masterpassword))
			return true;
		else{
			return false;
		}
	}

//===================================== CSV READING =====================================//
	//Reading the CustomerVisitHistory.csv file and import the data into an arraylist named "CustomerVisitHistory", lastly return the arraylist
	private static ArrayList<CustomerVisitHistory> readCustomerVisitHistoryFromFile() throws IOException {
        ArrayList<CustomerVisitHistory> CustomerVisitHistory = new ArrayList<>();

        // read students.csv into a list of lines.
		List<String> lines = Files.readAllLines(Paths.get("CustomerVisitHistory.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
			String[] items = lines.get(i).split(",");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime datetime = LocalDateTime.parse(items[2], formatter);

			// items[0] = customer's name, items[1] = Shop, items[2] = VisitDateTime
			CustomerVisitHistory.add(new CustomerVisitHistory(items[0],items[1],datetime));
		}
        return CustomerVisitHistory;
	}
	
	//Reading the shop.csv file and import the data into an arraylist named "shops", lastly return the arraylist
	private static ArrayList<Shop> readShopsFromFile() throws IOException {
        ArrayList<Shop> shops = new ArrayList<>();

        // read students.csv into a list of lines.
        List<String> lines = Files.readAllLines(Paths.get("shop.csv"));
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            // items[0] = shop_name, items[1] = shop_phonenumber, items[2] = shop_managername, items[3] = shop_status;
            shops.add (new Shop(items[0],items[1],items[2],items[3]));
        }
        return shops;
	}

	//Reading the CustomerInfo.csv file and import the data into an arraylist named "CustomerVisitHistory", lastly return the arraylist
	private static ArrayList<Customer> readCustomerInfoFromFile() throws IOException {
		ArrayList<Customer> CustomerInfo = new ArrayList<>();

		// read students.csv into a list of lines.
		List<String> lines = Files.readAllLines(Paths.get("CustomerInfo.csv"));
		for (int i = 0; i < lines.size(); i++) {
			// split a line by comma
			String[] items = lines.get(i).split(",");
			// items[0] = Cust_username, items[1] = Cust_password, items [2] = Cust_fullname, items[3] = Cust_phonenum
			// items[4] = Cust_status
			CustomerInfo.add(new Customer(items[0],items[1],items[2],items[3],items[4]));
		}
		return CustomerInfo;
	}

//===================================== MISC =====================================//
	//Function for pausing the terminal according to how many milliseconds(ms)
	// private static void sleep(int ms){
	// 	try{
	// 		Thread.sleep(ms);
	// 	}
	// 	catch(Exception e){
	// 		System.out.println(e);
	// 	}
	// }
}

