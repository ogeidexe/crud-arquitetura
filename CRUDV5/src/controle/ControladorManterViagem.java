package controle;

import modelo.Viagem;
import modelo.Modelo;


public class ControladorManterViagem extends ControleCRUD {

	@Override
	public Modelo getNewObjeto() {
		return new Viagem();
	}

}
