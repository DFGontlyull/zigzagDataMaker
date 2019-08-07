//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package DataType;

public class index_tester {
    private String terms;
    private int docid;
    private int tf;
    private int idf;

    public index_tester(String terms, int docid, int tf, int idf) {
        this.terms = terms;
        this.docid = docid;
        this.tf = tf;
        this.idf = idf;
    }

    public String getTerms() {
        return this.terms;
    }

    public int getDocid() {
        return this.docid;
    }

    public int getTf() {
        return this.tf;
    }

    public int getIdf() {
        return this.idf;
    }
}
