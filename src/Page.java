public class Page {
    boolean P = false;
    boolean R = false;
    boolean M = false;
    int ppn = -1; // physical page number

    public void createMapping(int ppn){ //for Kernel
        P = true;
        M = false;
        R = false;
        this.ppn = ppn;
    }

    public void rmMapping(){ //for Kernel clockAlg
        P = false;
        this.ppn = - 1;
    }

    public void resetR(){ //for kernel clockAlg
        R = false;
        Main.sb.append("\tRemoved R bit from " + this);
    }

    public void resetM(){ //for kernel clockAlg
        M = false;
        Main.sb.append("\tRemoved M bit from " + this);
    }

    @Override
    public String toString() {
        return "Virtual Page[" +
                "P: " + P +
                ", R: " + R +
                ", M: " + M +
                ", PPN: " + ppn + "]\n";
    }
}