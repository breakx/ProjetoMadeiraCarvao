/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.fazenda;

import Controle.ControleFazenda;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class InserirFazendaCtrl {

    public InserirFazendaCtrl(ControleFazenda fazenda) {
        String query = "INSERT INTO fazenda (`id_fazenda`, `cod_estado`, `estado`, `cod_bloco`, `bloco`, `municipio`, `fazenda`, `projeto`) "
              + "VALUES (" + null
              + ", '" + fazenda.getCod_estado()
              + "', '" + fazenda.getEstado()
              + "', '" + fazenda.getCod_bloco()
              + "', '" + fazenda.getBloco()
              + "', '" + fazenda.getMunicipio()
              + "', '" + fazenda.getFazenda()
              + "', '" + fazenda.getProjeto()
              //+ "', '1"
              +"')";
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
    
}