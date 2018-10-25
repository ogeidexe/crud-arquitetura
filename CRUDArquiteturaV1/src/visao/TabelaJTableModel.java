package visao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Modelo;

public class TabelaJTableModel extends AbstractTableModel {
	private List<Modelo<?>> tabList;
	private Modelo<?> tab;
	int numeroColunas=0;
	int numeroLinhas=0;
	ArrayList<String> colunasNome = null; 



	public TabelaJTableModel(List<Modelo<?>> tabList,Modelo<?> tab) {
		this.setTabList(tabList);
		this.setTab(tab);
	}

	
	@Override
	public int getColumnCount() {
		if(numeroColunas==0){
			numeroColunas = this.getTab().getNumerosCampos();
		}
		return numeroColunas;
	}

	@Override
	public int getRowCount() {
		if(numeroLinhas==0){
			numeroLinhas = this.getTabList().size();
		}
		return numeroLinhas;
	}

	@Override
	public String getColumnName(int column) {
		if(colunasNome==null){
			colunasNome = new ArrayList<>();
			for (String campoNome : this.getTab().getCamposNome()) {
				colunasNome.add(campoNome);
			}
		}
		return colunasNome.get(column);
	}


	@Override
	public Object getValueAt(int linha, int coluna) {
		Object valor = this.getTabList().get(linha).getCamposValor().get(coluna);
		return valor;
	}
	// gets e sets


	public List<Modelo<?>> getTabList() {
		return tabList;
	}


	public void setTabList(List<Modelo<?>> tabList) {
		this.tabList = tabList;
	}


	public Modelo<?> getTab() {
		return tab;
	}


	public void setTab(Modelo<?> tab) {
		this.tab = tab;
	}



	

}
