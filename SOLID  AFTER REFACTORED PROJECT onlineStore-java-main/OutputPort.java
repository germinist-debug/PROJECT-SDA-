// This is an ABSTRACTION
public interface OutputPort {
    void print(Object message);
    void println(Object message);
    void printStore(OnlineStore store);
     void setFileOutputStream(String fileName);
    void saveToFile(OnlineStore store);
    void closeFileStream();
    
}