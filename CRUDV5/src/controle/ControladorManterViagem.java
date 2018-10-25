package controle;

import modelo.Modelo;
import modelo.Viagem;

public class ControladorManterViagem extends ControleCRUD {

	@Override
	public Modelo getNewObjeto() {
		return new Viagem();
	}

}
