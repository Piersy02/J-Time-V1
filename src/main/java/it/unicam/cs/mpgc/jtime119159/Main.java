package it.unicam.cs.mpgc.jtime119159;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends javafx.application.Application {

    @Override
    public void start(javafx.stage.Stage stage) throws Exception {
        it.unicam.cs.mpgc.jtime119159.persistence.DatabaseManager.initialize();

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        javafx.scene.Parent root = loader.load();

        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);
        stage.setTitle("JTime Project Manager");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        it.unicam.cs.mpgc.jtime119159.persistence.DatabaseManager.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}