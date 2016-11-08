package mandelbrotSet;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

public class MandelbrotSetReducerTest {

	@Test
	public void test() throws IOException, InterruptedException {
		new ReduceDriver<IntWritable, Text, DoubleWritable, DoubleWritable>()
		.withReducer(new MandelbrotSetReducer())
		.withInputKey(new IntWritable(1))
		.withInputValues(Arrays.asList(new Text("-1.7499999999999998 0.010000000000000753")
				, new Text("-1.7499999999999928 0.010000000000000752")))
		.withOutput(new DoubleWritable(-1.7499999999999998), new DoubleWritable(0.010000000000000753))
		.withOutput(new DoubleWritable(-1.7499999999999928), new DoubleWritable(0.010000000000000752))
		.runTest();
	}
}
