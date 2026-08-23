/** Obviously-correct oracle: count the zeros, then overwrite. */
class Brute {
    void segregate(int[] arr) {
        int zeros = 0;
        for (int v : arr) if (v == 0) zeros++;
        for (int i = 0; i < arr.length; i++) arr[i] = i < zeros ? 0 : 1;
    }
}
