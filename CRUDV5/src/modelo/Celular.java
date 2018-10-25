package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import conversores.DateConversor;
import conversores.IntegerConversor;
import conversores.StringConversor;
import interfaces.IConversor;
import persistencia.DAOGeneric;
import util.EstatusViagem;
import util.Retorno;

public class Celular extends Modelo<Integer> {

	private String imei;
	private String marca;
	private String modelo;
	private String cor;
	private String ano;

	@Override
	public List<Object> getCamposValor() {

		ArrayList<Object> list = new ArrayList<>();

		list.add(this.getPk());
		list.add(this.getImei());
		list.add(this.getMarca());
		list.add(this.getModelo());
		list.add(this.getCor());
		list.add(this.getAno());

		return list;
	}
	
	@Override
	public Modelo getNovoObjeto() {
		return new Celular();
	}

	@Override
	public List<String> getCamposNome(){
		ArrayList<String> listNomes = new ArrayList<>();
		listNomes.add("pk");
		listNomes.add("dataSolicitacao");
		listNomes.add("dataAprovacaoSolicitacao");
		listNomes.add("dataPartida");
		listNomes.add("dataAcerto");
		listNomes.add("dataAprovacaoAcerto");
		listNomes.add("estatus");
		listNomes.add("motivo");
		listNomes.add("dias");
		listNomes.add("tipo");
		return listNomes ;
	}

	@Override
	protected Retorno setCamposValor(List<Object> list) {
		Retorno ret = new Retorno(true, "OK");
		try{

			this.setPk((Integer) list.get(0));
			this.setImei((String) list.get(1) );
			this.setMarca((String) list.get(2) );
			this.setModelo((String) list.get(3) );
			this.setCor((String) list.get(4) );
			this.setAno((String) list.get(5) );

			//tem que tratar para dados estrangeiros
			Tipo t = new Tipo();
			t.setPk((Integer)list.get(9)); 
			t = (Tipo) this.getDadosExtrangeiro(t);
			
			this.setTipo(t); 
		}catch(Exception e){
			ret.setSucesso(false);
			ret.addMensagem("Erro ao configura campos, ERROR:"+e.getMessage());
		}
				
		return ret;
	}

	@Override
	public String getModeloNome() {
		return "Celulares";
	}

	@Override
	public String getModeloPKNome() {
		return "pk";
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	@Override
	protected List<String> getCamposObrigatorios() {
		List<String> list = new ArrayList<>();
		list.add("dataSolicitacao");
		list.add("dias");
		return list;
	}

	@Override
	public List<IConversor> getCamposConversor() {
		ArrayList<IConversor> listConversor = new ArrayList<>();
		listConversor.add(new IntegerConversor());//pk
		listConversor.add(new DateConversor());
		listConversor.add(new DateConversor());
		listConversor.add(new DateConversor());
		listConversor.add(new DateConversor());
		listConversor.add(new DateConversor());
		listConversor.add(new IntegerConversor());
		listConversor.add(new StringConversor());
		listConversor.add(new IntegerConversor());
		listConversor.add(new IntegerConversor());
		return listConversor ;
	}

	@Override
	public boolean getUsarPkNaInsercao() {
		return false;
	}
}
