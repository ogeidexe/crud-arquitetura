package interfaces;

import java.util.List;

import modelo.Modelo;
import util.Retorno;

public interface IDAO {
	public <T extends Modelo<?>> List<T> listar(T tab);
	public <T extends Modelo<?>> List<T> procurar(T tab);
	public Retorno incluir(Modelo<?> tab);
	public Retorno alterar(Modelo<?> tab);
	public Retorno remover(Modelo<?> objPk);
	public <T extends Modelo<?>> T getObjeto(T objPk);
	public boolean existir(Modelo<?> tab);
	
}
