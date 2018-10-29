package controle;

import java.util.List;

import interfaces.IControladorCRUD;
import interfaces.IDAO;
import modelo.Modelo;
import persistencia.DAOGeneric;
import util.Retorno;

public abstract class ControleCRUD implements IControladorCRUD {
	private IDAO dao;
	

	public ControleCRUD() {
		dao = DAOGeneric.getIntancia();
	}

	@Override
	public List<Modelo<?>> listar() {
		return dao.listar(this.getNewObjeto());
	}

	@Override
	public List<Modelo<?>> procurar(Modelo<?> tab) {
		List<Modelo<?>> list = dao.procurar(tab);
		return list;
	}

	@Override
	public Retorno incluir(Modelo<?> tab) {
		Retorno retorno = dao.incluir(tab);
		return retorno;
	}

	@Override
	public Retorno alterar(Modelo<?> tab) {
		Retorno retorno = dao.alterar(tab);
		return retorno;
	}

	@Override
	public Retorno remover(Modelo<?> objPk) {
		Retorno retorno = dao.remover(objPk);
		return retorno;
	}

	@Override
	public Modelo<?> getObjeto(Modelo<?> objPk) {
		return dao.getObjeto(objPk);
		
	}

	@Override
	public abstract Modelo<?> getNewObjeto();

}
