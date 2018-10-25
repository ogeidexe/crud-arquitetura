package controle;

import java.util.List;

import interfaces.IControladorCRUD;
import interfaces.IDAO;
import modelo.Modelo;
import modelo.Tipo;
import persistencia.DAOGeneric;
import util.Retorno;

public class ControladorManterTipo extends ControleCRUD {
	

	@Override
	public Modelo getNewObjeto() {
		return new Tipo();
	}

}
