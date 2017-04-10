/**
 * Created by Alex Shaffer
 * March 18, 2017
 * Assignment 4: Solver
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// find a solution to the initial board (using the A* algorithm)
public class Solver {

    private List<Board> boardSolution = new ArrayList<>();
    private boolean solved;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {          
        MinPQ<SolverStep> priorSteps = new MinPQ<>(new SolverStepComparator());
        priorSteps.insert(new SolverStep(initial, 0, null));

        MinPQ<SolverStep> priorStepsTwin = new MinPQ<>(new SolverStepComparator());
        priorStepsTwin.insert(new SolverStep(initial.twin(), 0, null));

        SolverStep step;
        while (!priorSteps.min().getBoard().isGoal() && !priorStepsTwin.min().getBoard().isGoal()) {
            step = priorSteps.delMin();
            for (Board neighbor : step.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(step, neighbor)) {
                    priorSteps.insert(new SolverStep(neighbor, step.getMoves() + 1, step));
                }
            }

            SolverStep stepTwin = priorStepsTwin.delMin();
            for (Board neighbor : stepTwin.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(stepTwin, neighbor)) {
                    priorStepsTwin.insert(new SolverStep(neighbor, stepTwin.getMoves() + 1, stepTwin));
                }
            }
        }
        step = priorSteps.delMin();
        solved = step.getBoard().isGoal();

        boardSolution.add(step.getBoard());
        while ((step = step.getPreviousStep()) != null) {
            boardSolution.add(0, step.getBoard());
        }
    }

    private boolean isAlreadyInSolutionPath(SolverStep lastStep, Board board) {
        SolverStep previousStep = lastStep;
        while ((previousStep = previousStep.getPreviousStep()) != null) {
            if (previousStep.getBoard().equals(board)) {
                return true;
            }
        }
        return false;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        int moves;
        if (isSolvable()) {
            moves = boardSolution.size() - 1;
        } else {
            moves = -1;
        }
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Iterable<Board> iterable;
        if (isSolvable()) {
            iterable = new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
        } else {
            iterable = null;
        }
        return iterable;
    }


    private static class SolverStep {

        private int moves;
        private Board board;
        private SolverStep previousStep;

        private SolverStep(Board board, int moves, SolverStep previousStep) {
            this.board = board;
            this.moves = moves;
            this.previousStep = previousStep;
        }

        public int getMoves() {
            return moves;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public SolverStep getPreviousStep() {
            return previousStep;
        }
    }

    private class SolutionIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < boardSolution.size();
        }

        @Override
        public Board next() {
            return boardSolution.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("It is not supported to remove a board from the solution.");
        }
    }

    private static class SolverStepComparator implements Comparator<SolverStep> {

        @Override
        public int compare(SolverStep step1, SolverStep step2) {
            return step1.getPriority() - step2.getPriority();
        }
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}

}