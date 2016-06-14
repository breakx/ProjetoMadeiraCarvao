/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AlterarUsuario.java
 *
 * Created on 28/11/2010, 17:05:28
 */

package Visao.usuario;
import Controle.ControlePrincipal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controle.ControleUsuario;
import Controle.usuario.AlterarUsuarioCtrl;

/**
 *
 * @author Cristiano
 */
public class AlterarUsuario extends javax.swing.JFrame {
    
    //variaveis
    ControleUsuario usuario = new ControleUsuario();
    private String id;

    /** Creates new form Alterar_Usuarios
     * @param id
     * @param login
     * @param tipo
     * @param upc_u
     * @param nome */
    public AlterarUsuario(String id, String login,String tipo, String upc_u, String nome) {
        super("Alterar Usuarios");
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        CarregarNome();
        _carregarTipo();
        this.id = id;
        jTextFieldLogin.setText(login);
        jTextFieldNome.setText(nome);
        jComboBoxTipo.setSelectedItem(ConverterCodigoTipoUsuario(tipo));
        jComboBoxUpcOp.setSelectedIndex(Integer.valueOf(upc_u)); 
    }
    
    private void _carregarTipo(){
        /*jComboBoxTipo.addItem("op_bal");
        jComboBoxTipo.addItem("op_scv"); 
        jComboBoxTipo.addItem("op_dir");*/  
        jComboBoxTipo.addItem("Balanceiro");
        jComboBoxTipo.addItem("Supervisor Carvao"); 
        jComboBoxTipo.addItem("Gerente");
        jComboBoxTipo.addItem("Diretor");
        _carregarUPC_Op();
    }
    
    private void _carregarUPC_Op(){
        jComboBoxUpcOp.addItem("I");
        jComboBoxUpcOp.addItem("II");
        jComboBoxUpcOp.addItem("III");  
        jComboBoxUpcOp.addItem("IV"); 
        jComboBoxUpcOp.addItem("V"); 
        jComboBoxUpcOp.addItem("VI"); 
        jComboBoxUpcOp.addItem("VII"); 
        jComboBoxUpcOp.addItem("VIII"); 
        jComboBoxUpcOp.addItem("IX"); 
        jComboBoxUpcOp.addItem("X"); 
    }

    private AlterarUsuario() {
        throw new UnsupportedOperationException("Not yet implemented");
    }   
    
