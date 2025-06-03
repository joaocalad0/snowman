package pt.ipbeja.estig.po2.snowman.model;

import java.util.*;

public class BoardModel {

    private final List<List<PositionContent>> board;
    private Monster monster;
    private final Map<Position, SnowballType> snowballs;
    private final Map<Position, PositionContent> originalCellContent;  // Conteúdo original das células ocupadas pelas bolas
    private View view;

    public BoardModel(int rows, int cols, View view) {
        this.view = view;
        this.board = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<PositionContent> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(PositionContent.NO_SNOW);
            }
            this.board.add(row);
        }
        this.snowballs = new HashMap<>();
        this.originalCellContent = new HashMap<>();
        level1();
        this.monster = new Monster(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);
    }

    private void level1() {
        board.get(0).set(0, PositionContent.NO_SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);

        Position pos = new Position(1,1);
        board.get(pos.getRow()).set(pos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(pos, SnowballType.BIG);
    }

    public void level2() {
        // Limpa o tabuleiro completamente
        for (List<PositionContent> row : board) {
            Collections.fill(row, PositionContent.NO_SNOW);
        }
        snowballs.clear();
        originalCellContent.clear();

        board.get(0).set(0, PositionContent.NO_SNOW);
        board.get(0).set(1, PositionContent.SNOW);
        board.get(1).set(2, PositionContent.BLOCK);
        board.get(2).set(3, PositionContent.SNOW);
        board.get(1).set(3, PositionContent.BLOCK);
        board.get(3).set(1, PositionContent.BLOCK);
        board.get(4).set(4, PositionContent.BLOCK);
        board.get(2).set(6, PositionContent.SNOW);
        Position bigBallPos = new Position(1, 1);
        board.get(bigBallPos.getRow()).set(bigBallPos.getCol(), PositionContent.SNOWBALL);
        snowballs.put(bigBallPos, SnowballType.BIG);

        monster.setPosition(new Position(0, 0));
        updateCell(monster.getPosition(), PositionContent.MONSTER);

        // Atualiza a visualização
        if (view != null) {
            view.updateAllBoard();
        }
    }

    public void setView(View view){
        this.view = view;
    }

    public SnowballType getSnowballTypeAt(Position position) {
        return snowballs.getOrDefault(position, SnowballType.SMALL);
    }

    private SnowballType grow(SnowballType current) {
        return switch (current) {
            case SMALL -> SnowballType.AVERAGE;
            case AVERAGE -> SnowballType.BIG;
            case AVERAGE_SMALL -> SnowballType.BIG;
            case BIG -> SnowballType.BIG;
        };
    }

    private Position calculateNewPosition(Position currentPos, Direction direction) {
        return switch (direction) {
            case UP -> new Position(currentPos.getRow() - 1, currentPos.getCol());
            case DOWN -> new Position(currentPos.getRow() + 1, currentPos.getCol());
            case LEFT -> new Position(currentPos.getRow(), currentPos.getCol() - 1);
            case RIGHT -> new Position(currentPos.getRow(), currentPos.getCol() + 1);
        };
    }

    private boolean isValidMove(Position pos) {
        int rows = board.size();
        int cols = board.get(0).size();
        if (pos.getRow() < 0 || pos.getRow() >= rows || pos.getCol() < 0 || pos.getCol() >= cols) {
            return false;
        }
        PositionContent content = board.get(pos.getRow()).get(pos.getCol());
        return content != PositionContent.BLOCK;
    }

    public void moveMonster(Direction direction) {
        Position currentPos = monster.getPosition();
        Position nextPos = calculateNewPosition(currentPos, direction);

        if (!isValidMove(nextPos)) {
            return;
        }

        PositionContent nextContent = board.get(nextPos.getRow()).get(nextPos.getCol());

        if (nextContent == PositionContent.SNOWBALL) {
            Position beyondPos = calculateNewPosition(nextPos, direction);
            if (!isValidMove(beyondPos)) {
                return;
            }
            PositionContent beyondContent = board.get(beyondPos.getRow()).get(beyondPos.getCol());
            SnowballType currentType = snowballs.get(nextPos);

            SnowballType beyondType = null;
            if (beyondContent == PositionContent.SNOWBALL) {
                beyondType = snowballs.get(beyondPos);
                if (currentType == SnowballType.SMALL && beyondType == SnowballType.AVERAGE) {
                    snowballs.put(beyondPos, SnowballType.AVERAGE_SMALL);
                    view.update(beyondPos, PositionContent.SNOWBALL);

                    board.get(nextPos.getRow()).set(nextPos.getCol(),
                            originalCellContent.getOrDefault(nextPos, PositionContent.NO_SNOW));
                    originalCellContent.remove(nextPos);
                    snowballs.remove(nextPos);
                    view.update(nextPos, board.get(nextPos.getRow()).get(nextPos.getCol()));
                    return;
                }
            }

            if ((currentType == SnowballType.AVERAGE_SMALL && beyondType == SnowballType.BIG) ||
                    (currentType == SnowballType.BIG && beyondType == SnowballType.AVERAGE_SMALL)) {
                board.get(beyondPos.getRow()).set(beyondPos.getCol(), PositionContent.SNOWMAN);
                snowballs.remove(beyondPos);
                originalCellContent.remove(beyondPos);
                view.update(beyondPos, PositionContent.SNOWMAN);

                board.get(nextPos.getRow()).set(nextPos.getCol(),
                        originalCellContent.getOrDefault(nextPos, PositionContent.NO_SNOW));
                originalCellContent.remove(nextPos);
                snowballs.remove(nextPos);
                view.update(nextPos, board.get(nextPos.getRow()).get(nextPos.getCol()));

                view.onGameWon(PositionContent.SNOWMAN);
                return;
            }
            currentType = snowballs.get(nextPos);
            if (currentType == null) {
                System.err.println("Erro: bola de neve em " + nextPos + " sem tipo associado.");
                currentType = SnowballType.SMALL;
            }
            System.out.println("Bola em " + nextPos + " antes de mover tem tamanho: " + currentType);

            if (beyondContent == PositionContent.SNOW && currentType != SnowballType.BIG) {
                currentType = grow(currentType);
                System.out.println("Bola em " + nextPos + " cresceu para tamanho: " + currentType);
            } else {
                System.out.println("Bola em " + nextPos + " mantém tamanho: " + currentType);
            }

            PositionContent oldCellContent = originalCellContent.getOrDefault(nextPos, PositionContent.NO_SNOW);
            board.get(nextPos.getRow()).set(nextPos.getCol(), oldCellContent);
            originalCellContent.remove(nextPos);
            snowballs.remove(nextPos);
            view.update(nextPos, oldCellContent);
            // Guarda o conteúdo original da nova célula antes de colocar a bola
            originalCellContent.put(beyondPos, board.get(beyondPos.getRow()).get(beyondPos.getCol()));

            board.get(beyondPos.getRow()).set(beyondPos.getCol(), PositionContent.SNOWBALL);
            snowballs.put(beyondPos, currentType);
            view.update(beyondPos, PositionContent.SNOWBALL);

        } else if (nextContent != PositionContent.NO_SNOW && nextContent != PositionContent.SNOW) {
            System.out.println("Move bloqueado por " + nextContent + " em " + nextPos);
            return; // Bloqueado por bloco ou outra coisa
        }

        if (snowballs.containsKey(currentPos)) {
            board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.SNOWBALL);
            view.update(currentPos, PositionContent.SNOWBALL);
        } else {
            board.get(currentPos.getRow()).set(currentPos.getCol(), PositionContent.NO_SNOW);
            view.update(currentPos, PositionContent.NO_SNOW);
        }

        if (nextContent == PositionContent.SNOW) {
            originalCellContent.put(nextPos, board.get(nextPos.getRow()).get(nextPos.getCol()));
            board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.SNOWBALL);
            snowballs.put(nextPos, SnowballType.SMALL);
            System.out.println("Criada bola pequena em " + nextPos);
            view.update(nextPos, PositionContent.SNOWBALL);
        }

        monster.setPosition(nextPos);
        board.get(nextPos.getRow()).set(nextPos.getCol(), PositionContent.MONSTER);
        view.update(nextPos, PositionContent.MONSTER);
    }

    public List<List<PositionContent>> getBoard() {
        return board;
    }

    public Monster getMonster() {
        return monster;
    }

    public void updateCell(Position newPos, PositionContent positionContent) {
        // Implementa se precisares
    }
}
