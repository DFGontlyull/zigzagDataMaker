//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package DataType;

public class paragraph {
    private int docid;
    private int offset;
    private int paragraph;

    public paragraph(int docid, int offset, int paragraph) {
        this.docid = docid;
        this.offset = offset;
        this.paragraph = paragraph;
    }

    public int getParagraph() {
        return this.paragraph;
    }

    public int getDocid() {
        return this.docid;
    }

    public int getOffset() {
        return this.offset;
    }
}
