//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package DataType;

public class offset {
    private String term;
    private int docid;
    private int offset;
    private int para;

    public offset(String term, int docid, int offset) {
        this.term = term;
        this.docid = docid;
        this.offset = offset;
    }

    public offset(String term, int docid, int offset, int paragraphNumber) {
        this.term = term;
        this.docid = docid;
        this.offset = offset;
        this.para = paragraphNumber;
    }

    public int getPara() {
        return this.para;
    }

    public String getTerm() {
        return this.term;
    }

    public int getDocid() {
        return this.docid;
    }

    public int getOffset() {
        return this.offset;
    }
}
