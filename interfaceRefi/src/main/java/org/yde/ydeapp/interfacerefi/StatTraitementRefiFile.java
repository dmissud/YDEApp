package org.yde.ydeapp.interfacerefi;

public class StatTraitementRefiFile {

    private int statReadLineFile;
    private int statRejetedLinefile;

    public StatTraitementRefiFile() {
        this.statRejetedLinefile = 0;
        this.statReadLineFile = 0;
    }

    public StatTraitementRefiFile(StatTraitementRefiFile statTraitementRefiFile) {
        this.statReadLineFile = statTraitementRefiFile.statReadLineFile;
        this.statRejetedLinefile = statTraitementRefiFile.statRejetedLinefile;
    }

    public int getStatReadLineFile() {
        return statReadLineFile;
    }

    public int getStatRejetedLinefile() {
        return statRejetedLinefile;
    }

    void addReadLine(){
        statReadLineFile ++;
    }

    void addRejetedLine(){
        statRejetedLinefile ++;
    }
}
