import csv
import sys

# ✅ Fix OverflowError: safely set the CSV field size limit
max_int = sys.maxsize
while True:
    try:
        csv.field_size_limit(max_int)
        break
    except OverflowError:
        max_int = int(max_int / 10)

output_filename = 'articles.csv'
files_to_process = {
    'TRUE': 'True.csv',
    'FAKE': 'Fake.csv'
}

with open(output_filename, 'w', encoding='utf-8', newline='') as outfile:
    for label, filename in files_to_process.items():
        print(f"Processing {filename}...")
        try:
            with open(filename, 'r', encoding='utf-8') as infile:
                reader = csv.reader(infile)

                # Lire l'en-tête pour trouver l'index de la colonne 'text'
                header = next(reader)
                try:
                    text_index = header.index('text')
                except ValueError:
                    print(f"Error: 'text' column not found in {filename}")
                    continue

                # Traiter chaque ligne
                for row in reader:
                    if len(row) > text_index:
                        text = row[text_index].strip()
                        if text:
                            # Nettoyer les tabulations et retours à la ligne
                            cleaned_text = text.replace('\n', ' ').replace('\t', ' ')
                            outfile.write(f"{label}\t{cleaned_text}\n")

        except FileNotFoundError:
            print(f"Error: file {filename} not found.")
        except Exception as e:
            print(f"Error while processing {filename}: {e}")

print(f"✅ Done. Output saved to {output_filename}")
