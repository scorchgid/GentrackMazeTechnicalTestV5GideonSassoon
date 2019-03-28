import.java.io.FileReader;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        List<File> files = buildFileList();        
    }

    public List<File> buildFileList(){
        try {
            List<File> files;
            File folder;
            for (final File file : folder.listFiles()) {
                    files.add(file.getAbsoluteFile());
                    System.out.println("File retrieved: " + file.getAbsoluteFile().toString());
            }
        } catch(Exception e){
            System.out.println("Exception thrown " + e.toString());
        }
        return files;
    }
}
