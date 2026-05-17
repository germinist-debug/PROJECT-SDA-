/// Main class for the application that creates a store and runs it.
/// This is now a SIMPLE entry point - only ONE responsibility: starting the application

public class Main {
    public static void main(String[] args) {
        
        // Create input and output handlers (SRP compliant)
        ConsoleInputReader input = new ConsoleInputReader();
        ConsoleOutputWriter output = new ConsoleOutputWriter();
        
        // Create the refactored application
        RefactoredApplication app = new RefactoredApplication(input, output);
        
        // Handle command line arguments
        if(args.length == 1 && args[0].equals("scratch")) {
            output.printLine("Creating store from scratch");
            app.run(new String[]{"scratch"});
        }
        else if(args.length == 1 && args[0].equals("db")) {
            output.printLine("Starting store from database");
            app.run(new String[]{"db"});
        }
        else if(args.length == 0) {
            output.printLine("Starting store from file");
            app.run(new String[]{"old"});
        }
        else {
            output.printLine("Usage: java Main [scratch|db]");
            output.printLine("  - No arguments: Load from file");
            output.printLine("  - scratch: Create new store from scratch");
            output.printLine("  - db: Load from database");
            System.exit(1);
        }
    }
}