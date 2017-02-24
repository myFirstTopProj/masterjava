package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    // TODO implement parallel multiplication matrixA*matrixB
    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        final CompletionService<Result> completionService = new ExecutorCompletionService<>(executor);

        int[][] BT = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                BT[j][i] = matrixB[i][j];
            }
        }

        List<Future<Result>> futures = new ArrayList<>();
        for (int i = 0; i < matrixSize ; i++) {
            final int i_final = i;
            futures.add(completionService.submit(() -> {
                int[] res = new int[matrixSize];
                for (int j = 0; j < matrixSize; j++) {
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += matrixA[i_final][k] * BT[j][k];
                    }
                    res[j] = sum;
                }
                return new Result(i_final, res);
            }));
        }

        return new Callable<int[][]>() {
            @Override
            public int[][] call() {
                while (!futures.isEmpty()) {
                    try {
                        Future<Result> future = completionService.poll(10, TimeUnit.SECONDS);
                        if (future == null) {
                            new Exception("Timeout exception");
                        }
                        futures.remove(future);
                        Result res = future.get();
                        matrixC[res.i] = res.result;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                return matrixC;
            }
        }.call();
    }

    public static class Result {
        public final int i;
        public final int[] result;

        public Result(int i, int[] result) {
            this.i = i;
            this.result = result;
        }
    }

    // TODO optimize by https://habrahabr.ru/post/114797/
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        int[] thatColumn = new int[matrixSize];
        try {
            for (int j = 0; ; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    thatColumn[k] = matrixB[k][j];
                }

                for (int i = 0; i < matrixSize; i++) {
                    int[] thatRow = matrixA[i];
                    int summand = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        summand += thatRow[k]*thatColumn[k];
                    }
                    matrixC[i][j] = summand;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {}
        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
