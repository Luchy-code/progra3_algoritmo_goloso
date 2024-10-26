package Run;

import java.awt.EventQueue;

import ConexionBD.Conexion;
import Model.GreedyAlgorithm;
import Presenter.Presenter;
import View.DataInputForm;
import View.Menu;
import View.ShowOffers;
import View.ShowWinnersOffers;

public class Run_principal {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu window = new Menu(); 
                    window.show();
                    
                    DataInputForm di= new DataInputForm();
                    ShowOffers so= new ShowOffers();
                    Conexion bdd= new Conexion();
                    ShowWinnersOffers swo= new ShowWinnersOffers();
                    GreedyAlgorithm ga=new  GreedyAlgorithm();
                    
                    Presenter presenter = new Presenter(window,di,so,bdd,swo,ga);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
