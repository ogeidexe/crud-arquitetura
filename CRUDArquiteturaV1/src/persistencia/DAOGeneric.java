package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IDAO;
import modelo.Modelo;
import util.Configuracoes;
import util.Retorno;

public class DAOGeneric implements IDAO {

	protected Connection con;
	private static DAOGeneric dao;

	public static DAOGeneric getIntancia() {
		if (dao == null) {
			dao = new DAOGeneric();
		}
		return dao;
	}

	private DAOGeneric() throws RuntimeException {
		Configuracoes conf = Configuracoes.getInstance();
		String driver = conf.getDriveBd();
		try {
			Class.forName(driver);
			// padrao jdbc ://nomeserver:porta/database
			con = DriverManager.getConnection(conf.getUrlConexao(), conf.getUsuarioBd(), conf.getSenhaBd());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			throw new RuntimeException("DAOFuncionarioSQL erro(1):" + e.getMessage());
		}
	}

	private String getListaCamposNomes(Modelo<?> tab) {
		return this.getListaCamposNomes(tab, false, true);
	}

	private String getListaCamposNomesInserir(Modelo<?> tab) {
		boolean removerPk = !tab.getUsarPkNaInsercao();
		return this.getListaCamposNomes(tab, removerPk, false);
	}
	
	

	/**
	 * @param tab
	 * @param removerPKname
	 *            remover a pk da lista.
	 * @param desconsiderarValor
	 *            n�o olhar se ter valor para incluir na lista
	 * @return
	 */
	private String getListaCamposNomes(Modelo<?> tab, boolean removerPKname, boolean desconsiderarValor) {
		String lista = "";
		String pkName = tab.getModeloPKNomeUtil();
		if (tab != null) {
			for (String campo : tab.getCamposNome()) {
				if (!removerPKname || !campo.equals(pkName)) {
					if (desconsiderarValor || tab.getCampoValor(campo) != null) {
						lista = lista + "," + campo;
					}

				}
			}
			lista = lista.substring(1);
		}
		return lista;
	}

	@Override
	public <T extends Modelo<?>> List<T> listar(T tab) {
		T aux = limparCamposExcetoPk(tab);
		return this.listarOuProcurar(aux, false);
	}

	@SuppressWarnings("unchecked")
	public <T extends Modelo<?>> T limparCamposExcetoPk(T tab) {
		T aux = (T) tab.getNovoObjeto();
		List<Object> list = tab.getCamposValorUtil();
		int pkIndice = tab.getCampoIndice(tab.getModeloPKNomeUtil());
		for (int i = 0; i < list.size(); i++) {
			if (i != pkIndice) {
				list.set(i, null);
			}
		}
		aux.limparColunas();
		aux.setCamposValorUtil(list);
		return aux;
	}

