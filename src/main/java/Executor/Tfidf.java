//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import DataType.Tuple;
import DataType.offset;
import DataType.paragraph;

public class Tfidf {
    public Tfidf() {
    }

    public int tfCalculator(String[] totalterms, Connection conn, String termToCheck, int docid, int paraNum) throws SQLException {
        int TfCount = 0;
        String offsetquery = "INSERT INTO test_offset (term,docid,offsets) VALUES ";
        String paragraphquery = "INSERT INTO test_paragraph (docid,offsets,paragraph) VALUES ";
        ArrayList<offset> offsets = new ArrayList();
        ArrayList<paragraph> paragraphs = new ArrayList();
        int offset = 0;
        String[] var13 = totalterms;
        int var14 = totalterms.length;

        for(int var15 = 0; var15 < var14; ++var15) {
            String s = var13[var15];
            if (s.equalsIgnoreCase(termToCheck)) {
                offsetquery = offsetquery + "(?, ?, ?), ";
                paragraphquery = paragraphquery + "(?, ?, ?), ";
                offsets.add(new offset(termToCheck, docid, offset));
                paragraphs.add(new paragraph(docid, offset, paraNum));
                ++TfCount;
            }

            ++offset;
        }

        offsetquery = offsetquery.substring(0, offsetquery.length() - 2);
        paragraphquery = paragraphquery.substring(0, paragraphquery.length() - 2);
        if (offsetquery.contains("?")) {
            connectDB.offsetTableMultiInsert(conn, offsets, offsetquery);
        }

        if (paragraphquery.contains("?")) {
            connectDB.paragraphTableMultiInsert2(conn, paragraphs, paragraphquery);
        }

        return TfCount;
    }

    public int tfCalculator2(String[] totalterms, String termToCheck, Connection conn, int docid) throws SQLException {
        int TfCount = 0;
        String query = "INSERT INTO test_b (term,docid,offsets) VALUES ";
        ArrayList<offset> offsets = new ArrayList();
        int offset = 0;
        String[] var10 = totalterms;
        int var11 = totalterms.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            String s = var10[var12];
            if (s.equalsIgnoreCase(termToCheck)) {
                query = query + "(?, ?, ?), ";
                offsets.add(new offset(termToCheck, docid, offset));
                ++TfCount;
            }

            ++offset;
        }

        query = query.substring(0, query.length() - 2);
        if (query.contains("?")) {
            connectDB.offsetTableMultiInsert(conn, offsets, query);
        }

        return TfCount;
    }

    public int idfCalculator(ArrayList<String[]> allTerms, String termToCheck) {
        int DfCount = 0;
        int count1 = 0;

        for(Iterator var5 = allTerms.iterator(); var5.hasNext(); ++count1) {
            String[] ss = (String[])var5.next();
            String[] var7 = ss;
            int var8 = ss.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String s = var7[var9];
                if (s.equalsIgnoreCase(termToCheck)) {
                    ++DfCount;
                    break;
                }
            }
        }

        return DfCount;
    }

    public int subTfCal(int value) {
        return value + 1;
    }

    public Tuple<Integer, Map<String, Integer>> tfCal(Map<String, Integer> confirmTf, String term) {
        if (confirmTf.get(term) == null) {
            confirmTf.put(term, 1);
            return new Tuple(1, confirmTf);
        } else {
            Stream<Entry<String, Integer>> stream = confirmTf.entrySet().parallelStream();
            int returnvalue = (Integer)confirmTf.get(term);
            confirmTf.get(term);
            confirmTf.putAll((Map)stream.filter((checkTerm) -> {
                return ((String)checkTerm.getKey()).equals(term);
            }).collect(Collectors.toMap(Entry::getKey, (entry) -> {
                return this.subTfCal((Integer)entry.getValue());
            })));
            return new Tuple(returnvalue + 1, confirmTf);
        }
    }

    private int getString(String a) {
        return 1;
    }

    public Map<String, Integer> dfCal(Map<String, Integer> confirmDf, String term) {
        Stream<String> streamKeys = confirmDf.keySet().parallelStream();
        if (confirmDf.get(term) == null) {
            confirmDf.put(term, 1);
        } else {
            boolean anyMatch = streamKeys.anyMatch((s) -> {
                return s.equals(term);
            });
            if (anyMatch) {
                confirmDf.put(term, (Integer)confirmDf.get(term) + 1);
            } else {
                confirmDf.put(term, 1);
            }
        }

        return confirmDf;
    }
}
