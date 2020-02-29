class Main {

    static void main(String[] args) {
        File f = new File(getClass().getResource('/input').toURI())
        List<List<Integer>> input = f.readLines().collect { it.split(',').collect { it as Integer } as List<Integer> }
        SudokuBacktracking sudokuBacktracking = new SudokuBacktracking(input)

        sudokuBacktracking.resolve()
    }
}
