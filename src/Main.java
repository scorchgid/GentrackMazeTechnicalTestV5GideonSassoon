import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello GenTrack");
        List<Path> files = buildFileList();
        Maze maze = new Maze(files.get(3));
        //TODO change to this method on submission
        //runEachMaze(files);
        //TODO Print maze solution to file
    }

    public static void runEachMaze(List<Path> files){
        for(Path file : files){
            Maze maze = new Maze(file);
        }
    }

    /***
     * Loads the expected directory and builds a list of maze files
     * @return list of Maze files
     */
    public static List<Path> buildFileList() {
        try {
            String subDir = "src\\res\\Samples";
            Path dir = Paths.get(subDir);
            System.out.println("\nDirectory: " + dir.toAbsolutePath());
            List<Path> listOfFiles = Files.list(dir).collect(Collectors.toList());
            System.out.println("\nYour current list of files are as follows: ");
            listOfFiles.forEach(System.out::println);
            return listOfFiles;
        } catch (Exception e) {
            System.out.println("Exception thrown " + e.toString());
            return null;
        }
    }
}
