public class MMU {
    public boolean accessPage(int idx, Page[] pt, boolean writeA){
        Page p = pt[idx];

        if(!p.P) //сторінка входить в WS
            return true;

        p.R = true;
        if (writeA)
            p.M = true;
        Main.sb.append("\n\tAccess to page #")
                .append(idx)
                .append(": ")
                .append(p);
        return false;
    }

    //useless because working set of the process was created at the begggining
//    public boolean havePhysicalPage(Page[] pt, int N){
//        int c = 0;
//        for (Page p : pt) {
//            if(p.P)
//                c ++;
//        }
//        return N > c;
//    }
}
