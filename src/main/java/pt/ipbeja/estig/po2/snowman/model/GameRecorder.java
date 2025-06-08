package pt.ipbeja.estig.po2.snowman.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class GameRecorder {
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("ssmmHHddMMyyyy");
    private static final String LOG_DIRECTORY = "snowman_logs/";

    /**
     * Saves the complete game history to a file.
     *
     * @param moves list of moves in the format "from → to"
     * @param snowmanPos the position where the snowman was completed
     * @throws IOException if an error occurs during file writing
     */
    public static void saveGame(List<String> moves, Position snowmanPos) throws IOException {
        createLogDirectory();
        String filename = generateFilename();
        String content = buildLogContent(moves, snowmanPos);

        Files.writeString(Path.of(filename), content);
        System.out.println("Game log saved at: " + Path.of(filename).toAbsolutePath());
    }

    /**
     * Formats a position into a user-friendly string (e.g., "(1,A)").
     *
     * @param pos the position to format
     * @return formatted string representing the position
     */
    public static String formatPosition(Position pos) {
        return String.format("(%d,%c)", pos.getRow() + 1, (char)('A' + pos.getCol()));
    }

    /**
     * Creates the log directory if it does not exist.
     *
     * @throws IOException if directory creation fails
     */
    private static void createLogDirectory() throws IOException {
        if (!Files.exists(Path.of(LOG_DIRECTORY))) {
            Files.createDirectories(Path.of(LOG_DIRECTORY));
        }
    }

    /**
     * Generates a filename for the log file based on the current timestamp.
     *
     * @return the generated filename with path
     */
    private static String generateFilename() {
        return LOG_DIRECTORY + "snowman_" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".txt";
    }

    /**
     * Builds the content of the game log file.
     *
     * @param moves list of moves made in the game
     * @param snowmanPos the position where the snowman was completed
     * @return the formatted content as a string
     */
    private static String buildLogContent(List<String> moves, Position snowmanPos) {
        return new StringBuilder()
                .append("=== SNOWMAN GAME LOG ===\n\n")
                .append("MOVES:\n")
                .append(String.join("\n", moves))
                .append("\n\nFINAL DETAILS:\n")
                .append("- Total moves: ").append(moves.size()).append("\n")
                .append("- Snowman completed at: ").append(formatPosition(snowmanPos)).append("\n")
                .append("- Date/time: ").append(LocalDateTime.now()).append("\n")
                .toString();
    }
}
