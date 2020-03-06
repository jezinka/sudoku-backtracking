import groovy.time.TimeCategory
import groovy.time.TimeDuration

class SudokuBacktracking {

    private List<List<Integer>> board
    private Map<String, Boolean> isFixedNumber = [:]
    private int rowIndex, columnIndex

    private String textOutput = ''

    private Date startTime
    private TimeDuration duration

    SudokuBacktracking(List<List<Integer>> board) {
        this.board = board.collect { it.collect() }
        this.rowIndex = 0
        this.columnIndex = 0
        this.board.eachWithIndex { List<Integer> row, int i -> row.eachWithIndex { int col, int j -> this.isFixedNumber.put("$i$j".toString(), col != 0) } }
        this.startTime = new Date()
    }

    boolean isResolved() {
        def resolved = board.flatten().every { it != 0 }
        if (resolved) {
            Date endTime = new Date()
            duration = TimeCategory.minus(endTime, startTime)
        }
        return resolved
    }

    TimeDuration getDuration() {
        return duration
    }

    List getFixedPositions() {
        isFixedNumber
                .findAll { k, v -> v }
                .keySet()
                .collect { it.split('').collect { it as Integer } }
    }

    void step() {
        if (isFixedNumber["$rowIndex$columnIndex" as String]) {
            nextCell()
        } else {
            int cell = board[rowIndex][columnIndex]
            int value = ++cell
            if (value <= this.board.size()) {
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

    String getTextOutput() {
        return textOutput
    }

    private printTable() {
        this.textOutput = board.collect { it.join(' ') }.join('\n') + '\n'
    }

    private void nextCell() {
        if (columnIndex < this.board.size() - 1) {
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
            columnIndex = this.board.size() - 1
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
        (0..this.board.size() - 1).each { colNum ->
            List<Integer> column = board.collect { it[colNum] }
            if (column.findAll { it != 0 }.countBy { it }.any { k, v -> v != 1 }) {
                right = false
            }
        }

        Map<String, List<Integer>> squares = [:]
        board.eachWithIndex { List<Integer> row, int i ->
            row.eachWithIndex { int entry, int j ->
                String key = findSquare(i, j)
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

    private String findSquare(int i, int j) {
        int squareCount = Math.sqrt(board.size()) as Integer
        return "${Math.floor(i / squareCount)}${Math.floor(j / squareCount)}".toString()
    }
}