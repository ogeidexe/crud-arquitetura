package controle;

import modelo.FakeTabela;
import modelo.Modelo;

public class FakeControle extends ControleCRUD {

	@Override
	public Modelo getNewObjeto() {
		return new FakeTabela();
	}

}
