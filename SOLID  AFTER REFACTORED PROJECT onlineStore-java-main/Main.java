
public class Main {
    public static void main(String[] args) {
        Application app = AppConfig.createApplication();
        String[] processedArgs = args;
        
        if (args.length == 1 && args[0].equals("scratch")) {
            app.getOutput().println("Creating store from scratch");
            processedArgs = new String[]{"scratch"};
        }
        else if (args.length == 0) {
            processedArgs = new String[]{"old"};
        }
        else if (args.length == 1 && args[0].equals("db")) {
            processedArgs = new String[]{"db"};
        }
        else if (args.length == 1 && args[0].equals("test")) {
            // Optional: For testing with mocked dependencies
            processedArgs = new String[]{"test"};
        }
        else {
            app.getOutput().println("Usage: java Main <scratch|old|db>");
            System.exit(1);
        }
        
        // Print arguments (for debugging)
        for (String arg : processedArgs) {
            app.getOutput().print(arg + " ");
        }
        app.getOutput().println("\n");
        
        // =========================================================
        // Run the application
        // =========================================================
        app.run(processedArgs);
    }
}