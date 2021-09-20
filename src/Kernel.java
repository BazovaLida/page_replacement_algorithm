import java.util.ArrayList;
import java.util.Arrays;

public class Kernel {
    private final int N; // physical memory page amount
    private final int maxProc; // max number of process
    private final int workingTime = 100;
    private final int[] clockRow;

    public Kernel(int memoryN, int maxProcN) {
        this.maxProc = maxProcN;
        this.N = memoryN;
        clockRow = new int[maxProc];
    }


    public void modeling() {
        Process[] processes = createProcesses();
        MMU mmu = new MMU();

        // глобальна політика розподілу пам'яті
        for (int i = 0; i < workingTime; i++) {
            int processN = i % maxProc;
            // Процеси отримують процесорний час по черзі RR.
            Process currP = processes[processN];
            // 90% звернень до сторінки з робочого набору
            int idx = generateVPidx(currP.getPageTable());
            Main.sb.append("Time: " + i + " process: " + processN + " page idx: " + idx);
            boolean writeAccess = Math.random() > 0.5;

            boolean pageFault = mmu.accessPage(idx, currP.getPageTable(), writeAccess);
            if(pageFault) {// сторінка не входить в робочий набір, сторінковий промах
                pageFault(currP.getPageTable(), idx, currP.getWorkingSet(), processN);
                mmu.accessPage(idx, currP.getPageTable(), writeAccess);
            }
        }
    }

    private int generateVPidx(Page[] pageTable){
        boolean inWS = Math.random() < 0.9;
        Page vp = pageTable[(int)(Math.random() * pageTable.length)];
        while(vp.P != inWS){
            vp = pageTable[(int)(Math.random() * pageTable.length)];
        }
        return Arrays.asList(pageTable).indexOf(vp);
    }

    private Process[] createProcesses(){
        Process[] units = new Process[maxProc];
        for (int i = 0; i < maxProc; i++) {
            // amount of pages for curr process
            int pages = (int) (Math.random() * N + 1.5 * N);
            units[i] = new Process(pages, N);
            clockRow[i] = 0;
        }
        return units;
    }

    // звільняє зайняту фізичну сторінку за алгоритмом clock
    private void pageFault(Page[] pt, int idx, ArrayList<Page> ws, int procN){
        Main.sb.append("\n\nPage fault. Searching for a space...");
        Main.sb.append("\nClock algorithm logs:\n");

        Page pp = clockAlgorithm(pt, procN);
        Main.sb.append("Clock algorithm choose: " + pp);

        // init mapping to the physical page
        pt[idx].createMapping(pp.ppn);
        pp.rmMapping();
        ws.remove(pp);
        ws.add(pt[idx]);
    }

    private Page clockAlgorithm(Page[] pt, int procN){
        for (int idx = clockRow[procN]; idx < pt.length; idx++) {
            Page p = pt[idx];
            if(!p.R ){
                if(!p.M){
                    clockRow[procN] = idx + 1;
                    return p;
                }
                else p.resetM();
            } else p.resetR();
        }
        clockRow[procN] = 0;
        return clockAlgorithm(pt, procN);
    }
}
