package visao;

import controle.ControladorManterCelular;
import controle.ControladorManterTipo;
import interfaces.IControladorCRUD;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JanelaManterCelular extends JanelaCrudModelo {

    private JTextField imei;
    private JTextField marca;
    private JTextField modelo;
    private JTextField cor;
    private JTextField ano;


    public JanelaManterCelular(IControladorCRUD contr) {
        super(contr);
        getPainelDados().setLayout(null);

        JLabel lbImei = new JLabel("IMEI");
        lbImei.setBounds(10, 10, 80, 20);
        getPainelDados().add(lbImei);

        imei = new JTextField();
        imei.setBounds(80,10,100,20);
        getPainelDados().add(imei);

        JLabel lbMarca = new JLabel("MARCA");
        lbMarca.setBounds(10, 30, 80, 20);
        getPainelDados().add(lbMarca);

        marca = new JTextField();
        marca.setBounds(80,30,100,20);
        getPainelDados().add(marca);

        JLabel lbModelo = new JLabel("MODELO");
        lbModelo.setBounds(10, 50, 80, 20);
        getPainelDados().add(lbModelo);

        modelo = new JTextField();
        modelo.setBounds(80,50,100,20);
        getPainelDados().add(modelo);

        JLabel lblCor = new JLabel("COR");
        lblCor.setBounds(10, 70, 80, 20);
        getPainelDados().add(lblCor);

        cor = new JTextField();
        cor.setBounds(80,70,100,20);
        getPainelDados().add(cor);

        JLabel lblAno = new JLabel("ANO");
        lblAno.setBounds(10, 90, 80, 20);
        getPainelDados().add(lblAno);

        ano = new JTextField();
        ano.setBounds(80,90,100,20);
        getPainelDados().add(ano);



    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JanelaManterCelular frame = new JanelaManterCelular(new ControladorManterCelular());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Acessa os textfileds e retorna uma lista com os valores dentro que estoa dentro dele
     * @return
     */
    @Override
    public List<Object> getFormularioValores() {

        List<Object> lista = new ArrayList<>();

        lista.add(getImei().getText());
        lista.add(getMarca().getText());
        lista.add(getModelo().getText());
        lista.add(getCor().getText());
        lista.add(getAno().getText());

        lista.add(getPK());

        return lista;
    }


    @Override
    public void setValoresFormulario(List<Object> valores) {

        this.getImei().setText(valores.get(1).toString());
        this.getMarca().setText(valores.get(2).toString());
        this.getModelo().setText(valores.get(3).toString());
        this.getCor().setText(valores.get(4).toString());
        this.getAno().setText(valores.get(5).toString());

    }

    @Override
    public void limpaCampos() {

        this.getImei().setText("");

        this.getMarca().setText("");
        this.getModelo().setText("");
        this.getCor().setText("");
        this.getAno().setText("");

    }

    public JTextField getImei() {
        return imei;
    }

    public void setImei(JTextField imei) {
        this.imei = imei;
    }

    public JTextField getMarca() {
        return marca;
    }

    public void setMarca(JTextField marca) {
        this.marca = marca;
    }

    public JTextField getModelo() {
        return modelo;
    }

    public void setModelo(JTextField modelo) {
        this.modelo = modelo;
    }

    public JTextField getCor() {
        return cor;
    }

    public void setCor(JTextField cor) {
        this.cor = cor;
    }

    public JTextField getAno() {
        return ano;
    }

    public void setAno(JTextField ano) {
        this.ano = ano;
    }
}
