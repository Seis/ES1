/*
 * The MIT License
 *
 * Copyright 2020 Tomaszewski.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package DAO;

import Dom.Consulta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomaszewski
 */
public class ConsultaDAO {
    public static void insert(Consulta c){
        try {
            String statement;
            statement = "" +
                "INSERT INTO CONSULTA\n" +
                "           (FK_COD_PACIENTE" +
                "           ,FK_COD_MEDICO" +
                "           ,VC_HORARIO" +
                "           ,TXT_RECEITA)" +
                "     VALUES\n" +
                "           ('" + c.getCodigoPaciente() + "'," +
                "           '" + c.getCodigoMedico() + "'," +
                "           '" + c.getData()+ "'," +
                "           '" + c.getReceituario() + "')";
            ConnectionHandler.execute(statement);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void delete(Consulta c) throws SQLException{
        String statement;
        statement = "" +
            "DELETE FROM CONSULTA\n" +
            "      WHERE PK_COD_CONSULTA = " + c.getCodigo();
        ConnectionHandler.execute(statement);
    }
    
    public static void update(Consulta c){
        try {
            String statement;
            statement = "" +
                "UPDATE CONSULTA\n" +
                "   SET FK_COD_PACIENTE = '" + c.getCodigoPaciente()+ "'\n" +
                "      ,FK_COD_MEDICO =  '" + c.getCodigoMedico()+ "'\n" +
                "      ,VC_HORARIO =  '" + c.getData()+ "'\n" +
                "      ,TXT_RECEITA =  '" + c.getReceituario()+ "'\n" +
                "      WHERE PK_COD_CONSULTA = " + c.getCodigo();
            ConnectionHandler.execute(statement);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Consulta> select(){
        String statement;
        ArrayList<Consulta> consultas = new ArrayList<Consulta>();
        statement = "" +
            "SELECT PK_COD_CONSULTA\n" +
            "      ,FK_COD_PACIENTE\n" +
            "      ,FK_COD_MEDICO\n" +
            "      ,VC_HORARIO\n" +
            "      ,TXT_RECEITA\n" +
            "  FROM CONSULTA";
        java.sql.Statement stmt = null;
        ResultSet rs;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName = SGC", "sa", "sa");

            stmt = con.createStatement();
            rs = stmt.executeQuery(statement);

            while(rs.next()){
                Consulta c = new Consulta("", 0, 0, "", rs.getInt("PK_COD_CONSULTA"));
                c.setCodigoPaciente(rs.getInt("FK_COD_PACIENTE"));
                c.setCodigoMedico(rs.getInt("FK_COD_MEDICO"));
                c.setReceituario(rs.getString("TXT_RECEITA"));
                c.setReceituario(rs.getString("VC_HORARIO"));
                if(rs.wasNull()){
                    c.setReceituario(rs.getString(""));
                }
                c.setReceituario(rs.getString("TXT_RECEITA"));
                consultas.add(c);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new java.lang.RuntimeException("erro ao conectar");
        } finally {
            try{
                stmt.close(); 
            } catch (SQLException e){
            }
        }
        return consultas;
    }
}