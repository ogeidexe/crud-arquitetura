package modelo;

import java.util.ArrayList;
import java.util.List;

import interfaces.IConversor;
import persistencia.DAOGeneric;
import util.Retorno;

public abstract class Modelo<TIPOPK> {
	private TIPOPK pk;

	public TIPOPK getPk() {
		return pk;
	}

	public void setPk(TIPOPK pk2) {
		this.pk = pk2;
	}
	
	
	/** retorno o nome da tabela para utilizar na persitência
	 * @return nome da tabela
	 */
	public abstract String getModeloNome();
	/** retorno o nome da coluna da chave primaria
	 * @return
	 */
	public abstract String getModeloPKNome();
	/** retorna a lista de valor dos campos
	 * @return
	 */
	public abstract List<Object> getCamposValor();
	
	/** Obter o valor de um atributo pelo nome do atributo
	 * @param nome
	 * @return
	 */
	public Object getCampoValor(String nome) {
		List<String> camposNome = this.getCamposNome();
		if(!camposNome.contains(nome)) {
			throw new RuntimeException("Campo: "+nome+" não existe na tabela:"+this.getModeloNome());
		}
		List<Object> camposValor = this.getCamposValorUtil();
		return camposValor.get(this.getCampoIndice(nome));
	}	
	
	/** retorno o indice do campo pelo nome 
	 * @param nome
	 * @return
	 */
	public int getCampoIndice(String nome) {
		int indice = this.getIndiceColuna(nome);
		if(indice>=this.getCamposNome().size()) {
			throw new RuntimeException("Coluna: "+nome+" Não pertence a tabela:"+this.getClass().getName());
		}
		return indice;
	}
	
	/** retorna se é para utilizar a PK na inserção
	 * @return
	 */
	public abstract boolean getUsarPkNaInsercao();
	
	/** retorna uma lista com objetos conversores para converter
	 * um string no tipo do atributo.
	 * @return
	 */
	public abstract List<IConversor> getCamposConversor();
	
	public List<IConversor> getCamposConversorUtil(){
		List<IConversor> list = this.getCamposConversor();
		if(list.size()!=this.getNumerosCampos()){
			
			String msg = "O Método "+this.getClass().getName()+".getCamposConversor()"
					+ " deveria retornar uma lista com "+this.getNumerosCampos()
					+ " item/itens e retornou com "+list.size()+" item/itens!";
			throw new RuntimeException(msg);
			
		}
		return list;
	}
	
	public String getModeloPKNomeUtil() {
		if(this.getModeloPKNome()== null) {
		
		}
		String pkNome = this.getModeloPKNome();
		for(String campoNome: this.getCamposNome()) {
			if (campoNome.equals(pkNome)) {
				return pkNome;
			}
		}
		String msg = "O Método "+this.getClass().getName()+".getTabelaPKNome()"
				+ " está retornando um nome de coluna que não existe, "
				+ " valor retornado:"+pkNome+ " valores válidos: "+String.join(",",this.getCamposNome());
		throw new RuntimeException(msg);
		
	}
	public List<Object> getCamposValorUtil(){
		List<Object> list = this.getCamposValor();
		if(list.size()!=this.getNumerosCampos()){
			
			String msg = "O Método "+this.getClass().getName()+".getCamposValor()"
					+ " deveria retornar uma lista com "+this.getNumerosCampos()
					+ " item/itens e retornou com "+list.size()+" item/itens!";
			throw new RuntimeException(msg);
			
		}
		return list;
	}
	
	/** retorna uma lista com o nome dos campos
	 * @return
	 */
	public abstract List<String> getCamposNome();
	
	protected abstract List<String> getCamposObrigatorios();
	/*{//Seria necessário se não quisesse quebra o código
		List<String> list = new ArrayList<String>();
		return list;
	}*/
	
	public List<String> getCamposObrigatoriosUtil(){
		List<String> camposValidos = this.getCamposNome();
		List<String> camposObrigatorios = this.getCamposObrigatorios();
		
		for (String campoNome : camposObrigatorios) {
			boolean encontrou = false;
			for (String campoValido : camposValidos) {
				if(campoNome.equals(campoValido)){
					encontrou = true;
					break;
				}
			}
			if(encontrou== false){
				throw new RuntimeException(
						"Campo "+campoNome+
						" não é um nome válido para:"+
								this.getClass().getName());
			}
		}
		return camposObrigatorios;
				
	}
	/** metodo para configurar os valores do objeto.
	 * ele só é chamado se a lista tiver o número correto de valores
	 * @param list
	 * @return
	 */
	protected abstract Retorno setCamposValor(List<Object> list);
	
	/** metodo utilizado para retornar um novo objeto da tabela implementada
	 * @return
	 */
	public abstract Modelo<?> getNovoObjeto();
	
	public Retorno setCamposValorUtil(List<Object> list){
		Retorno ret = new Retorno(true,"");
		if(list==null){
			ret.setSucesso(false);
			ret.addMensagem("Necessário passar a lista de valores para configura objeto");
			return ret;
		}
		if(list.size()!=this.getNumerosCampos()){
			ret.setSucesso(false);
			ret.addMensagem("Necessário passar a lista de valores para configura objeto com " + this.getNumerosCampos());
			return ret;
		}
		//metodo abstrato que realmente configura os valores

		List<IConversor> listaConversores = this.getCamposConversorUtil();
		List<String> listaCamposNomes = this.getCamposNome();
		for (int i = 0; i < list.size(); i++) {
			Object valor = list.get(i);
			if(valor instanceof String){
				try{
					valor = listaConversores.get(i).converter((String)valor);
				}catch(RuntimeException e){
					ret = new Retorno(false,
							"Erro de Conversão no campo:"+listaCamposNomes.get(i)+", ERRO:"+e.getMessage());
					return ret;
					
				}
				list.set(i, valor);
			}
		}
		
		ret = this.setCamposValor(list);
		return ret;
	}
	
	
	public int getNumerosCampos(){
		return this.getCamposNome().size();
	}
	

	
	public String toString(){
		List<String> camposNome = this.getCamposNome();
		List<Object> camposValor = this.getCamposValor();
		String resultado = "";
		for(int i=0;i<camposNome.size();i++){
			resultado =resultado + ","+camposNome.get(i)+"="+camposValor.get(i);
		}
		return resultado.substring(1);
	}
	
	public <T extends Modelo<?>> T getDadosExtrangeiro(T tab){
		if(tab==null ) return null;
		tab = DAOGeneric.getIntancia().getObjeto(tab);
		return tab;
	}



	public int getColunaPK() {
		int indice = this.getIndiceColuna(this.getModeloPKNomeUtil());
		return indice;
	}

	private int getIndiceColuna(String nomeColuna) {
		int indice = 0;
		List<String> camposNome = this.getCamposNome();
		for (String coluna : camposNome) {
			if(coluna.equals(nomeColuna) == true){
				break;
			}
			indice++;
		}
		return indice;
		
	}
	public void limparColunas() {
		
		int qtdCampos = this.getCamposValorUtil().size();
		List<Object> valorVazios = new ArrayList<>();
		for (int i = 0; i < qtdCampos; i++) {
			valorVazios.add(null);			
		}
		this.setCamposValor(valorVazios);
	}
}
