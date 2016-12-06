package mandelbrotSet;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MandelbrotSetReducer
	extends Reducer<IntWritable, Text, DoubleWritable, DoubleWritable> {
	private DoubleWritable reValue = new DoubleWritable();
	private DoubleWritable imValue = new DoubleWritable();

	@Override
	public void reduce(IntWritable key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		for(Text s : values) {
			//マンデルブロ集合のデータのみ処理
			if (key.get() == 1) {
				//データを取り出す
				String line = s.toString();
				String[] line2 = line.split(" ", 0);
				//データを実部と虚部で別々に格納する
				double re = Double.parseDouble(line2[0]);
				double im = Double.parseDouble(line2[1]);
				//Reducerの出力
				reValue.set(re);
				imValue.set(im);
				context.write(reValue, imValue);
			}
		}
	}
}