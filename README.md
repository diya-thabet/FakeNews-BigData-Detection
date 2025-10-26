# Big Data Analysis: Fake News Detection with Hadoop & Spark

This project explores the Hadoop ecosystem by implementing a fake news detection analysis using different Big Data technologies. The goal was to compare the classic MapReduce paradigm with higher-level tools like Pig, Hive, and the more modern Apache Spark.

## Project Overview

We analyzed a dataset containing real and fake news articles to identify differences in language patterns, specifically word frequencies.

**Dataset:**
* "Fake and Real News Dataset" from Kaggle: [https://www.kaggle.com/datasets/clmentbisaillon/fake-and-real-news-dataset](https://www.kaggle.com/datasets/clmentbisaillon/fake-and-real-news-dataset)
    * Contains ~21k true articles and ~23k fake articles.

**Technologies Compared:**
1.  **Hadoop MapReduce (Java):** The foundational implementation.
2.  **Apache Pig (PigLatin):** A dataflow scripting approach.
3.  **Apache Hive (HiveQL):** A SQL-based approach for querying data on HDFS.
4.  **Apache Spark (PySpark/Scala):** Using RDDs for in-memory processing (run on Google Colab and the Cloudera VM).

## Key Task: Word Frequency Analysis

The core task was to perform a "WordCount" on the article text, grouped by category ('FAKE' vs 'TRUE'), to find the most commonly used words in each. This involved text cleaning (lowercase, removing punctuation) and filtering common stop words.

## Environment & Setup Challenges

This project was initially developed using an older Cloudera QuickStart VM (CentOS 6). Significant challenges were encountered due to the outdated OS and broken package manager (`yum`), requiring manual fixes for repositories and SSL certificates. The Spark part was also executed on Google Colab for a smoother experience. Check out the Project Report (PDF) for more details on overcoming these hurdles.

## Code Repository

All the code for the different implementations (Java MapReduce, Pig script, Hive queries, Spark script) can be found in this repository.

## Findings

The analysis revealed distinct lexical patterns between fake and real news articles. Fake news tended to heavily feature specific political figures and terms related to media, while real news (sourced largely from Reuters) often included institutional terms and cited the source itself.

---
*Feel free to adapt this, especially the "How to Run" section if you want to add specific instructions.*
