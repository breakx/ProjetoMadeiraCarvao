/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.estoqueprincipal;

import Controle.ControlePrincipal;
import Modelo.ConexaoBD;
import Visao.login.Login;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cristiano GD
 */
public class GerenciarEstoquePrincipal extends javax.swing.JFrame {

    /**
     * Creates new form Estoque
     * @throws java.sql.SQLException
     */
    public GerenciarEstoquePrincipal() throws SQLException {
        initComponents();
        CarregarNome();
        DefaultTableModel dtm = (DefaultTableModel) jTableEstoquePrincipal.getModel();
        String query = "Select * from estoque_principal";
        ConexaoBD con = ConexaoBD.getConexao();
        
        ResultSet rs = con.consultaSql(query);

        while(rs.next()){
            String [] reg = {
                rs.getString("id_estoque_p"),
                rs.getString("estado"),
                rs.getString("bloco"),
                rs.getString("municipio"),
                rs.getString("fazenda"),
                rs.getString("projeto"),
                rs.getString("upc"),
                rs.getString("talhao"),
                rs.getString("area"),
                rs.getString("m3_ha"),
                rs.getString("material_genetico"),
                rs.getString("talhadia"),
                rs.getString("ano_rotacao"),
                rs.getString("data_plantio"),
                rs.getString("data_rotacao_1"),
                rs.getString("data_rotacao_2"),
                rs.getString("data_rotacao_3"),
                rs.getString("idade"),
                rs.getString("categoria"),
                rs.getString("situacao"),
                rs.getString("ima"),
                rs.getString("mad_ton_tot"),
                rs.getString("carv_ton_tot"),
                rs.getString("mdc_ha"),
                rs.getString("densidade_carvao"),
                rs.getString("densidade_madeira"),
                rs.getString("id_operario"),
                rs.getString("data_estoque"),
                rs.getString("vol_mad_estimado"),
                rs.getString("vol_mad_real"),
                rs.getString("vol_mad_balanco"),
                rs.getString("mdc_estimado"),
                rs.getString("mdc_real"),
                rs.getString("mdc_balanco"),
                rs.getString("mad_ton_estimado"),
                rs.getString("mad_ton_real"),
                rs.getString("mad_ton_balanco"),
                rs.getString("carv_ton_estimado"),
                rs.getString("carv_ton_real"),
                rs.getString("carv_ton_balanco"),
                rs.getString("madeira_talhao"),
                rs.getString("madeira_praca"),
                rs.getString("madeira_forno"),
                rs.getString("rend_grav_estimado"),
                rs.getString("rend_grav_real"),
                rs.getString("fator_empilalhemto")
            };
            dtm.addRow(reg);
        }
        con.fecharConexao();
    }
        
