public int[] combine(int[] A, int[] B){
    int i = 0, j = 0, curr = 0;
    int[] res = new int[A.length + B.length];

    while(i < A.length && j < B.length) {
        if(A[i] <= B[j]) {
            res[curr] = A[i];
            i++;
        } else{
            res[curr] = B[j];
            j++;
        }
        curr++;
    }
    while (i < A.length) {
        res[curr] = A[i];
        i++;
        curr++;
    }
    while (j < B.length) {
        res[curr] = B[j];
        j++;
        curr++;
    }
    return res;
}


public int[] mergesort(int [] arr){
    if(arr.length <= 1){
        return arr;
    }
    int m = arr.length/2;
    int[] h1 = Arrays.copyOfRange(arr, 0, m);
    int[] h2 = Arrays.copyOfRange(arr, m, arr.length);
    return combine(mergesort(h1), mergesort(h2));
}

void main() {
    int[] arr1 = new int[]{1, 2, 3};
    int[] arr2 = new int[]{2, 3, 4};
    int[] combinedResult = combine(arr1, arr2);

    IO.println(Arrays.toString(combinedResult));

    int[] M = new int[]{2, 1, 3, 5, 1};
    IO.println(Arrays.toString(mergesort(M)));
}
