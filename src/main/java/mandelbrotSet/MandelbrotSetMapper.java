package mandelbrotSet;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MandelbrotSetMapper
	extends Mapper<LongWritable, Text, IntWritable, Text> {

	//Mapperの出力であり、Reducerでkeyとなるデータ
	//1 = マンデルブロ集合、 0 = マンデルブロ集合でない
	private IntWritable result = new IntWritable();

	@Override
	public void map(LongWritable key, Text values, Context context)
			throws IOException, InterruptedException {
		//ファイルから1行分データを取り出す
		String line = values.toString();
		String[] line2 = line.split(" ", 0);

		//取り出したデータを実部と虚部で別々に格納する
		//実部
		double before_x = Double.parseDouble(line2[0]);
		//虚部
		double before_y = Double.parseDouble(line2[1]);

		//計算で使う変数
		double after_x = 0;
		double after_y = 0;
		//実部
		double re = 0;
		//虚部
		double im = 0;
		//z = 実部 + 虚部
		double z = 0;

		result.set(1);

		for (int i = 0; i < 50; i++) {
			//マンデルブロ集合を計算
			re = after_x * after_x - (after_y * after_y) + before_x;
			im = 2 * after_x * after_y + before_y;
			z = re + im;
			//マンデルブロ集合でなかったらresult = 0
			if (z > 2.0 || z < -2.0) {
				result.set(0);
				break;
			}
			after_x = re;
			after_y = im;
		}

		//Mapperの出力
		context.write(result, values);
	}
}