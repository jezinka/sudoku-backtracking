class SudokuBacktracking {

    List<List<Integer>> board
    int rowIndex, columnIndex

    SudokuBacktracking(List<List<Integer>> board) {
        this.board = board
        this.rowIndex = 0
        this.columnIndex = 0
    }

    void resolve() {
        Map<String, Boolean> isFixedNumber = [:]
        board.eachWithIndex { List<Integer> row, int i -> row.eachWithIndex { int col, int j -> isFixedNumber.put("$i$j".toString(), col != 0) } }
        printTable()

        while (board.flatten().any { it == 0 }) {
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
        }
        printTable()
    }

    private printTable() {
        println board.collect { it.join(',') }.join('\n') + '\n'
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