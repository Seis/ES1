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

import Dom.Paciente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomaszewski
 */
public class PacienteDAO {
    public static void insert(Paciente p){
        try {
            String statement;
            statement = "" +
                "INSERT INTO PACIENTE\n" +
                "           (VC_NOME_PACIENTE" +
                "           ,TXT_HISTORICO_PACIENTE)" +
                "     VALUES\n" +
                "           ('" + p.getNome() + "'," +
                "           '" + p.getHistorico()+ "')";
            ConnectionHandler.execute(statement);
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void delete(Paciente p) throws SQLException{
        String statement;
        statement = "" +
            "DELETE FROM PACIENTE\n" +
            "      WHERE PK_COD_PACIENTE = " + p.getCodigo();
        ConnectionHandler.execute(statement);
    }
    
    public static void update(Paciente p){
        try {
            String statement;
            statement = "" +
                "UPDATE PACIENTE\n" +
                "   SET VC_NOME_PACIENTE = '" + p.getNome() + "'\n" +
                "      ,TXT_HISTORICO_PACIENTE =  '" + p.getHistorico()+ "'\n" +
                "      WHERE PK_COD_PACIENTE = " + p.getCodigo();
            ConnectionHandler.execute(statement);
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Paciente> select(){
        String statement;
        ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
        statement = "" +
            "SELECT [PK_COD_PACIENTE]\n" +
            "      ,[VC_NOME_PACIENTE]\n" +
            "      ,[TXT_HISTORICO_PACIENTE]\n" +
            "  FROM [dbo].[PACIENTE]";
        
        java.sql.Statement stmt = null;
        ResultSet rs;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName = SGC", "sa", "sa");

            stmt = con.createStatement();
            rs = stmt.executeQuery(statement);

            while(rs.next()){
                Paciente p = new Paciente("", "", rs.getInt("PK_COD_PACIENTE"));
                p.setNome(rs.getString("VC_NOME_PACIENTE"));
                if(rs.wasNull()){
                    p.setHistorico(rs.getString(""));
                }
                p.setHistorico(rs.getString("TXT_HISTORICO_PACIENTE"));
                pacientes.add(p);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new java.lang.RuntimeException("erro ao conectar");
        } finally {
            try {
                stmt.close();
            } 
            catch (SQLException ignore) {
            }
        }
        return pacientes;
    }
}
