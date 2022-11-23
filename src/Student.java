
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Student {
    public void insertUpdateDeleteStudent(char operation, Integer id, String fname, String lname, String sex, String bdate, String phone, String address) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        
        // i de inserir
        if (operation == 'i') {
            try {
                ps = con.prepareStatement("INSERT INTO estudante(nome, sobrenome, sexo, nascimento, telefone, endereco) VALUES (?,?,?,?,?,?)");
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, sex);
                ps.setString(4, bdate);
                ps.setString(5, phone);
                ps.setString(6, address);
                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Novo estudante adicionado!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // u = update
        if (operation == 'u') {
            try {
                ps = con.prepareStatement("UPDATE `estudante` SET `nome` = ?, `sobrenome` = ?, `sexo` = ?, `nascimento` = ?, `telefone` = ?, `endereco` = ? WHERE id = ?");
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, sex);
                ps.setString(4, bdate);
                ps.setString(5, phone);
                ps.setString(6, address);
                ps.setInt(7, id);
                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Informações do estudante atualizadas!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // d = deletar
        if (operation == 'd') {
            
            int YesOrNo = JOptionPane.showConfirmDialog(null, "As notas do aluno também serão deletadas!", "Deletar estudante?", JOptionPane.OK_CANCEL_OPTION, 0);
            
            if(YesOrNo == JOptionPane.OK_OPTION) { 
                try {
                ps = con.prepareStatement("DELETE FROM `estudante` WHERE id = ?");
                ps.setInt(1, id);
                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Informações do estudante deletadas!");
                }
                
                } catch (SQLException ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        }
        
    }
    
    public void fillStudentJtable(JTable table, String valueToSearch) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `estudante` WHERE CONCAT(`nome`, `sobrenome`, `telefone`, `endereco`)LIKE ?");
            ps.setString(1, "%"+valueToSearch+"%");
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next()) {
                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
