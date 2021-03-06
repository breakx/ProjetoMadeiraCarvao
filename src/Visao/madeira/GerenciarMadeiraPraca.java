/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.madeira;

import Controle.ControlePrincipal;
import Controle.ControleUsuario;
import Modelo.ConexaoBD;
import Modelo.GerarTabela;
import Visao.login.Login;
import Visao.relatorios.GerarRelatorioEstoqueBasico;
import Visao.relatorios.GerarRelatorioEstoquePrincipal;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Cristiano GD
 */
public class GerenciarMadeiraPraca extends javax.swing.JFrame {

    ControleUsuario usuario = new ControleUsuario();
    
    /**
     * Creates new form Movimentar_Madeira_Talhao_Praca
     * @throws java.sql.SQLException
     */
    public GerenciarMadeiraPraca() throws SQLException {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);    
        jButtonExcluir.setVisible(false);
        //jButtonAlterar.setVisible(false);
        if(!ControlePrincipal.tipo_u.equals("op_ger")){
            jButtonRelatorio.setVisible(false);
        }
        CarregarNome();        
        PreencherTabela();
    }   
    
    String[] colunas;
    /**
     * 
     */
    private void PreencherTabela(){
        ArrayList dados = new ArrayList();
        colunas = new String[] { 
            "upc_m", 
            "talhao", 
            "mad_volume_m_stereo", 
            "mad_volume_m3", 
            "altura1_t", 
            "altura2_t", 
            "altura3_t", 
            "comprimento_t", 
            "largura_t", 
            "peso_t", 
            "altura1_bt", 
            "altura2_bt", 
            "altura3_bt", 
            "comprimento_bt", 
            "largura_bt", 
            "peso_bt",
            "data_entrega", 
            "id_controle_madeira", 
            "id_estoque_p", 
            "id_operario"
        };
        int tamanho = 0;    
        String query;
        if(ControlePrincipal.tipo_u.equals("op_ger")){
            query = "Select * from controle_madeira";
        }else{
            jButtonAlterar.setVisible(false);
            query = "Select * from controle_madeira where id_operario = '" +ControlePrincipal.id_op+"'";
        }
        ConexaoBD con = ConexaoBD.getConexao(0);         
        ResultSet rs = con.consultaSql(query);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            while(rs.next()){
                dados.add(new Object[]{
                    rs.getString("upc_m"),//0
                    rs.getString("talhao"),//1
                    rs.getString("mad_volume_m_stereo"),//2
                    rs.getString("mad_volume_m3"),//3
                    rs.getString("altura1_t"),//4
                    rs.getString("altura2_t"),//5
                    rs.getString("altura3_t"),//6
                    rs.getString("comprimento_t"),//7
                    rs.getString("largura_t"),//8
                    rs.getString("peso_t"),//9
                    rs.getString("altura1_bt"),//10
                    rs.getString("altura2_bt"),//11
                    rs.getString("altura3_bt"),//12
                    rs.getString("comprimento_bt"),//13
                    rs.getString("largura_bt"),//14
                    rs.getString("peso_bt"),//15
                    newDateFormat.format(rs.getTimestamp("data_entrega")),//16
                    rs.getString("id_controle_madeira"),//17
                    rs.getString("id_estoque_p"),//18
                    rs.getString("id_operario")//19
                });
                tamanho++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorioEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela! "+ex);
        }
        
        GerarTabela modelo = new GerarTabela(dados, colunas);
        jTableMadeira.setModel(modelo);
        for(int i=0;i<colunas.length;i++){
            if(colunas[i].length()<=8){                
                jTableMadeira.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*12);
            }else if(colunas[i].length()>8 && colunas[i].length()<=15){
                jTableMadeira.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*10);
            }else{
                jTableMadeira.getColumnModel().getColumn(i).setPreferredWidth(colunas[i].length()*8);
            }
            if(i>15 && !ControlePrincipal.tipo_u.equals("op_ger")){
                jTableMadeira.getColumnModel().getColumn(i).setMinWidth(0);     
                jTableMadeira.getColumnModel().getColumn(i).setPreferredWidth(0);  
                jTableMadeira.getColumnModel().getColumn(i).setMaxWidth(0);
                jTableMadeira.getColumnModel().getColumn(i).setResizable(false);
            }
            //System.out.println("Indice: "+i+" - "+ colunas[i].length()*200);
        }
        jTableMadeira.getTableHeader().setReorderingAllowed(false);
        jTableMadeira.setAutoResizeMode(jTableMadeira.AUTO_RESIZE_OFF);
        jTableMadeira.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //duplo click
        /*if(!ControlePrincipal.tipo_u.equals("op_ger")){
            jTableMadeira.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                        if(e.getClickCount() == 2){
                            //System.out.println("duplo-clique detectado");
                            AlterarInfo();
                        }
                    }
                }); 
        }*/
        con.fecharConexao();
        RenomearColunas();
    }
    
    private void RenomearColunas(){
        /*for(int i=0;i<colunas.length;i++){
            System.out.println("Indice: "+i+" - "+ colunas[i]);
        }*/
        jTableMadeira.getColumnModel().getColumn(0).setHeaderValue("UPC");
        jTableMadeira.getColumnModel().getColumn(1).setHeaderValue("Talhão");
        jTableMadeira.getColumnModel().getColumn(2).setHeaderValue("Vol. Madeira m.e.");
        jTableMadeira.getColumnModel().getColumn(3).setHeaderValue("Vol. Madeira m³");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Altura 1(m)");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Altura 2(m)");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Altura 3(m)");        
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Comprimento(m)");  
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Largura(m)");  
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Peso(t)");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Altura 1(m)BT");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Altura 2(m)BT");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Altura 3(m)BT");        
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Comprimento(m)BT");  
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Largura(m)BT");  
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Peso(t)BT");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Data Entrega");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Numero");
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Nº Estoque");        
        jTableMadeira.getColumnModel().getColumn(4).setHeaderValue("Operario");
    }
    
    private void AlterarInfo(){
        String[] info = new String[colunas.length];
        if(jTableMadeira.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableMadeira.getSelectedRow();   
            
            for(int i=0; i<colunas.length;i++){
                //System.out.println(colunas[i].toString()+"["+i+"]: " + jTableMadeira.getValueAt(linha, i).toString());
                info[i]=jTableMadeira.getValueAt(linha, i).toString();
                //System.out.println(colunas[i].toString()+"["+i+"]: " + info[i]);
            }
            /*//JOptionPane.showMessageDialog(null, "Em transporte!"+jTableMadeira.getValueAt(linha, 12).toString());
            String id_controle_madeira = jTableMadeira.getValueAt(linha, 17).toString();
            String id_estoque_p = jTableMadeira.getValueAt(linha, 18).toString();
            //String id_operador = jTableMadeira.getValueAt(linha, 2).toString();
            //String talhao = jTableMadeira.getValueAt(linha, 3).toString();
            //String data_entrega = jTableMadeira.getValueAt(linha, 4).toString();
            String mad_volume_m_stereo = jTableMadeira.getValueAt(linha, 2).toString();
            String mad_volume_m3 = jTableMadeira.getValueAt(linha, 3).toString();
            String altura1_t = jTableMadeira.getValueAt(linha, 4).toString();
            String altura2_t = jTableMadeira.getValueAt(linha, 5).toString();
            String altura3_t = jTableMadeira.getValueAt(linha, 6).toString();
            String comprimento_t = jTableMadeira.getValueAt(linha, 7).toString();
            String largura_t = jTableMadeira.getValueAt(linha, 8).toString();            
            String peso_t = jTableMadeira.getValueAt(linha, 9).toString();
            String altura1_bt = jTableMadeira.getValueAt(linha, 10).toString();
            String altura2_bt = jTableMadeira.getValueAt(linha, 11).toString();
            String altura3_bt = jTableMadeira.getValueAt(linha, 12).toString();
            String comprimento_bt = jTableMadeira.getValueAt(linha, 13).toString();
            String largura_bt = jTableMadeira.getValueAt(linha, 14).toString();            
            String peso_bt = jTableMadeira.getValueAt(linha, 15).toString();*/
            try {
                //new AlterarMadeiraPraca(id_controle_madeira,id_estoque_p,altura1_t,altura2_t,altura3_t,comprimento_t,largura_t,peso_t,altura1_bt,altura2_bt,altura3_bt,comprimento_bt,largura_bt,peso_bt,mad_volume_m_stereo,mad_volume_m3).setVisible(true);
                new AlterarMadeiraPraca(info).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setVisible(false);
            dispose();
            
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void ExcluirInfo(){
        if(jTableMadeira.getSelectedRow()>=0) {
            int linha = jTableMadeira.getSelectedRow();
            String id_madeira = jTableMadeira.getValueAt(linha, 0).toString();
            new ExcluirMadeiraInfo(id_madeira).setVisible(true);
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void InserirInfo(){
        if(ControlePrincipal.talhao != 0)//verifica se a linha a ser alterada esta marcada
        {
            new InserirMadeiraPraca().setVisible(true);
            this.setVisible(false);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione um talhão!");
    } 
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
        //ControlePrincipal.tela_anterior = "madeira";
        //JOptionPane.showMessageDialog(null, "Tela: "+ControlePrincipal.tela_anterior);
    } 
    
    /*public void CalcularFator(){
        if(volumePraca != 0 && volumeTalhao != 0){
            //JOptionPane.showMessageDialog(null, "Dados ok: "+(volumePraca-volumeTalhao));
            jLabelFator.setText("Fator: "+(volumePraca-volumeTalhao));
        }
    }*/

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
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jButtonBuscarEstoque = new javax.swing.JButton();
        jButtonRelatorio = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMadeira = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Madeira");
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

        jButtonAlterar.setFont(jButtonAlterar.getFont().deriveFont(jButtonAlterar.getFont().getSize()+1f));
        jButtonAlterar.setText("Alterar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonExcluir.setFont(jButtonExcluir.getFont().deriveFont(jButtonExcluir.getFont().getSize()+1f));
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        jButtonBuscarEstoque.setFont(jButtonBuscarEstoque.getFont().deriveFont(jButtonBuscarEstoque.getFont().getSize()+1f));
        jButtonBuscarEstoque.setText("<html>Buscar <br>Estoque</html>");
        jButtonBuscarEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarEstoqueActionPerformed(evt);
            }
        });

        jButtonRelatorio.setFont(jButtonRelatorio.getFont().deriveFont(jButtonRelatorio.getFont().getSize()+1f));
        jButtonRelatorio.setText("<html>Voltar<br>Relatorio</html>");
        jButtonRelatorio.setPreferredSize(new java.awt.Dimension(100, 60));
        jButtonRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jButtonAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(48, 48, 48)
                        .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonBuscarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonAlterar, jButtonBuscarEstoque, jButtonExcluir});

        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane2.setViewportView(jTableMadeira);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2)
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
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)))
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonBuscarEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarEstoqueActionPerformed
        try {
            //new BuscarRelatorioMadeiraEstoquePrincipal().setVisible(true);
            new GerarRelatorioEstoquePrincipal().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonBuscarEstoqueActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        AlterarInfo();
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelatorioActionPerformed
        try {
            new GerarRelatorioEstoqueBasico().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButtonRelatorioActionPerformed
    
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
            java.util.logging.Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerenciarMadeiraPraca().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarMadeiraPraca.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonBuscarEstoque;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRelatorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableMadeira;
    // End of variables declaration//GEN-END:variables
}
