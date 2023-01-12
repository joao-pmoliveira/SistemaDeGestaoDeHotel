package gui;



import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI extends JFrame{

    private JPanel mainPanel;
    private JButton loginButton;
    private JButton exitButton;
    private JPanel loginPanel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JLabel userLabel;

    static JFrame frame;
    GestorDeBaseDeDados gestorBaseDados;
    public GUI(String title){
        super(title);
        this.setUndecorated(true);// tela sem a barra de cima

        ValidadorDeLogin vl = new ValidadorDeLogin("src/main/java/loginData");
        gestorBaseDados = new GestorDeBaseDeDados(vl.getHostname(), vl.getPort(), vl.getSchema(), vl.getUsername(), vl.getPassword());
        gestorBaseDados.tryConnectionToDataBase();


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);


        //Dimensões
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/3-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        this.setSize(500,250);



        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);



                if(userField.getText().isEmpty()||passwordField.getPassword().length==0){
                    System.out.println("campos vazios");
                    return;
                }

                String pass = new String(passwordField.getPassword());
                String pesquisa = String.format("SELECT (id) FROM empregado WHERE id = %d and palavra_passe = '%s'", Integer.parseInt(userField.getText()), pass);
                List<String> resultado = gestorBaseDados.tryQueryDatabase(pesquisa);

                if(!resultado.isEmpty()) {
                    JFrame menuGui = new MenuGui("Menu");
                    menuGui.setVisible(true);
                    frame.setVisible(false);
                }else {
                    System.out.println("palavra passe errada ou utilizador errado");
                }
            }
        });
    }


    public static void main(String[] args) {
        frame = new GUI("Login");
        frame.setVisible(true);
    }
}

