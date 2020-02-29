import processing.core.PApplet
import processing.core.PFont

class SudokuBacktracking extends PApplet {

    List<List<Integer>> board
    Map<String, Boolean> isFixedNumber = [:]
    int rowIndex, columnIndex

    PFont f
    String textOutput = ''

    SudokuBacktracking(List<List<Integer>> board) {
        this.board = board
        this.rowIndex = 0
        this.columnIndex = 0
        this.board.eachWithIndex { List<Integer> row, int i -> row.eachWithIndex { int col, int j -> this.isFixedNumber.put("$i$j".toString(), col != 0) } }
    }

    void resolve() {
        String[] processingArgs = { "Sudoku backtracking" }
        runSketch(processingArgs, this)
    }

    void settings() {
        size(200, 250)
    }

    void setup() {
        frameRate = 30
        f = createFont("Arial", 16, true)
    }

    void draw() {
        background(255)
        textFont(f, 16)
        fill(0)
        drawBoard()
        text(this.textOutput, 20, 20)
        if (board.flatten().any { it == 0 }) {
            step()
        } else {
            text("Done", 190, 210)
            noLoop()
        }
    }

    private void drawBoard() {
        line(64, 0, 64, 220)
        line(110, 0, 110, 220)
        line(20, 72, 160, 72)
        line(20, 145, 160, 145)
    }

    private step() {
        if (isFixedNumber["$rowIndex$columnIndex" as String]) {
            nextCell()
        } else {
            int cell = board[rowIndex][columnIndex]
            int value = ++cell
            if (value <= 9) {
                board[rowIndex][columnIndex] = value
                if (this.boardValidation()) {
                    nextCell()
                }
            } else {
                board[rowIndex][columnIndex] = 0
                do {
                    previousCell()
                } while (isFixedNumber["$rowIndex$columnIndex" as String])
            }
        }
        printTable()
    }

    private printTable() {
        this.textOutput = board.collect { it.join(' ') }.join('\n') + '\n'
    }

    private void nextCell() {
        if (columnIndex < 8) {
            columnIndex++
        } else {
            columnIndex = 0
            rowIndex++
        }
    }

    private void previousCell() {
        if (columnIndex > 0) {
            columnIndex--
        } else {
            columnIndex = 8
            rowIndex--
        }
    }

    private boolean boardValidation() {
        boolean right = true
        board.each { row ->
            if (row.findAll { it != 0 }.countBy { it }.any { k, v -> v != 1 }) {
                right = false
            }
        }
        (0..8).each { colNum ->
            List<Integer> column = board.collect { it[colNum] }
            if (column.findAll { it != 0 }.countBy { it }.any { k, v -> v != 1 }) {
                right = false
            }
        }

        Map<String, List<Integer>> squares = [:]
        board.eachWithIndex { List<Integer> row, int i ->
            row.eachWithIndex { int entry, int j ->
                String key = "${Math.floor(i / 3) as Integer}${Math.floor(j / 3) as Integer}".toString()
                if (!squares.containsKey(key)) {
                    squares[key] = [entry]
                } else {
                    squares[key] << entry
                }
            }
        }

        squares.values().each { square ->
            if (square.findAll { it != 0 }.countBy { it }.any { k, v -> v != 1 }) {
                right = false
            }
        }
        return right
    }
}