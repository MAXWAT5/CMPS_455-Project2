import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main{

    public static void main(String[] args)
    {
        int selection = 0;
        if(args.length!=0)
        {
            if(args[0].equals("-S")) {
                if (args[1].equals("1")) {
                    selection = 1;
                } else if (args[1].equals("2")) {
                    selection = 2;
                } else if (args[1].equals("3")) {
                    selection = 3;
                } else {
                    System.out.println("Something went wrong :(");
                }
            } else {
                System.out.println("Something went wrong :(");
            }
        } else {
            System.out.println("No command line arg found.");
        }
        Random randominteger = new Random();
        int N = randominteger.nextInt(4)+3; //Domains
        int M = randominteger.nextInt(4)+3; //Files
        System.out.println("Domain Count: "+N);
        System.out.println("Object Count: "+M);

        if(selection==1) //Access Matrix
        {
            System.out.println("Access control scheme: Access Matrix");
            String[][] arrayMain = FunctMatrix(N, M);
            for (int i = 0; i < N; i++){
                establishMeowl othermeowl = new establishMeowl(arrayMain);
                Thread aUser = new Thread(othermeowl);
                aUser.start();
            }
        }
        else if(selection==2) //Access Lists
        {
            System.out.println("Access control scheme: Access List");
            HashMap<Integer, String>[] accessList = AccessList(N, M);
            for (int i = 0; i < N; i++){
                establishMeowl othermeowl = new establishMeowl(accessList, N, selection);
                Thread aUser = new Thread(othermeowl);
                aUser.start();
            }
        }
        else if(selection==3) //Capability Lists
        {
            System.out.println("Access control scheme: Capability List");
            HashMap<Integer, String>[] capabilityList = CapabilityList(N, M);
            for (int i = 0; i < N; i++){
                establishMeowl othermeowl = new establishMeowl(capabilityList, M, selection);
                Thread aUser = new Thread(othermeowl);
                aUser.start();
            }
        }
        else
        {
            System.out.println("Use the numbers 1, 2, or 3 :(");
        }
    }

    public static String[][] FunctMatrix(int N, int M){
        Random randominteger = new Random();
        String[][] thematrix = new String[N][M+N];

        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < M+N; j++){
                if (j < M){
                    int canReadOrWrite = randominteger.nextInt(5);
                    switch (canReadOrWrite){
                        case 0:
                            thematrix[i][j] = "R";
                            break;
                        case 1:
                            thematrix[i][j] = "W";
                            break;
                        case 2:
                            thematrix[i][j] = "R/W";
                            break;
                        case 3:
                            thematrix[i][j] = "";
                            break;
                    }
                }
                else{
                    int canSwitch = randominteger.nextInt(3);
                    switch (canSwitch){
                        case 0:
                            thematrix[i][j] = "switch";
                            break;
                        case 1:
                            thematrix[i][j] = "";
                            break;
                    }
                }
            }
        }

        // Print table header
        System.out.printf("        ");
        for (int i = 0; i < thematrix[0].length ; i++) {
            if (i < thematrix[0].length - thematrix.length){
                System.out.printf("f" + (i+1) + "        ");
            }
            else{
                System.out.print("D" + (i + 1 - (thematrix[0].length - thematrix.length)) + "        ");
            }
        }

        System.out.println();
        System.out.println("-".repeat(10 + (thematrix[0].length * 10)));

        // Print matrix rows
        for (int i = 0; i < thematrix.length; i++) {
            System.out.printf("D" + (i+1) + "      ");
            for (int j = 0; j < thematrix[i].length; j++) {
                System.out.printf("%-10s", thematrix[i][j]);
            }
            System.out.println();
        }

        for (int j = 0; j < thematrix[0].length; j++){
            int randomvalue = randominteger.nextInt(3);
            switch (randomvalue){
                case 0:
                    thematrix[0][j] = thematrix[0][j] + " Pink";
                    break;
                case 1:
                    thematrix[0][j] = thematrix[0][j] + " Black";
                    break;
                case 2:
                    thematrix[0][j] = thematrix[0][j] + " Blue";
                    break;
            }
        }

        return thematrix;
    }

    public static HashMap<Integer, String>[] AccessList(int N, int M){
        Random randominteger = new Random();
        @SuppressWarnings("unchecked")
        HashMap<Integer, String>[] accessLists = (new HashMap[N+M]);
        for(int i=0; i<N+M; i++)
        {
            accessLists[i] = new HashMap<Integer, String>();
        }
        for(int i=0; i<M; i++)
        {
            System.out.print("F"+(i+1)+"--> ");
            for(int j=0; j<N; j++){
                int canReadOrWrite = randominteger.nextInt(5);
                switch (canReadOrWrite){
                    case 0:
                        accessLists[i].put(j, "R");
                        System.out.print("D"+(j+1)+":R, ");
                        break;
                    case 1:
                        accessLists[i].put(j, "W");
                        System.out.print("D"+(j+1)+":W, ");
                        break;
                    case 2:
                        accessLists[i].put(j, "R/W");
                        System.out.print("D"+(j+1)+":R/W, ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        for(int i=M; i<N+M; i++)
        {
            System.out.print("D"+(i-M+1)+"--> ");
            for(int j=0; j<N; j++){
                int canSwitch = randominteger.nextInt(3);
                switch (canSwitch){
                    case 0:
                        accessLists[i].put(j, "switch");
                        System.out.print("D"+(j+1)+":switch, ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }


        return accessLists;
    }

    public static HashMap<Integer, String>[] CapabilityList(int N, int M) {
        Random randominteger = new Random();
        @SuppressWarnings("unchecked")
        HashMap<Integer, String>[] capabilityLists = new HashMap[N];
        for (int i = 0; i < N; i++) {
            capabilityLists[i] = new HashMap<Integer, String>();
            System.out.print("D" + (i + 1) + "--> ");
            for (int j = 0; j < M; j++) {
                int canReadOrWrite = randominteger.nextInt(5);
                switch (canReadOrWrite) {
                    case 0:
                        capabilityLists[i].put(j, "R");
                        System.out.print("F" + (j + 1) + ":R, ");
                        break;
                    case 1:
                        capabilityLists[i].put(j, "W");
                        System.out.print("F" + (j + 1) + ":W, ");
                        break;
                    case 2:
                        capabilityLists[i].put(j, "R/W");
                        System.out.print("F" + (j + 1) + ":R/W, ");
                        break;
                    default:
                        break;
                }
            }
            for (int j = M; j < N + M; j++) {
                int canSwitch = randominteger.nextInt(3);
                switch (canSwitch) {
                    case 0:
                        capabilityLists[i].put(j, "switch");
                        System.out.print("D" + (j - M + 1) + ":switch, ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }


        return capabilityLists;
    }
}

class establishMeowl implements Runnable{
    public String[][] yayAmatrix;
    public HashMap<Integer, String>[] accessList;
    public HashMap<Integer, String>[] capabilityList;
    public Semaphore[] fileMutex;
    public String[] files;
    public int domainCount;
    public int fileCount;
    public int readCount;
    public int select;

    public establishMeowl(String[][] matrix){
        select=1;
        domainCount = matrix.length;
        fileCount = matrix[0].length - matrix.length;
        fileMutex = new Semaphore[fileCount];
        yayAmatrix = matrix;
        Random randominteger = new Random();
        if(files==null){
            files = new String[fileCount];
            for (int i=0; i<fileCount; i++)
            {
                fileMutex[i]= new Semaphore(1);
                int randomvalue = randominteger.nextInt(3);
                switch (randomvalue){
                    case 0:
                        files[i] = "Pink";
                        break;
                    case 1:
                        files[i] = "Black";
                        break;
                    case 2:
                        files[i] = "Blue";
                        break;
                }
            }
        }
    }

    public establishMeowl(HashMap<Integer, String>[] lists, int count, int selection){
        Random randominteger=new Random();
        select=selection;
        if(selection==2) //Access List
        {
            domainCount=count;
            fileCount=lists.length-count;
            accessList=lists;
        }
        if(selection==3) //Capability List
        {
            domainCount=lists.length;
            fileCount=count-lists.length;
            capabilityList=lists;
        }

        fileMutex = new Semaphore[fileCount];
        if(files==null){
            files = new String[fileCount];
            for (int i=0; i<fileCount; i++)
            {
                fileMutex[i]= new Semaphore(1);
                int randomvalue = randominteger.nextInt(3);
                switch (randomvalue){
                    case 0:
                        files[i] = "Pink";
                        break;
                    case 1:
                        files[i] = "Black";
                        break;
                    case 2:
                        files[i] = "Blue";
                        break;
                }
            }
        }

    }

    public void read(int filechosen, int theExactSpotInTheDomain, String containedValue) throws InterruptedException{
        Random randominteger = new Random();
        System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Attempting to read F" + (filechosen+1) + ".");
        if (containedValue!=null && containedValue.contains("R")) {
            if(!fileMutex[filechosen].tryAcquire()&&readCount==0)
            {
                fileMutex[filechosen].acquire();
            }
            readCount++;
            //Reader Start
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Reading " + files[filechosen] + " from F" + (filechosen+1) + ".");
            //KILL Reader
            readCount--;
            if (readCount == 0) {
                fileMutex[filechosen].release(); //Writers may write now
            }
            int value = randominteger.nextInt(4) + 3;
            for (int i = 0; i <= value; i++) {
                Thread.yield();
            }
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Yielding " + value + " times.");
        } else {
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Operation failed, permission denied.");
        }
    }

    public void write(int filechosen, int theExactSpotInTheDomain, String containedValue) throws InterruptedException{
        Random randominteger = new Random();
        System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Attempting to write to F" + (filechosen+1) + ".");
        if (containedValue!=null && containedValue.contains("W")) {
            fileMutex[filechosen].acquire();
            //Writer Start
            int value = randominteger.nextInt(4) + 3;
            {
                int randomvalue = randominteger.nextInt(3);
                switch (randomvalue){
                    case 0:
                        files[filechosen] = "Pink";
                        System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Writing Pink to F" + (filechosen+1) + ".");
                        break;
                    case 1:
                        files[filechosen] = "Black";
                        System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Writing Black to F" + (filechosen+1) + ".");
                        break;
                    case 2:
                        files[filechosen] = "Blue";
                        System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Writing Blue to F" + (filechosen+1) + ".");
                        break;
                }
            }
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Yielding " + value + " times");
            for (int i = 0; i <= value; i++) {
                Thread.yield();
            }
            //KILL Writer
            fileMutex[filechosen].release();
        } else {
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Operation failed, permission denied.");
        }
    }

    public void switchMethod(int filechosen, int theExactSpotInTheDomain, String containedValue) throws InterruptedException{
        Random randominteger = new Random();
        while (filechosen < (fileCount) || theExactSpotInTheDomain == filechosen - fileCount) {
            filechosen = (randominteger.nextInt(fileCount, fileCount+domainCount));
        }
        System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Attempting to switch to D" + (filechosen-fileCount+1) + ".");
        if (containedValue!=null && containedValue.contains("switch")) {
            int value = randominteger.nextInt(4) + 3;

            theExactSpotInTheDomain = (filechosen-fileCount);
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Switched to D" + (filechosen-fileCount+1) + ".");
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Yielding " + value + " times");
            for (int i = 0; i <= value; i++) {
                Thread.yield();
            }
        } else {
            System.out.println("[" + Thread.currentThread().getName() + ":(D" + (theExactSpotInTheDomain+1) + ")] Did not switch to D" + (filechosen-fileCount+1) + ".");
        }
    }

    public void run(){
        Random randominteger = new Random();
        int theExactSpotInTheDomain = randominteger.nextInt(domainCount);
        for(int k=0; k<=5; k++) {
            int filechosen = randominteger.nextInt(domainCount+fileCount);
            int readOrWrite = 2;
            if(filechosen<fileCount){
                readOrWrite = randominteger.nextInt(2);
            }
            String containedValue;
            if(select==1)
            {
                containedValue = yayAmatrix[theExactSpotInTheDomain][filechosen];
            }
            else if(select==2)
            {
                containedValue = accessList[filechosen].get(theExactSpotInTheDomain);
            }
            else
            {
                containedValue = capabilityList[theExactSpotInTheDomain].get(filechosen);
            }
            try {
                if (readOrWrite == 0) {
                    read(filechosen, theExactSpotInTheDomain, containedValue);
                }
                else if (readOrWrite == 1) {
                    write(filechosen, theExactSpotInTheDomain, containedValue);
                }
                else {
                    switchMethod(filechosen, theExactSpotInTheDomain, containedValue);
                }
            } catch (InterruptedException e) {
                System.out.println("Something went wrong reading :(");
            }

            //randomly choose a file
            //randomly choose to read or write
            //check the matrix to seeif current thread can read or write
            //ifit can read, then steal the writer semaphore forthat inde
            //ifit can write then steal writer semaphore
            //ifit can swit then the index of the row its at will change.

            //yield3to(6)7 times
        }
    }
}



