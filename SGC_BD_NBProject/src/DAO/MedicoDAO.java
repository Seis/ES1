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

import Dom.Medico;
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
public class MedicoDAO {
    public static void insert(Medico m){
        try {
            String statement;
            statement = "" +
                "INSERT INTO MEDICO\n" +
                "           (VC_NOME_MEDICO" +
                "           ,VC_ESPECIALIDADE_MEDICO)" +
                "     VALUES\n" +
                "           ('" + m.getNome() + "'," +
                "           '" + m.getEspecialidade()+ "')";
            ConnectionHandler.execute(statement);
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void delete(Medico m) throws SQLException{
        String statement;
        statement = "" +
            "DELETE FROM MEDICO\n" +
            "      WHERE PK_COD_MEDICO = " + m.getCodigo();
        ConnectionHandler.execute(statement);
    }
    
    public static void update(Medico m){
        try {
            String statement;
            statement = "" +
                "UPDATE MEDICO\n" +
                "   SET VC_NOME_MEDICO = '" + m.getNome() + "'\n" +
                "      ,VC_ESPECIALIDADE_MEDICO =  '" + m.getEspecialidade()+ "'\n" +
                "      WHERE PK_COD_MEDICO = " + m.getCodigo();
            ConnectionHandler.execute(statement);
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Medico> select(){
        String statement;
        ArrayList<Medico> medicos = new ArrayList<Medico>();
        statement = "" +
            "SELECT [PK_COD_MEDICO]\n" +
            "      ,[VC_NOME_MEDICO]\n" +
            "      ,[VC_ESPECIALIDADE_MEDICO]\n" +
            "  FROM [dbo].[MEDICO]";
        
        java.sql.Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName = SGC", "sa", "sa");

            stmt = con.createStatement();
            rs = stmt.executeQuery(statement);

            while(rs.next()){
                Medico m = new Medico("", "", rs.getInt("PK_COD_MEDICO"));
                m.setNome(rs.getString("VC_NOME_MEDICO"));
                m.setEspecialidade(rs.getString("VC_ESPECIALIDADE_MEDICO"));

                medicos.add(m);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new java.lang.RuntimeException("erro ao conectar");
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
            }
        }
        return medicos;
    }
}
