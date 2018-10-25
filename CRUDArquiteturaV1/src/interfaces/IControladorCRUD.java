package interfaces;

import java.util.List;

import modelo.Modelo;
import util.Retorno;

public interface IControladorCRUD {
	public List<Modelo<?>> listar();
	public List<Modelo<?>> procurar(Modelo<?> tab);
	public Retorno incluir(Modelo<?> tab);
	public Retorno alterar(Modelo<?> tab);
	public Retorno remover(Modelo<?> objPk);
	public Modelo<?> getObjeto(Modelo<?> objPk);
	public Modelo<?> getNewObjeto();
}
