class ConsoleOutput {

    SudokuBacktracking sudokuBacktracking

    void main(SudokuBacktracking sudoku) {
        this.sudokuBacktracking = sudoku
        while (!sudokuBacktracking.isResolved()) {
            sudokuBacktracking.step()
        }
        println(sudokuBacktracking.textOutput)
        println("Done in ${sudokuBacktracking.duration}")
    }
}
