import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;

/// OutputDevice class is used to print messages to console and to file (if file is set)
public class OutputDevice {

    OutputStream consoleOutputStream;
    FileOutputStream fileOutputStream;

    OutputDevice() {
        this.consoleOutputStream = System.out;
    }

    public void setFileOutputStream(String fileName) {
        try {
            this.fileOutputStream = new FileOutputStream(fileName, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println("Permission denied for file");
        }
    }

    public void closeFileOutputStream() {
        if (this.fileOutputStream == null) return;
        try {
            this.fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error closing file");
        }
    }

    public void writeMessageNl(Object message) {
        try
        {
            fileOutputStream.write(message.toString().getBytes());
            fileOutputStream.write("\n".getBytes());
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }
    public void writeMessage(Object message) {
        try
        {
            fileOutputStream.write(message.toString().getBytes());
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }

    public void printMessage(Object message) { System.out.print(message); }
    public void printMessageNl(Object message) { System.out.println(message); }

    public void printStore(OnlineStore store){
        printMessageNl(store.owner);
        if(store.getProductList().length == 0) printMessageNl("Store is empty");
        int i = 1;
        for(Product product : store.getProductList()){
            printMessageNl(i + ". " + product.toString());
            i++;
        }
    }

    public void storeToFile(OnlineStore store){
        writeMessageNl(store.owner);
        if(store.getProductList().length == 0) writeMessageNl("Store is empty");
        int i = 1;
        for(Product product : store.getProductList()){
            writeMessageNl(i + ". " + product.toString());
            i++;
        }
    }

    public void printFileContent(String fileName) { // used for testing and debugging
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            if (lines.isEmpty()) {
                System.out.println("File is empty");
                return;
            }
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
