
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Course {
    
     public void insertUpdateDeleteStudent(char operation, Integer id, String label, Integer hours) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        
        // i de inserir
        if (operation == 'i') {
            try {
                ps = con.prepareStatement("INSERT INTO `curso`(`cnome`, `num_horas`) VALUES (?,?)");
                ps.setString(1, label);
                ps.setInt(2, hours);

                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Novo curso adicionado!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // u = update
        if (operation == 'u') {
            try {
                ps = con.prepareStatement("UPDATE `curso` SET `cnome` = ?, `num_horas` = ? WHERE `id` = ?");
                ps.setString(1, label);
                ps.setInt(2, hours);
                ps.setInt(3, id);
                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Informações do curso atualizadas!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // d = deletar
        if (operation == 'd') {
            
            int YesOrNo = JOptionPane.showConfirmDialog(null, "As notas do aluno também serão deletadas!", "Deletar nota?", JOptionPane.OK_CANCEL_OPTION, 0);
            
            if(YesOrNo == JOptionPane.OK_OPTION) { 
                try {
                ps = con.prepareStatement("DELETE FROM `curso` WHERE `id` = ?");
                ps.setInt(1, id);
                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Informações do curso deletadas!");
                }
                
                } catch (SQLException ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        }
        
    } 
     
    
     public boolean isCourseExist(String courseName) {
        boolean isExist = false; 
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `curso` WHERE `cnome` = ?");
            ps.setString(1, courseName);
            
            ResultSet rs = ps.executeQuery();

            
            if(rs.next()) {
                isExist = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
         return isExist;
     }
     
      public void fillCourseJtable(JTable table) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `curso`");
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next()) {
                row = new Object[3];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getInt(3);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      
    public int getCourseId(String courseLabel) {
        int courseId = 0;
        
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `curso` WHERE `cnome` = ?");
            ps.setString(1, courseLabel);
            
            ResultSet rs = ps.executeQuery();

            
            if(rs.next()) {
                courseId = rs.getInt("ID");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return courseId;
    } 
      
    
    
    public void fillCourseCombo(JComboBox combo) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `curso`");
            
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()) {
                combo.addItem(rs.getString(2));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
     
}
