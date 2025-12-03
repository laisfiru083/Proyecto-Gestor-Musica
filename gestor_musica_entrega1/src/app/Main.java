package app;

import model.MusicManager;
import ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        MusicManager manager = new MusicManager();
        // cargar datos desde archivos si existen
        manager.loadAllIfExists("data");
        MainWindow w = new MainWindow(manager);
        w.show();
    }
}
