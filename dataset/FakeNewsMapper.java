import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FakeNewsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split("\t");

        // Assurer que la ligne a bien un label ET un texte
        if (parts.length < 2) {
            return;
        }

        String label = parts[0];
        String text = parts[1];

        // Vérifier que le label est bien celui qu'on attend
        if (!label.equals("TRUE") && !label.equals("FAKE")) {
            return;
        }

        // 1. Nettoyage : Mettre en minuscule
        text = text.toLowerCase();
        
        // 2. Nettoyage : Enlever tout ce qui n'est pas une lettre ou un espace
        // Remplacer par un espace pour ne pas coller les mots (ex: "don't" -> "don t")
        text = text.replaceAll("[^a-zA-Z\\s]", " ");

        // 3. Tokenization (séparation en mots)
        StringTokenizer itr = new StringTokenizer(text);
        while (itr.hasMoreTokens()) {
            String token = itr.nextToken();

            // 4. Filtre (Stop words) : Ignorer les mots très courts
            // C'est un filtre simple pour enlever "a", "an", "is", "of", "in", etc.
            if (token.length() > 3) {
                // Créer la clé composite: "LABEL<tab>mot"
                word.set(label + "\t" + token);
                context.write(word, one);
            }
        }
    }
}