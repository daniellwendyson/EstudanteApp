
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Score {
    
    public void insertUpdateDeleteStudent(char operation, Integer Sid, Integer Cid, Double scr, String descp) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        
        // i de inserir
        if (operation == 'i') {
            try {
                ps = con.prepareStatement("INSERT INTO `score`(`estudante_id`, `curso_id`, `score_estudante`, `descricao`) VALUES (?,?,?,?)");
                ps.setInt(1, Sid);
                ps.setInt(2, Cid);
                ps.setDouble(3, scr);
                ps.setString(4, descp);

                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Nota adicionada!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // u = update
        if (operation == 'u') {
            try {
                ps = con.prepareStatement("UPDATE `score` SET `score_estudante` = ?, `descricao` = ? WHERE `estudante_id` = ? AND `curso_id` = ?");
                ps.setDouble(1, scr);
                ps.setString(2, descp);
                ps.setInt(3, Sid);
                ps.setInt(4, Cid);
                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Informações do curso atualizadas!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // d = deletar
        if (operation == 'd') {
            try {
                ps = con.prepareStatement("DELETE FROM `score` WHERE `estudante_id` = ? AND `curso_id` = ?");
                ps.setInt(1, Sid);
                ps.setInt(2, Cid);
                
                if(ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Nota deletada!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
     public void fillScoreJtable(JTable table) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `score`");
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next()) {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getDouble(3);
                row[3] = rs.getString(4);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
     
     public void ShowAllScores(JTable table) {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT `estudante_id`, nome, sobrenome, cnome, score_estudante\n" +
"FROM `score`\n" +
"INNER JOIN estudante as stab on stab.id = `estudante_id`\n" +
"INNER JOIN curso as ctab on ctab.id = `curso_id`");
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next()) {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
