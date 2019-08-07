//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Executor;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import DataType.df;
import org.apache.commons.dbcp2.BasicDataSource;
import DataType.index;
import DataType.offset;
import DataType.paragraph;

public class connectDB {
    private BasicDataSource connectionPool;
    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;
    private String server = null;
    private String url = null;
    private final String user = "postgres";
    private final String password = "postgres";

    public connectDB(String temp, String db) throws URISyntaxException {
        this.server = temp;
        this.url = "jdbc:postgresql://" + this.server + ":5432/" + db;
    }

    public static void indexTableInsert(Connection conn, int docnum, String term, int tf, int df) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_index (term,docid,tf,df,doclen) VALUES (?, ?, ?, ?, ?)");
            st.setString(1, term);
            st.setInt(2, docnum);
            st.setInt(3, tf);
            st.setInt(4, df);
            st.setDouble(5, 0.0D);
            st.executeUpdate();
            st.close();
        } catch (SQLException var7) {
            PreparedStatement st = conn.prepareStatement("UPDATE term_index set df=? where term=? and docid=?");
            st.setInt(1, df);
            st.setString(2, term);
            st.setInt(3, docnum);
            st.executeUpdate();
            st.close();
        }

    }

    public static void indexTableInsert2(Connection conn, int docnum, String term) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_index (term,docid) VALUES (?, ?)");
            st.setString(1, term);
            st.setInt(2, docnum);
            st.executeUpdate();
            st.close();
        } catch (SQLException var7) {
            System.out.printf(String.valueOf(var7));
        }

    }

    public static void offsetTableInsert(Connection conn, String term, int docnum, int offset) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_offset (term,docid,offsets) VALUES (?, ?, ?)");
            st.setString(1, term);
            st.setInt(2, docnum);
            st.setInt(3, offset);
            st.executeUpdate();
            st.close();
        } catch (SQLException var5) {
            System.out.println(var5);
        }

    }

    public static void paragraphTableInsert(Connection conn, String term, int docnum, int offset, int paragraph) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_paragraph1 (term,docid,offsets,paragraph) VALUES (?, ?, ?, ?)");
            st.setString(1, term);
            st.setInt(2, docnum);
            st.setInt(3, offset);
            st.setInt(4, paragraph);
            st.executeUpdate();
            st.close();
        } catch (SQLException var6) {
            System.out.println(var6);
        }

    }

    public BasicDataSource connect() {
        System.out.print("connect 도입");
        this.connectionPool = new BasicDataSource();
        this.connectionPool.setUsername("postgres");
        this.connectionPool.setPassword("postgres");
        this.connectionPool.setDriverClassName("org.postgresql.Driver");
        this.connectionPool.setUrl(this.url);
        this.connectionPool.setInitialSize(4);
        System.out.print("connect 도입완료\n");
        return this.connectionPool;
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

    public static void paragraphTableMultiInsert2(Connection conn, ArrayList<paragraph> paragraphs, String paragraphQuery) {
        try {
            int count = 1;
            PreparedStatement st = conn.prepareStatement(paragraphQuery);
            Iterator var5 = paragraphs.iterator();

            while(var5.hasNext()) {
                paragraph temp = (paragraph)var5.next();
                st.setInt(count++, temp.getDocid());
                st.setInt(count++, temp.getOffset());
                st.setInt(count++, temp.getParagraph());
            }

            st.executeUpdate();
            st.close();
        } catch (SQLException var7) {
            System.out.println(var7);
        }

    }

    public static void offsetTable(Connection conn, String term, int docid, int offset) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_offset (term,docid,offsets) VALUES (?, ?, ?)");
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
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_index (term,docid,tf,df,doclen) VALUES (?, ?, ?, ?, ?)");
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

    public static void mainTable(Connection conn, String term, int docid) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_a (term,docid) VALUES (?, ?)");
            st.setString(1, term);
            st.setInt(2, docid);
            st.executeUpdate();
            st.close();
        } catch (SQLException var4) {
            System.out.println(var4);
        }

    }

    public static void termInsertToTable(Connection conn, String term) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO term_terms (term) VALUES (?)");
            st.setString(1, term);
            st.executeUpdate();
            st.close();
        } catch (SQLException var3) {
            System.out.println(var3);
        }

    }

    public static List<String> getTermTable(Connection conn) {
        ArrayList returnList = new ArrayList();

        try {
            PreparedStatement st = conn.prepareStatement("select * from term_terms");
            ResultSet result = st.executeQuery();

            while(result.next()) {
                returnList.add(result.getString("term"));
            }

            st.close();
        } catch (SQLException var4) {
            System.out.println(var4);
        }

        return returnList;
    }

    public static void UpdateDoclen(Connection conn, int docid, double docln) {
        try {
            System.out.println(docid + " 번째의 doclen 값을 수정합니다 : " + docln);
            PreparedStatement st = conn.prepareStatement("UPDATE term_index set doclen = ? where docid = ?");
            st.setDouble(1, docln);
            st.setInt(2, docid);
            st.executeUpdate();
            st.close();
        } catch (SQLException var5) {
            System.out.println(var5);
        }

    }

    public static void getDfTable(Connection conn, List<df> returnTable) {
        try {
            String SQL_SELECT = "SELECT * FROM zigzag.term_df";
            PreparedStatement st = conn.prepareStatement(SQL_SELECT);
            rs =  st.executeQuery();

            while(rs.next()){
                String terms = rs.getString("term");
                int df = rs.getInt("df");
//                BigDecimal salary = rs.getBigDecimal("SALARY");
//                Timestamp createdDate = rs.getTimestamp("CREATED_DATE");

                df obj = new df();
                obj.setDf(df);
                obj.setTerm(terms);

                returnTable.add(obj);
            }
        } catch (SQLException var6) {
            System.out.println(var6);
        }

    }

    public static void deleteDfs(Connection conn) {
        try {
            String SQL_DELETE = "TRUNCATE zigzag.term_df";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL_DELETE);
        } catch (SQLException var6) {
            System.out.println(var6);
        }
    }
}