    private void AlterarDados(){
        usuario.setId_usuario(id);
        usuario.setLogin_usuario(jTextFieldLogin.getText());      
        //usuario.setTipo_usuario(jTextFieldTipo.getText());
        usuario.setSenha_usuario(jTextFieldSenha.getText());          
        //usuario.setTipo_usuario(jComboBoxTipo.getSelectedItem().toString()); 
        usuario.setTipo_usuario(CodigoTipoUsuario(jComboBoxTipo.getSelectedItem().toString())); 
        usuario.setUpc_usuario(String.valueOf(jComboBoxUpcOp.getSelectedIndex()));
        usuario.setNome_usuario(jTextFieldNome.getText());

        AlterarUsuarioCtrl alterar = new AlterarUsuarioCtrl(usuario);

        try {
            new GerenciarUsuarios().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(AlterarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }
    
    private String CodigoTipoUsuario(String nome_tipo){
        String tipo="-";
        switch(nome_tipo){
            case "Balanceiro":
                tipo="op_bal";
                break;
            case "Supervisor Carvao":
                tipo="op_scv";
                break;                
            case "Gerente":
                tipo="op_ger";
                break;
            case "Diretor":
                tipo="op_dir";
                break;
        }
        return tipo;
    }
    
    private String ConverterCodigoTipoUsuario(String tipo_nome){
        String nome="-";
        switch(tipo_nome){
            case "op_bal":
                nome="Balanceiro";
                break;
            case "op_scv":
                nome="Supervisor Carvao";
                break;                
            case "op_ger":
                nome="Gerente";
                break;
            case "op_dir":
                nome="Diretor";
                break;
        }
        return nome;
    }
    
    private void VoltarMenu(){
        try 
        {
            new GerenciarUsuarios().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InserirUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
        dispose();
    }
    
    private void CarregarNome(){
        jLabelNome.setText(ControlePrincipal.nome);
        jLabelIdTipo.setText(ControlePrincipal.id_op);
    } 

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelName = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelIdTipo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButtonAlterarDados = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldLogin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jComboBoxTipo = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldSenha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxUpcOp = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jLabelName.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelName.setText("Alterar Dados do Usuário");
        jLabelName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelName.setPreferredSize(new java.awt.Dimension(275, 60));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(270, 145));

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()+4f));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Bem Vindo");

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
                    .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabelIdTipo, jLabelNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabelNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabelIdTipo)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, jLabelIdTipo, jLabelNome});

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 350));

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
                .addComponent(jButtonLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(425, Short.MAX_VALUE)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        jButtonAlterarDados.setFont(jButtonAlterarDados.getFont().deriveFont(jButtonAlterarDados.getFont().getSize()+4f));
        jButtonAlterarDados.setText("<html>Alterar<br>Dados</html>");
        jButtonAlterarDados.setPreferredSize(new java.awt.Dimension(150, 60));
        jButtonAlterarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarDadosActionPerformed(evt);
            }
        });

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+4f));
        jLabel3.setText("Nome");

        jTextFieldLogin.setFont(jTextFieldLogin.getFont().deriveFont(jTextFieldLogin.getFont().getSize()+4f));
        jTextFieldLogin.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getSize()+4f));
        jLabel4.setText("Tipo");

        jTextFieldNome.setFont(jTextFieldNome.getFont().deriveFont(jTextFieldNome.getFont().getSize()+4f));
        jTextFieldNome.setPreferredSize(new java.awt.Dimension(200, 25));
        jTextFieldNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeActionPerformed(evt);
            }
        });

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+4f));
        jLabel2.setText("Login");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 25));

        jButtonVoltar.setFont(jButtonVoltar.getFont().deriveFont(jButtonVoltar.getFont().getSize()+4f));
        jButtonVoltar.setText("Voltar");
        jButtonVoltar.setPreferredSize(new java.awt.Dimension(150, 60));
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jComboBoxTipo.setFont(jComboBoxTipo.getFont().deriveFont(jComboBoxTipo.getFont().getSize()+4f));
        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxTipo.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()+4f));
        jLabel6.setText("Senha");

        jTextFieldSenha.setFont(jTextFieldSenha.getFont().deriveFont(jTextFieldSenha.getFont().getSize()+4f));
        jTextFieldSenha.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()+4f));
        jLabel7.setText("UPC");

        jComboBoxUpcOp.setFont(jComboBoxUpcOp.getFont().deriveFont(jComboBoxUpcOp.getFont().getSize()+4f));
        jComboBoxUpcOp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxUpcOp.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxTipo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                            .addComponent(jTextFieldSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxUpcOp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonAlterarDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(392, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBoxTipo, jComboBoxUpcOp, jTextFieldLogin, jTextFieldNome, jTextFieldSenha});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel6, jLabel7});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxUpcOp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonAlterarDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBoxTipo, jComboBoxUpcOp, jTextFieldLogin, jTextFieldNome, jTextFieldSenha});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel6, jLabel7});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jButtonAlterarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarDadosActionPerformed
        AlterarDados();
    }//GEN-LAST:event_jButtonAlterarDadosActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        VoltarMenu();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jTextFieldNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        //new Login().setVisible(true);
        //dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlterarUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterarDados;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JComboBox jComboBoxTipo;
    private javax.swing.JComboBox jComboBoxUpcOp;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelIdTipo;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldLogin;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldSenha;
    // End of variables declaration//GEN-END:variables

}
