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
     * Salva o histórico completo do jogo em arquivo
     * @param moves Lista de movimentos no formato "from → to"
     * @param snowmanPos Posição onde o snowman foi completado
     * @throws IOException Se ocorrer erro na gravação do arquivo
     */
    public static void saveGame(List<String> moves, Position snowmanPos) throws IOException {
        createLogDirectory();
        String filename = generateFilename();
        String content = buildLogContent(moves, snowmanPos);

        Files.writeString(Path.of(filename), content);
        System.out.println("Log do jogo salvo em: " + Path.of(filename).toAbsolutePath());
    }

    /**
     * Formata uma posição para exibição amigável (ex: "(1,A)")
     */
    public static String formatPosition(Position pos) {
        return String.format("(%d,%c)", pos.getRow() + 1, (char)('A' + pos.getCol()));
    }

    private static void createLogDirectory() throws IOException {
        if (!Files.exists(Path.of(LOG_DIRECTORY))) {
            Files.createDirectories(Path.of(LOG_DIRECTORY));
        }
    }

    private static String generateFilename() {
        return LOG_DIRECTORY + "snowman_" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".txt";
    }

    private static String buildLogContent(List<String> moves, Position snowmanPos) {
        return new StringBuilder()
                .append("=== SNOWMAN GAME LOG ===\n\n")
                .append("MOVIMENTOS:\n")
                .append(String.join("\n", moves))
                .append("\n\nDETALHES FINAIS:\n")
                .append("- Total de movimentos: ").append(moves.size()).append("\n")
                .append("- Boneco de neve completo em: ").append(formatPosition(snowmanPos)).append("\n")
                .append("- Data/hora: ").append(LocalDateTime.now()).append("\n")
                .toString();
    }
}