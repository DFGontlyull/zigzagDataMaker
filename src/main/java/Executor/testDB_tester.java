//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import DataType.index;
import DataType.offset;

public class testDB_tester {
    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;
    private final String url = "jdbc:postgresql://210.119.105.218:5432/postgres";
    private final String user = "postgres";
    private final String password = "password";

    public testDB_tester() {
    }

    public Connection connect() {
        System.out.print("connect 도입");

        try {
            Class.forName("org.postgresql.Driver");
            System.out.print("driver 설정");
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://210.119.105.218:5432/postgres", "postgres", "password");
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException var2) {
            System.out.println(var2.getMessage());
        }

        return conn;
    }

    public static void offsetTableMultiInsert(Connection conn, ArrayList<offset> offsets, String query) {
        try {
            int count = 1;
            PreparedStatement st = conn.prepareStatement(query);
            Iterator var5 = offsets.iterator();

            while(var5.hasNext()) {
                offset temp = (offset)var5.next();
                st.setString(count++, temp.getTerm());
                st.setInt(count++, temp.getDocid());
                st.setInt(count++, temp.getOffset());
            }

            st.executeUpdate();
            st.close();
        } catch (SQLException var7) {
            System.out.println(var7);
        }

    }

    public static void mainTableMultiInsert(Connection conn, List<index> indexes, String query, double doclen) {
        try {
            int count = 1;
            PreparedStatement st = conn.prepareStatement(query);
            Iterator var7 = indexes.iterator();

            while(var7.hasNext()) {
                index temp = (index)var7.next();
                st.setString(count++, temp.getTerms());
                st.setInt(count++, temp.getDocid());
                st.setInt(count++, temp.getTf());
                st.setInt(count++, temp.getIdf());
                st.setDouble(count++, doclen);
            }

            st.executeUpdate();
            st.close();
        } catch (SQLException var9) {
            System.out.println(var9);
        }

    }

    public static void offsetTable(Connection conn, String term, int docid, int offset) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO test_offset (term,docid,offsets) VALUES (?, ?, ?)");
            st.setString(1, term);
            st.setInt(2, docid);
            st.setInt(3, offset);
            st.executeUpdate();
            st.close();
        } catch (SQLException var5) {
            System.out.println(var5);
        }

    }

    public static void mainTable(Connection conn, String term, int docid, int tf, int df, double docln) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO test_index (term,docid,tf,df,doclen) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, term);
            st.setInt(2, docid);
            st.setInt(3, tf);
            st.setInt(4, df);
            st.setDouble(5, docln);
            st.executeUpdate();
            st.close();
        } catch (SQLException var8) {
            System.out.println(var8);
        }

    }

    public static void UpdateDoclen(Connection conn, int docid, double docln) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE test_index set doclen = ? where docid = ?");
            st.setDouble(1, docln);
            st.setInt(2, docid);
            st.executeUpdate();
            st.close();
        } catch (SQLException var5) {
            System.out.println(var5);
        }

    }
}
