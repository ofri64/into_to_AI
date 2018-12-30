public interface Classifier<E> {
    void fit(DataFrameInterface<E> df);
    double getAccuracy(DataFrameInterface<E> df);
    SeriesInterface<E> predict(DataFrameInterface<E> df);
}
