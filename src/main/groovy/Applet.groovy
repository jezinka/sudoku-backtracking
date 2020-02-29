import processing.core.PApplet
import processing.core.PFont

class Applet extends PApplet {

    SudokuBacktracking sudokuBacktracking

    PFont f

    void main(SudokuBacktracking sudoku) {
        this.sudokuBacktracking = sudoku
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
        text(sudokuBacktracking.textOutput, 20, 20)
        if (!sudokuBacktracking.isResolved()) {
            sudokuBacktracking.step()
        } else {
            text("Done", 150, 240)
            println(sudokuBacktracking.textOutput)
            noLoop()
        }
    }

    private void drawBoard() {
        line(64, 0, 64, 220)
        line(110, 0, 110, 220)
        line(20, 72, 160, 72)
        line(20, 145, 160, 145)
    }
}