	private List<Object> getCamposValores(ResultSet rs) throws SQLException {
		ArrayList<Object> list = new ArrayList<>();
		int columnCount = rs.getMetaData().getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			list.add(rs.getObject(i));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T extends Modelo<?>> List<T> listarOuProcurar(T tab, boolean procurar) {
		if (tab == null) {
			String metodo = procurar ? "procurar" : "listar";
			throw new RuntimeException("Defina a Modelo para usar DAOGenric." + metodo);
		}
		String sql = "Select " + this.getListaCamposNomes(tab) + " from " + tab.getModeloNome();

		// busca apenas o selecionado se informar a pk no objeto
		String strWhere = "";
		strWhere = getStringWhereProcurar(tab);
		if (!strWhere.equals("")) {
			strWhere = " where " + strWhere;
			sql = sql + strWhere;
		}

		System.out.println("SQL:" + sql);
		PreparedStatement pst;
		ArrayList<T> lista = new ArrayList<>();
		try {
			pst = con.prepareStatement(sql);
			setPreparedStatementProcurar(tab, pst);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				T auxTab = (T) tab.getNovoObjeto();
				auxTab.setCamposValorUtil(this.getCamposValores(rs));
				lista.add(auxTab);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

		return lista;
	}

	@Override
	public <T extends Modelo<?>> List<T> procurar(T tab) {
		return this.listarOuProcurar(tab, true);
	}

	private void setPreparedStatementProcurar(Modelo<?> tab, PreparedStatement pst) throws SQLException {
		List<String> camposNome = tab.getCamposNome();
		List<Object> camposValores = tab.getCamposValorUtil();

		int indTab = 1;
		int indPS = 1;

		for (@SuppressWarnings("unused")
		String campoNome : camposNome) {
			Object campoValor = camposValores.get(indTab - 1);
			if (campoValor != null) {
				if (this.validarCampoPreechido(campoValor)) {
					if (campoValor instanceof String) {
						campoValor = ((String) campoValor).concat("%");
					} else {
						campoValor = trataValoresEspeciaisObjetoParaBanco(campoValor);
					}
					pst.setObject(indPS, campoValor);

					indPS++;
				}
			}
			indTab++;
		}

	}

	@SuppressWarnings("rawtypes")
	private boolean validarModeloPreenchida(Object campoValor) {
		if (campoValor != null && campoValor instanceof Modelo) {
			Modelo tab_aux = (Modelo) campoValor;
			if (tab_aux.getPk() != null) {
				return  !validarVazioSeString(tab_aux.getPk());//se n�o for string retorna falso, s� retorna verdadeiro se for string e for uma string vazia ""
			}
		}
		return false;
	}
	public boolean validarCampoPreechido(Object campoValor) {
		if(campoValor == null) {
			return false;
		}
		if(campoValor instanceof Modelo && !validarModeloPreenchida(campoValor)) {
			return false;
		}
		if(campoValor instanceof String && validarVazioSeString(campoValor)) {
			return false;
		}
		return true;
	}

	private String getStringWhereProcurar(Modelo<?> tab) {
		String strWhere = "";
		List<String> camposNome = tab.getCamposNome();
		List<Object> camposValores = tab.getCamposValorUtil();

		int indTab = 1;

		for (String campoNome : camposNome) {
			Object campoValor = camposValores.get(indTab - 1);
			if (campoValor != null)
				if (this.validarCampoPreechido(campoValor)) {
					if (campoValor instanceof String) {
						strWhere = strWhere + " and " + campoNome + " like ?";
					} else {
						strWhere = strWhere + " and " + campoNome + " = ? ";
					}
				}
			indTab++;
		}
		if (strWhere.length() > 0) {
			strWhere = strWhere.substring(4);
		}
		return strWhere;
	}

	private Retorno validarIncluir(Modelo<?> tab) {
		Retorno ret = new Retorno(true, null);
		ret = this.validarCamposObrigatorios(tab);
		return ret;
	}

	private Retorno validarAlterar(Modelo<?> tab) {
		Retorno ret = new Retorno(true, null);
		ret = this.validarCamposObrigatorios(tab);
		return ret;
	}

	private Retorno validarCamposObrigatorios(Modelo<?> tab) {
		Retorno ret = new Retorno(true, null);
		List<String> camposObrigatorios = tab.getCamposObrigatoriosUtil();
		List<Object> camposValores = tab.getCamposValorUtil();
		List<String> camposNomes = tab.getCamposNome();
		int ind = 0;
		for (String nomeCampo : camposNomes) {
			for (String nomeObrigatorio : camposObrigatorios) {
				if (nomeCampo.equals(nomeObrigatorio)) {
					Object valor = camposValores.get(ind);
					if (valor == null || validarVazioSeString(valor)) {
						ret.setSucesso(false);
						ret.addMensagem("Campo:" + nomeCampo + " n�o est� preenchido");
						//return ret;
					}

				}
			}
			ind++;
		}
		return ret;
	}

	private boolean validarVazioSeString(Object valor) {
		return valor instanceof String && ((String) valor).equals("");
	}

	@Override
	public Retorno incluir(Modelo<?> tab) {
		if (tab == null) {
			throw new RuntimeException("Defina a Modelo para usar DAOGenric.incluir");
		}
		Retorno ret = validarIncluir(tab);
		if (!ret.isSucesso()) {
			return ret;
		}
		String camposNome = this.getListaCamposNomesInserir(tab);
		// string com ?,? para cada valor de campo(sem incluir a PK)
		String valores = getStringValoresPreparedstatementInsert(tab);

		String sql = "insert into " + tab.getModeloNome() + "(" + camposNome + ")" + " values(" + valores + ")";
		System.out.println("SQL Incluir:" + sql);
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			// set os valores para ?,? sem incluir a PK
			setPreparedstatementValoresInsert(tab, ps);
			int result = ps.executeUpdate();
			if (result != 1) {
				ret.setSucesso(false);
				ret.addMensagem("Erro ao Incluir");
			}

		} catch (SQLException e) {
			ret.setSucesso(false);
			ret.addMensagem("ERROSQL:" + e.getMessage());
		}
		return ret;
	}

	/**
	 * configura os valores do preparedstatement pulando o valor da PK.
	 * 
	 * @param tab
	 * @param ps
	 * @return retorna um indice da proximo valor a ser configurado na string de ?
	 * @throws SQLException
	 */
	private int setPreparedstatementValores(Modelo<?> tab, PreparedStatement ps, boolean usarPK) throws SQLException {
		int indPk = getCampoPkIndice(tab);
		// percorre setando os valores e pula o indice da PK
		int indTab = 1;
		int indPS = 1;

		for (Object valor : tab.getCamposValorUtil()) {
			// s� seta o valor se o indice n�o for o da pk
			// Tratamento para s� utilizar colunas preenchidas
			if ((valor != null) && (indTab != indPk || usarPK)) {
				valor = trataValoresEspeciaisObjetoParaBanco(valor);
				ps.setObject(indPS, valor);
				//System.out.println("Campo ID:" + indPS + " Valor:" + valor);
				indPS++;
			}
			indTab++;
		}
		return indPS;
	}

	/**
	 * configura os valores do preparedstatement para inser��o.
	 * 
	 * @param tab
	 * @param ps
	 * @return retorna um indice da proximo valor a ser configurado na string de ?
	 * @throws SQLException
	 */
	private int setPreparedstatementValoresInsert(Modelo<?> tab, PreparedStatement ps) throws SQLException {
		boolean usarPk = tab.getUsarPkNaInsercao();
		return setPreparedstatementValores(tab, ps, usarPk);
	}

	/**
	 * configura os valores do preparedstatement para Update.
	 * 
	 * @param tab
	 * @param ps
	 * @return retorna um indice da proximo valor a ser configurado na string de ?
	 * @throws SQLException
	 */
	private int setPreparedstatementValoresUpdate(Modelo<?> tab, PreparedStatement ps) throws SQLException {
		return setPreparedstatementValores(tab, ps, false);
	}

	private Object trataValoresEspeciaisObjetoParaBanco(Object valor) {
		if (valor instanceof java.util.Date) {
			valor = new java.sql.Date(((java.util.Date) valor).getTime());
		} else if (valor instanceof Modelo) {
			valor = ((Modelo<?>) valor).getPk();
		} else if (valor instanceof Enum<?>) {
			valor = ((Enum<?>) valor).ordinal();
		}
		return valor;
	}

	private String getStringValoresPreparedstatementInsert(Modelo<?> tab) {
		List<Object> camposValor = tab.getCamposValorUtil();
		/*
		 * int redutor = 3; if (tab.getUsarPkNaInsercao()) { redutor = 1; }
		 */
		int qtdCampos = camposValor.size();// - redutor;
		String valores = "";
		for (int i = 0; i < qtdCampos; i++) {
			if (tab.getCamposValorUtil().get(i) != null) {
				valores = valores + ",?";
			}
		}
		//System.out.println("getStringValoresPreparedstatementInsert Valores antes:" + valores);
		valores = valores.substring(1);
		//System.out.println("getStringValoresPreparedstatementInsert Valores depois:" + valores);
		return valores;
	}

	private String getStringValoresPreparedstatementUpdate(Modelo<?> tab) {
		String sqlSet = "";
		int indPk = getCampoPkIndice(tab);
		// percorre setando os valores e pula o indice da PK
		int indTab = 1;
		@SuppressWarnings("unused")
		int indPS = 1;

		for (String campoNome : tab.getCamposNome()) {
			// s� seta o valor se o indice n�o for o da pk
			if ((tab.getCampoValor(campoNome) != null) && indTab != indPk) {
				sqlSet = sqlSet + "," + campoNome + " = ? ";
				indPS++;
			}
			indTab++;
		}

		sqlSet = sqlSet.substring(1);
		return sqlSet;
	}

	public int getCampoPkIndice(Modelo<?> tab) {
		// verifica o indice da PK para ser pulado
		int indPk = 1;
		for (String campoNome : tab.getCamposNome()) {
			if (campoNome.equals(tab.getModeloPKNomeUtil())) {
				break;
			}
			indPk++;
		}
		return indPk;
	}

	@Override
	public Retorno alterar(Modelo<?> tab) {
		if (tab == null || tab.getPk() == null) {
			throw new RuntimeException("Defina a Modelo para usar DAOGenric.alterar");
		}

		Retorno ret = validarAlterar(tab);
		if (!ret.isSucesso()) {
			return ret;
		}
		String sqlValores = getStringValoresPreparedstatementUpdate(tab);

		String sql = "update " + tab.getModeloNome() + " " + "set " + sqlValores + "" + " where "
				+ tab.getModeloPKNomeUtil() + " = ?";
		System.out.println("SQL Update:" + sql);

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			int indInsert = setPreparedstatementValoresUpdate(tab, ps);
			ps.setObject(indInsert, tab.getPk());
			//System.out.println("Campo ID:" + indInsert + " Valor:" + tab.getPk().toString());
			int ok = ps.executeUpdate();
			if (ok != 1) {
				ret.setSucesso(false);
				ret.addMensagem("ERRO: nenhuma linha alterada");
			}
		} catch (SQLException e) {
			ret.setSucesso(false);
			ret.addMensagem("ERRO UPDATE:" + e.getMessage());
		}

		return ret;
	}

	@Override
	public Retorno remover(Modelo<?> objPk) {
		if (objPk == null || objPk.getPk() == null) {
			throw new RuntimeException("Defina o Modelo para usar DAOGenric.remover");
		}
		Retorno ret = new Retorno(true, null);
		String sql = "delete from " + objPk.getModeloNome() + "" + " where " + objPk.getModeloPKNomeUtil() + " = ?";
		System.out.println(sql);

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1, objPk.getPk());

			int ok = ps.executeUpdate();
			if (ok != 1) {
				ret.setSucesso(false);
				ret.addMensagem("nenhuma linha excluida");
			}
		} catch (SQLException e) {
			ret.setSucesso(false);
			ret.addMensagem("EERRO ao excluir:" + e.getMessage());
		}

		return ret;
	}

	@Override
	public <T extends Modelo<?>> T getObjeto(T objPk) {
		if (objPk == null || objPk.getPk() == null) {
			return null;
		}
		List<T> tab = this.listar(objPk);
		if (tab.size() == 1) {
			return tab.get(0);
		}
		return null;
	}

	@Override
	public boolean existir(Modelo<?> tab) {
		Modelo<?> t = this.getObjeto(tab);
		if (t != null)
			return true;
		return false;
	}
}
