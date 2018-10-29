package modelo;

import conversores.IntegerConversor;
import conversores.StringConversor;
import interfaces.IConversor;
import sun.awt.image.IntegerComponentRaster;
import util.Retorno;

import java.util.ArrayList;
import java.util.List;

public class Celular extends Modelo<Integer>{

    private String imei;
    private String marca;
    private String modelo;
    private String cor;
    private String ano;

    /**
     * Retorna o nome da tabela para ser usado para persisitir os dados
     * @return
     */
    @Override
    public String getModeloNome() {
        return "Celulares";
    }

    /**
     * Retorna o nome da chave primeira da tabela 'Celular'
     * @return
     */
    @Override
    public String getModeloPKNome() {
        return "pk";
    }

    /**
     * Retorna a lista de valor dos campos
     * @return
     */
    @Override
    public List<Object> getCamposValor() {

        ArrayList<Object> lista = new ArrayList<>();

        lista.add(this.getImei());
        lista.add(this.getMarca());
        lista.add(this.getModelo());
        lista.add(this.getCor());
        lista.add(this.getAno());

        lista.add(this.getPk());

        return lista;
    }

    /**
     * Caso a chave primaria for serial retorna true
     * Caso a chave primeira for auto increment retorna false
     * @return
     */
    @Override
    public boolean getUsarPkNaInsercao() {
        return false;
    }

    /**
     * Metodo reposnsavel por converter os dados da visao para o tipo de dado do banco de dados
     * @return
     */
    @Override
    public List<IConversor> getCamposConversor() {

        List<IConversor> list = new ArrayList<>();


        list.add(new StringConversor());

        list.add(new StringConversor());
        list.add(new StringConversor());
        list.add(new StringConversor());
        list.add(new StringConversor());

        list.add(new IntegerConversor());



        return list;
    }

    /**
     * Retorna uma lista com os nomes dos campos
     * @return
     */
    @Override
    public List<String> getCamposNome() {
        ArrayList<String> listaCampos = new ArrayList<>();

        listaCampos.add("imei");
        listaCampos.add("marca");
        listaCampos.add("modelo");
        listaCampos.add("cor");
        listaCampos.add("ano");

        listaCampos.add("pk");

        return listaCampos;
    }

    /**
     * Retorna os campos obrigatorios para preenchimento
     * @return
     */
    @Override
    protected List<String> getCamposObrigatorios() {

        ArrayList<String> lista = new ArrayList<>();

        lista.add("imei");
        lista.add("marca");
        lista.add("modelo");
        lista.add("cor");
        lista.add("ano");

        return lista;
    }

    /**
     * Usado para preencher o banco de dados ou a visao
     * @param list
     * @return
     */
    @Override
    protected Retorno setCamposValor(List<Object> list) {
        Retorno ret = new Retorno(true, "OK");

        try{

            this.setImei((String) list.get(0));
            this.setMarca((String) list.get(1));
            this.setModelo((String) list.get(2));
            this.setCor((String) list.get(3));
            this.setAno((String) list.get(4));

            this.setPk((Integer) list.get(5));

        }catch(Exception e){
            ret.setSucesso(false);
            ret.addMensagem("Erro ao configura campos, ERROR:"+e.getMessage());
        }

        return null;
    }

    /**
     * Retorna um objeto do tipo celular
     * @return
     */
    @Override
    public Modelo<?> getNovoObjeto() {
        return new Celular();
    }


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
