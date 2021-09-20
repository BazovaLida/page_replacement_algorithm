import java.util.ArrayList;

public class Process {
    private final Page[] pageTable;
    private final ArrayList<Page> workingSet = new ArrayList<>();

    public Process(int pagesNum, int N) {
        pageTable = new Page[pagesNum];
        for(int i = 0; i < pageTable.length; i++) {
            pageTable[i] = new Page();
            if(i < N) {
                workingSet.add(pageTable[i]);
                pageTable[i].createMapping(i);
            }
        }
        Main.sb.append("Created process. " + this + "\n");
    }

    public ArrayList<Page> getWorkingSet() {
        return workingSet;
    }

    public Page[] getPageTable() {
        return pageTable;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder( "Amount of virtual pages: " + this.pageTable.length + "\n");
        for (Page p : pageTable) {
            sb.append("\t").append(p.toString());
        }
        return sb.toString();
    }
}