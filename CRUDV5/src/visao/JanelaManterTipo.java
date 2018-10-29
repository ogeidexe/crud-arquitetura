package visao;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import controle.ControladorManterTipo;
import interfaces.IControladorCRUD;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class JanelaManterTipo extends JanelaCrudModelo {
	private JTextField textFieldNome;
	private JComboBox comboBoxAtivo;


	public JanelaManterTipo(IControladorCRUD contr) {
		super(contr);
		getPainelDados().setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 11, 46, 14);
		getPainelDados().add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(75, 8, 86, 20);
		getPainelDados().add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblAtivo = new JLabel("Ativo");
		lblAtivo.setBounds(10, 36, 46, 14);
		getPainelDados().add(lblAtivo);
		
		comboBoxAtivo = new JComboBox();
		comboBoxAtivo.setModel(new DefaultComboBoxModel(new String[] {"Verdadeiro", "Falso"}));
		comboBoxAtivo.setSelectedIndex(0);
		comboBoxAtivo.setBounds(75, 33, 86, 20);
		getPainelDados().add(comboBoxAtivo);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaManterTipo frame = new JanelaManterTipo(new ControladorManterTipo());
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
		lista.add(getTextFieldNome().getText());
		lista.add(getComboBoxAtivo().getSelectedItem());
		return lista;
	}
	
	
	protected JTextField getTextFieldNome() {
		return textFieldNome;
	}
	protected JComboBox getComboBoxAtivo() {
		return comboBoxAtivo;
	}

	@Override
	public void setValoresFormulario(List<Object> valores) {

		getTextFieldNome().setText((String) valores.get(1));

		if(valores.get(2) != null && (Boolean) valores.get(2) == true){
			getComboBoxAtivo().setSelectedIndex(0);
		}
		else{
			getComboBoxAtivo().setSelectedIndex(1);
		}	
	}

	@Override
	public void limpaCampos() {
		getTextFieldNome().setText("");
		getComboBoxAtivo().setSelectedIndex(0);
	}



}
