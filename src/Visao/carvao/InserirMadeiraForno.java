/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao.carvao;

import Controle.ControleCarvao;
import Controle.ControlePrincipal;
import Controle.ControleUsuario;
import Controle.carvao.InserirCarvaoCtrl;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristiano GD
 */
public class InserirMadeiraForno extends javax.swing.JFrame {

    ControleUsuario usuario = new ControleUsuario();
    /**
     * Creates new form InserirMadeiraForno
     */
    public InserirMadeiraForno() {
        initComponents();        
        jLabelMunicipio.setText("Municipio: "+ControlePrincipal.municipio);
        jLabelFazenda.setText("Fazenda: "+ControlePrincipal.fazenda);        
        jLabelTalhao.setText("Talhao: "+ControlePrincipal.talhao);        
        jLabelUPC.setText("UPC: "+ControlePrincipal.upc);
        
        jLabelVolumeMadeiraPraca.setText("Volume atual de madeira: "+ControlePrincipal.madeira_praca+" m³");
        CarregarNome();
        //RegistrarMadeiraForno();
    }   
    
    private void RegistrarMadeiraForno(){
        DateFormat data_forno = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();

        ControleCarvao carvao = new ControleCarvao();
        carvao.setId_estoque(ControlePrincipal.id_estoque_principal);
        carvao.setId_operario(ControlePrincipal.id_op);
        carvao.setTalhao(ControlePrincipal.talhao);
        carvao.setForno(jTextFieldForno.getText());
        carvao.setVolume_madeira(Float.parseFloat(jTextFieldVolume_madeira.getText()));
        carvao.setData_entrada_madeira_forno(data_forno.format(date));

        ControlePrincipal.madeira_forno += Float.parseFloat(jTextFieldVolume_madeira.getText());
        ControlePrincipal.madeira_praca -= Float.parseFloat(jTextFieldVolume_madeira.getText());
        ControlePrincipal.atualizarDados = "carvao";
        InserirCarvaoCtrl inserir = new InserirCarvaoCtrl(carvao);

        try {
            new GerenciarCarvaoForno().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InserirMadeiraForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }
    
    private void VoltarMenu(){
        try {
            new GerenciarCarvaoForno().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InserirMadeiraForno.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldVolume_madeira = new javax.swing.JTextField();
        jButtonRegistrarMadeiraForno = new javax.swing.JButton();
        jLabelTalhao = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldForno = new javax.swing.JTextField();
        jButtonVoltar = new javax.swing.JButton();
        jLabelFazenda = new javax.swing.JLabel();
        jLabelVolumeMadeiraPraca = new javax.swing.JLabel();
        jLabelMunicipio = new javax.swing.JLabel();
        jLabelUPC = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitulo.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Volume de madeira no forno");
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setPreferredSize(new java.awt.Dimension(275, 70));

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 145));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+4f));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Bem Vindo");

        jLabelNome.setFont(jLabelNome.getFont().deriveFont((jLabelNome.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, jLabelNome.getFont().getSize()+4));
        jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelNome.setText("Usuario");

        jLabelIdTipo.setFont(jLabelIdTipo.getFont().deriveFont(jLabelIdTipo.getFont().getSize()+1f));
        jLabelIdTipo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIdTipo.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addGap(94, 94, 94))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(147, 147, 147))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.setPreferredSize(new java.awt.Dimension(270, 350));

        jButtonLogout.setFont(jButtonLogout.getFont().deriveFont(jButtonLogout.getFont().getSize()+13f));
        jButtonLogout.setText("Logout");
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(252, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel1.setText("Volume");

        jTextFieldVolume_madeira.setText("0");
        jTextFieldVolume_madeira.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldVolume_madeiraActionPerformed(evt);
            }
        });

        jButtonRegistrarMadeiraForno.setText("Registrar");
        jButtonRegistrarMadeiraForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarMadeiraFornoActionPerformed(evt);
            }
        });

        jLabelTalhao.setFont(jLabelTalhao.getFont().deriveFont(jLabelTalhao.getFont().getSize()+4f));
        jLabelTalhao.setText("Talhao:");

        jLabel4.setText("Forno");

        jTextFieldForno.setText("0");
        jTextFieldForno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFornoActionPerformed(evt);
            }
        });

        jButtonVoltar.setText("Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jLabelFazenda.setFont(jLabelFazenda.getFont().deriveFont(jLabelFazenda.getFont().getSize()+4f));
        jLabelFazenda.setText("Fazenda: ");

        jLabelVolumeMadeiraPraca.setFont(jLabelVolumeMadeiraPraca.getFont().deriveFont(jLabelVolumeMadeiraPraca.getFont().getSize()+4f));
        jLabelVolumeMadeiraPraca.setText("Volume atual de madeira: 0.00 m³");

        jLabelMunicipio.setFont(jLabelMunicipio.getFont().deriveFont(jLabelMunicipio.getFont().getSize()+4f));
        jLabelMunicipio.setText("Municipio: ");

        jLabelUPC.setFont(jLabelUPC.getFont().deriveFont(jLabelUPC.getFont().getSize()+4f));
        jLabelUPC.setText("UPC:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUPC)
                    .addComponent(jLabelVolumeMadeiraPraca)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(60, 60, 60)
                            .addComponent(jTextFieldForno, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldVolume_madeira, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButtonRegistrarMadeiraForno, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelMunicipio)
                    .addComponent(jLabelFazenda)
                    .addComponent(jLabelTalhao))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabelMunicipio)
                .addGap(18, 18, 18)
                .addComponent(jLabelFazenda)
                .addGap(18, 18, 18)
                .addComponent(jLabelTalhao)
                .addGap(18, 18, 18)
                .addComponent(jLabelUPC)
                .addGap(18, 18, 18)
                .addComponent(jLabelVolumeMadeiraPraca)
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldVolume_madeira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldForno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRegistrarMadeiraForno)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(290, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(506, 506, 506)))
                    .addGap(12, 12, 12)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(12, 12, 12)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldVolume_madeiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldVolume_madeiraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldVolume_madeiraActionPerformed

    private void jButtonRegistrarMadeiraFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarMadeiraFornoActionPerformed
        RegistrarMadeiraForno();
    }//GEN-LAST:event_jButtonRegistrarMadeiraFornoActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jTextFieldFornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFornoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFornoActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

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
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InserirMadeiraForno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InserirMadeiraForno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonRegistrarMadeiraForno;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelFazenda;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelMunicipio;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelTalhao;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelUPC;
    private javax.swing.JLabel jLabelVolumeMadeiraPraca;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldForno;
    private javax.swing.JTextField jTextFieldVolume_madeira;
    // End of variables declaration//GEN-END:variables
}
