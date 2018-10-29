package controle;


import modelo.Celular;
import modelo.Modelo;

public class ControladorManterCelular extends ControleCRUD {

    @Override
    public Modelo getNewObjeto() {
        return new Celular();
    }
}
