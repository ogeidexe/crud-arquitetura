package visao;
import javax.swing.JButton;

import controle.ControladorManterCelular;
import controle.ControladorManterTipo;
import controle.ControladorManterViagem;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class Principal extends JanelaPrincipalModelo {

	public Principal() {
		
		JButton btnNewButton = new JButton("Tipo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirJanelaManterTipo();
			}
		});
		btnNewButton.setIcon(new ImageIcon(Principal.class.getResource("/icons/Notes.png")));
		btnNewButton.setSelectedIcon(new ImageIcon(Principal.class.getResource("/icons/Notes.png")));
		getToolBar().add(btnNewButton);
		
		JButton btnViagem = new JButton("Viagem");
		btnViagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaManterViagem();
			}
		});
		btnViagem.setIcon(new ImageIcon(Principal.class.getResource("/icons/Paste.png")));
		getToolBar().add(btnViagem);

		JButton btnCelular = new JButton("Celular");
		btnCelular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirJanelaManterCelular();
			}
		});
		btnCelular.setIcon(new ImageIcon(Principal.class.getResource("/icons/Add.png")));
		getToolBar().add(btnCelular);

		JMenuItem mntmManterViagem = new JMenuItem("Manter Viagem");
		mntmManterViagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirJanelaManterViagem();
			}			
		});
		getMnPrincipal().add(mntmManterViagem);
		
		JMenuItem mntmManterTipo = new JMenuItem("Manter Tipo");
		mntmManterTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirJanelaManterTipo();
			}			
		});

		getMnPrincipal().add(mntmManterTipo);
	}
	public void abrirJanelaManterTipo() {
		new JanelaManterTipo(new ControladorManterTipo()).setVisible(true);
	}

	public void abrirJanelaManterViagem() {
		new JanelaManterViagem(new ControladorManterViagem()).setVisible(true);
	}

	public void abrirJanelaManterCelular(){
		new JanelaManterCelular(new ControladorManterCelular()).setVisible(true);
	}
	public static void main(String[] args) {
		Principal janela  = new Principal();
		janela.setVisible(true);

	}


}
