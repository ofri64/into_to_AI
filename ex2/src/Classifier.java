public interface Classifier {
    void fit(DataFrameInterface df);
    double getAccuracy(DataFrameInterface df);
    SeriesInterface predict(DataFrameInterface df);
}
