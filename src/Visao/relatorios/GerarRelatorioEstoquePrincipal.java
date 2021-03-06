/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.relatorios;

import Controle.ControlePrincipal;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.carvao.GerenciarCarvaoForno;
import Visao.carvao.InserirMadeiraForno;
import Visao.expedircarvao.GerenciarEnvioCarvao;
import Visao.expedircarvao.InserirEnvioCarvao;
import Visao.login.Login;
import Visao.madeira.GerenciarMadeiraPraca;
import Visao.madeira.InserirMadeiraPraca;
import Visao.usuario.GerenciarUsuarios;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Cristiano GD
 */
public class GerarRelatorioEstoquePrincipal extends javax.swing.JFrame {
    private ArrayList linhas;
    private String[] colunas;
    private int tamanho;
    private String dado;
    private float totalMadeiraPraca;
    private float totalMadeiraForno;
    private float totalCarvaoPraca;
    private float totalMDCTransp;
    
    /**
     * Creates new form GerarRelatorioEstoquePrincipal
     * @throws java.sql.SQLException
     */
    public GerarRelatorioEstoquePrincipal() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        ChangeName();
        _carregarProjetos();
        //PreencherTabela();
        PreencherTabelaFiltrada();
    }
    
    private void _carregarProjetos(){
        jComboBoxProjeto.addItem("I");
        jComboBoxProjeto.addItem("II");
        jComboBoxProjeto.addItem("III");  
        jComboBoxProjeto.addItem("IV"); 
        jComboBoxProjeto.addItem("V"); 
        jComboBoxProjeto.addItem("VI"); 
        jComboBoxProjeto.addItem("VII"); 
        jComboBoxProjeto.addItem("VIII"); 
        jComboBoxProjeto.addItem("IX"); 
        jComboBoxProjeto.addItem("X");  
        _carregarFazendas();
    }
    
    private void _carregarFazendas(){ 
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT fazenda FROM estoque_principal";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        jComboBoxFazenda.addItem("-");
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<jComboBoxFazenda.getItemCount(); j++) {
                    if (jComboBoxFazenda.getItemAt(j).equals(rs.getString("fazenda"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("fazenda"));
                    jComboBoxFazenda.addItem(rs.getString("fazenda"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao gerar lista de fazendas! "+ex);
        }
        _carregarTalhoes();
    }
    
    private void _carregarTalhoes(){ 
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        query = "SELECT talhao FROM estoque_principal";
        //JOptionPane.showMessageDialog(null, "Teste!" + query);
        rs = con.consultaSql(query);
        jComboBoxTalhao.addItem("-");
        try {
            while(rs.next()){
                int i=0;
                for (int j=0; j<jComboBoxTalhao.getItemCount(); j++) {
                    if (jComboBoxTalhao.getItemAt(j).equals(rs.getString("talhao"))) {
                        i++; 
                        //System.out.println("i: "+i);       
                    }
                }
                if(i==0){
                    //System.out.println("Add: "+i+" f "+rs.getString("fazenda"));
                    jComboBoxTalhao.addItem(rs.getString("talhao"));
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao gerar lista de talhões! "+ex);
        }
    }
    
    private void PreencherTabelaFiltrada(){  
        //JOptionPane.showMessageDialog(null, "Size! " + jListFiltrar.getSelectedIndices().length + jListFiltrar.getModel().getSize());
        Locale brasil = new Locale ("pt", "BR");
        DecimalFormat decformat = new DecimalFormat ("#,##0.00", new DecimalFormatSymbols (brasil));
        ConexaoBD con = ConexaoBD.getConexao(0);
        String query;
        ResultSet rs;
        String whereSql;
        dado = jListFiltrar.getSelectedValuesList().toString();
        dado = dado.replaceAll("[\\[\\]]", "");               
        linhas = new ArrayList();
        // busca todos os campos
        if(dado.equals("-")){
            jListFiltrar.clearSelection();
        }
        
        //System.out.println("Dado sql: "+dado);
        //verifica se algum campo foi selecionado e defini quantidade, senao defini total
        if(jListFiltrar.getSelectedIndices().length>0){
            tamanho = jListFiltrar.getSelectedIndices().length;
        }else{
            tamanho = jListFiltrar.getModel().getSize()-1;
        }
        
        //defini quantidade de colunas
        colunas = new String[tamanho];
        
        //carrega nomes das colunas selecionadas ou todas.
        for(int i = 0; i < tamanho; i++)
        {
            if(tamanho<jListFiltrar.getModel().getSize()-1){
               //System.out.println("i["+i+"]: "+jListFiltrar.getSelectedValuesList().get(i));
               colunas[i] = (String) jListFiltrar.getSelectedValuesList().get(i);
            }else{
               //System.out.println(jListFiltrar.getModel().getElementAt(i+1));
               colunas[i] = (String) jListFiltrar.getModel().getElementAt(i+1);
            }
        }
        
        //Controle e definição das variaveis da clausula where like. Filtros
        String filtro_upc = String.valueOf(ControlePrincipal.upc_u);
        String filtro_proj;
        String filtro_faz;  
        String filtro_talhao;       
        
        if(jComboBoxProjeto.getSelectedItem().equals("-")){
            filtro_proj="";
        }else{
            filtro_proj = jComboBoxProjeto.getSelectedItem().toString();
        }
        
        if(jComboBoxFazenda.getSelectedItem().equals("-")){
            filtro_faz="";
        }else{
            filtro_faz=jComboBoxFazenda.getSelectedItem().toString();
        }
        
        if(jComboBoxTalhao.getSelectedItem().equals("-")){
            filtro_talhao="";
        }else{
            filtro_talhao=jComboBoxTalhao.getSelectedItem().toString();
        }
        
        //faz busca a partir dos filtros acima        
        whereSql = "where ";        
        if(!filtro_upc.equals("")){
            whereSql += "upc = '"+filtro_upc+"'";
        }
        //System.out.println("whereSql: " + whereSql.length());
        
        if(!filtro_proj.equals("")){
            if(whereSql.length()>=15){
                whereSql += " and projeto = '"+filtro_proj+"'";
            }else{
                whereSql += "projeto = '"+filtro_proj+"'";
            }
        }
        
        if(!filtro_faz.equals("")){
            if(whereSql.length()>=15){
                whereSql += " and fazenda = '"+filtro_faz+"'";
            }else{
                whereSql += "fazenda = '"+filtro_faz+"'";
            }
        }
        
        if(!filtro_talhao.equals("")){
            if(whereSql.length()>=15){
                whereSql += " and talhao = '"+filtro_talhao+"'";
            }else{
                whereSql += "talhao = '"+filtro_talhao+"'";
            }
        }
        //System.out.println("whereSql: " + whereSql.length());
        
        if(whereSql.length()<7){
            whereSql = "";
        }
        
        if(tamanho<jListFiltrar.getModel().getSize()-1){
            query = "SELECT "+dado+" FROM estoque_principal "+whereSql;
        }else{
            query = "SELECT * FROM estoque_principal "+whereSql;
        }        
        //System.out.printf("query: "+ query); 
        
        //carrega dados do banco de dados dependendo da consulta sql
        rs = con.consultaSql(query);
        
        totalMadeiraPraca=0;
        totalMadeiraForno=0;
        totalCarvaoPraca=0;
        totalMDCTransp=0;
        try {            
            while(rs.next()){
                //cria um objeto coluna de acordo com as colunas selecionadas para cada linha encontrada na consulta
                Object[] coluna = new Object[tamanho];
                
                //carrega em cada coluna seu respectivo valor do banco de dados referente a sua coluna.
                for(int i = 0; i < tamanho; i++)
                {
                    coluna[i] = rs.getString(colunas[i]);
                    //System.out.println("Add Dados ["+i+"]: "+ob[i]);
                }               
                //System.out.printf("\nCalculo m3ha: "+ m3_haMedia);
                
                //adiciona a cada linha os valores de cada objeto coluna
                linhas.add(coluna);
                
                //volume total madeira_praca
                if(rs.getString("madeira_praca")!=null){
                    totalMadeiraPraca += Float.valueOf(rs.getString("madeira_praca"));
                }
                
                //volume total madeira_forno
                if(rs.getString("madeira_forno")!=null){
                    totalMadeiraForno += Float.valueOf(rs.getString("madeira_forno"));
                }
                
                //volume total carvao_praca
                if(rs.getString("carvao_praca")!=null){
                    totalCarvaoPraca += Float.valueOf(rs.getString("carvao_praca"));
                }
                
                //volume total mdc_transp
                if(rs.getString("mdc_transp")!=null){
                    totalMDCTransp += Float.valueOf(rs.getString("mdc_transp"));
                }
            }
            jLabelVolTotalMadPraca.setText("Volume total madeira praça: "+decformat.format(totalMadeiraPraca)+" m³");  
            jLabelVolTotalMadForno.setText("Volume total madeira forno: "+decformat.format(totalMadeiraForno)+" m³"); 
            jLabelVolTotalCarvPraca.setText("Volume total carvão praça: "+decformat.format(totalCarvaoPraca)+" m³"); 
            jLabelVolTotalMDCTransp.setText("Volume total mdc transportado: "+decformat.format(totalMDCTransp)+" m³"); 
            //System.out.printf("\nCalculo 2 m3ha: "+ m3_haMedia+ "linhas "+linhas.size());
            /*int n = dados.size(); 
            for (int i=0; i<n; i++) { 
                System.out.printf("Posição %d- %s\n", i, dados.get(i)); 
            } */
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoqueBasico.class.getName()).log(Level.SEVERE, null, "Erro ao Preencher Tabela Filtrada ! "+ex);
            JOptionPane.showMessageDialog(null, "Erro ao Preencher Tabela Filtrada ! "+ex);
        }
        
        MontarTabela();        
        con.fecharConexao();
    }
    
    private void MontarTabela(){
        //monta a tabela com as respectivas linhas e colunas
        GerarTabela modelo = new GerarTabela(linhas, colunas);
        jTableRelatorioEstoquePrincipal.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }     
            if(i>10 && ControlePrincipal.tipo_u.equals("op_bal")){
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setMinWidth(0);     
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(0);  
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setMaxWidth(0);
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setResizable(false);
            }else if(i>13 && ControlePrincipal.tipo_u.equals("op_scv")){
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setMinWidth(0);     
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setPreferredWidth(0);  
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setMaxWidth(0);
                jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(i).setResizable(false);
            }
            //System.out.println("Indice: "+i+" - "+ colunas[i]);
        }           
        jTableRelatorioEstoquePrincipal.getTableHeader().setReorderingAllowed(false);
        jTableRelatorioEstoquePrincipal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //duplo click
        jTableRelatorioEstoquePrincipal.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    //System.out.println("duplo-clique detectado");
                    SelecionarTalhao();
                }
            }
        });
        //RenomearColunas();
    }
    
    private void RenomearColunas(){
        /*for(int i=0;i<colunas.length;i++){
            System.out.println("Indice: "+i+" - "+ colunas[i]);
        }*/
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(0).setHeaderValue("Estado");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(1).setHeaderValue("Bloco");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(2).setHeaderValue("Municipio");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(3).setHeaderValue("Fazenda");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(4).setHeaderValue("Projeto");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(5).setHeaderValue("UPC");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(6).setHeaderValue("Talhão");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(7).setHeaderValue("Área");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(8).setHeaderValue("Material Genetico");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(9).setHeaderValue("M³ por Ha.");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(10).setHeaderValue("Talhadia");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(11).setHeaderValue("Ano Rotação");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(12).setHeaderValue("Data Plantio");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(13).setHeaderValue("Data 1ª Rotação");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(14).setHeaderValue("Data 2ª Rotação");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(15).setHeaderValue("Data 3ª Rotação");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(16).setHeaderValue("Idade 1º Corte");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(17).setHeaderValue("Idade 2º Corte");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(18).setHeaderValue("Idade 3º Corte");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(19).setHeaderValue("Idade Hoje");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(20).setHeaderValue("Desbrota");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(21).setHeaderValue("Categoria");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(22).setHeaderValue("Situação");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(23).setHeaderValue("Dias de Secagem");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(24).setHeaderValue("IMA");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(25).setHeaderValue("MDC por Ha.");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(26).setHeaderValue("Densidade da Madeira");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(27).setHeaderValue("Densidade do Carvão");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(28).setHeaderValue("Madeira(t/ha)");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(29).setHeaderValue("Carvão(t/ha)");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(30).setHeaderValue("Id Operario");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(31).setHeaderValue("Data Estoque");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(32).setHeaderValue("Vol. Mad. Estimada");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(33).setHeaderValue("Vol. Mad. Transportada");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(34).setHeaderValue("Vol. Mad. Balanço");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(35).setHeaderValue("MDC Estimado");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(36).setHeaderValue("MDC Produzido");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(37).setHeaderValue("MDC Balanço");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(38).setHeaderValue("Madeira(t) Estimada");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(39).setHeaderValue("Madeira(t) Transportada");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(40).setHeaderValue("Madeira(t) Balanço");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(41).setHeaderValue("Carvão(t) Estimado");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(42).setHeaderValue("Carvão(t) Produzido");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(43).setHeaderValue("Carvão(t) Balanço");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(44).setHeaderValue("Madeira(m³) em Praça");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(45).setHeaderValue("Carvão(m³) Praça");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(46).setHeaderValue("Madeira(m³) Enfornada");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(47).setHeaderValue("MDC Transportado");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(48).setHeaderValue("Carvão(t) Transportado");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(49).setHeaderValue("Rend. Volumétrico Estimado");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(50).setHeaderValue("Rend. Volumétrico Real");
        jTableRelatorioEstoquePrincipal.getColumnModel().getColumn(51).setHeaderValue("Fator de Empilalhemto");
    }
    
    private void SelecionarTalhao(){
        if(jTableRelatorioEstoquePrincipal.getSelectedRow()>=0) {
            int linha = jTableRelatorioEstoquePrincipal.getSelectedRow();
            ControlePrincipal.id_estoque_principal = jTableRelatorioEstoquePrincipal.getValueAt(linha, 31).toString();
            ControlePrincipal.municipio = jTableRelatorioEstoquePrincipal.getValueAt(linha, 2).toString();
            ControlePrincipal.fazenda = jTableRelatorioEstoquePrincipal.getValueAt(linha, 3).toString();
            ControlePrincipal.projeto = jTableRelatorioEstoquePrincipal.getValueAt(linha, 4).toString();
            ControlePrincipal.upc = Integer.parseInt(jTableRelatorioEstoquePrincipal.getValueAt(linha, 5).toString());
            ControlePrincipal.talhao = Integer.parseInt(jTableRelatorioEstoquePrincipal.getValueAt(linha, 6).toString()); 
            ControlePrincipal.material_genetico = jTableRelatorioEstoquePrincipal.getValueAt(linha, 8).toString(); 
            ControlePrincipal.fator_empilalhemto = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 9).toString());
            ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 10).toString());
                        
            switch (ControlePrincipal.tipo_u) {
                case "op_bal":
                    ControlePrincipal.densidade_madeira = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 14).toString());
                    ControlePrincipal.vol_mad_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 16).toString());
                    ControlePrincipal.vol_mad_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 17).toString());
                    ControlePrincipal.vol_mad_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 18).toString());
                    ControlePrincipal.mad_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 22).toString());
                    ControlePrincipal.mad_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 23).toString());
                    ControlePrincipal.mad_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 24).toString());
                    //ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 10).toString());
                    //ControlePrincipal.fator_empilalhemto = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 9).toString());
                    ControlePrincipal.situacao = jTableRelatorioEstoquePrincipal.getValueAt(linha, 7).toString();
                    if(ControlePrincipal.situacao.equals("Empilhado")){
                        if(ControlePrincipal.fator_empilalhemto>0){
                            //JOptionPane.showMessageDialog(null, "InserirMadeiraPraca");
                            new InserirMadeiraPraca().setVisible(true);
                            this.setVisible(false);
                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem fator empilhamento definido!");
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Talhão não empilhado!");
                    }   
                    break;
                case "op_scv":
                    ControlePrincipal.densidade_carvao = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 15).toString());
                    ControlePrincipal.mdc_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 19).toString());
                    ControlePrincipal.mdc_prod = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 20).toString());
                    ControlePrincipal.mdc_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 21).toString());
                    ControlePrincipal.carv_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 25).toString());
                    ControlePrincipal.carv_ton_prod = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 26).toString());
                    ControlePrincipal.carv_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 27).toString());
                    //ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 10).toString());
                    ControlePrincipal.carvao_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 11).toString());
                    ControlePrincipal.madeira_forno = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 12).toString());
                    ControlePrincipal.rend_grav_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 29).toString());
                    ControlePrincipal.carv_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 30).toString());
                    if(ControlePrincipal.madeira_praca>0 && ControlePrincipal.condicao_carvao.equals("forno")){
                        //JOptionPane.showMessageDialog(null, "InserirMadeiraForno");
                        new InserirMadeiraForno().setVisible(true);
                        this.setVisible(false);
                        dispose();
                    }else if(ControlePrincipal.carvao_praca>0 && ControlePrincipal.condicao_carvao.equals("transporte")){
                        //JOptionPane.showMessageDialog(null, "InserirEnvioCarvao");
                        new InserirEnvioCarvao().setVisible(true);
                        this.setVisible(false);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem estoque na praça!");
                    }   
                    break;
                    case "op_ger":
                        if(ControlePrincipal.tipo_estoque.equals("madeira")){
                            ControlePrincipal.densidade_madeira = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 14).toString());
                            ControlePrincipal.vol_mad_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 16).toString());
                            ControlePrincipal.vol_mad_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 17).toString());
                            ControlePrincipal.vol_mad_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 18).toString());
                            ControlePrincipal.mad_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 22).toString());
                            ControlePrincipal.mad_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 23).toString());
                            ControlePrincipal.mad_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 24).toString());
                            //ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 10).toString());
                            //ControlePrincipal.fator_empilalhemto = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 9).toString());
                            ControlePrincipal.situacao = jTableRelatorioEstoquePrincipal.getValueAt(linha, 7).toString();
                            if(ControlePrincipal.situacao.equals("Empilhado")){
                                if(ControlePrincipal.fator_empilalhemto>0){
                                    //JOptionPane.showMessageDialog(null, "InserirMadeiraPraca");
                                    new InserirMadeiraPraca().setVisible(true);
                                    this.setVisible(false);
                                    dispose();
                                }else {
                                    JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem fator empilhamento definido!");
                                }
                            }else {
                                JOptionPane.showMessageDialog(null, "Talhão não empilhado!");
                            }         
                        }else{
                            ControlePrincipal.densidade_carvao = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 15).toString());
                            ControlePrincipal.mdc_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 19).toString());
                            ControlePrincipal.mdc_prod = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 20).toString());
                            ControlePrincipal.mdc_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 21).toString());
                            ControlePrincipal.carv_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 25).toString());
                            ControlePrincipal.carv_ton_prod = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 26).toString());
                            ControlePrincipal.carv_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 27).toString());
                            //ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 10).toString());
                            ControlePrincipal.carvao_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 11).toString());
                            ControlePrincipal.madeira_forno = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 12).toString());
                            ControlePrincipal.rend_grav_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 29).toString());
                            ControlePrincipal.carv_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 30).toString());
                            if(ControlePrincipal.madeira_praca>0 && ControlePrincipal.condicao_carvao.equals("forno")){
                                //JOptionPane.showMessageDialog(null, "InserirMadeiraForno");
                                new InserirMadeiraForno().setVisible(true);
                                this.setVisible(false);
                                dispose();
                            }else if(ControlePrincipal.carvao_praca>0 && ControlePrincipal.condicao_carvao.equals("transporte")){
                                //JOptionPane.showMessageDialog(null, "InserirEnvioCarvao");
                                new InserirEnvioCarvao().setVisible(true);
                                this.setVisible(false);
                                dispose();
                            }else {
                                JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem estoque na praça!");
                            }   
                        }
                    break;
            }   
            /*if(ControlePrincipal.tipo_u.equals("op_bal") || ControlePrincipal.tipo_u.equals("op_ger")){
                ControlePrincipal.densidade_madeira = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 14).toString());
                ControlePrincipal.vol_mad_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 16).toString());
                ControlePrincipal.vol_mad_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 17).toString());
                ControlePrincipal.vol_mad_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 18).toString());
                ControlePrincipal.mad_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 22).toString());
                ControlePrincipal.mad_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 23).toString());
                ControlePrincipal.mad_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 24).toString());
                //ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 10).toString());
                //ControlePrincipal.fator_empilalhemto = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 9).toString());
                ControlePrincipal.situacao = jTableRelatorioEstoquePrincipal.getValueAt(linha, 7).toString();
                if(ControlePrincipal.situacao.equals("Empilhado")){
                    if(ControlePrincipal.fator_empilalhemto>0){
                        //JOptionPane.showMessageDialog(null, "InserirMadeiraPraca");
                        new InserirMadeiraPraca().setVisible(true);
                        this.setVisible(false);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem fator empilhamento definido!");
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Talhão não empilhado!");
                }
            }else if(ControlePrincipal.tipo_u.equals("op_scv") || ControlePrincipal.tipo_u.equals("op_ger")){
                ControlePrincipal.densidade_carvao = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 15).toString());
                ControlePrincipal.mdc_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 19).toString());
                ControlePrincipal.mdc_prod = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 20).toString());
                ControlePrincipal.mdc_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 21).toString());
                ControlePrincipal.carv_ton_estimado = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 25).toString());
                ControlePrincipal.carv_ton_prod = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 26).toString());
                ControlePrincipal.carv_ton_balanco = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 27).toString());
                //ControlePrincipal.madeira_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 10).toString());
                ControlePrincipal.carvao_praca = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 11).toString());
                ControlePrincipal.madeira_forno = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 12).toString());
                ControlePrincipal.rend_grav_real = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 29).toString());
                ControlePrincipal.carv_ton_transp = Float.parseFloat(jTableRelatorioEstoquePrincipal.getValueAt(linha, 30).toString());
                if(ControlePrincipal.madeira_praca>0 && ControlePrincipal.condicao_carvao.equals("forno")){
                    //JOptionPane.showMessageDialog(null, "InserirMadeiraForno");
                    new InserirMadeiraForno().setVisible(true);
                    this.setVisible(false);
                    dispose();
                }else if(ControlePrincipal.carvao_praca>0 && ControlePrincipal.condicao_carvao.equals("transporte")){
                    //JOptionPane.showMessageDialog(null, "InserirEnvioCarvao");
                    new InserirEnvioCarvao().setVisible(true);
                    this.setVisible(false);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "Talhão "+ControlePrincipal.talhao+" sem estoque na praça!");
                }
            }*/
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void ChangeName(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    }
    
    private void VoltarMenu(){        
        switch (ControlePrincipal.tipo_u) {
            case "op_bal":
                try {
                    new GerenciarMadeiraPraca().setVisible(true);
                    this.setVisible(false);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "op_scv":
                try {
                    if(ControlePrincipal.condicao_carvao=="forno"){
                        new GerenciarCarvaoForno().setVisible(true);
                    }else{
                        new GerenciarEnvioCarvao().setVisible(true);
                    }
                    this.setVisible(false);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }   break;
            case "op_ger":
                try {
                    new GerenciarUsuarios().setVisible(true);
                    this.setVisible(false);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }   break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonSelecionar = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonVoltar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListFiltrar = new javax.swing.JList();
        jButtonFiltrar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxFazenda = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxProjeto = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxTalhao = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRelatorioEstoquePrincipal = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabelVolTotalMadPraca = new javax.swing.JLabel();
        jLabelVolTotalMadForno = new javax.swing.JLabel();
        jLabelInfo1 = new javax.swing.JLabel();
        jLabelVolTotalMDCTransp = new javax.swing.JLabel();
        jLabelVolTotalCarvPraca = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Relatorio Estoque M/C");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 60));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 145));

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+4f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Bem Vindo");

        jLabelNome.setFont(jLabelNome.getFont().deriveFont((jLabelNome.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, jLabelNome.getFont().getSize()+4));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelNome.setText("Usuario");

        jLabelIdTipo.setFont(jLabelIdTipo.getFont().deriveFont(jLabelIdTipo.getFont().getSize()+1f));
        jLabelIdTipo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdTipo.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabelIdTipo, jLabelNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabelIdTipo, jLabelNome});

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

        jButtonSelecionar.setFont(jButtonSelecionar.getFont().deriveFont(jButtonSelecionar.getFont().getSize()+1f));
        jButtonSelecionar.setText("Selecionar");
        jButtonSelecionar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelecionarActionPerformed(evt);
            }
        });

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabel5.setText("Filtrar por:");

        jListFiltrar.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "-", "estado", "bloco", "municipio", "fazenda", "projeto", "upc", "talhao", "situacao", "material_genetico", "fator_empilalhemto", "madeira_praca", "carvao_praca", "madeira_forno", "mdc_transp", "densidade_madeira", "densidade_carvao", "vol_mad_estimado", "vol_mad_transp", "vol_mad_balanco", "mdc_estimado", "mdc_prod", "mdc_balanco", "mad_ton_estimado", "mad_ton_transp", "mad_ton_balanco", "carv_ton_estimado", "carv_ton_prod", "carv_ton_balanco", "rend_grav_estimado", "rend_grav_real", "carv_ton_transp", "id_estoque_p" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jListFiltrar);

        jButtonFiltrar.setText("Filtrar");
        jButtonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFiltrarActionPerformed(evt);
            }
        });

        jLabel7.setText("FAZENDAS");

        jLabel6.setText("PROJETOS");

        jComboBoxProjeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));

        jLabel8.setText("TALHÕES");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBoxFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBoxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jButtonFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBoxTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxFazenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxTalhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonFiltrar)
                .addGap(25, 25, 25)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jTableRelatorioEstoquePrincipal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTableRelatorioEstoquePrincipal);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        jLabelVolTotalMadPraca.setFont(jLabelVolTotalMadPraca.getFont());
        jLabelVolTotalMadPraca.setText("Volume total madeira praça: 0 m³");
        jLabelVolTotalMadPraca.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelVolTotalMadForno.setFont(jLabelVolTotalMadForno.getFont());
        jLabelVolTotalMadForno.setText("Volume total madeira forno: 0 m³");
        jLabelVolTotalMadForno.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelInfo1.setFont(jLabelInfo1.getFont().deriveFont(jLabelInfo1.getFont().getStyle() | java.awt.Font.BOLD, 12));
        jLabelInfo1.setText("Informações Gerais");
        jLabelInfo1.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelVolTotalMDCTransp.setFont(jLabelVolTotalMDCTransp.getFont());
        jLabelVolTotalMDCTransp.setText("Volume total mdc transportado: 0 m³");
        jLabelVolTotalMDCTransp.setPreferredSize(new java.awt.Dimension(200, 15));

        jLabelVolTotalCarvPraca.setFont(jLabelVolTotalCarvPraca.getFont());
        jLabelVolTotalCarvPraca.setText("Volume total carvão praça: 0 m³");
        jLabelVolTotalCarvPraca.setPreferredSize(new java.awt.Dimension(200, 15));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabelInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(532, 532, 532))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelVolTotalMadForno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelVolTotalMadPraca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelVolTotalCarvPraca, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelVolTotalMDCTransp, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabelInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabelVolTotalMadPraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabelVolTotalMadForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabelVolTotalCarvPraca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabelVolTotalMDCTransp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelecionarActionPerformed
        //AlterarInfo();
        SelecionarTalhao();
    }//GEN-LAST:event_jButtonSelecionarActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        PreencherTabelaFiltrada();
        //JOptionPane.showMessageDialog(null, jListFiltrar.getSelectedValuesList());
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new GerarRelatorioEstoquePrincipal().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonSelecionar;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JComboBox jComboBoxFazenda;
    private javax.swing.JComboBox jComboBoxProjeto;
    private javax.swing.JComboBox jComboBoxTalhao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelInfo1;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVolTotalCarvPraca;
    private javax.swing.JLabel jLabelVolTotalMDCTransp;
    private javax.swing.JLabel jLabelVolTotalMadForno;
    private javax.swing.JLabel jLabelVolTotalMadPraca;
    private javax.swing.JList jListFiltrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableRelatorioEstoquePrincipal;
    // End of variables declaration//GEN-END:variables
    
}
