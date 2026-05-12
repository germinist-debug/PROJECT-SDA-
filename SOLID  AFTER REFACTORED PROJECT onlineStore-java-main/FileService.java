import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileService {
    
    private final OutputPort output;
    private final FileInputPort fileInput;
    
    public FileService(OutputPort output, FileInputPort fileInput) {
        this.output = output;
        this.fileInput = fileInput;
    }
    
    public void recreateFile(String fileName) {
        if (output instanceof ConsoleOutput) {
            ((ConsoleOutput) output).closeFileStream();
        }
        if (fileInput != null) {
            fileInput.closeFileInputStream();
        }
        
        try {
            Files.deleteIfExists(Path.of(fileName));
        } catch (IOException e) {
            output.println("Error deleting file");
        }
        
        try {
            Files.createFile(Path.of(fileName));
            if (output instanceof ConsoleOutput) {
                ((ConsoleOutput) output).setFileOutputStream(fileName);
            }
            if (fileInput != null) {
                fileInput.setFileInputStream(fileName);
            }
        } catch (IOException e) {
            output.println("Error creating file");
        }
    }
    
    public void saveStoreToFile(OnlineStore store) {
        recreateFile("output.txt");
        if (output instanceof ConsoleOutput) {
            ((ConsoleOutput) output).saveToFile(store);
        }
    }
    
    public Owner loadOwnerFromFile() {
        return fileInput.readOwnerFromFile();
    }
    
    public ArrayList<Product> loadProductsFromFile() {
        return fileInput.readProductsFromFile();
    }
    
    public void setFileInputStream(String fileName) {
        fileInput.setFileInputStream(fileName);
    }
}