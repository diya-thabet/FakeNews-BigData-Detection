-- 1. CHARGER (LOAD)
-- Charger les données TSV depuis HDFS.
articles = LOAD '/user/cloudera/fake_news_project/articles.tsv' 
           USING PigStorage('\t') 
           AS (label:chararray, text:chararray);

-- 2. MAP (FOREACH ... GENERATE)
-- Pour chaque ligne, générer le label et les mots (tokens).
-- FLATTEN transforme une collection de mots en lignes séparées.
words = FOREACH articles GENERATE 
        label AS label, 
        FLATTEN(TOKENIZE(LOWER(REPLACE(text, '[^a-zA-Z\\s]', '')))) AS word;

-- 3. FILTRER
-- Enlever les mots courts (stop words simples).
filtered_words = FILTER words BY SIZE(word) > 3;

-- 4. GROUPER (Le "Shuffle" du MapReduce)
-- Regrouper par la clé composite (label, word).
grouped_words = GROUP filtered_words BY (label, word);

-- 5. REDUCE (FOREACH ... GENERATE)
-- Compter les occurrences pour chaque groupe.
word_counts = FOREACH grouped_words GENERATE 
              group.label AS label, 
              group.word AS word, 
              COUNT(filtered_words) AS count;

-- 6. ORDONNER
-- Trier les résultats par le compte (du plus grand au plus petit).
ordered_counts = ORDER word_counts BY count DESC;

-- 7. STOCKER (ou Afficher)
-- DUMP affiche le résultat à l'écran.
-- LIMIT 100 -- (Optionnel) pour ne voir que le top 100.
DUMP ordered_counts;

-- Optionnel : Sauvegarder le résultat sur HDFS au lieu de l'afficher
-- STORE ordered_counts INTO '/user/cloudera/fake_news_project/output_pig';