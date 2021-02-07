package fr.anewplayerfr.launcher.template;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;

import fr.northenflo.auth.exception.AccountSuspendException;
import fr.northenflo.auth.exception.DataEmptyException;
import fr.northenflo.auth.exception.DataWrongException;
import fr.northenflo.auth.exception.RequireGAuthException;
import fr.northenflo.auth.exception.ServerNotFoundException;
import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.UserConfig;
import fr.trxyy.alternative.alternative_api.utils.config.UsernameSaver;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherProgressBar;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherPanel extends IScreen {
	/** TOP */
	private LauncherRectangle topRectangle;
	private LauncherRectangle backgroundWhiteRectangle;
	private LauncherImage titleImage;
	private LauncherLabel titleLabel;
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	/** BUTTONS IMAGE URL */
	
	/** LOGIN */
	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	private LauncherButton loginButton;
	private LauncherButton settingsButton;
	/** UPDATE */
	public Timeline timeline;
	private DecimalFormat decimalFormat = new DecimalFormat(".#");
	private Thread updateThread;
	private GameUpdater updater = new GameUpdater();
	private LauncherRectangle updateRectangle;
	private LauncherLabel updateLabel;
	private LauncherLabel currentFileLabel;
	private LauncherLabel percentageLabel;
	private LauncherLabel currentStep;
	/** USERNAME SAVER, CONFIG SAVER */
	private UsernameSaver usernameSaver;
	public UserConfig userConfig;
	/** PROGRESS BAR */
	public LauncherProgressBar bar;
	/** LINKS FOR BUTTONS IMAGES */

	/** GAMEENGINE REQUIRED */
	private GameEngine theGameEngine;
	/** AUTO LOGIN */
	private Timer autoLoginTimer;
	private LauncherLabel autoLoginLabel;
	private LauncherRectangle autoLoginRectangle;
	private LauncherButton autoLoginButton;

	public LauncherPanel(Pane root, GameEngine engine)
	{
		this.drawBackgroundImage(engine, root, "background.png");
		this.theGameEngine = engine;
		/** ===================== CONFIGURATION UTILISATEUR ===================== */
		this.userConfig = new UserConfig(theGameEngine);
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.usernameSaver = new UsernameSaver(theGameEngine);
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.backgroundWhiteRectangle = new LauncherRectangle(root, 0, 0, theGameEngine.getWidth(), theGameEngine.getHeight());
		this.backgroundWhiteRectangle.setFill(Color.rgb(255, 255, 255, 0.17));
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, theGameEngine.getWidth(), 31);
		this.topRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.topRectangle.setOpacity(1.0);
		/** ===================== AFFICHER LA PARTIE INFO ===================== */                                               //G  //B  
		this.drawLogo(theGameEngine, getResourceLocation().loadImage(theGameEngine, "favicon.png"), theGameEngine.getWidth() / 2 - 100, 35, 220, 200, root, Mover.DONT_MOVE);
		/** ===================== ICONE BLOCK EN HAUT MILIEU ===================== */
		this.titleImage = new LauncherImage(root);
		this.titleImage.setImage(getResourceLocation().loadImage(theGameEngine, "favicon.png"));
		this.titleImage.setSize(25, 25);
		this.titleImage.setPosition(theGameEngine.getWidth() / 3 + 40, 3);

		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Iridium - LauncherV1");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.titleLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.titleLabel.setPosition(theGameEngine.getWidth() / 2 - 80, -4);
		this.titleLabel.setOpacity(0.7);
		this.titleLabel.setSize(500, 40);

		this.closeButton = new LauncherButton(root);
		this.closeButton.setInvisible();
		this.closeButton.setPosition(theGameEngine.getWidth() - 35, 2);
		this.closeButton.setSize(15, 15);
		this.closeButton.setBackground(null);
		LauncherImage closeImage = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "close.png"));
		closeImage.setSize(15, 15);
		this.closeButton.setGraphic(closeImage);
		this.closeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		this.reduceButton = new LauncherButton(root);
		this.reduceButton.setInvisible();
		this.reduceButton.setPosition(theGameEngine.getWidth() - 55, 2);
		this.reduceButton.setSize(15, 15);
		this.reduceButton.setBackground(null);
		LauncherImage reduceImage = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "reduce.png"));
		reduceImage.setSize(15, 15);
		this.reduceButton.setGraphic(reduceImage);
		this.reduceButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
				stage.setIconified(true);
			}
			
		});

		this.usernameField = new LauncherTextField(root);
		this.usernameField.setText(this.usernameSaver.getUsername());
		this.usernameField.setPosition(theGameEngine.getWidth() / 2 - 135, theGameEngine.getHeight() / 2 - 57);
		this.usernameField.setSize(270, 50);
		this.usernameField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.usernameField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.usernameField.setVoidText("Nom de compte");

		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setPosition(theGameEngine.getWidth() / 2 - 135, theGameEngine.getHeight() / 2);
		this.passwordField.setSize(270, 50);
		this.passwordField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.passwordField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.passwordField.setVoidText("Mot de passe");

		this.loginButton = new LauncherButton(root);
		this.loginButton.setText("Se connecter");
		this.loginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.loginButton.setPosition(theGameEngine.getWidth() / 2 - 67, theGameEngine.getHeight() / 2 + 60);
		this.loginButton.setSize(200, 45);
		this.loginButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.loginButton.setAction(ce -> {
			 usernameSaver.writeUsername(this.usernameField.getText());
			 if (this.usernameField.getText().length() < 3) {
			 new LauncherAlert("Authentification echouee",
			 "Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
			 } else if (this.usernameField.getText().length() > 3 && this.passwordField.getText().isEmpty()) {
			 new LauncherAlert("Authentification echouee",
			 "Il y a un probleme lors de la tentative de connexion: Veuillez spécifier un mot de passe");
			 }

			 else if (this.usernameField.getText().length() > 3 && !this.passwordField.getText().isEmpty()) {

			 MinewebAuth auth = null;

			 try {
			 auth = new MinewebAuth(this.usernameField.getText(), this.passwordField.getText(),
			 "https://www.iradium.gq/");

			 } catch (AccountSuspendException e) {
			 new LauncherAlert("Erreur lors de l'auth", "Votre compte est suspendu");

			 } catch (DataWrongException e) {
			 new LauncherAlert("Erreur lors de l'auth", "Mauvais pseudo / mot de passe");

			 } catch (DataEmptyException e) {
			 new LauncherAlert("Erreur lors de l'auth", "Veuillez spécifier un pseudo et un mot de passe");

			 } catch (IOException e) {
			 e.printStackTrace();
			 new LauncherAlert("Erreur lors de l'auth",
			 "Problème lors de la connexion au serveur " + e.getMessage());

			 } catch (ServerNotFoundException e) {
			 e.printStackTrace();
			 new LauncherAlert("Erreur lors de l'auth",
			 "Impossible de se connecter au serveur " + e.getMessage());

			 } catch (RequireGAuthException e) {
			 new LauncherAlert("Erreur lors de l'auth", e.getLocalizedMessage());
			 e.printStackTrace();
			 }

			 if (auth.isLogged()) {

			 this.update(engine, auth);
			 }

			 }
			 });

		this.settingsButton = new LauncherButton(root);
		this.settingsButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		LauncherImage imageButton = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "settings.png"));
		imageButton.setSize(27, 27);
		this.settingsButton.setGraphic(imageButton);
		this.settingsButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.settingsButton.setPosition(theGameEngine.getWidth() / 2 - 135, theGameEngine.getHeight() / 2 + 60);
		this.settingsButton.setSize(60, 45);
		this.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = new Scene(createSettingsPanel());
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Settings");
				stage.setWidth(500);
				stage.setHeight(230);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
			}
		});
		

		this.updateRectangle = new LauncherRectangle(root, theGameEngine.getWidth() / 2 - 175, theGameEngine.getHeight() / 2 - 60, 350, 180);
		this.updateRectangle.setArcWidth(10.0);
		this.updateRectangle.setArcHeight(10.0);
		this.updateRectangle.setFill(Color.rgb(0, 0, 0, 0.60));
		this.updateRectangle.setVisible(false);

		this.updateLabel = new LauncherLabel(root);
		this.updateLabel.setText("- MISE A JOUR -");
		this.updateLabel.setAlignment(Pos.CENTER);
		this.updateLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.updateLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.updateLabel.setPosition(theGameEngine.getWidth() / 2 - 95, theGameEngine.getHeight() / 2 - 55);
		this.updateLabel.setOpacity(1);
		this.updateLabel.setSize(190, 40);
		this.updateLabel.setVisible(false);

		this.currentStep = new LauncherLabel(root);
		this.currentStep.setText("Verification des fichiers");
		this.currentStep.setFont(Font.font("Verdana", FontPosture.ITALIC, 18F)); // FontPosture.ITALIC
		this.currentStep.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentStep.setAlignment(Pos.CENTER);
		this.currentStep.setPosition(theGameEngine.getWidth() / 2 - 160, theGameEngine.getHeight() / 2 + 83);
		this.currentStep.setOpacity(0.4);
		this.currentStep.setSize(320, 40);
		this.currentStep.setVisible(false);

		this.currentFileLabel = new LauncherLabel(root);
		this.currentFileLabel.setText("launchwrapper-12.0.jar");
		this.currentFileLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.currentFileLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentFileLabel.setAlignment(Pos.CENTER);
		this.currentFileLabel.setPosition(theGameEngine.getWidth() / 2 - 160, theGameEngine.getHeight() / 2 + 25);
		this.currentFileLabel.setOpacity(0.8);
		this.currentFileLabel.setSize(320, 40);
		this.currentFileLabel.setVisible(false);

		this.percentageLabel = new LauncherLabel(root);
		this.percentageLabel.setText("0%");
		this.percentageLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 30F));
		this.percentageLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.percentageLabel.setAlignment(Pos.CENTER);
		this.percentageLabel.setPosition(theGameEngine.getWidth() / 2 - 50, theGameEngine.getHeight() / 2 - 5);
		this.percentageLabel.setOpacity(0.8);
		this.percentageLabel.setSize(100, 40);
		this.percentageLabel.setVisible(false);
		
		this.bar = new LauncherProgressBar(root);
		this.bar.setPosition(theGameEngine.getWidth() / 2 - 125, theGameEngine.getHeight() / 2 + 60);
		this.bar.setSize(250, 20);
		this.bar.setVisible(false);
		
		this.autoLoginRectangle = new LauncherRectangle(root, 0, theGameEngine.getHeight() - 32, 1000, theGameEngine.getHeight());
		this.autoLoginRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.autoLoginRectangle.setOpacity(1.0);
		this.autoLoginRectangle.setVisible(false);
		
		this.autoLoginLabel = new LauncherLabel(root);
		this.autoLoginLabel.setText("AutoConnection dans 3 sec");
		this.autoLoginLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.autoLoginLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: blue;");
		this.autoLoginLabel.setPosition(theGameEngine.getWidth() / 2 - 280, theGameEngine.getHeight() - 34);
		this.autoLoginLabel.setOpacity(0.7);
		this.autoLoginLabel.setSize(700, 40);
		this.autoLoginLabel.setVisible(false);
		
		this.autoLoginButton = new LauncherButton(root);
		this.autoLoginButton.setText("Annuler");
		this.autoLoginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.autoLoginButton.setPosition(theGameEngine.getWidth() / 2 + 60, theGameEngine.getHeight() - 30);
		this.autoLoginButton.setSize(200, 20);
		this.autoLoginButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-text-fill: black;");
		this.autoLoginButton.setVisible(false);
		this.autoLoginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				autoLoginTimer.cancel();
				autoLoginLabel.setVisible(false);
				autoLoginButton.setVisible(false);
				autoLoginRectangle.setVisible(false);
			}
		});
		
	}



	private void update(GameEngine engine, MinewebAuth auth) {
		this.usernameField.setDisable(true);
		this.passwordField.setDisable(true);
		this.loginButton.setDisable(true);
		this.settingsButton.setDisable(true);
		this.usernameField.setVisible(false);
		this.passwordField.setVisible(false);
		this.loginButton.setVisible(false);
		this.settingsButton.setVisible(false);
		this.updateRectangle.setVisible(true);
		this.updateLabel.setVisible(true);
		this.currentStep.setVisible(true);
		this.currentFileLabel.setVisible(true);
		this.percentageLabel.setVisible(true);
		this.bar.setVisible(true);
		updater.reg(theGameEngine);
		updater.reg(auth.getSession());
		theGameEngine.reg(this.updater);
		this.updateThread = new Thread() {
			public void run() {
				theGameEngine.getGameUpdater().run();
			}
		};
		this.updateThread.start();

		this.timeline = new Timeline(
				new KeyFrame[] { new KeyFrame(javafx.util.Duration.seconds(0.0D), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						timelineUpdate(theGameEngine);
					}
				}, new javafx.animation.KeyValue[0]),
						new KeyFrame(javafx.util.Duration.seconds(0.1D), new javafx.animation.KeyValue[0]) });
		this.timeline.setCycleCount(Animation.INDEFINITE);
		this.timeline.play();
	}

	private Parent createSettingsPanel() {
		LauncherPane contentPane = new LauncherPane(theGameEngine);
		Rectangle rect = new Rectangle(500, 230);
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherSettings(contentPane, theGameEngine, this);
		return contentPane;
	}

	public void timelineUpdate(GameEngine engine) {
		if (engine.getGameUpdater().downloadedFiles > 0) {
			this.percentageLabel.setText(decimalFormat.format(
				engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload) + "%");
		}
		this.currentFileLabel.setText(engine.getGameUpdater().getCurrentFile());
		this.currentStep.setText(engine.getGameUpdater().getCurrentInfo());
		double percent = (engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload / 100.0D);
		this.bar.setProgress(percent);
	}

}
