class Main {

    static void main(String[] args) {
        File f = new File(getClass().getResource('/input').toURI())
        List<List<Integer>> input = f.readLines().collect { it.split(',').collect { it as Integer } as List<Integer> }

        ConsoleOutput consoleOutput = new ConsoleOutput()
        consoleOutput.main(new SudokuBacktracking(input))

        Applet applet = new Applet()
        applet.main(new SudokuBacktracking(input))
    }
}
