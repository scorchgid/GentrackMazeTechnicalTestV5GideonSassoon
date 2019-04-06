import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static Maze maze;

    public static void main(String[] args) {
        System.out.println("Hello GenTrack");
        List<Path> files = buildFileList();
        runSingleMaze(files.get(1));
        //TODO change to this method on submission
        assert files != null;
        //runEachMaze(files);
    }

    private static void runEachMaze(List<Path> files) {
        for (Path file : files) {
            maze = new Maze(file);
            exportMaze(file.getFileName());
        }
    }

    private static void runSingleMaze(Path file) {
        maze = new Maze(file);
        exportMaze(file.getFileName());
    }

    /***
     * Loads the expected directory and builds a list of maze files
     * @return list of Maze files
     */
    private static List<Path> buildFileList() {
        try {
            String subDir = "src\\res\\Samples";
            Path dir = Paths.get(subDir);
            System.out.println("\nDirectory: " + dir.toAbsolutePath());
            List<Path> listOfFiles = Files.list(dir).collect(Collectors.toList());
            System.out.println("\nYour current list of files are as follows: ");
            listOfFiles.forEach(System.out::println);
            return listOfFiles;
        } catch (Exception e) {
            System.err.println("Exception thrown " + e.toString());
            return null;
        }
    }

    private static void exportMaze(Path fileName) {
        try {
            String subDir = "src\\res\\Solutions";
            Path dir = Paths.get(subDir);
            String mazeExport = maze.getMaze();

            //Check if Solutions folder does not exist
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
                System.out.println("\nSolutions Directory: " + dir.toAbsolutePath() + " created");
            }

            //Check if Solutions folder has write access
            if (!Files.isWritable(dir)) {
                System.err.println("EXCEPTION WILL LIKELY BE THROWN, please move program to a place folders have Write access");
                maze.printMaze("As a file write cannot occur, here is the solution for the Maze");
            }

            subDir = subDir + "\\" + fileName.toString();
            System.out.println("New file and Directory: " + dir.toString());
            dir = Paths.get(subDir);

            //Check if this file already exists if so delete it to create a new one
            if (Files.exists(dir))
                Files.delete(dir);

            Files.createFile(dir);
            List<String> lines = Arrays.asList(mazeExport);
            Files.write(dir, lines, Charset.forName("UTF-8"));
        } catch (Exception e) {
            System.err.println("Exception thrown " + e.toString());
        }
    }
}