/// Main class for the application that creates a store and runs it.
public class Main {
    public static void main(String[] args) {

        OutputDevice outputDevice = new OutputDevice();
        InputDevice inputDevice = new InputDevice();
        Application app = new Application(inputDevice, outputDevice);

        if(args.length == 1 && args[0].equals("scratch")){
            outputDevice.printMessageNl("Creating store from scratch");
            args = new String[]{"scratch"};
        }
        else if(args.length == 0 ){
            args = new String[]{"old"};
        }
        else if(args.length == 1 && args[0].equals("db")){
            args = new String[]{"db"};
        }
        else{
            outputDevice.printMessageNl("Usage: java Main <scratch>");
            System.exit(1);
        }
        for(String arg:args) outputDevice.printMessage(arg + " ");
        outputDevice.printMessageNl("\n");
        app.run(args);
    }
}
