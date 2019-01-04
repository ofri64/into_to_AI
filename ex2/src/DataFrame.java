import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataFrame<E> implements DataFrameInterface<E> {
    protected SeriesInterface<String> headerLine;
    protected List<SeriesInterface<E>> df;

    public DataFrame(String inputPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String names = br.readLine(); // first line is feature names;
            String[] headTokens = names.split("\t");
            this.headerLine = new Series<>(headTokens);


            String line = null;
            df = new LinkedList<>();
            while ((line = br.readLine()) != null){
                String[] rowTokens = line.split("\t");
                List<E> rowTokensE = new LinkedList<>();

                for(String token: rowTokens){
                    rowTokensE.add((E) token);
                }

                Series<E> rowSeries = new Series<>(rowTokensE);
                df.add(rowSeries);

            }

        } catch (FileNotFoundException fnf) {
            System.out.println("The file" + inputPath + "was not found " + fnf.getMessage());
            System.exit(-1); // terminate process
        }
        catch (IOException ioe){
            System.out.println("The following exception occurred: " + ioe.getMessage());
            System.exit(-1); // terminate process
        }
    }

    DataFrame(List<SeriesInterface<E>> listOfRows){
        this.headerLine = null;
        this.df = new LinkedList<>();
        for (SeriesInterface<E> s: listOfRows){
            this.df.add(s);
        }
    }

    @Override
    public E getElement(int rowIndex, int colIndex) {
        return df.get(rowIndex).getElement(colIndex);
    }

    @Override
    public int getNumCols() {
        return df.get(0).getLength();
    }

    @Override
    public int getNumRows() {
        return df.size();
    }

    @Override
    public Series<E> getCol(int columnIndex) {
        List<E> col = new LinkedList<>();
        for (SeriesInterface<E> series : df) {
            col.add(series.getElement(columnIndex));
        }
        return new Series<>(col);
    }

    @Override
    public SeriesInterface<E> getRow(int rowIndex) {
        return new Series<>(this.df.get(rowIndex));
    }

    @Override
    public DataFrameInterface<E> getSlice(int rowFrom, int rowTo) {
            return new DataFrame<>(this.df.subList(rowFrom, rowTo));
    }

    @Override
    public Iterator<SeriesInterface<E>> iterator() {
        return new DataFrameIterator(this);
    }

    @Override
    public DataFrameInterface<E> filterRowsByColumnValue(int colNum, E value) {
        List<SeriesInterface<E>> newDF = new LinkedList<>();

        SeriesInterface<E> desiredColumn = this.getCol(colNum);
        for (int i=0; i < desiredColumn.getLength(); i++){
            if (desiredColumn.getElement(i).equals(value)){
                newDF.add(this.getRow(i));
            }
        }
        return new DataFrame<>(newDF);
    }

    @Override
    public boolean isEmpty() {
        return this.df.isEmpty();
    }

    public SeriesInterface<String> getHeaderLine() {
        return this.headerLine;
    }

    @Override
    public void setHeaderRow(SeriesInterface<String> headerRow) {
        this.headerLine = headerRow;
    }

    @Override
    public String printDataFrame() {
        StringBuilder print = new StringBuilder();
        if (this.headerLine != null){
            print.append(this.headerLine.printSeries());
        }

        for (SeriesInterface<E> row: this.df){
            print.append(row.printSeries());
        }

        // delete last end of line to avoid empty row
        print.deleteCharAt(print.length() - 1);
        return print.toString();
    }
}