    private void AlterarInfo(){
        if(jTableEstoquePrincipal.getSelectedRow()>=0)//verifica se a linha a ser alterada esta marcada
        {
            int linha = jTableEstoquePrincipal.getSelectedRow();
            String id_estoque = jTableEstoquePrincipal.getValueAt(linha, 0).toString();
            String estado = jTableEstoquePrincipal.getValueAt(linha, 1).toString();
            String upc = jTableEstoquePrincipal.getValueAt(linha, 2).toString();
            String bloco = jTableEstoquePrincipal.getValueAt(linha, 3).toString();
            String municipio = jTableEstoquePrincipal.getValueAt(linha, 4).toString();
            String fazenda = jTableEstoquePrincipal.getValueAt(linha, 5).toString();
            String projeto = jTableEstoquePrincipal.getValueAt(linha, 6).toString();
            String ano_rotacao = jTableEstoquePrincipal.getValueAt(linha, 7).toString();
            String talhao = jTableEstoquePrincipal.getValueAt(linha, 8).toString();
            String area = jTableEstoquePrincipal.getValueAt(linha, 9).toString();
            String m3_ha = jTableEstoquePrincipal.getValueAt(linha, 10).toString();
            String data_plantio = jTableEstoquePrincipal.getValueAt(linha, 11).toString();
            String material_genetico = jTableEstoquePrincipal.getValueAt(linha, 12).toString();
            String talhadia = jTableEstoquePrincipal.getValueAt(linha, 13).toString();
            String data_rotacao_1 = jTableEstoquePrincipal.getValueAt(linha, 14).toString();
            String data_rotacao_2 = jTableEstoquePrincipal.getValueAt(linha, 15).toString();
            String idade = jTableEstoquePrincipal.getValueAt(linha, 16).toString();
            String categoria = jTableEstoquePrincipal.getValueAt(linha, 17).toString();
            String situacao = jTableEstoquePrincipal.getValueAt(linha, 18).toString();
            String ima = jTableEstoquePrincipal.getValueAt(linha, 19).toString();
            String mdc_ha = jTableEstoquePrincipal.getValueAt(linha, 20).toString();
            String mdc = jTableEstoquePrincipal.getValueAt(linha, 21).toString();
            String densidade_carvao = jTableEstoquePrincipal.getValueAt(linha, 22).toString();
            String densidade_madeira = jTableEstoquePrincipal.getValueAt(linha, 23).toString();
            String id_operario = jTableEstoquePrincipal.getValueAt(linha, 24).toString();
            String data_estoque	= jTableEstoquePrincipal.getValueAt(linha, 25).toString();
            String volume_estimado = jTableEstoquePrincipal.getValueAt(linha, 26).toString();
            String madeira_talhao = jTableEstoquePrincipal.getValueAt(linha, 27).toString();
            String madeira_praca = jTableEstoquePrincipal.getValueAt(linha, 28).toString();
            String madeira_forno = jTableEstoquePrincipal.getValueAt(linha, 29).toString();
            String mad_ton_tot = jTableEstoquePrincipal.getValueAt(linha, 30).toString();
            String carv_ton_tot = jTableEstoquePrincipal.getValueAt(linha, 31).toString();
            
            //String data_estoque = jTableEstoque.getValueAt(linha, 28).toString();
            //String id_operario = jTableEstoque.getValueAt(linha, 29).toString();
 
            new AlterarEstoquePrincipal(id_estoque, estado, upc, bloco, municipio,
            fazenda, projeto, ano_rotacao, talhao, area, m3_ha, data_plantio,
            material_genetico, talhadia, data_rotacao_1, data_rotacao_2, idade, 
            categoria, situacao, ima, mdc_ha, mdc, densidade_carvao, 
            densidade_madeira, id_operario, data_estoque, volume_estimado, 
            madeira_talhao, madeira_praca, madeira_forno, mad_ton_tot, carv_ton_tot).setVisible(true);
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void ExcluirInfo(){
        if(jTableEstoquePrincipal.getSelectedRow()>=0) {
            int linha = jTableEstoquePrincipal.getSelectedRow();
            String id_estoque = jTableEstoquePrincipal.getValueAt(linha, 0).toString();
            dispose();
        }else JOptionPane.showMessageDialog(null, "Selecione uma linha!");
    }
    
    private void InserirInfo(){
        new InserirEstoquePrincipal().setVisible(true);
        dispose();
    } 
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
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
        jButtonInserir = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEstoquePrincipal = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Gerenciar Estoque Principal");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 70));

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addGap(53, 53, 53))
                    .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(94, 94, 94))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

        jButtonInserir.setFont(jButtonInserir.getFont().deriveFont(jButtonInserir.getFont().getSize()+1f));
        jButtonInserir.setText("Inserir");
        jButtonInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jButtonInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.LOG_OPTION);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1000, 1000));
        jScrollPane1.setRequestFocusEnabled(false);

        jTableEstoquePrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_estoque", "estado", "bloco", "municipio", "fazenda", "projeto", "upc", "talhao", "area", "m3/ha", "mat_gen", "talhadia", "ano_rotacao", "data_plantio", "data_rotacao_1", "data_rotacao_2", "idade", "categoria", "situacao", "ima", "mad_ton_tot", "carv_ton_tot", "mdc_ha", "dens_mad", "dens_carv", "id_oper", "data_estoque", "vol_mad_est", "vol_mad_real", "vol_mad_bal", "mdc_est", "mdc_real", "mdc_bal", "mad_ton_est", "mad_ton_real", "mad_ton_bal", "carv_ton_est", "carv_ton_real", "carv_ton_bal", "mad_talhao", "mad_praca", "mad_forno", "rend_grav_est", "rend_grav_real", "fator_emp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEstoquePrincipal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableEstoquePrincipal.setColumnSelectionAllowed(true);
        jTableEstoquePrincipal.setFillsViewportHeight(true);
        jTableEstoquePrincipal.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jTableEstoquePrincipal.setMinimumSize(new java.awt.Dimension(450, 450));
        jTableEstoquePrincipal.setPreferredSize(new java.awt.Dimension(3000, 0));
        jTableEstoquePrincipal.setRequestFocusEnabled(false);
        jTableEstoquePrincipal.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableEstoquePrincipal);
        jTableEstoquePrincipal.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirActionPerformed
        InserirInfo();
    }//GEN-LAST:event_jButtonInserirActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        AlterarInfo();
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        ExcluirInfo();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GerenciarEstoquePrincipal().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GerenciarEstoquePrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonInserir;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEstoquePrincipal;
    // End of variables declaration//GEN-END:variables
}