import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputWriter {
    
    private FileOutputStream fileOutputStream;
    private boolean isOpen = false;
    
    public void openFile(String fileName) throws IOException {
        this.fileOutputStream = new FileOutputStream(fileName, true);
        this.isOpen = true;
    }
    
    public void closeFile() throws IOException {
        if(fileOutputStream != null) {
            fileOutputStream.close();
            isOpen = false;
        }
    }
    
    public void writeLine(Object message) throws IOException {
        if(isOpen) {
            fileOutputStream.write(message.toString().getBytes());
            fileOutputStream.write("\n".getBytes());
        }
    }
    
    public void saveStore(OnlineStore store) throws IOException {
        if(!isOpen) return;
        writeLine(store.getOwner());
        if(store.getProductList().length == 0) {
            writeLine("Store is empty");
            return;
        }
        int i = 1;
        for(Product product : store.getProductList()) {
            writeLine(i + ". " + product.toString());
            i++;
        }
    }
}