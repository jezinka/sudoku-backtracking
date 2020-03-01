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
        f = createFont("Arial", 16, true)
    }

    void draw() {
        background(255)
        textFont(f, 16)
        drawBoard()
        markFixed()
        fill(0)
        text(sudokuBacktracking.textOutput, 20, 20)
        if (!sudokuBacktracking.isResolved()) {
            sudokuBacktracking.step()
        } else {
            text("Done in ${sudokuBacktracking.duration}", 0, 240)
            noLoop()
        }
    }

    private void drawBoard() {
        fill(0)
        stroke(0)
        line(64, 0, 64, 220)
        line(110, 0, 110, 220)
        line(20, 72, 160, 72)
        line(20, 145, 160, 145)
    }

    private void markFixed() {
        fill(0, 255, 0, 63)
        noStroke()
        sudokuBacktracking.fixedPositions.each { Integer row, Integer col ->
            rect(20 + (col * 15), 5 + (row * 24), 12, 20)
        }
    }
}
