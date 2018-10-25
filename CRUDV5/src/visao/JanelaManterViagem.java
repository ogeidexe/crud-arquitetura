package visao;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controle.ControladorManterViagem;
import interfaces.IControladorCRUD;
import javax.swing.JLabel;
import javax.management.RuntimeErrorException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextPane;

public class JanelaManterViagem extends JanelaCrudModelo {
	private JTextField textFieldEstatus;
	private JTextField textFieldDias;
	private JTextField textFieldTipo;
	private JDateChooser dateChooserDataSolicitacao;
	private JDateChooser dateChooserDataAprovacao;
	private JDateChooser dateChooserDataPartida;
	private JDateChooser dateChooserDataAcerto;
	private JTextPane textPane;
	private JDateChooser dateChooserAprovacaoAcerto;

	public JanelaManterViagem(IControladorCRUD contr) {
		super(contr);
		getPainelDados().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data Solicita\u00E7\u00E3o");
		lblNewLabel.setBounds(10, 11, 89, 14);
		getPainelDados().add(lblNewLabel);
		
		dateChooserDataSolicitacao = new JDateChooser();
		dateChooserDataSolicitacao.setBounds(161, 5, 87, 20);
		getPainelDados().add(dateChooserDataSolicitacao);
		
		JLabel lblDataAprovao = new JLabel("Data Aprova\u00E7\u00E3o Solicita\u00E7\u00E3o");
		lblDataAprovao.setBounds(10, 39, 141, 14);
		getPainelDados().add(lblDataAprovao);
		
		dateChooserDataAprovacao = new JDateChooser();
		dateChooserDataAprovacao.setBounds(161, 33, 87, 20);
		getPainelDados().add(dateChooserDataAprovacao);
		
		JLabel lblDataPartida = new JLabel("Data Partida");
		lblDataPartida.setBounds(10, 70, 141, 14);
		getPainelDados().add(lblDataPartida);
		
		dateChooserDataPartida = new JDateChooser();
		dateChooserDataPartida.setBounds(161, 64, 87, 20);
		getPainelDados().add(dateChooserDataPartida);
		
		JLabel lblDataAcerto = new JLabel("Data Acerto");
		lblDataAcerto.setBounds(10, 101, 141, 14);
		getPainelDados().add(lblDataAcerto);
		
		dateChooserDataAcerto = new JDateChooser();
		dateChooserDataAcerto.setBounds(161, 95, 87, 20);
		getPainelDados().add(dateChooserDataAcerto);
		
		JLabel lblDataAprovaoAcerto = new JLabel("Data Aprova\u00E7\u00E3o Acerto");
		lblDataAprovaoAcerto.setBounds(10, 125, 141, 14);
		getPainelDados().add(lblDataAprovaoAcerto);
		
		dateChooserAprovacaoAcerto = new JDateChooser();
		dateChooserAprovacaoAcerto.setBounds(161, 119, 87, 20);
		getPainelDados().add(dateChooserAprovacaoAcerto);
		
		JLabel lblNewLabel_1 = new JLabel("Estatus");
		lblNewLabel_1.setBounds(10, 150, 46, 14);
		getPainelDados().add(lblNewLabel_1);
		
		textFieldEstatus = new JTextField();
		textFieldEstatus.setBounds(162, 150, 86, 20);
		getPainelDados().add(textFieldEstatus);
		textFieldEstatus.setColumns(10);
		
		JLabel lblMotivo = new JLabel("Motivo");
		lblMotivo.setBounds(272, 11, 46, 14);
		getPainelDados().add(lblMotivo);
		
		textPane = new JTextPane();
		textPane.setBounds(272, 33, 185, 137);
		getPainelDados().add(textPane);
		
		JLabel lblDias = new JLabel("Dias");
		lblDias.setBounds(10, 175, 46, 14);
		getPainelDados().add(lblDias);
		
		textFieldDias = new JTextField();
		textFieldDias.setBounds(162, 172, 86, 20);
		getPainelDados().add(textFieldDias);
		textFieldDias.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(10, 193, 46, 14);
		getPainelDados().add(lblTipo);
		
		textFieldTipo = new JTextField();
		textFieldTipo.setBounds(162, 190, 86, 20);
		getPainelDados().add(textFieldTipo);
		textFieldTipo.setColumns(10);
		initJanelaManterViagem(); 
	}

	private void initJanelaManterViagem() {
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaManterViagem frame = new JanelaManterViagem(new ControladorManterViagem());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public List<Object> getFormularioValores() {
		List<Object> lista = new ArrayList<>();
		lista.add(getPK());
		lista.add(getDateChooserDataSolicitacao().getDate());
		lista.add(getDateChooserDataAprovacao().getDate());
		lista.add(getDateChooserDataPartida().getDate());
		lista.add(getDateChooserDataAcerto().getDate());
		lista.add(getDateChooserAprovacaoAcerto().getDate());
		lista.add(getTextFieldEstatus().getText());
		lista.add(getTextPane().getText());
		lista.add(getTextFieldDias().getText());
		lista.add(getTextFieldTipo().getText());
		
		return lista;
	}
	protected JDateChooser getDateChooserDataSolicitacao() {
		return dateChooserDataSolicitacao;
	}
	protected JDateChooser getDateChooserDataAprovacao() {
		return dateChooserDataAprovacao;
	}
	protected JDateChooser getDateChooserDataPartida() {
		return dateChooserDataPartida;
	}
	protected JDateChooser getDateChooserDataAcerto() {
		return dateChooserDataAcerto;
	}
	protected JTextField getTextFieldTipo() {
		return textFieldTipo;
	}
	protected JTextField getTextFieldEstatus() {
		return textFieldEstatus;
	}
	protected JTextField getTextFieldDias() {
		return textFieldDias;
	}
	protected JTextPane getTextPane() {
		return textPane;
	}
	protected JDateChooser getDateChooserAprovacaoAcerto() {
		return dateChooserAprovacaoAcerto;
	}

	@Override
	public void setValoresFormulario(List<Object> valores) {
		this.getDateChooserDataSolicitacao().setDate((Date) valores.get(1));
		this.getDateChooserDataAprovacao().setDate((Date) valores.get(2));
		this.getDateChooserDataPartida().setDate((Date) valores.get(3));
		this.getDateChooserDataAcerto().setDate((Date) valores.get(4));
		this.getDateChooserAprovacaoAcerto().setDate((Date) valores.get(5));
		this.getTextFieldEstatus().setText(valores.get(6).toString());
		this.getTextPane().setText(valores.get(7).toString());
		this.getTextFieldDias().setText(valores.get(8).toString());
		this.getTextFieldTipo().setText(valores.get(9).toString());
		
	}

	@Override
	public void limpaCampos() {
		this.getDateChooserDataSolicitacao().setDate(null);
		this.getDateChooserDataAprovacao().setDate(null);
		this.getDateChooserDataPartida().setDate(null);
		this.getDateChooserDataAcerto().setDate(null);
		this.getTextFieldTipo().setText("");
		this.getTextFieldEstatus().setText("");
		this.getTextFieldDias().setText("");
		this.getTextPane().setText("");
		this.getDateChooserAprovacaoAcerto().setDate(null);
	}


}
